package elementar.analise.combinatoria.permutacao;


import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.geradores.GeradorFormulas;

public class GeradorPermutacao extends GeradorFormulas {

    private int elementosMenosPosicoes;
    private String fatorialElementos;

    private static Calculadora calculadora = Calculadora.getInstance();


    public GeradorPermutacao() {

    }

    private String gerarCabecalho(int elementos, int posicoes) {
        return "P(" + elementos + ", " + posicoes + ")";
    }

    //Gera a primeira equação, após aplicar os valores N e P
    private String gerarAplicacaoPermutacao(int elementos, int posicoes) {
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

    // Gera o desenvolvimento da Permutacao
    private  String gerarDesenvolvimento(int valorElementos, int valorPosicoes, String valorNumerador, int elementosMenosPosicoes) {
        return "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorNumerador, elementosMenosPosicoes) + "$$";
    }

    // Gera o resultado final da permutação
    public long getResultadoFinalPermutacao(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = valorElementos - valorPosicoes;

        // Nas condições abaixo, é necessário realizar cálculos
        if ((valorElementos == valorPosicoes) || (valorElementos != elementosMenosPosicoes)) {
            return Calculadora.gerarResultadoCalculoFatorial(valorElementos, valorPosicoes);

        }

        // Nº de elementos igual ao resultado de (n-p)!, sempre resultará 1
        return 1;
    }



    /*
    Seção de métodos para imprimir mensagens de desenvolvimento
    */

    // Primeira mensagem na condição (elementos == posições)
    private String gerarMensagemDesenvolvimento1(int valorElementos, int elementosMenosPosicoes) {
        return "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";
    }

    // Segunda mensagem na condição (elementos != elementosMenosPosicoes)
    private String gerarMensagemDesenvolvimento2() {
        return "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:";
    }

    private String gerarMensagemDesenvolvimento3(int valorElementos, int elementosMenosPosicoes) {
        return "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";
    }


    /*
    Seção de métodos para imprimir mensagens de simplificação
    */

    // Primeira mensagem de simplicação a depender dos valores da entrada serem iguais
    private String gerarMensagemSimplificacao1(int valorElementos, int valorPosicoes) {
        String mensagem;

        if (valorElementos == valorPosicoes && valorElementos != 0) {
            mensagem = "Como o valor do numerador e denominador é 0!, e 0! = 1, basta resolver o " +
                    valorElementos + "!" + " do numerador";

        } else {
            mensagem = "Como o valor do numerador e denominador são iguais a 0!, e 0! = 1. Isso resulta em " +
                    gerarFracaoInline(valorElementos, valorPosicoes, "") + " = 1";
        }

        return  mensagem;
    }

    // Segunda mensagem de simplificação para nº de elementos diferente de (n-p)
    private String gerarMensagemSimplificacao2(int elementosMenosPosicoes) {
        return "Simplificando o " + elementosMenosPosicoes +
                "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";
    }

    // Terceira a mensagem de simplificação para nº de elementos igual a (n-p)!
    private String gerarMensagemSimplificacao3(int valorElementos, int elementosMenosPosicoes) {
        return "Simplificando " + valorElementos + "!" + " do numerador com " +
                elementosMenosPosicoes + "!" + " do denominador, resulta-se em 1";
    }


    //Gera todo o cálculo no formato LaTeX
    private String gerarPassoAPasso(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = valorElementos - valorPosicoes;

        String mensagemDesenvolvimento, mensagemSimplificacao;
        String numeradorDesenvolvimento;

        fatorialElementos = calculadora.gerarFatorialElementos(valorElementos, valorPosicoes);

        // Elementos = Posições, o valor do denominador será zero. Basta fazer o cálculo do numerador (no Permutacao Simples)
        if (valorElementos == valorPosicoes) {
            mensagemDesenvolvimento = gerarMensagemDesenvolvimento1(valorElementos, elementosMenosPosicoes);

            mensagemSimplificacao = gerarMensagemSimplificacao1(valorElementos, valorPosicoes);

            numeradorDesenvolvimento = Integer.toString(valorElementos);


        // Valores distintos, é necessário desenvolver o fatorial (no Permutacao Simples)
        } else if (valorElementos != elementosMenosPosicoes) {
            mensagemDesenvolvimento = gerarMensagemDesenvolvimento2();

            mensagemSimplificacao = gerarMensagemSimplificacao2(elementosMenosPosicoes);

            numeradorDesenvolvimento = fatorialElementos;


        // Nº de elementos igual ao resultado de (n-p)!, sempre resultará 1 (no Permutacao Simples)
        } else {
            mensagemDesenvolvimento = gerarMensagemDesenvolvimento3(valorElementos, elementosMenosPosicoes);

            mensagemSimplificacao = gerarMensagemSimplificacao3(valorElementos, elementosMenosPosicoes);

            numeradorDesenvolvimento = Integer.toString(valorElementos);
        }


        String desenvolvimentoLatex = gerarDesenvolvimento(valorElementos, valorPosicoes, numeradorDesenvolvimento, elementosMenosPosicoes);

        String simplificacaoLatex = gerarSimplificacaoFatorial(valorElementos, valorPosicoes);

        long resultadoFinal = getResultadoFinalPermutacao(valorElementos, valorPosicoes);

        String resultadoFinalLatex = calculadora.gerarResultadoFinal("P", valorElementos, valorPosicoes, resultadoFinal);


        return  mensagemDesenvolvimento + desenvolvimentoLatex +
                mensagemSimplificacao + simplificacaoLatex + resultadoFinalLatex;
    }


    public String gerarResultadoPermutacao(int valorElementos, int valorPosicoes) {
        return gerarAplicacaoPermutacao(valorElementos, valorPosicoes) + gerarPassoAPasso(valorElementos, valorPosicoes);
    }


}