package elementar.analise.combinatoria.geradores;


import elementar.analise.combinatoria.Calculadora;

public abstract class GeradorFormulas {

    private static Calculadora calculadora = Calculadora.getInstance();


    // Gera o desenvolvimento do fatorial
    public static String gerarDesenvolvimentoFatorial(int valorElementos, int valorPosicoes, String valorNumerador, int elementosMenosPosicoes) {
        String desenvolvimento = "$$A(" + valorElementos + ", " + valorPosicoes + ") = \\frac{" + valorNumerador + "!} " +
                "{" + elementosMenosPosicoes + "!}$$";

        return desenvolvimento;
    }

    // Gera o resultado final, contendo a inicial (Arranjo = A e Combinação = C)
    public String gerarResultadoFinal(String tipo, int valorElementos, int valorPosicoes, long resultadoFinal) {

        String resultado = "$$\\bold{Resultado}$$" + "$$" + tipo + "(" + valorElementos + ", " + valorPosicoes + ") = " + resultadoFinal + "$$";

        return resultado;
    }

    // Remove o último valor que continha o fatorial
    public static String removerUltimoValor(String fatorialNumerador) {

        // Exemplo: 4.3.2.1" retornará os caracteres a partir do 4 até  2. Igual a 4.3.2
        String[] valores = fatorialNumerador.replace(".", ";").split(";");

        // Pega o primeiro valor da String (índice zero)
        int indexZero = Integer.parseInt(valores[0]);

        if (indexZero > 1) {

            // Valor que será removido, fica no último índice do vetor
            String valorARemover = valores[valores.length-1];

            int tamanhoUltimo = valorARemover.length();
            int tamanhoFatorial = fatorialNumerador.length();

            /*Removendo o último valor, e o ponto final excedente
             Ex.: Remover o valor 10 e o ponto final -> 11.10
             Dez possui tamanho dois, e então decrementando dois índices da substring,
             o 10 é removido, mas o ponto final ainda fica. Para remover o ponto final,
             decrementa-se +1 índice da String. RESULTADO = 11
             */
            fatorialNumerador = fatorialNumerador.substring(0, (tamanhoFatorial - tamanhoUltimo)-1);

            return fatorialNumerador;

        } else {

            // 0! ou 1!, não tem o que remover então retorna o próprio valor
            return fatorialNumerador;
        }

    }

    public String gerarStringFatorial (long valorEntrada) {
        return valorEntrada + "!";
    }

    // Gera o resultado final, contendo o passo a passo construído
    public String gerarResultadoPermutacaoLatex(String titulo, long valorEntrada) {

        String resultadoParcial = Calculadora.gerarDesenvolvimentoPermutacao(valorEntrada);
        String textoResultado = Long.toString(calculadora.gerarResultadoPermutacao(valorEntrada));

        String resultado = adicionarTitulo(titulo);

        // Para questões de quebrar a visualização do passo a passo (2 até 10 fatorial, imprimo em uma única linha)
        if (valorEntrada  > 1 && valorEntrada <= 10) {
            resultado += "$$" + gerarStringFatorial(valorEntrada) +  " = " + resultadoParcial + " = " + textoResultado + "$$";

        // Valor da entrada maior que 10 fatorial, quebro o resultado em duas linhas
        } else if (valorEntrada > 10) {
            resultado += "$$"+ gerarStringFatorial(valorEntrada) +  " = " + resultadoParcial + "$$";
            resultado += "$$" + gerarStringFatorial(valorEntrada) + " = " + textoResultado + "$$";

        // 0 ou 1 fatorial, apenas repito o valor com sinal de fatorial, sendo igual a ele mesmo. Ex.: 0! = 0 ou 1! = 1
        } else if (valorEntrada == 0 || valorEntrada == 1) {
            resultado += "$$" + valorEntrada + "!" + " = " + textoResultado + "$$";

        // Os valores devem ser maiores ou iguais a zero
        } else {
            return "Apenas valores inteiros positivos";
        }

        return resultado;
    }

    private String adicionarTitulo(String titulo) {
        if (titulo.isEmpty()) {
            return "";
        }
        return "$$\\bold{Resultado}$$";
    }

}