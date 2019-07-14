package elementar.analise.combinatoria.combinacao;


import android.util.Log;

import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.calculadoras.CalculadoraCombinacao;
import elementar.analise.combinatoria.geradores.GeradorFormulas;

public class GeradorCombinacao extends GeradorFormulas {

    private static int elementosMenosPosicoes;
    private static String fatorialElementos;


    private static Calculadora calculadoraGeral = Calculadora.getInstance();
    private static CalculadoraCombinacao calculadora = CalculadoraCombinacao.getInstance();

    public GeradorCombinacao() {

    }

    private static String gerarCabecalho(int elementos, int posicoes) {
        return "C(" + elementos + ", " + posicoes + ")";
    }

    // Retorna uma String LaTeX, aplicando os valores de entrada na fórmula
    public String gerarAplicacaoValoresCombinacao(int elementos, int posicoes) {
        String mensagem = "$$\\bold{\\text{Passo a Passo}}$$" +
                "Após a aplicação dos valores, obtemos:";

        String resultado = "$$" + gerarCabecalho(elementos, posicoes) + " = " + gerarFracaoAplicacao(elementos, posicoes) + "$$";

        return mensagem + resultado;
    }

    // Gera a parte fracionária, para aplicar os valores da entrada (em LaTeX)
    private String gerarFracaoAplicacao(int elementos, int posicoes) {
        return "\\frac{" + elementos + "!" + "}" + "{" + posicoes + "!" + " \\ " +
                "(" + calculadoraGeral.gerarElementosMenosPosicoes(elementos, posicoes) + ")" + "!" + "}";
    }


    // Gera a mensagem de simplicação a depender dos valores da entrada serem iguais
    private static String gerarMensagemSimplificacao(int valorElementos, int valorPosicoes) {
        String mensagem;

        if ((valorElementos == valorPosicoes) && (valorElementos != 0)) {
            mensagem = "Como (n-p) foi zero, restará apenas " + gerarFracaoInline(valorElementos, valorPosicoes, "!");

        } else {
            mensagem = "Como o valor do numerador e denominador são iguais a 0!, e 0! = 1. Isso resultará em 1:";
        }

        return  mensagem;
    }

    private static boolean verificarIgualdade(int elementosMenosPosicoes, int posicoes) {
        return elementosMenosPosicoes == posicoes;
    }

    private static String gerarFracaoCifrao(int valorElementos, int valorPosicoes, String exclamacao) {
        return "\\frac{" + valorElementos + exclamacao + "}" + "{" + valorPosicoes + exclamacao + " \\ " + "{(" + (valorElementos-valorPosicoes) + ")" + exclamacao + "}";
    }

//    private static String gerarDesenvolvimento(int valorElementos, int valorPosicoes, String valorNumerador, int elementosMenosPosicoes) {
//        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " +
//    }
//
//    private static String gerarFatorialElementos(int elementos, int posicoes) {
//
//        // Número de elementos a arranjar maior que zero, é necessário desenvolver o fatorial
//        if (elementos > 0) {
//
//            int fim;
//
//            if ()
//
//        }
//    }

    // Gera todo o cálculo no formato LaTeX
    public static String gerarPassoAPasso(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = calculadoraGeral.resultadoElementosMenosPosicoes(valorElementos, valorPosicoes);

        fatorialElementos = calculadora.gerarFatorialElementos(valorElementos, valorPosicoes);

        String mensagemDesenvolvimento , mensagemSimplificacao;
        String numeradorDesenvolvimento, denominadorDesenvolvimento, elementosMenosPosicoesDesenvolvimento;
        long resultadoFinal;

        if (valorElementos == valorPosicoes) {
            mensagemDesenvolvimento = "Como o nº de elementos = nº de posições, o valor de (n-p) será igual a zero";

            mensagemSimplificacao = gerarMensagemSimplificacao(valorElementos, valorPosicoes);


        }

//        String desenvolvimentoLatex = gerarDesenvolvimento(valorElementos, valorPosicoes, numeradorDesenvolvimento, denominadorDesenvolvimento, elementosMenosPosicoesDesenvolvimento);


        return "PASSO A PASSO AQUI";
    }


    private static String gerarDesenvolvimento(int valorElementos, int valorPosicoes, String numeradorDesenvolvimento, String posicoesDesenvolvimento, String elementosMenosPosicoesDesenvolvimento) {
        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorElementos, valorPosicoes, "!");

        return "DESENVOLVIMENTO AQUI";
    }

//    public String gerarSimplificacaoFatorial(int valorElementos, int valorPosicoes) {
//        String resultado;
//        String ultimoValorNumerador = fatorialElementos;
//        String simplificacao;
//
//        // Elementos = Posições, sempre resulta em 1
//        if (valorElementos == valorPosicoes) {
//            resultado = "1";
//
//        } else if ()
//
//        simplificacao = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + "";
//
//        return simplificacao;
//    }


    // Remove o último valor do numerador para plotar o valor após a simplificação com o denominador
    public static String removerUltimoValor(String fatorialNumerador) {

        String[] valores = fatorialNumerador.replace(".", ";").split(";");

        // Pega o primeiro valor da String (índice zero)
        int indexZero = Integer.parseInt(valores[0]);

        if (valores.length > 1) {

            // Valor a ser removido, fica no último índice do vetor
            String valorARemover = valores[valores.length-1];

            int tamanhoUltimo = valorARemover.length();
            int tamanhoFatorial = fatorialNumerador.length();

            /*Removendo o último valor, e o ponto final excedente
             Ex.: Remover o valor 10 e o ponto final -> 11.10
             Dez possui tamanho dois, e então decrementando dois índices da substring,
             o 10 é removido, mas o ponto final ainda fica. Para remover o ponto final,
             decrementa-se +1 índice da String. RESULTADO = 11
             */

            fatorialNumerador = fatorialNumerador.substring(0, (tamanhoFatorial-tamanhoUltimo)-1);

            Log.i("RESULTADO FAT NUMERADOR", fatorialNumerador);

            return fatorialNumerador;

        } else {

            Log.i("RESULTADO FAT NUMERADOR", fatorialNumerador);

            // Apenas o valor sem fatorial, não é necessário remover nada
            return fatorialNumerador;
        }
    }


    // Gera o resultado passo a passo em LaTeX
    public String gerarResultadoCombinacao(int valorElementos, int valorPosicoes) {
        return gerarAplicacaoValoresCombinacao(valorElementos, valorPosicoes);
    }
}
