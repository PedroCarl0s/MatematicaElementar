package elementar.analise.combinatoria.latex;

import android.support.v4.app.INotificationSideChannel;
import android.telephony.mbms.MbmsErrors;

import elementar.analise.combinatoria.Calculadora;

public final class GeradorFormulas {

    private static GeradorFormulas INSTANCE = null;

    private static int elementosMenosPosicoes;
    private static String fatorialElementos;

    private static Calculadora calculadora = Calculadora.getInstance();
    private static GeradorFormulas geradorFormulas = GeradorFormulas.getInstance();

    private GeradorFormulas() {

    }

    public static synchronized GeradorFormulas getInstance() {
        if (INSTANCE == null) {
            return new GeradorFormulas();
        }
        return INSTANCE;
    }

    //Gera a primeira equação, após aplicar os valores N e P
    private static String gerarAplicacaoValores(int valorElementos, int valorPosicoes) {

        String mensagem = "$$\\bold{\\text{Passo a Passo}}$$" +
                "Após a aplicação dos valores, obtemos:";

        String resultado = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorElementos + "!} " +
                "{(" + calculadora.gerarElementosMenosPosicoes() + ")!}$$";

        return mensagem + resultado;
    }

    //Gera todo o cálculo no formato LaTeX
    public static String gerarResultadoArranjo(int valorElementos, int valorPosicoes) {

        elementosMenosPosicoes = calculadora.resultadoElementosMenosPosicoes(valorElementos, valorPosicoes);

        fatorialElementos = calculadora.gerarFatorialElementos(valorElementos, valorPosicoes);

        String mensagemDesenvolvimento, mensagemSimplificacao;
        String numeradorDesenvolvimento;
        long resultadoFinal;

        // Elementos = Posições, o valor do denominador será zero. Basta fazer o cálculo do numerador (no Arranjo Simples)
        if (valorElementos == valorPosicoes) {
            mensagemDesenvolvimento = "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                    "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";

            mensagemSimplificacao = "Como o valor do denominador é 0!, e 0! = 1, basta resolver o " +
                    valorElementos + "!" + " do numerador";

            numeradorDesenvolvimento = Integer.toString(valorElementos);

            resultadoFinal = calculadora.gerarResultadoCalculoPermutacao(valorElementos, valorPosicoes);

        }

        // Valores distintos, é necessário desenvolver o fatorial (no Arranjo Simples)
        else if (valorElementos != elementosMenosPosicoes) {
            mensagemDesenvolvimento = "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:";

            mensagemSimplificacao = "Simplificando o " + elementosMenosPosicoes +
                    "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";

            numeradorDesenvolvimento = fatorialElementos;

            resultadoFinal = calculadora.gerarResultadoCalculoPermutacao(valorElementos, valorPosicoes);

        // Nº de elementos igual ao resultado de (n-p)!, sempre resultará 1 (no Arranjo Simples)
        } else {
            mensagemDesenvolvimento = "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                    "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";

            mensagemSimplificacao = "Simplificando " + valorElementos + "!" + " do numerador com " +
                    elementosMenosPosicoes + "!" + " do denominador, resulta-se em 1";

            numeradorDesenvolvimento = Integer.toString(valorElementos);

            // a! sobre b!, sendo a = b, resultará sempre em 1
            resultadoFinal = 1;
        }

        return gerarAplicacaoValores(valorElementos, valorPosicoes) + mensagemDesenvolvimento + gerarDesenvolvimentoFatorial(valorElementos, valorPosicoes, numeradorDesenvolvimento) +
                mensagemSimplificacao + gerarSimplificacaoFatorial(valorElementos, valorPosicoes) + gerarResultadoFinal(valorElementos, valorPosicoes, resultadoFinal);
    }

