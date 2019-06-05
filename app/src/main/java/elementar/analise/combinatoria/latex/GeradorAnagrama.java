package elementar.analise.combinatoria.latex;

import android.util.Log;

public class GeradorAnagrama {

    public static String gerarFormula() {
        String formulaAnagrama = "$$\\bold{Formula}$$" +
                "$$\\large {P_{n} \\ ^ {n_1, \\ n_2, \\ ..., \\ n_k}} = \\small {\\frac{n!} {n_1! \\ n_2! \\ ... \\ n_k!}}$$";

        return formulaAnagrama;
    }

    public static String gerarDescricaoVariaveis(String palavraInserida) {

        String descricao = "$$\\text{Ocorrencias} \\begin{cases}" +
                "a = & \\text{2 vezes} \\\\" +
                "b = & \\text{3 vezes} \\\\" +
                "c = & \\text{2 vezes}" +
                "\\end{cases}$$";

        return descricao;
    }

}