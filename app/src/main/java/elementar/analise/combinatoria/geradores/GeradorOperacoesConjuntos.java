package elementar.analise.combinatoria.geradores;

import android.util.Log;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;

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

        return Arrays.toString(arrayValue);

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

            if(index == -1 || newValue.length() == 0){

                newValue.append(arrayNumber[i]);

                if(i < arrayNumber.length -1) newValue.append(comma);

            }

        }

        return ordenar(newValue.toString());

    }

    public String checkComma(String value){

        value = value.replaceAll("\\s+","");

        String[] arrayValueA = value.split(comma);

        StringBuffer newValue = new StringBuffer();

        for(int i = 0; i < arrayValueA.length; i++){

            if(!arrayValueA[i].equals("")){

                newValue.append(arrayValueA[i]);
                if(i < arrayValueA.length -1) newValue.append(comma);

            }

        }

        return newValue.toString().trim();

    }

    //calculor individuais

    public StringBuilder calcularUniao(String inputA, String inputB){

        String resultado = checkComma(inputA) +","+ checkComma(inputB);

        return new StringBuilder(removeEquals(resultado));
    }

    public StringBuilder calcularIntersecao(String inputA, String inputB){

        StringBuffer newCalculo = new StringBuffer();

        inputA  = inputA.trim();
        inputB = inputB.trim();
        String[] array = inputA.split("");
        if(array[array.length - 1].equals(","))Log.i("texot","valor "+array[array.length-1]);

        return new StringBuilder();
    }

    public StringBuilder calcularComplementar(String inputA, String inputB, String inputC){
        return new StringBuilder();
    }

    public StringBuilder calcularDiferencaAB(String inputA, String inputB){
        return new StringBuilder();
    }

    public StringBuilder calcularDiferencaBA(String inputA, String inputB){
        return new StringBuilder();
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
