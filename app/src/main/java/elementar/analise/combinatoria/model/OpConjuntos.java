package elementar.analise.combinatoria.model;

import java.util.List;

public class OpConjuntos {

    private String conjuntoA;
    private String conjuntoB;
    private String conjuntoU;
    private List<String> respostaConjuntos;

    public OpConjuntos(String conjuntoA,String conjuntoB, String conjuntoU, List<String> resultados){

        this.conjuntoA = conjuntoA;
        this.conjuntoB = conjuntoB;
        this.conjuntoU = conjuntoU;
        this.respostaConjuntos = resultados;

    }

    public String getConjuntoA() {
        return conjuntoA;
    }

    public void setConjuntoA(String conjuntoA) {
        this.conjuntoA = conjuntoA;
    }

    public String getConjuntoB() {
        return conjuntoB;
    }

    public void setConjuntoB(String conjuntoB) {
        this.conjuntoB = conjuntoB;
    }

    public String getConjuntoU() {
        return conjuntoU;
    }

    public void setConjuntoU(String conjuntoU) {
        this.conjuntoU = conjuntoU;
    }

    public List<String> getRespostaConjuntos() {
        return respostaConjuntos;
    }

    public void setRespostaConjuntos(List<String> respostaConjuntos) {
        this.respostaConjuntos = respostaConjuntos;
    }
}
