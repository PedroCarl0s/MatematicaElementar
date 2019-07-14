package elementar.analise.combinatoria.geradores;


import android.util.Log;

import elementar.analise.combinatoria.Calculadora;

public class GeradorPermutacao extends GeradorFormulas {

    private static int elementosMenosPosicoes;
    private static String fatorialElementos;

    private static Calculadora calculadora = Calculadora.getInstance();


    public GeradorPermutacao() {

    }

    private static String gerarCabecalho(int elementos, int posicoes) {
        return "P(" + elementos + ", " + posicoes + ")";
    }

    //Gera a primeira equação, após aplicar os valores N e P
    private static String gerarAplicacaoPermutacao(int elementos, int posicoes) {
        String resultado;

        String mensagem = "Após a aplicação dos valores, obtemos:";

        resultado = "$$" + gerarCabecalho(elementos, posicoes) + " = " +"\\frac{" + elementos + "!} " +
                "{(" + calculadora.gerarElementosMenosPosicoes(elementos, posicoes) + ")!}$$";


        return mensagem + resultado;
    }

    // Gera a simplificação do fatorial (numerador e denominador)
    private String gerarSimplificacaoFatorial(int valorElementos, int valorPosicoes) {
        String ultimoValorNumerador = fatorialElementos;
        String simplificacao;

        // Elementos = Posições, o valor do denominador será zero. Basta fazer o cálculo do numerador
        if (valorElementos == valorPosicoes) {

        } else if (valorElementos != elementosMenosPosicoes) {
            ultimoValorNumerador = removerUltimoValor(ultimoValorNumerador);

        } else {
            ultimoValorNumerador = "1";
        }

        simplificacao = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + ultimoValorNumerador + "$$";

        return simplificacao;
    }


    private String gerarFracaoCifrao(String valorNumerador, int elementosMenosPosicoes) {
        return "\\frac{" + valorNumerador + "!" + "}" + "{" + elementosMenosPosicoes + "!" + "}";
    }

    // Gera o desenvolvimento do Permutacao
    private  String gerarDesenvolvimento(int valorElementos, int valorPosicoes, String valorNumerador, int elementosMenosPosicoes) {
        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorNumerador, elementosMenosPosicoes) + "$$";

        return desenvolvimento;
    }


    // Gera o resultado final da permutação
    public long getResultadoFinalPermutacao(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = valorElementos - valorPosicoes;
        long resultadoFinal;

        // Elementos = Posições, o valor do denominador será zero. Basta gerar o fatorial do numerador
        if (valorElementos == valorPosicoes) {
            return resultadoFinal = calculadora.gerarResultadoCalculoFatorial(valorElementos, valorPosicoes);

        // Valores distintos, é necessário desenvolver os fatoriais
        } else if (valorElementos != elementosMenosPosicoes) {
            return resultadoFinal = calculadora.gerarResultadoCalculoFatorial(valorElementos, valorPosicoes);

        }

        // Nº de elementos igual ao resultado de (n-p)!, sempre resultará 1
        return 1;
    }
    
    //Gera todo o cálculo no formato LaTeX
    private String gerarPassoAPasso(int valorElementos, int valorPosicoes) {

        String mensagemDesenvolvimento, mensagemSimplificacao;
        String numeradorDesenvolvimento;
        long resultadoFinal;

        resultadoFinal = getResultadoFinalPermutacao(valorElementos, valorPosicoes);

        fatorialElementos = calculadora.gerarFatorialElementos(valorElementos, valorPosicoes);

        // Elementos = Posições, o valor do denominador será zero. Basta fazer o cálculo do numerador (no Permutacao Simples)
        if (valorElementos == valorPosicoes) {
            mensagemDesenvolvimento = "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                    "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";

            mensagemSimplificacao = gerarMensagemSimplificacao(valorElementos, valorPosicoes);

            numeradorDesenvolvimento = Integer.toString(valorElementos);


        // Valores distintos, é necessário desenvolver o fatorial (no Permutacao Simples)
        } else if (valorElementos != elementosMenosPosicoes) {
            mensagemDesenvolvimento = "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:";

            mensagemSimplificacao = "Simplificando o " + elementosMenosPosicoes +
                    "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";

            numeradorDesenvolvimento = fatorialElementos;


        // Nº de elementos igual ao resultado de (n-p)!, sempre resultará 1 (no Permutacao Simples)
        } else {
            mensagemDesenvolvimento = "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                    "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";

            mensagemSimplificacao = "Simplificando " + valorElementos + "!" + " do numerador com " +
                    elementosMenosPosicoes + "!" + " do denominador, resulta-se em 1";

            numeradorDesenvolvimento = Integer.toString(valorElementos);

        }

        String desenvolvimentoLatex = gerarDesenvolvimento(valorElementos, valorPosicoes, numeradorDesenvolvimento, elementosMenosPosicoes);
        String simplificacaoLatex = gerarSimplificacaoFatorial(valorElementos, valorPosicoes);
        String resultadoFinalLatex = calculadora.gerarResultadoFinal("P", valorElementos, valorPosicoes, resultadoFinal);


        return  mensagemDesenvolvimento + desenvolvimentoLatex +
                mensagemSimplificacao + simplificacaoLatex + resultadoFinalLatex;
    }

    // Gera a mensagem de simplicação a depender dos valores da entrada serem iguais
    private String gerarMensagemSimplificacao(int valorElementos, int valorPosicoes) {
        String mensagem;

        if (valorElementos == valorPosicoes && valorElementos != 0) {
            mensagem = "Como o valor do numerador e denominador é 0!, e 0! = 1, basta resolver o " +
                    valorElementos + "!" + " do numerador";

        } else {
            mensagem = "Como o valor do numerador e denominador são iguais a 0!, e 0! = 1. Isso resulta em " +
                    gerarFracaoInline(valorElementos, valorPosicoes, "");
        }

        return  mensagem;
    }

    public String gerarResultadoPermutacao(int valorElementos, int valorPosicoes) {
        return gerarAplicacaoPermutacao(valorElementos, valorPosicoes) + gerarPassoAPasso(valorElementos, valorPosicoes);
    }


}