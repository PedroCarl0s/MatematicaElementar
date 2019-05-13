package elementar.analise.combinatoria;

import android.support.design.widget.TextInputLayout;
import android.view.View;

import elementar.matematica.pedrock.matemticaelementar.R;

public class Calculadora {

    private static int elementos, posicoes;


    private static boolean validarCampoVazio(TextInputLayout inputText) {
        String testeInput = inputText.getEditText().toString().trim();

        return testeInput.isEmpty();
    }

    private static boolean validarElementos(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {

        // Campo preenchido
        if (!validarCampoVazio(inputElementos)) {

            elementos = Integer.parseInt(inputElementos.toString());
            posicoes = Integer.parseInt(inputPosicoes.toString());

            // Condição necessária para realizar cálculo de arranjo A(n, p), onde n >= p
            if (elementos >= posicoes) {
                inputElementos.setError(null);
                return true;
            }
            inputElementos.setError("O número de elementos deve ser ≥ posições");

            return false;
        }

        // Campo vazio
        inputElementos.setError("Número de elementos não pode ser vazio!");

        return false;
    }

    private static boolean validarPosicoes(TextInputLayout inputPosicoes) {

        // Campo preenchido
        if (!validarCampoVazio(inputPosicoes)) {
            inputPosicoes.setError(null);
            return true;
        }

        // Campo vazio
        inputPosicoes.setError("Número de posições não pode ser vazio!");

        return false;
    }

    // Verifica se número de elementos e posições estão corretos
    public static boolean validarEntrada(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {
        return validarElementos(inputElementos, inputPosicoes) && validarPosicoes(inputPosicoes);
    }


    public static String gerarFatorialElementos(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {

        String n = inputElementos.getEditText().toString();
        String p = inputPosicoes.getEditText().toString();

        elementos = Integer.parseInt(n);
        posicoes = Integer.parseInt(p);

        StringBuilder numerador = new StringBuilder();

        // Gera os valores do numerador até o menor valor possível para simplificar
        for (int e = elementos; e >= (elementos-posicoes); e++) {
            numerador.append(Integer.toString(e));
            numerador.append(".");
        }

        // Remove o último caracter em excesso (um ponto final)
        numerador.delete(numerador.length()-1, numerador.length());
        numerador.append("!");

        return numerador.toString();
    }

    public static String gerarElementosMenosPosicoes(TextInputLayout inputElementos, TextInputLayout inputPosicoes) {
        return "(" + inputElementos.toString() + "-" + inputPosicoes + ")";
    }

}
