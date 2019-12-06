package elementar.analise.combinatoria.combinacao;


import android.util.Log;

import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.calculadoras.CalculadoraCombinacao;
import elementar.analise.combinatoria.geradores.GeradorFormulas;



public class GeradorCombinacao extends GeradorFormulas {

    private int elementosMenosPosicoes;
    private static String fatorialElementos;
    private int maior, auxSecundario;



    private static Calculadora calculadoraGeral = Calculadora.getInstance();
    private static CalculadoraCombinacao calculadoraEspecifica = CalculadoraCombinacao.getInstance();

    public GeradorCombinacao() {

    }

    private String gerarCabecalho(int elementos, int posicoes) {
        return "C(" + elementos + ", " + posicoes + ")";
    }

    private String gerarMensagemAplicacaoValores() {
        String mensagem = "$$\\bold{\\text{Passo a Passo}}$$" +
                "Após a aplicação dos valores, obtemos:";

        return  mensagem;
    }

    // Retorna uma String LaTeX, aplicando os valores de entrada na fórmula
    public String gerarAplicacaoValoresCombinacao(int elementos, int posicoes) {

        String resultado = "$$" + gerarCabecalho(elementos, posicoes) + " = " +
                gerarFracaoAplicacao(Integer.toString(elementos), Integer.toString(posicoes), calculadoraGeral.gerarElementosMenosPosicoes(elementos, posicoes), "(", ")") + "$$";

        return resultado;
    }

    // Gera a parte fracionária, para aplicar os valores da entrada (em LaTeX)
    private String gerarFracaoAplicacao(String elementos, String posicoes, String elementosMenosPosicoes, String parenteses1, String parenteses2) {
        return "\\frac{" + elementos + "!" + "}" + "{" + posicoes + "!" + " \\ " +
                parenteses1 + elementosMenosPosicoes + parenteses2 + "!" + "}";
    }
//
//    private String gerarFracaoSimplificacao1(int valorElementos, int valorPosicoes, int elementosMenosPosicoes) {
//
//        int maior = calculadoraEspecifica.getMaiorDenominador(valorPosicoes, elementosMenosPosicoes);
//
//        String fracao = "$$" + gerarCabecalho(valorElementos, valorPosicoes)
//
//    }

    // Gera a parte fracionária, quando p == (n-p), onde p = 1
    private String gerarFracaoSimplificacao(int elementos, int elementosMenosPosicoes) {
        return "\\frac{" + elementos + "!" + "}" + "{" + elementosMenosPosicoes + "!" + "}";
    }


    // Gera a mensagem de simplicação a depender dos valores da entrada serem iguais
    private String gerarMensagemSimplificacao(int valorElementos, int valorPosicoes) {
        String mensagem;

        if ((valorElementos == valorPosicoes) && (valorElementos != 0)) {
            mensagem = "Como (n-p)! = 0! = 1, restará apenas " + gerarFracaoInline(valorElementos, valorPosicoes, "!") + " = 1";

        } else {
            mensagem = "Como o valor do numerador e denominador são iguais a 0!, e 0! = 1. Isso resultará em 1:";
        }

        return  mensagem;
    }

    public boolean verificarIgualdade(int posicoes, int elementosMenosPosicoes) {
        return posicoes == elementosMenosPosicoes;
    }

    private String gerarFracaoInlineSimplificacao(String valorElementos, String valorAux, String exclamacao) {

        return "\\frac{" + valorElementos + exclamacao + "}" +  "{" + valorAux + exclamacao + "}";
    }

    private String gerarFracaoCifrao(int valorElementos, int valorPosicoes, String exclamacao) {

        int elementosMenosPosicoes = valorElementos - valorPosicoes;

        return "\\frac{" + valorElementos + exclamacao + "}" + "{" + valorPosicoes + exclamacao + " \\ " + "{(" + elementosMenosPosicoes + ")" + exclamacao + "}";
    }

