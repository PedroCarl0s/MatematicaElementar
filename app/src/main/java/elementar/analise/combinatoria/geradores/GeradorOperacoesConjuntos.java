package elementar.analise.combinatoria.geradores;

import com.google.android.material.textfield.TextInputLayout;

public class GeradorOperacoesConjuntos {

    public GeradorOperacoesConjuntos(){

    }

    //calculor individuais

    public StringBuilder calcularUniao(String inputA, String inputB){

        String resultado = inputA +","+ inputB;

        return new StringBuilder(resultado);
    }

    public StringBuilder calcularIntersecao(String inputA, String inputB){
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
