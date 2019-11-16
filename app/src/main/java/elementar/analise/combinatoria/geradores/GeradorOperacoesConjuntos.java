package elementar.analise.combinatoria.geradores;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

public class GeradorOperacoesConjuntos {

    private final String comma = ",";

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
            if(i < values.length-1) value.append(comma);
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
        String[] arrayNumber =  oldString.split(comma);

        for (int i = 0; i < arrayNumber.length; i++) {

            String compare = arrayNumber[i];

            String[] auxArray = newValue.toString().split(comma);

            int index = findArray(compare,auxArray);
            Log.i("calc","arrayNumber[i]) "+arrayNumber[i]);
            if(index == -1 || newValue.length() == 0){

                newValue.append(arrayNumber[i]);

                if(i < arrayNumber.length -1) newValue.append(comma);

            }

        }
        Log.i("calc","newValue.toString() "+newValue.toString());
        return ordenar(newValue.toString());

    }

    public String checkComma(String value){

        value = value.replaceAll("\\s+","");

        String[] arrayValueA = value.split(comma);

        StringBuilder newValue = new StringBuilder();

        for(int i = 0; i < arrayValueA.length; i++){

            if(!arrayValueA[i].equals("")){

                newValue.append(arrayValueA[i]);
                if(i < arrayValueA.length -1) newValue.append(comma);

            }

        }

        return newValue.toString().trim();

    }

    private String findEquals(String inputA, String inputB){

        StringBuilder newValue = new StringBuilder();

        inputA = removeEquals(inputA);

        inputB = removeEquals(inputB);

        String[] arrayInputA = inputA.split(comma);
        String[] arrayInputB = inputB.split(comma);

        for(int i = 0;i < arrayInputA.length;i++){
            for(int j = 0;j < arrayInputB.length;j++){
                if(Integer.parseInt(arrayInputA[i]) == Integer.parseInt(arrayInputB[j])){
                    newValue.append(arrayInputA[i]);
                    if(i < arrayInputA.length -2) newValue.append(comma);
                    break;
                }
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

            String[] auxArray = newValue.toString().split(comma);

            int index = findArray(compare,auxArray);

            int indexValueB = findArray(compare,arrayValueB);

            if((index == -1 && indexValueB == -1) || newValue.length() == 0 &&  indexValueB == -1){

                newValue.append(compare);

                if(i < arrayValueA.length - 1) newValue.append(comma);

            }

        }

        if(String.valueOf(newValue.charAt(newValue.length()-1)).equals(comma)) newValue.deleteCharAt(newValue.length() - 1);

        return newValue.toString();
    }

    //calculor individuais

    public StringBuilder calcularUniao(String inputA, String inputB){

        String resultado = checkComma(inputA) +","+ checkComma(inputB);

        return new StringBuilder(removeEquals(resultado));
    }

    public StringBuilder calcularIntersecao(String inputA, String inputB){

        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        inputB = checkComma(inputB);

        newCalculo.append(findEquals(inputA,inputB));

        return newCalculo;
    }

    public StringBuilder calcularComplementar(String inputA, String inputB, String inputC){
        return new StringBuilder();
    }

    public StringBuilder calcularDiferencaAB(String inputA, String inputB){

        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        inputB = checkComma(inputB);

        newCalculo.append(conjuntoDiferença(inputA,inputB));

        return newCalculo;
    }

    public StringBuilder calcularDiferencaBA(String inputB, String inputA){

        StringBuilder newCalculo = new StringBuilder();

        inputA = checkComma(inputA);
        inputB = checkComma(inputB);

        newCalculo.append(conjuntoDiferença(inputB,inputA));

        return newCalculo;
    }

    public StringBuilder calcularConjuntoPartes(String inputA, String inputB){
        return new StringBuilder();
    }

//    calcular pares

    //Uniao
    public StringBuilder calcularUniaoComplementar(TextInputLayout inputA, TextInputLayout inputB, TextInputLayout inputC){
        return new StringBuilder();
    }

    public StringBuilder calcularUniaoIntersecao(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularUniaoConjPartes(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularUniaoDifAB(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularUniaoDifBAo(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    //Interseção
    public StringBuilder calcularIntersecaoConjPartes(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularIntersecaoDifAB(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularIntersecaoComplementar(TextInputLayout inputA, TextInputLayout inputB, TextInputLayout inputU){
        return new StringBuilder();
    }

    public StringBuilder calcularIntersecaoDifBA(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    //Diferença A-B
    public StringBuilder calcularDifABDifBA(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularDifABComplementar(TextInputLayout inputA, TextInputLayout inputB, TextInputLayout inputU){
        return new StringBuilder();
    }

    public StringBuilder calcularDifABConjPartes(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    //Complementar
    public StringBuilder calcularComplementarConjPartes(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularComplementarDifBA(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

    //Conjunto das Partes
    public StringBuilder calcularConjPartesDifBA(TextInputLayout inputA, TextInputLayout inputB){
        return new StringBuilder();
    }

}
