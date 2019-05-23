package elementar.analise.combinatoria;

import android.support.design.widget.TextInputLayout;
import android.util.Log;

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

        String resultado = "$$\\bold{Resultado}$$" +
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

        String mensagem = "Simplificando o " + elementosMenosPosicoes +
            "! do numerador, com o " + elementosMenosPosicoes + "! ficamos com:";

        String ultimoValorNumerador = Calculadora.gerarFatorialElementos();
        ultimoValorNumerador = removerUltimoValor(ultimoValorNumerador);

        String terceiroPasso = "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + ultimoValorNumerador + "$$";

        return mensagem + terceiroPasso;
    }

    private static String gerarResultadoFinal() {
        String resultadoFinal = "$$A(" + valorElementos + ", " + valorPosicoes + ") = " + Integer.toString(Calculadora.gerarResultadoCalculo()) + "$$";

        return resultadoFinal;
    }

    // Remove o último valor que continha o fatorial
    public static String removerUltimoValor(String fatorialNumerador) {
        // Exemplo: 4.3.2.1! retornará os caracteres a partir do 4 até  2. Igual a 4.3.2

        // Pega o primeiro valor da String (índice zero)0
        int indexZero = Character.getNumericValue(fatorialNumerador.charAt(0));

        Log.i("INDEX ZERO", Integer.toString(indexZero));

        Log.i("FATORIAL", fatorialNumerador);
        Log.i("FATORIAL SIZE", Integer.toString(fatorialNumerador.length()));
        if (indexZero > 2) {
            int fim = fatorialNumerador.length() - 2;

            Log.i("Fat Numerador", fatorialNumerador.substring(0, fim));
            return fatorialNumerador.substring(0, fim);

        } else {

            // 0! = 1 | 1! = 1 ou 2! = 2
            return (indexZero == 0) ? "0" : fatorialNumerador;
        }

    }


    //        return resultado + segundoPasso;
    //
    //
    //        String res = "Desenvolvendo ate o valor do fatorial do denominador, obtemos";
    //
    //                " \\end{cases}$$";
    //                " p = \\text{numero de posicoes a arranjar }" +
    //                " n = \\text{numero de elementos a arranjar }  \\\\" +
    //        String teste = "$$ x = \\begin{cases}" +
    //
    //                "do numerador, com o " + Calculadora.resultadoElementosMenosPosicoes() + "ficamos com:";
    //        String terceiroPasso = "Simplificando o " + Calculadora.resultadoElementosMenosPosicoes() +
    //
    //                "{" + Calculadora.resultadoElementosMenosPosicoes() + "!}$$";
    //                "$$A(" + this.valorElementos + ", " + this.valorPosicoes + ") = \\frac{" + numeradorElementos + "!} " +
    //        String segundoPasso = "Desenvolvendo até o valor do fatorial do denominador para simplificar, obtemos:" +
    //
    //
    //                "{(" + Calculadora.gerarElementosMenosPosicoes() + ")!}$$";
    //                "$$A(" + this.valorElementos + ", " + this.valorPosicoes + ") = \\frac{" + this.valorElementos + "!} " +
    //                "Após a aplicação dos valores, obtemos:" +
    //        String resultado = "$$\\bold{Resultado}$$" +
    //
    //        this.valorPosicoes = getNumeroPosicoes(inputPosicoes);
    //        this.valorElementos = getNumeroElementos(inputElementos);
    //
    //        String denominadorPosicoes = Calculadora.gerarElementosMenosPosicoes();
    //        String numeradorElementos = Calculadora.gerarFatorialElementos();
    //
    //    public String gerarArranjoLaTeX(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {
    //
//    // Gera o resultado em formato LaTeX para impressão na tela

//    }



}