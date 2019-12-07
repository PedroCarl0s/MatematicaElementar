package elementar.operacoes.conjuntos;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;


public class GeradorOperacoesConjuntos {

    private final int INDEX_ID = 0;
    private final int INDEX_VALUES = 1;

    private final String COMMA = ",";
    private final String DELIMITER = "$$";
    private final String BRACE_LEFT = "\\lbrace";
    private final String BRACE_RIGHT = "\\rbrace";
    private final String NEW_LINE = "\\";

    private final String UNIAO_LATEX = "\\cup";
    private final String INTERSECAO_LATEX = "\\cap";
    private final String DIFERENCA_LATEX = "-";

    public GeradorOperacoesConjuntos(){

    }

    private String ordenar(String value){

        String[] arrayValue = value.split(",");

        for (int i = 0;i < arrayValue.length; i++){

            for(int j = i;j < arrayValue.length;j++){

                if(Integer.parseInt(arrayValue[i]) > Integer.parseInt(arrayValue[j])){
                    String aux = arrayValue[i];
                    arrayValue[i] = arrayValue[j];
                    arrayValue[j] = aux;
                }
            }
        }

        return toArrayString(arrayValue);
    }

    private String toArrayString(String[] values){

        StringBuilder value = new StringBuilder();
        for(int i = 0;i < values.length;i++){
            value.append(values[i]);
            if(i < values.length-1) value.append(COMMA);
        }

        return value.toString();
    }

    private int findArray(String value, String[] arrayValues){

        for(int i = 0; i < arrayValues.length;i++){
            if(value.equals(arrayValues[i])) return i;
        }
        return -1;
    }

    private String removeEquals(String oldString) {

        StringBuilder newValue = new StringBuilder();
        String[] arrayNumber =  oldString.split(COMMA);

        for (int i = 0; i < arrayNumber.length; i++) {

            String compare = arrayNumber[i];

            String[] auxArray = newValue.toString().split(COMMA);

            int index = findArray(compare,auxArray);
            Log.i("calc","arrayNumber[i]) "+arrayNumber[i]);
            if(index == -1 || newValue.length() == 0){

                newValue.append(arrayNumber[i]);

                if(i < arrayNumber.length -1) newValue.append(COMMA);

            }

        }
        Log.i("calc","newValue.toString() "+newValue.toString());
        return ordenar(newValue.toString());

    }

    public String checkComma(String value){

        value = value.replaceAll("\\s+","");
        String[] arrayValueA = value.split(COMMA);
        StringBuilder newValue = new StringBuilder();

        for(int i = 0; i < arrayValueA.length; i++){

            if(!arrayValueA[i].equals("")){
                newValue.append(arrayValueA[i]);

                if(i < arrayValueA.length -1) newValue.append(COMMA);
            }

        }
        return newValue.toString().trim();
    }

    private String findEquals(String inputA, String inputB){

        StringBuilder newValue = new StringBuilder();

        inputA = removeEquals(inputA);
        inputB = removeEquals(inputB);

        String[] arrayInputA = inputA.split(COMMA);
        String[] arrayInputB = inputB.split(COMMA);

        for(int i = 0;i < arrayInputA.length;i++){
            for(int j = 0;j < arrayInputB.length;j++){
                if(Integer.parseInt(arrayInputA[i]) == Integer.parseInt(arrayInputB[j])){
                    newValue.append(arrayInputA[i]);
                    if(i < arrayInputA.length -1) newValue.append(COMMA);
                    break;
                }
            }
        }


        int tamanho = newValue.length();
        if (tamanho > 0) {
            if(newValue.charAt(tamanho-1) == ','){
                newValue.deleteCharAt(tamanho-1);
            }
        }

        return newValue.toString();
    }

    private String conjuntoDiferença(String inputA, String inputB){

        StringBuilder newValue = new StringBuilder();

        inputA = removeEquals(inputA);
        inputB = removeEquals(inputB);

        String[] arrayValueA = inputA.split(",");
        String[] arrayValueB = inputB.split(",");

        for(int i = 0;i  < arrayValueA.length;i++){

            String compare = arrayValueA[i];
            String[] auxArray = newValue.toString().split(COMMA);

            int index = findArray(compare,auxArray);
            int indexValueB = findArray(compare,arrayValueB);

            if((index == -1 && indexValueB == -1) || newValue.length() == 0 &&  indexValueB == -1){
                newValue.append(compare);

                if(i < arrayValueA.length - 1) newValue.append(COMMA);
            }

        }

        int tamanho = newValue.length();
        if (tamanho > 0) {
            if(newValue.charAt(tamanho-1) == ','){
                newValue.deleteCharAt(tamanho-1);
            }
        }

        return newValue.toString();
    }

