package elementar.analise.combinatoria;



import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.fragments.Permutacao;
import elementar.analise.combinatoria.geradores.GeradorFormulas;

public final class Calculadora extends GeradorFormulas{

    private static Calculadora INSTANCE = null;
    private static int elementos, posicoes;
    private static String RETICENCIAS = " \\ ... \\ ";

    private Calculadora() {

    }

    public static synchronized Calculadora getInstance() {
        if (INSTANCE == null) {
            INSTANCE  = new Calculadora();
        }
        return INSTANCE;
    }

    // Verifica se a entrada está vazia
    public boolean verificarCampoVazio(TextInputLayout inputText) {
        String testeInput = inputText.getEditText().getText().toString();
        return testeInput.isEmpty();
    }

    // Verifica a entrada, para saber se alguma condição está violada
    public boolean validarElementosEPosicoes(int elementosEntrada, int posicoesEntrada, TextInputLayout inputElementos, TextInputLayout inputPosicoes) {

        boolean elementosVazio = verificarCampoVazio(inputElementos);
        boolean posicoesVazio = verificarCampoVazio(inputPosicoes);

        // Campos de elemento e posição preenchidos
        if (!elementosVazio && !posicoesVazio) {
            inputElementos.setError(null);
            inputPosicoes.setError(null);


            // Tentativa de atribuição para Inteiro, e caso o valor exceda 32 bits, ocorrerá uma exceção
            try {
                elementos = elementosEntrada;

            } catch (Exception error) {
                inputElementos.setError("Valor muito grande!");

                return false;
            }

            try {
                posicoes = posicoesEntrada;

            } catch (Exception error) {
                inputPosicoes.setError("Valor muito grande!");

                return false;
            }

            // Condição necessária para realizar cálculo de arranjo A(n, p) e combinação C(n, p), onde n >= p
            if (elementos >= posicoes) {
                inputElementos.setError(null);

                return true;
            }

            inputElementos.setError("Valor de n ≥ Valor de p");
            inputElementos.requestFocus();

            return false;

        } else if (!elementosVazio && posicoesVazio){
            inputPosicoes.setError("Não pode ser vazio!");
            inputPosicoes.requestFocus();
            inputElementos.setError(null);

            return false;

        } else {
           inputElementos.setError("Não pode ser vazio!");
           inputElementos.requestFocus();
           inputPosicoes.setError(null);

           return false;

        }

    }

    // Verifica se o campo da Permutação é válido
    public boolean validarEntradaPermutacao(TextInputLayout inputPermutacao) {
        boolean entradaVazia = verificarCampoVazio(inputPermutacao);
        int teste;

        if (!entradaVazia) {
            inputPermutacao.setError(null);

            try {
                teste = Integer.parseInt(Permutacao.getEntradaPermutacao());

                return true;

            } catch (Exception e) {
                inputPermutacao.setError("O valor digitado é muito grande!");

                return false;
            }

        } else {
            inputPermutacao.setError("O valor de N não pode ser vazio!");

             return false;
        }

    }

    public static long gerarResultadoCalculoPermutacao(int elementos, int posicoes) {

        String valoresFinais = gerarFatorialElementos(elementos, posicoes);
        valoresFinais = GeradorFormulas.removerUltimoValor(valoresFinais);


        if (!valoresFinais.equals("0") && !valoresFinais.equals("1") && !valoresFinais.equals("2")) {

            // Troca o ponto finais por ponto e vírgula, para depois separar com .split
            valoresFinais = valoresFinais.replace(".", ";");

            long resultado = 1;
            String[] valores = valoresFinais.split(";");

            for (String atual : valores) {
                resultado *= Integer.parseInt(atual);
            }

            return resultado;

        } else if (valoresFinais.equals("0")) {
            return 1;

        } else {
            return Long.parseLong(valoresFinais);
        }

    }


    // Gera o fatorial dos elementos a Arranjar, para ser usado no MathView. Exemplo: 4! = 4.3.2.1
    public String gerarFatorialElementos(int elementos, int posicoes) {

        // Número de elementos a arranjar maior que zero, é necessário desenvolver o fatorial
        if (elementos > 0) {

            int fim;

            // Nas condições abaixo, é necessário desenvolver até 1
            if (elementos == posicoes || posicoes == 0 || elementos <= 2) {
                fim = 1;

            // O fatorial do nº de elementos irá até o "valor normal" da fórmula (n-p)
            } else {
                fim = elementos - posicoes;
            }

            StringBuilder numerador = new StringBuilder();

            // Gera os valores do numerador até o menor valor possível para simplificar
            for (int e = elementos; e >= fim; e--) {
                numerador.append(e);
                numerador.append(".");
            }

            // Remove o último caracter em excesso (um ponto final)
            numerador.delete(numerador.length()-1, numerador.length());

            return numerador.toString();

        // 0! = 1
        } else if (elementos == 0){
            return "1";

        } else {
            return "não suporta valores negativos!";
        }

    }

    // Gera (n-p) da fórmula para ser usado na String do LaTeX
    public String gerarElementosMenosPosicoes(int numeroElementos, int numeroPosicoes) {
        return numeroElementos + "-" + numeroPosicoes;
    }

    // Gera o resultado da subtração de (n-p) para ser usado na String do LaTeX
    public int resultadoElementosMenosPosicoes(int valorElementos, int valorPosicoes) {
       return valorElementos - valorPosicoes;
    }

    // Gera o resultado da Permutação
    public String gerarDesenvolvimentoPermutacao(long valorPermutacao) {

        if (valorPermutacao > 0) {

            StringBuilder valores = new StringBuilder();

            if(valorPermutacao < 16){

                for (long atual = valorPermutacao; atual >= 1; atual--) {
                    valores.append(atual);
                    valores.append(".");
                }

                // Remove o último caracter em excesso (um ponto final)
                valores.delete(valores.length()-1, valores.length());

            } else {

                StringBuilder novoTexto = gerarValorAntesDoSeparador(valorPermutacao);

                valores.append(gerarValorDepoisDoSeparador(novoTexto));
            }

            return valores.toString();


        } else if (valorPermutacao == 0) {

            return "1";

        } else {
            return "Insira apenas valores positivos!";
        }
    }

    public long gerarResultadoPermutacao(long valorEntrada) {

        // O fatorial vai até o valor 1
        long resultado = 1;

        // Multiplica até o valor 2 (removi o 1, porque 1 vezes outro valor, dá ele mesmo). Exemplo: 3! = 3 * 2 * 1 equivale a 3 * 2
        for (long atual = valorEntrada; atual >= 2; atual--) {
            resultado *= atual;
        }

        return resultado;
    }

    private StringBuilder gerarValorAntesDoSeparador(Long valorPermutacao){

        StringBuilder textoParcial = new StringBuilder();

        for (long atual = valorPermutacao; atual >= (valorPermutacao - 5); atual--) {
            textoParcial.append(atual);
            textoParcial.append(".");
        }

        textoParcial.delete(textoParcial.length()-1,textoParcial.length());

        textoParcial.append(RETICENCIAS);

        return textoParcial;

    }

    private StringBuilder gerarValorDepoisDoSeparador(StringBuilder textoParcial){

        for (long atual = 3; atual > 0; atual--) {

            textoParcial.append(atual);
            textoParcial.append(".");
        }

        textoParcial.delete(textoParcial.length()-1,textoParcial.length());

        return textoParcial;
    }

}