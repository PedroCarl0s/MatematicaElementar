package elementar.analise.combinatoria.calculadoras;


import elementar.analise.combinatoria.combinacao.GeradorCombinacao;

public class CalculadoraCombinacao {

    private static CalculadoraCombinacao INSTANCE = null;
    private Calculadora calculadoraGeral = Calculadora.getInstance();
    private GeradorCombinacao geradorEspecifico = new GeradorCombinacao();


    private CalculadoraCombinacao() {

    }

    public static synchronized CalculadoraCombinacao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CalculadoraCombinacao();
        }
        return INSTANCE;
    }

    // Verifica se o valor de P no denominador é igual a (n-p)
    public boolean getIgualdade(int posicoesDenominador, int elementosMenosPosicoes) {
        return posicoesDenominador == elementosMenosPosicoes;
    }

    // Retorna o maior valor do denominador, para desenvolver o fatorial do numerador até esse maior valor para simplificarc
    public int getMaiorDenominador(int valorPosicoes, int elementosMenosPosicoes) {
        if (valorPosicoes > elementosMenosPosicoes) {
            return valorPosicoes;
        }

        return elementosMenosPosicoes;
    }

    public int getMenorDenominador(int posicoesDenominador, int elementosMenosPosicoes) {
        if (posicoesDenominador < elementosMenosPosicoes) {
            return posicoesDenominador;
        }

        return elementosMenosPosicoes;
    }

    // Método que seta o maior valor e o secundário, para ser usado na simplificação de Combinação
    public void setMaiorValorESecundario(int valorElementos, int valorPosicoes, int maior, int auxSecundario) {

        int elementosMenosPosicoes = valorElementos - valorPosicoes;

        if (geradorEspecifico.verificarIgualdade(elementosMenosPosicoes, valorPosicoes)) {
            maior = elementosMenosPosicoes;
            auxSecundario = valorPosicoes;

        } else {
            maior = getMaiorDenominador(valorPosicoes, elementosMenosPosicoes);
            auxSecundario = getMenorDenominador(valorPosicoes, elementosMenosPosicoes);
        }

    }

    private boolean getExisteValorZero(int posicoesDenominador, int elementosMenosPosicoes) {
        return (posicoesDenominador == 0 || elementosMenosPosicoes == 0);
    }

    public int getDiferenteDeZero(int posicoesDenominador, int elementosMenoPosicoes) {
        if (posicoesDenominador != 0) {
            return posicoesDenominador;
        }

        return elementosMenoPosicoes;
    }

    private String desenvolverValorAteN(int valorElementos, int fim) {

        StringBuilder valores = new StringBuilder();
        valores.append(valorElementos);

        for (int e = valorElementos-1; e >= fim; e--) {
            valores.append(".");
            valores.append(e);
        }

        return valores.toString();

    }


//    public String geraFatorialElementoss(int valorElementos, int valorPosicoes) {
//
//        int elementosMenosPosicoes = valorElementos - valorPosicoes;
//        String fatorialGerado;
//
//        // n == p
//        if (valorElementos == valorPosicoes) {
//
//            return "1";
//
//        // n != p
//        } else {
//
//            // p == (n-p)
//            if (valorPosicoes == elementosMenosPosicoes) {
//
//                // p == 1
//                if (valorPosicoes == 1) {
//                    fatorialGerado = desenvolverValorAteN(valorElementos, 1);
//
//                //p != 1
//                } else {
//                    fatorialGerado = desenvolverValorAteN(valorElementos, valorPosicoes);
//                }
//
//            // p != (n-p)
//            } else {
//
//                if (valorPosicoes == 0) {
//
//                }
//
//            }
//        }
//
//
//    }



    public String gerarFatorialElementos(int elementos, int posicoes) {

        // Número de elementos a arranjar maior que zero, é necessário desenvolver o fatorial
        if (elementos > 0) {

            int fim;

            // Em quaisquer condições abaixo, é necessário desenvolver até o 1
            if (elementos == posicoes || posicoes == 0) {
                fim = elementos;

            // O fatorial do nº de elementos irá até o valor do maior elemento do denominador (p ou [n-p])
            } else {
                int maior = getMaiorDenominador(posicoes, elementos-posicoes);

                fim = maior;
            }

            StringBuilder numerador = new StringBuilder();

            // Gera os valores do numerador até o menor valor possível para simplificar
            for (int e = elementos; e >= fim; e--) {
                numerador.append(e);
                numerador.append(".");
            }

            // Remove o último caracter em excesso (um ponto final)
            numerador.delete(numerador.length()-1, numerador.length());

            return numerador.toString();

        // 0! = 1
        } else if (elementos == 0) {
            return "1";

        } else {
            return "não suporta valores negativos";
        }

    }


    // Gerar o resultado final, com base no valor do numerador e denominadores
    public long gerarResultadoFinalCombinacao(int elementos, int posicoes) {

        int elementosMenosPosicoes = elementos - posicoes;

        String valoresFinais = gerarFatorialElementos(elementos, posicoes);

        valoresFinais = geradorEspecifico.removerUltimoValor(valoresFinais);

        int aux;
        long fatorialAux;

        if (!valoresFinais.equals("0") && !valoresFinais.equals("1")) {

            // Troca o ponto finais por ponto e vírgula, para depois separar com .split
            valoresFinais = valoresFinais.replace(".", ";");

            long resultado = 1;
            String[] valores = valoresFinais.split(";");

            for (String atual: valores) {
                resultado *= Integer.parseInt(atual);
            }

            // Verifica se P é igual a (N-P), pois é necessário fazer o fatorial do denominador
            if (getIgualdade(posicoes, elementosMenosPosicoes)) {
                aux = getMaiorDenominador(posicoes, elementosMenosPosicoes);
                fatorialAux = calculadoraGeral.gerarResultadoFatorial(aux);

            } else {
                boolean existeZero = getExisteValorZero(posicoes, elementosMenosPosicoes);

                // Caso tenha zero, é necessário pegar o maior valor do denominador e não calcular o fatorial do mesmo
                if (existeZero) {
                    aux = getMaiorDenominador(posicoes, elementosMenosPosicoes);
                    fatorialAux = aux;

                // Sem zeros no denominador, é necessário fazer o fatorial do menor valor do denominador
                } else {
                    aux = getMenorDenominador(posicoes, elementosMenosPosicoes);
                    fatorialAux = calculadoraGeral.gerarResultadoFatorial(aux);
                }
            }

            // Divisão para calcular o resultado final
            resultado = resultado / fatorialAux;


            return resultado;

        } else if (valoresFinais.equals("0")) {
            return 1;

        } else {
            return Long.parseLong(valoresFinais);
        }

    }


}