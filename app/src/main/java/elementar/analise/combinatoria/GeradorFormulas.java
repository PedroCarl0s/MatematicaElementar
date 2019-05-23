package elementar.analise.combinatoria;

import android.support.design.widget.TextInputLayout;
import android.util.Log;

import javax.security.auth.login.LoginException;

import elementar.analise.combinatoria.Fragments.Arranjo;


public class GeradorFormulas {

    private static String valorElementos, valorPosicoes;
    private static String elementosMenosPosicoes;


    public GeradorFormulas() {

    }

    //Gera todoo o cálculo no formato LaTeX
    public static String gerarResultado() {
        valorElementos = Arranjo.getNumeroElementos();
        valorPosicoes = Arranjo.getNumeroPosicoes();

        return gerarAplicacaoValores() + gerarDesenvolvimentoFatorial() + gerarSimplificacaoFatorial() + gerarResultadoFinal();
    }

    private static String getNumeroElementos(TextInputLayout inputElementos) {
        return inputElementos.getEditText().getText().toString();
    }

    private static String getNumeroPosicoes(TextInputLayout inputPosicoes) {
        return inputPosicoes.getEditText().getText().toString();
    }

    //Gera a primeira equação, após aplicar os valores N e P
    private static String gerarAplicacaoValores() {

        String resultado = "$$\\bold{\\text{Passo a Passo}}$$" +
                "Após a aplicação dos valores, obtemos:" +
                "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorElementos + "!} " +
                "{(" + Calculadora.gerarElementosMenosPosicoes() + ")!}$$";

        Log.i("gerarAplicacaoValores", "x");

        return resultado;
    }

    // Gera o desenvolvimento do fatorial
    private static String gerarDesenvolvimentoFatorial() {

        String numeradorElementos = Calculadora.gerarFatorialElementos();
        //String denominadorPosicoes = Calculadora.gerarElementosMenosPosicoes();



        String mensagem = "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:";

        elementosMenosPosicoes = Calculadora.resultadoElementosMenosPosicoes();

        String segundoPasso = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + numeradorElementos + "!} " +
                "{" + elementosMenosPosicoes + "!}$$";

        return mensagem + segundoPasso;
    }

    // Gera a simplificação do fatorial (numerador e denominador)
    private static String gerarSimplificacaoFatorial() {
        String mensagem;

        if (!Calculadora.gerarFatorialElementos().equals(Calculadora.resultadoElementosMenosPosicoes())) {

            mensagem = "Simplificando o " + elementosMenosPosicoes +
                    "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";

            String ultimoValorNumerador = Calculadora.gerarFatorialElementos();
            ultimoValorNumerador = removerUltimoValor(ultimoValorNumerador);

            String terceiroPasso = "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + ultimoValorNumerador + "$$";

            return mensagem + terceiroPasso;

        } else {
            if (Calculadora.gerarFatorialElementos().equals("0")) {
                return "E como 0! é igual a um, resulta em (1/1) = 1";
            }

            return "";
        }

    }

    private static String gerarResultadoFinal() {
        //Log.i("RESULTADO", Integer.toString(Calculadora.gerarResultadoCalculo()));

        String resultadoFinal = "$$\\bold{Resultado}$$" + "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + Calculadora.gerarResultadoCalculo() + "$$";
        // "$$\\bold{\\text{Passo a Passo}}$$
        return resultadoFinal;
    }

    // Remove o último valor que continha o fatorial
    public static String removerUltimoValor(String fatorialNumerador) {

        // Exemplo: 4.3.2.1! retornará os caracteres a partir do 4 até  2. Igual a 4.3.2
        String[] valores = fatorialNumerador.replace(".", ";").split(";");

        // Pega o primeiro valor da String (índice zero)
        int indexZero = Integer.parseInt(valores[0]);

        if (indexZero > 2) {

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