    // Gera o desenvolvimento LaTeX após realizar valorElementos - valorPosicoes
    private String gerarDesenvolvimentoLatex(int valorElementos, int valorPosicoes, String exclamacao, String delimitador) {

        String desenvolvimento = delimitador + gerarCabecalho(valorElementos, valorPosicoes) + " = " + "\\frac{" + valorElementos + exclamacao + "}" +
                "{" + valorPosicoes + exclamacao + " \\ " + elementosMenosPosicoes + exclamacao + "}" + delimitador;


        return desenvolvimento;
    }


    // Método que seta o maior valor e o secundário, para ser usado na simplificação de Combinação
    private void setMaiorValorESecundario(int valorElementos, int valorPosicoes) {

        int elementosMenosPosicoes = valorElementos - valorPosicoes;

        if (verificarIgualdade(elementosMenosPosicoes, valorPosicoes)) {
            this.maior = elementosMenosPosicoes;

            this.auxSecundario = valorPosicoes;

        } else {
            this.maior = calculadoraEspecifica.getMaiorDenominador(valorPosicoes, elementosMenosPosicoes);
            this.auxSecundario = calculadoraEspecifica.getMenorDenominador(valorPosicoes, elementosMenosPosicoes);
        }

        Log.i("MAIOR ##: ", Integer.toString(maior));
        Log.i("AUX SEC ##: ", Integer.toString(auxSecundario));

    }


    // Retorna o desenvolvimento do fatorial do numerador, para ser usado nas simplificações
    private String getDesenvolvimentoNumerador(int valorElementos, int valorPosicoes) {

        setMaiorValorESecundario(valorElementos, valorPosicoes);

        String desenvolvimentoNumerador = calculadoraGeral.gerarFatorialElementos(valorElementos, maior);

        return desenvolvimentoNumerador;
    }


    /*Seção dos geradores para simplificar a combinação*/

    private String gerarSimplificacaoParte1(int valorElementos, int valorPosicoes, int elementosMenosPosicoes) {


        String desenvolvimentoNumerador = getDesenvolvimentoNumerador(valorElementos, valorPosicoes);

        String simplificacao = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " +
                gerarFracaoAplicacao(desenvolvimentoNumerador, Integer.toString(valorPosicoes), Integer.toString(elementosMenosPosicoes), "", "") +
                "$$";

        return simplificacao;
    }

    private String gerarSimplificacaoParte2(int valorElementos, int valorPosicoes) {

        String desenvolvimentoNumerador = getDesenvolvimentoNumerador(valorElementos, valorPosicoes);
        desenvolvimentoNumerador = removerUltimoValor(desenvolvimentoNumerador);


        String desenvolvimentoAux = calculadoraGeral.gerarFatorialQualquer(auxSecundario);

        String simplificacao = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " +
                gerarFracaoInlineSimplificacao(desenvolvimentoNumerador, desenvolvimentoAux, "") + "$$";

        return simplificacao;
    }