    //União
    public StringBuilder calcularUniao(String inputA, String inputB){
        String resultado = checkComma(inputA) +","+ checkComma(inputB);
        return new StringBuilder(removeEquals(resultado));
    }

    public String getTitleUniaoLatex() {
        return "$$\\normalsize \\bold{Uniao}$$";
    }

    public String getTextUniao() {
        return "A união dos conjuntos A e B, são todos os valores que estão em A ou B (as duplicatas são removidas)";
    }


    //Interseção
    public StringBuilder calcularIntersecao(String inputA, String inputB){
        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        Log.i("intersecao","valor inputA "+inputA);
        inputB = checkComma(inputB);
        Log.i("intersecao","valor inputB "+inputB);

        newCalculo.append(findEquals(inputA,inputB));

        if(newCalculo.toString().isEmpty()){
            return new StringBuilder("");
        }

        Log.i("intersecao","valor final "+newCalculo.toString());
        return newCalculo;
    }

    public String getTitleIntersecaoLatex() {
        return "$$\\normalsize \\bold{Intersecao}$$";
    }
    public String getTextIntersecao() {
        return "A interseção dos conjuntos A e B, são todos os valores que estão em A e B ao mesmo tempo";
    }

    //Complementar
    public StringBuilder calcularComplementar(String inputA, String inputB, String inputC){
        return new StringBuilder();
    }

    //Diferença A-B
    public StringBuilder calcularDiferencaAB(String inputA, String inputB){

        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        inputB = checkComma(inputB);

        newCalculo.append(conjuntoDiferença(inputA,inputB));

        return newCalculo;
    }

    public String getTitleDiferencaABLatex() {
        return "$$\\normalsize \\bold{Diferenca \\ (A-B)}$$";
    }


    public String getTextDiferencaAB() {
        return "A diferença de A-B, são todos os valores que estão em A mas não estão em B";
    }

    //Diferença B-A
    public StringBuilder calcularDiferencaBA(String inputB, String inputA){
        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        inputB = checkComma(inputB);

        newCalculo.append(conjuntoDiferença(inputB,inputA));

        return newCalculo;
    }

    public String getTitleDiferancaBALatex() {
        return "$$\\normalsize \\bold{Diferenca \\ (B-A)}$$";
    }

    public String getTextDiferencaBA() {
        return "A diferença de B-A, são todos os valores que estão em B mas não estão em A";
    }

    // Conjunto das Partes
    public StringBuilder calcularConjuntoPartes(String inputA, String inputB){
        return new StringBuilder();
    }


    // Obtêm o resultado final das operações com dois conjuntos, no formato LaTeX
    public String getResultadoOperacaoAB(String nomeA, String nomeB, String operacao, String conjuntoResultante) {
        return "$$" + " \\ " + nomeA + operacao + " \\ " + nomeB + " = " + BRACE_LEFT + conjuntoResultante + BRACE_RIGHT + "$$";
    }

    public String convert(TextInputLayout textInputLayout){
        return textInputLayout.getEditText().getText().toString();
    }

    public String imprimirConjuntosLatex(String conjuntoA, String conjuntoB, String conjuntoU) {

        String resultado = "";
        String[][] conjuntos = {{"A", ""},{"B", ""},{"U", ""}};

        String titulo = "$$\\normalsize \\bold{Conjuntos}$$";

        conjuntos[0][1] = conjuntoA;
        conjuntos[1][1] = conjuntoB;
        conjuntos[2][1] = conjuntoU;

        for (int atual = 0; atual < conjuntos.length; atual++) {
            if (conjuntos[atual][INDEX_VALUES] != "") {
                resultado += "$$";
                resultado += conjuntos[atual][INDEX_ID];
                resultado += " = ";
                resultado += BRACE_LEFT;
                resultado += conjuntos[atual][INDEX_VALUES];
                resultado += BRACE_RIGHT;
                resultado += "$$";
            }
        }

        return titulo + resultado + "$$ \\ $$";
    }


}