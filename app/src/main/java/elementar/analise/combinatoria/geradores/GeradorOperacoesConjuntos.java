package elementar.analise.combinatoria.geradores;

import android.util.Log;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class GeradorOperacoesConjuntos {

    public GeradorOperacoesConjuntos(){

    }

    private String removeCommaFirsLast(String value){

        String[] arrayValueA = value.split("");

        StringBuffer newValue = new StringBuffer();

        if(arrayValueA[1].equals(",")){
            for(int i = 2   ; i < arrayValueA.length;i++){
                newValue.append(arrayValueA[i]);
            }
        }

        if(newValue.length() == 0) {

            newValue.append(value);

        }

        String[] newArray = newValue.toString().split("");

        if(newArray[newArray.length - 1].equals(",")){

            newValue.delete(0,newValue.length());

            for(int i = 0; i < newArray.length - 1;i++){

                newValue.append(newArray[i]);

            }
        }

        return newValue.toString().trim();

    }

    //calculor individuais

    public StringBuilder calcularUniao(String inputA, String inputB){

        String resultado = removeCommaFirsLast(inputA) +","+ removeCommaFirsLast(inputB);

        return new StringBuilder(resultado);
    }

    public StringBuilder calcularIntersecao(String inputA, String inputB){

        StringBuffer newCalculo = new StringBuffer();

        inputA  = inputA.trim();
        inputB = inputB.trim();
        String[] array = inputA.split("");
        if(array[array.length - 1].equals(","))Log.i("texot","valor "+array[array.length-1]);
        for(String s : array){
            Log.i("texot","valor "+s);
        }
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
