package elementar.analise.combinatoria;

import android.support.design.widget.TextInputLayout;
import android.util.Log;

import javax.security.auth.login.LoginException;

import elementar.analise.combinatoria.Fragments.Arranjo;


public class GeradorFormulas {

    private static String valorElementos, valorPosicoes;
    private static String elementosMenosPosicoes, fatorialElementos;


    public GeradorFormulas() {

    }

    private static String getNumeroElementos(TextInputLayout inputElementos) {
        return inputElementos.getEditText().getText().toString();
    }



    private static String getNumeroPosicoes(TextInputLayout inputPosicoes) {
        return inputPosicoes.getEditText().getText().toString();
    }

    //Gera a primeira equação, após aplicar os valores N e P
    private static String gerarAplicacaoValores() {

        String mensagem = "$$\\bold{\\text{Passo a Passo}}$$" +
                "Após a aplicação dos valores, obtemos:";

        String resultado = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorElementos + "!} " +
                "{(" + Calculadora.gerarElementosMenosPosicoes() + ")!}$$";

        Log.i("gerarAplicacaoValores", "x");

        return mensagem + resultado;
    }

    //Gera todo o cálculo no formato LaTeX
    public static String gerarResultado() {
        valorElementos = Arranjo.getNumeroElementos();
        valorPosicoes = Arranjo.getNumeroPosicoes();
        elementosMenosPosicoes = Calculadora.resultadoElementosMenosPosicoes();
        fatorialElementos = Calculadora.gerarFatorialElementos();

        String mensagemDesenvolvimento, mensagemSimplificacao;
        String numeradorDesenvolvimento;
        long resultadoFinal;

        // Valores distintos, é necessário desenvolver o fatorial
        if (!valorElementos.equals(elementosMenosPosicoes)) {
            mensagemDesenvolvimento = "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:";

            mensagemSimplificacao = "Simplificando o " + elementosMenosPosicoes +
                    "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";

            numeradorDesenvolvimento = fatorialElementos;

            resultadoFinal = Calculadora.gerarResultadoCalculo();

        } else {
            mensagemDesenvolvimento = "Desenvolvendo o fatorial do denominador, obtemos " + valorElementos + "!" + " do numerador " +
                    "com " + elementosMenosPosicoes + "!" + " do denominador, resultando em:";

            mensagemSimplificacao = "Simplificando " + valorElementos + "!" + " do numerador com " +
                    elementosMenosPosicoes + "!" + " do denominador, resulta-se em 1";

            numeradorDesenvolvimento = valorElementos;

            // a! sobre b!, sendo a = b, resultará sempre em 1
            resultadoFinal = 1;
        }



        return gerarAplicacaoValores() + mensagemDesenvolvimento + gerarDesenvolvimentoFatorial(numeradorDesenvolvimento) + mensagemSimplificacao + gerarSimplificacaoFatorial() + gerarResultadoFinal(resultadoFinal);
    }

    // Gera o desenvolvimento do fatorial
    private static String gerarDesenvolvimentoFatorial(String valorNumerador) {
        String desenvolvimento = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorNumerador + "!} " +
                "{" + elementosMenosPosicoes + "!}$$";

        Log.i("XXXXXX", valorNumerador);
        return desenvolvimento;
    }

    // Gera a simplificação do fatorial (numerador e denominador)
    private static String gerarSimplificacaoFatorial() {
        String ultimoValorNumerador = fatorialElementos;
        String simplificacao;

        if (!valorElementos.equals(elementosMenosPosicoes)) {
            Log.i("ULTIMO", ultimoValorNumerador);
            ultimoValorNumerador = removerUltimoValor(ultimoValorNumerador);
            Log.i("ULTIMO", ultimoValorNumerador);


        } else {
            ultimoValorNumerador = "1";
        }

        simplificacao = "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + ultimoValorNumerador + "$$";

        return simplificacao;
    }

    private static String gerarResultadoFinal(long resultadoFinal) {
        //Log.i("RESULTADO", Integer.toString(Calculadora.gerarResultadoCalculo()));

        String resultado = "$$\\bold{Resultado}$$" + "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + resultadoFinal + "$$";
        // "$$\\bold{\\text{Passo a Passo}}$$
        return resultado;
    }

    // Remove o último valor que continha o fatorial
    public static String removerUltimoValor(String fatorialNumerador) {

        // Exemplo: 4.3.2.1! retornará os caracteres a partir do 4 até  2. Igual a 4.3.2
        String[] valores = fatorialNumerador.replace(".", ";").split(";");

        // Pega o primeiro valor da String (índice zero)
        int indexZero = Integer.parseInt(valores[0]);

        if (indexZero > 1) {

            // Valor que será removido, fica no último índice do vetor
            String valorARemover = valores[valores.length-1];

            int tamanhoUltimo = valorARemover.length();
            int tamanhoFatorial = fatorialNumerador.length();

            /*Removendo o último valor, e o ponto final excedente
             Ex.: Remover o valor 10 e ponto final -> 11.10
             Dez possui tamanho dois, e então decrementando dois índices da substring,
             o 10 é removido, mas o ponto final ainda fica. Para remover o ponto final,
             decrementa-se +1 índice da String. RESULTADO = 11
             */
            fatorialNumerador = fatorialNumerador.substring(0, (tamanhoFatorial - tamanhoUltimo)-1);

            return fatorialNumerador;

        } else {

            // 0! = 1 | 1! = 1 ou 2! = 2
            return fatorialNumerador;
        }

    }

}