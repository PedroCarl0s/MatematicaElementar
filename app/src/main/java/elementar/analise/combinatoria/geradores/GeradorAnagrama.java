package elementar.analise.combinatoria.geradores;

import java.util.HashMap;
import java.util.Map;

public class GeradorAnagrama extends GeradorFormulas {

    private static final String beginCases = "$$\\text{Ocorrencias} \\begin{cases}";
    private static final String EqualEcomInit = "= \\text{";
    private static final String fimText = " vez} \\\\";
    private static final String fimTextS = " vezes} \\\\";
    private static final String endCases = "\\end{cases}$$";

    public String gerarFormula() {
        String formulaAnagrama = "$$\\bold{Formula}$$" +
                "$$\\large {P_{n} \\ ^ {n_1, \\ n_2, \\ ..., \\ n_k}} = \\small {\\frac{n!} {n_1! \\ n_2! \\ ... \\ n_k!}}$$";

        return formulaAnagrama;
    }

    public String gerarDescricaoVariaveis(HashMap<String,Integer> mapPalavras) {

        StringBuilder descricao = new StringBuilder();

        descricao.append(beginCases);

        for (Map.Entry<String,Integer> entry : mapPalavras.entrySet()) {

            descricao.append(entry.getKey()).append(EqualEcomInit).append(entry.getValue());

            if (entry.getValue() == 1) {
                descricao.append(fimText);

            } else {
                descricao.append(fimTextS);
            }

        }

        // Removendo os 4 últimos índices (os quatro \\\\ excedentes)
        descricao.delete(descricao.length()-3, descricao.length());
        descricao.append(endCases);

        return String.valueOf(descricao);
    }

}