    // Gera o desenvolvimento do fatorial
    private static String gerarDesenvolvimentoFatorial(int valorElementos, int valorPosicoes, String valorNumerador) {
        String desenvolvimento = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorNumerador + "!} " +
                "{" + elementosMenosPosicoes + "!}$$";

        return desenvolvimento;
    }

    // Gera a simplificação do fatorial (numerador e denominador)
    private static String gerarSimplificacaoFatorial(int valorElementos, int valorPosicoes) {
        String ultimoValorNumerador = fatorialElementos;
        String simplificacao;

        // Elementos = Posições, o valor do denominador será zero. Basta fazer o cálculo do numerador
        if (valorElementos == valorPosicoes) {

        } else if (valorElementos != elementosMenosPosicoes) {
            ultimoValorNumerador = removerUltimoValor(ultimoValorNumerador);

        } else {
            ultimoValorNumerador = "1";
        }

        simplificacao = "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + ultimoValorNumerador + "$$";

        return simplificacao;
    }

    private static String gerarResultadoFinal(int valorElementos, int valorPosicoes, long resultadoFinal) {
        String resultado = "$$\\bold{Resultado}$$" + "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + resultadoFinal + "$$";

        return resultado;
    }

    // Remove o último valor que continha o fatorial
    public static String removerUltimoValor(String fatorialNumerador) {

        // Exemplo: 4.3.2.1" retornará os caracteres a partir do 4 até  2. Igual a 4.3.2
        String[] valores = fatorialNumerador.replace(".", ";").split(";");

        // Pega o primeiro valor da String (índice zero)
        int indexZero = Integer.parseInt(valores[0]);

        if (indexZero > 1) {

            // Valor que será removido, fica no último índice do vetor
            String valorARemover = valores[valores.length-1];

            int tamanhoUltimo = valorARemover.length();
            int tamanhoFatorial = fatorialNumerador.length();

            /*Removendo o último valor, e o ponto final excedente
             Ex.: Remover o valor 10 e o ponto final -> 11.10
             Dez possui tamanho dois, e então decrementando dois índices da substring,
             o 10 é removido, mas o ponto final ainda fica. Para remover o ponto final,
             decrementa-se +1 índice da String. RESULTADO = 11
             */
            fatorialNumerador = fatorialNumerador.substring(0, (tamanhoFatorial - tamanhoUltimo)-1);

            return fatorialNumerador;

        } else {

            // 0! ou 1!, não tem o que remover então retorna o próprio valor
            return fatorialNumerador;
        }

    }

    // Gera o resultado final, contendo o passo a passo construído
    public static String gerarResultadoPermutacao(int valorEntrada) {

        String resultadoParcial = Calculadora.gerarDesenvolvimentoPermutacao(valorEntrada);
        String textoResultado = Integer.toString(Calculadora.gerarResultadoPermutacao(valorEntrada));

        String resultado = "$$\\bold{Resultado}$$";

        // Para questões de quebrar a visualização do passo a passo (2 até 10 fatorial, imprimo em uma única linha)
        if (valorEntrada  > 1 && valorEntrada <= 10) {
            resultado += "$$" + valorEntrada +  "!" +  " = " + resultadoParcial + " = " + textoResultado + "$$";

        // Valor da entrada maior que 10 fatorial, quebro o resultado em duas linhas
        } else if (valorEntrada > 10) {
            resultado += "$$" + valorEntrada +  "!" +  " = " + resultadoParcial + "$$";
            resultado += "$$" + valorEntrada + "!" + " = " + textoResultado + "$$";

        // 0 ou 1 fatorial, apenas repito o valor com sinal de fatorial, sendo igual a ele mesmo. Ex.: 0! = 0 ou 1! = 1
        } else if (valorEntrada == 0 || valorEntrada == 1) {
            resultado += "$$" + valorEntrada + "!" + " = " + textoResultado + "$$";

        // Os valores devem ser maiores ou iguais a zero
        } else {
            return "Apenas valores inteiros positivos";
        }

        return resultado;
    }


}