    private String gerarSimplificacaoParte3(int valorElementos, int valorPosicoes, int auxSecundario) {

        long resultadoNumerador = calculadoraGeral.gerarResultadoCalculoFatorial(valorElementos, auxSecundario);
        long resultadoDenominador = calculadoraGeral.gerarResultadoFatorial(auxSecundario);


        String simplificacao = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " +
                gerarFracaoInlineSimplificacao(Long.toString(resultadoNumerador), Long.toString(resultadoDenominador), "") + "$$";

        return simplificacao;
    }



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
    private String gerarPassoAPasso(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = valorElementos - valorPosicoes;

        fatorialElementos = calculadoraEspecifica.gerarFatorialElementos(valorElementos, valorPosicoes);

        String mensagemDesenvolvimento , mensagemSimplificacao = "";
        String numeradorDesenvolvimento, denominadorDesenvolvimento, desenvolvimentoLatex;
        String simplificacaoLatex, elementosMenosPosicoesDesenvolvimento;


        // Elementos igual ao número de posições: n == p
        if (valorElementos == valorPosicoes) {

            // n == 0
            if (valorElementos == 0) {

                numeradorDesenvolvimento = "0";

                mensagemDesenvolvimento = "Como o nº de elementos e de posições foram zero, e 0! = 1, o resultado final será um:";

                desenvolvimentoLatex = gerarDesenvolvimentoLatex(0, 0, "!", "$$");

                simplificacaoLatex = "";

                mensagemSimplificacao = "";


            // n != 0
            } else {

                numeradorDesenvolvimento = Integer.toString(valorElementos);

                mensagemDesenvolvimento = "Como o valor de n = valor de p, então o valor de (n-p) será igual a zero:";

                desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "$$");

                mensagemSimplificacao = "Como (n-p)! foi zero, e 0! = 1, restou apenas " + gerarFracaoInline(valorElementos, valorPosicoes, "!") + " = 1";

                simplificacaoLatex = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoInlineSimplificacao(Integer.toString(valorElementos), Integer.toString(valorPosicoes), "!") + " = 1" + "$$";


            }


//            mensagemSimplificacao = gerarMensagemSimplificacao(valorElementos, valorPosicoes);


//            numeradorDesenvolvimento = Integer.toString(valorElementos);

        // Elementos diferentes de posições: n != p
        } else {

            // Posições igual a elementosMenosPosicoes: p == (n-p)
            if (valorPosicoes == elementosMenosPosicoes) {

                mensagemDesenvolvimento = "Como o valor de p! = (n-p)!, ";

                // p == (n-p), com posicoes igual a zero
                if (valorPosicoes == 0) {
                    mensagemDesenvolvimento += " e nº de posições = " + valorPosicoes + "!" + " = " + "1" +
                            ", resta fazer " + gerarFracaoCifrao(valorElementos, valorPosicoes, "!") + " que resulta em 1";


                    desenvolvimentoLatex = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoInline(valorElementos, elementosMenosPosicoes, "!") + gerarFracaoInline(valorElementos, elementosMenosPosicoes, "!") + " = 1" +"$$";


                    mensagemSimplificacao = "";

                    simplificacaoLatex = "";

                // p == (n-p), com posições igual a um
                } else if (valorPosicoes == 1) {

                    mensagemDesenvolvimento += "e ambos foram 1! = 1," +
                            " então basta calcular o fatorial do numerador:";

                    desenvolvimentoLatex = "$$" + gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "") +
                            " = " + valorElementos + "!" + "$$";


                    mensagemSimplificacao = "Desenvolvendo o fatorial do numerador, ficamos com:";

                    fatorialElementos = calculadoraGeral.gerarFatorialElementos(valorElementos, elementosMenosPosicoes);

                    simplificacaoLatex = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + fatorialElementos + "$$";



                // p == (n-p), com (p != 0 && p != 1)
                } else {

                    mensagemDesenvolvimento += "então podemos desenvolver o " + valorElementos + "!" + " do numerador até qualquer valor do denominador:";

                    desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "$$");

                    mensagemSimplificacao = "Desenvolvendo o " + valorElementos + "!" + " do numerador até " + elementosMenosPosicoes + "! do denominador:";

                    simplificacaoLatex = gerarSimplificacaoParte1(valorElementos, valorPosicoes, elementosMenosPosicoes) +
                            gerarSimplificacaoParte2(valorElementos, valorPosicoes) + gerarSimplificacaoParte3(valorElementos, valorPosicoes, auxSecundario);

                }

