package elementar.analise.combinatoria;

import android.support.design.widget.TextInputLayout;
import android.util.Log;

import elementar.analise.combinatoria.Fragments.Arranjo;

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

    public static long gerarResultadoCalculo() {

        String valoresFinais = Calculadora.gerarFatorialElementos();
        valoresFinais = GeradorFormulas.removerUltimoValor(valoresFinais);

        Log.i("FINAIS", valoresFinais);

        if (!valoresFinais.equals("0") && !valoresFinais.equals("1") && !valoresFinais.equals("2")) {
            valoresFinais = valoresFinais.replace(".", ";");

            long resultado = 1;
            String[] valores = valoresFinais.split(";");

            for (int i = 0; i < valores.length; i++) {
                resultado *= Integer.parseInt(valores[i]);
                //Log.i("FOR", Integer.toString(resultado));;
            }

            //Log.i("PRIMEIRO IF", Integer.toString(resultado));
            return resultado;

        } else if (valoresFinais.equals("0")){
            Log.i("SEGUNDO IF", "1");
            return 1;

        } else {
            Log.i("TERCEIRO IF ELSE", valoresFinais);
            return Integer.parseInt(valoresFinais);
        }

    }

    // Gera o fatorial dos elementos a Arranjar, para ser usado no MathView. Exemplo: 4! = 4.3.2.1
    public static String gerarFatorialElementos() {

        String n = Arranjo.getNumeroElementos();
        String p = Arranjo.getNumeroPosicoes();

        elementos = Integer.parseInt(n);
        posicoes = Integer.parseInt(p);

        // removido && elementos > 1
        if (elementos > 0) {

            int fim;
            if (elementos == posicoes) {
                fim = 1;

            } else if (posicoes == 0 || elementos == 1 || elementos == 2){
                fim = 1;

            } else {
                fim = elementos - posicoes;
            }


            StringBuilder numerador = new StringBuilder();

            // Gera os valores do numerador até o menor valor possível para simplificar
            for (int e = elementos; e >= fim; e--) {
                numerador.append(Integer.toString(e));
                numerador.append(".");
            }

            // Remove o último caracter em excesso (um ponto final)
            numerador.delete(numerador.length()-1, numerador.length());

            Log.i("GERAR", numerador.toString());
            return numerador.toString();

        } else if (elementos == 0){
            Log.i("ZERO", "zero;");
            return "0";

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

}