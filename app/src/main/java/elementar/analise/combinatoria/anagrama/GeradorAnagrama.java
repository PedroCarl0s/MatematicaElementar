package elementar.analise.combinatoria.anagrama;


import java.util.HashMap;
import java.util.Map;

import elementar.analise.combinatoria.geradores.GeradorFormulas;

import java.text.Normalizer;


public class GeradorAnagrama extends GeradorFormulas {

    private static final String beginCases = "$$\\text{Ocorrencias} \\begin{cases}";
    private static final String EqualEcomInit = "= \\text{";
    private static final String fimText = " vez} \\\\";
    private static final String fimTextS = " vezes} \\\\";
    private static final String endCases = "\\end{cases}$$";

    private final String inicio1 = "$$\\large {P_{";
    private final String inicio2 = " \\small {\\frac{";
    private final String potencia = "} \\ ^ {";
    private final String separador1 = ", \\ ";
    private final String separador2 = "\\ ";
    private final String fechaChaves1 = "}} =";
    private final String fechaChaves2 = "}}$$";

    private final String igualdadaFinal = "} =";
    private final String fechamentoFinal = "}$$";

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
           descricao.append(fimTextS);
        }

        // Removendo os 4 últimos índices (os quatro \\\\ excedentes)
        descricao.delete(descricao.length()-2, descricao.length());

        descricao.append(endCases);

        return String.valueOf(descricao);
    }

    public String gerarAplicacaoValores(HashMap<String, Integer> hashMapLetras, int getTamanhodaPalavra){

        StringBuilder resultaooFinal = gerarEtapa1(hashMapLetras,getTamanhodaPalavra);

        resultaooFinal = gerarEtapa2(resultaooFinal,hashMapLetras,getTamanhodaPalavra);

        return resultaooFinal.toString();

    }


    private StringBuilder gerarEtapa1(HashMap<String, Integer> hashMapLetras, int getTamanhodaPalavra){

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

        return aplicacao;

    }

    private StringBuilder gerarEtapa2(StringBuilder aplicacao,HashMap<String,Integer> hashMap,int tamanhoPalavra){

        aplicacao.append(inicio2).append(tamanhoPalavra).append("!} {");

        for(Map.Entry<String,Integer> entry : hashMap.entrySet()){

            aplicacao.append(entry.getValue()).append("! ").append(separador2);
        }

        //remover os \\ sobrando
        aplicacao = aplicacao.replace(aplicacao.length()-2,aplicacao.length()-1,"");

        aplicacao.append(fechaChaves2);

        return aplicacao;


    }


    public String removerEspacos(String entrada) {
        return entrada.replaceAll("\\s", "");
    }

    // Remove tudo que não seja letra e número
    private String removerCaracteresEspeciais(String entrada) {
        return entrada.replaceAll("[^a-zA-Z0-9]", "");
    }

    // Deixa a palavra sem espaços, acentos e símbolos
    public String removerAcentosESimbolos(String entrada) {
        String novaPalavra = removerEspacos(entrada);

        novaPalavra = Normalizer.normalize(novaPalavra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");

        return removerCaracteresEspeciais(novaPalavra);
    }

    private StringBuilder gerarFormulaResultadoFinal(HashMap<String,Integer> hashMap,Long resultadoFinal,int tamanhoPalavra){

        StringBuilder aplicacao = new StringBuilder(inicio1);
        aplicacao.append(tamanhoPalavra).append(potencia);

        for(Map.Entry<String,Integer> entry : hashMap.entrySet()){
            aplicacao.append(entry.getValue()).append(separador1);
        }

        //remover , do final
        aplicacao = aplicacao.replace(aplicacao.length()-4,aplicacao.length()-1,"");

        aplicacao.append(igualdadaFinal);

        aplicacao.append(resultadoFinal).append(fechamentoFinal);

        return aplicacao;

    }

    public String gerarResultadoFinal(HashMap<String,Integer> hashMap,Long resultadoFinal,int tamanhoPalavra){

        StringBuilder aplicacao = gerarFormulaResultadoFinal(hashMap,resultadoFinal,tamanhoPalavra);

        return aplicacao.toString();

    }

    private StringBuilder pegarValoresIqualUm(HashMap<String,Integer> hashMap){

        StringBuilder letrasRepetidas = new StringBuilder();

        for(Map.Entry<String,Integer> map : hashMap.entrySet()){

            if(map.getValue() == 1){

                letrasRepetidas.append(map.getKey()).append(", ");

            }
        }

        letrasRepetidas = letrasRepetidas.delete(letrasRepetidas.length()-2,letrasRepetidas.length());

        return letrasRepetidas;
    }
}