            // Posições diferente de elementosMenosPosições: p != (n-p)
            } else {

                // p == 0!, resulta em um, e basta dividir n! / (n-p)! que será igual a um
                if (valorPosicoes == 0) {

                    mensagemDesenvolvimento = "Como o nº de posições foi " + valorPosicoes + "!" + " e " + valorPosicoes + "!" + " = 1" + ", basta dividir "
                            + gerarFracaoInline(valorElementos, elementosMenosPosicoes, "!") + " = 1:";

                    desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "$$");


                    simplificacaoLatex = gerarSimplificacaoParte1(valorElementos, valorPosicoes, elementosMenosPosicoes) + "$$ $$" +
                            gerarSimplificacaoParte2(valorElementos, valorPosicoes) + "$$  $$" + gerarSimplificacaoParte3(valorElementos, valorPosicoes, auxSecundario);

                    mensagemSimplificacao = "";

                // p == 1: resulta em um, e basta desenvolver o fatorial do numerador até (n-p)!
                } else if (valorPosicoes == 1) {

                    mensagemDesenvolvimento = "Como o nº de posições foi " + valorPosicoes + "!" + " e " + valorPosicoes + "!" + " = 1" + ", basta desenvolver " +
                            "o " + valorElementos + "!" + " até (n-p)!";

                    desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "$$");

                    mensagemSimplificacao = "Desenvolvendo o fatorial do numerador até (n-p)!";

                    simplificacaoLatex = gerarSimplificacaoParte1(valorElementos, valorPosicoes, elementosMenosPosicoes) +
                            gerarSimplificacaoParte2(valorElementos, valorPosicoes) + gerarSimplificacaoParte3(valorElementos, valorPosicoes, auxSecundario);


                } else {

                    mensagemDesenvolvimento = "Como o nº de posições foi diferente de (n-p), temos que desenvolver o fatorial do numerador até o maior valor do denominador que é " +
                            calculadoraEspecifica.getMaiorDenominador(valorPosicoes, elementosMenosPosicoes) + "!";

                    desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!", "$$");


                    simplificacaoLatex = gerarSimplificacaoParte1(valorElementos, valorPosicoes, elementosMenosPosicoes) + " " +
                            gerarSimplificacaoParte2(valorElementos, valorPosicoes) + "  " + gerarSimplificacaoParte3(valorElementos, valorPosicoes, auxSecundario);

                }

            }

        }

//        String desenvolvimentoLatex = gerarDesenvolvimento(valorElementos, valorPosicoes, numeradorDesenvolvimento, denominadorDesenvolvimento, elementosMenosPosicoesDesenvolvimento);


//        String desenvolvimentoLatex = gerarDesenvolvimentoLatex(valorElementos, valorPosicoes, "!");


        long resultadoFinal = calculadoraEspecifica.gerarResultadoFinalCombinacao(valorElementos, valorPosicoes);

        String resultadoFinalLatex = gerarResultadoFinal("C", valorElementos, valorPosicoes, resultadoFinal);

        return resultadoFinalLatex;

//        return mensagemDesenvolvimento + desenvolvimentoLatex + mensagemSimplificacao + simplificacaoLatex + resultadoFinalLatex;
    }

//
//    private String gerarDesenvolvimentoComposto(int valorElementos, int valorPosicoes, String numeradorDesenvolvimento, String posicoesDesenvolvimento, String elementosMenosPosicoesDesenvolvimento) {
//        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorElementos, valorPosicoes, "!");
//
//        return desenvolvimento;
//    }


    private String gerarDesenvolvimentoComposto(int valorElementos, int valorPosicoes) {
        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorElementos, valorPosicoes, "!");

        return desenvolvimento;
    }

    // Gera o desenvolvimento simples no formato LaTeX
    private String gerarDesenvolvimentoSimples(int valorElementos, int valorPosicoes) {
        String desenvolvimento = "$$" + gerarCabecalho(valorElementos, valorPosicoes) + " = " + gerarFracaoCifrao(valorElementos, valorPosicoes, "!") + "$$";

        return desenvolvimento;
    }

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

            return fatorialNumerador;

        } else {

            // Apenas o valor sem fatorial, não é necessário remover nada
            return fatorialNumerador;
        }
    }


    // Gera o resultado passo a passo em LaTeX
    public String gerarResultadoCombinacao(int valorElementos, int valorPosicoes) {
        return gerarPassoAPasso(valorElementos, valorPosicoes);
//        return gerarMensagemAplicacaoValores() + gerarAplicacaoValoresCombinacao(valorElementos, valorPosicoes) + gerarPassoAPasso(valorElementos, valorPosicoes);
    }

}