package elementar.analise.combinatoria.geradores;

import java.util.HashMap;
import java.util.Map;

public class GeradorAnagrama extends GeradorFormulas {

    private static final String beginCases = "$$\\text{Ocorrencias} \\begin{cases}";
    private static final String EqualEcomInit = "= \\text{";
    private static final String fimText = " vez} \\\\";
    private static final String fimTextS = " vezes} \\\\";
    private static final String endCases = "\\end{cases}$$";

    private final String inicio1 = "$$\\large {P_{";
    private final String início2 = " \\small {\\frac{";
    private final String potencia = "} \\ ^ {";
    private final String separador1 = ", \\ ";
    private final String separador2 = "\\ ";
    private final String fechaChaves1 = "}} =";
    private final String fechaChaves2 = "}}$$";

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

    public String gerarAplicacaoValores(HashMap<String, Integer> hashMapLetras, int getTamanhodaPalavra){

        StringBuilder aplicacao = new StringBuilder(inicio1);
        aplicacao.append(getTamanhodaPalavra).append(potencia);

        for(Map.Entry<String,Integer> entry : hashMapLetras.entrySet()){

            aplicacao.append(entry.getValue()).append(separador1);

        }

        //remover os \\ sobrando
        aplicacao = aplicacao.replace(aplicacao.length()-3,aplicacao.length()-1,"");

        //remover , do final
        aplicacao = aplicacao.replace(aplicacao.length()-2,aplicacao.length()-1,"");

        aplicacao.append(fechaChaves1);

        aplicacao.append(início2).append(getTamanhodaPalavra).append("!} {");

        for(Map.Entry<String,Integer> entry : hashMapLetras.entrySet()){

            aplicacao.append(entry.getValue()).append("! ").append(separador2);

        }

        //remover os \\ sobrando
        aplicacao = aplicacao.replace(aplicacao.length()-2,aplicacao.length()-1,"");

        aplicacao.append(fechaChaves2);

        return aplicacao.toString();

    }

}