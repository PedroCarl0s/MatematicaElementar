package elementar.analise.combinatoria;

import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.fragments.Arranjo;
import elementar.analise.combinatoria.fragments.Permutacao;
import elementar.analise.combinatoria.latex.GeradorFormulas;

public class Calculadora {

    private static int elementos, posicoes;

    // Verifica se a entrada está vazia
    private static boolean validarCampoVazio(TextInputLayout inputText) {
        String testeInput = inputText.getEditText().getText().toString();
        return testeInput.isEmpty();
    }

    // Verifica a entrada, para saber se alguma condição está violada
    public static boolean validarEntradas(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {

        boolean elementosVazio = validarCampoVazio(inputElementos);
        boolean posicoesVazio = validarCampoVazio(inputPosicoes);

        // Campos de elemento e posição preenchidos
        if (!elementosVazio && !posicoesVazio) {
            inputElementos.setError(null);
            inputPosicoes.setError(null);


            // Tentativa de conversão de String para Inteiro, e caso o valor exceda 32 bits, ocorrerá uma exceção
            try {
                elementos = Integer.parseInt(Arranjo.getNumeroElementos());

            } catch (Exception error) {
                inputElementos.setError("O valor digitado é muito grande!");

                return false;
            }

            try {
                posicoes = Integer.parseInt(Arranjo.getNumeroPosicoes());

            } catch (Exception error) {
                inputPosicoes.setError("O valor digitado é muito grande!");

                return false;
            }

            // Condição necessária para realizar cálculo de arranjo A(n, p), onde n >= p
            if (elementos >= posicoes) {
                inputElementos.setError(null);

                return true;
            }
            inputElementos.setError("O número de elementos deve ser ≥ posições");
            inputElementos.requestFocus();

            return false;

        } else if (!elementosVazio && posicoesVazio){
            inputPosicoes.setError("O número de posições não pode ser vazio!");
            inputPosicoes.requestFocus();
            inputElementos.setError(null);

            return false;

        } else {
           inputElementos.setError("O número de elementos não pode ser vazio!");
           inputElementos.requestFocus();
           inputPosicoes.setError(null);

           return false;

        }

    }

    // Verifica se o campo da Permutação é válido
    public static boolean validarEntradaPermutacao(TextInputLayout inputPermutacao) {
        boolean entradaVazia = validarCampoVazio(inputPermutacao);
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

    public static long gerarResultadoCalculo() {

        String valoresFinais = Calculadora.gerarFatorialElementos();
        valoresFinais = GeradorFormulas.removerUltimoValor(valoresFinais);


        if (!valoresFinais.equals("0") && !valoresFinais.equals("1") && !valoresFinais.equals("2")) {
            valoresFinais = valoresFinais.replace(".", ";");

            long resultado = 1;
            String[] valores = valoresFinais.split(";");

            for (String atual : valores) {
                resultado *= Integer.parseInt(atual);
            }

            return resultado;

        } else if (valoresFinais.equals("0")){
            return 1;

        } else {
            return Integer.parseInt(valoresFinais);
        }

    }

    // Gera o fatorial dos elementos a Arranjar, para ser usado no MathView. Exemplo: 4! = 4.3.2.1
    public static String gerarFatorialElementos() {

        String n = Arranjo.getNumeroElementos();
        String p = Arranjo.getNumeroPosicoes();

        elementos = Integer.parseInt(n);
        posicoes = Integer.parseInt(p);

        // Número de elementos a arranjar maior que zero, é necessário desenvolver o fatorial
        if (elementos > 0) {

            int fim;

            // Nº de elementos igual ao nº de posições, o resultado será um
            if (elementos == posicoes) {
                fim = 1;

            // nº de posições sendo zero, um ou dois temos que desenvolver até o valor um
            } else if (posicoes == 0 || elementos == 1 || elementos == 2){
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
    public static String gerarElementosMenosPosicoes() {
        return Arranjo.getNumeroElementos() + "-" + Arranjo.getNumeroPosicoes();
    }

    // Gera o resultado da subtração de (n-p) para ser usado na String do LaTeX
    public static String resultadoElementosMenosPosicoes() {
        int resultado =  Integer.parseInt(Arranjo.getNumeroElementos()) - Integer.parseInt(Arranjo.getNumeroPosicoes());

        return Integer.toString(resultado);
    }

    // Gera o resultado da Permutação
    public static String gerarDesenvolvimentoPermutacao() {

        String n = Permutacao.getEntradaPermutacao();
        int permutacao = Integer.parseInt(n);

        if (permutacao > 0) {

            StringBuilder valores = new StringBuilder();
            for (int atual = permutacao; atual >= 1; atual--) {
                valores.append(atual);
                valores.append(".");
            }

            // Remove o último caracter em excesso (um ponto final)
            valores.delete(valores.length()-1, valores.length());

            return valores.toString();

        } else if (permutacao == 0) {
            return "1";

        } else {
            return "Insira apenas valores positivos!";
        }
    }

    public static String gerarResultadoPermutacao() {
        int valorEntrada = Integer.parseInt(Permutacao.getEntradaPermutacao());

        // O fatorial vai até o valor 1
        int resultado = 1;

        // Multiplica até o valor 2 (removi o 1, porque 1 vezes outro valor, dá ele mesmo). Exemplo: 5! = 5 * 4 * 3 * 2
        for (int atual = valorEntrada; atual >= 2; atual--) {
            resultado *= atual;
        }

        return Integer.toString(resultado);
    }

}