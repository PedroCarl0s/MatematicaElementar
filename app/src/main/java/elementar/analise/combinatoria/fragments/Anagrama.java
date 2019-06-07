package elementar.analise.combinatoria.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.latex.GeradorAnagrama;
import elementar.analise.combinatoria.latex.GeradorFormulas;
import elementar.matematica.pedrock.matemticaelementar.LottieController;
import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.matematica.pedrock.matemticaelementar.TextInputController;
import io.github.kexanie.library.MathView;


public class Anagrama extends Fragment {

    private View view;
    private Handler handler;

    private final int DELAY_TIME = 750;

    private static TextInputLayout inputAnagrama;
    private static TextInputEditText txtAnagrama;
    private static LottieAnimationView animationWrite, animationSwipe;

    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private boolean jaCalculou = false;
    private String palavraDigitada;

    private MathView formulaAnagrama, resultadoAnagrama;
    private Calculadora calculadora = Calculadora.getInstance();

    private Button btnCalcular;

    public Anagrama() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
        TextInputController.setLabelTextInput(inputAnagrama, txtAnagrama, "Anagrama de", "Insira uma palavra");

        // Ação do botão para calcular
        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputAnagrama.setError(null);
                calcularAnagrama();
            }
        });

        // Verifica se o usuário digitou de A até Z, ou algum espaço
        txtAnagrama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (Anagrama.getEntradaAnagrama().matches("[a-zA-Z ]*")) {
                        inputAnagrama.setError(null);
                        calcularAnagrama();

                        return true;
                    }
                }
                inputAnagrama.setError("Insira apenas letras!");
                MainActivity.hideKeyboard(getActivity());

                return false;

            }
        });

        this.formulaAnagrama.setText(GeradorAnagrama.gerarFormula());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_anagrama, container, false);

        return view;
    }

    private void init () {
        this.inputAnagrama = (TextInputLayout) view.findViewById(R.id.input_anagrama);
        this.txtAnagrama = (TextInputEditText) view.findViewById(R.id.txt_anagrama);

        this.formulaAnagrama = (MathView) view.findViewById(R.id.formula_anagrama);
        this.resultadoAnagrama = (MathView) view.findViewById(R.id.resultado_anagrama);

        this.btnCalcular = (Button) view.findViewById(R.id.btn_calcular);
    }


    // Retorna a palavra que foi digitada
    public static String getEntradaAnagrama() {
        return inputAnagrama.getEditText().getText().toString().toUpperCase();
    }

    public static int getTamanhodaPalavra(){
        return inputAnagrama.getEditText().getText().toString().toUpperCase().length();
    }

    private void setResultado(final HashMap<String,Integer> hashLetraEQuant) {
        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);

        MainActivity.hideKeyboard(getActivity());

        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.5f, 0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoAnagrama.setText(GeradorAnagrama.gerarDescricaoVariaveis(hashLetraEQuant));
                LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 2);
            }

        }, DELAY_TIME);

        // Cancela as animações
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);
    }

    private String gerarTrechoInicial(int tamanhoPalavra) {
        String trecho = "$$A(" + tamanhoPalavra + ", " + tamanhoPalavra + ") = P_" + tamanhoPalavra + "$$";

        return trecho;
    }

    private void calcularAnagrama() {

        // Campo não vazio
        if (!calculadora.verificarCampoVazio(inputAnagrama)) {
            MainActivity.hideKeyboard(getActivity());

            // Campo não vaz
            if (!Calculadora.verificarCampoVazio(inputAnagrama)) {

                // Verifica se tem apenas letras
                if (Anagrama.getEntradaAnagrama().matches("[a-zA-z]*")) {

                    // Retorna um HashMap de letra e valor
                    HashMap<String, Integer> novoArrayQuantPalavras = contarPalavrasIguais(getEntradaAnagrama());

                    int resultadoFinal = calcularResultadoAnagrama(getTamanhodaPalavra(), novoArrayQuantPalavras);
                    // Verifica se tem letras repetidas
                    if (verificarTemQuantPalavrasMaiorUm(novoArrayQuantPalavras)) {

                        setResultado(novoArrayQuantPalavras);

                    } else {

                        resultadoAnagrama.setText("Como nenhuma letra se repetiu, isso equivale a fazer um Arranjo onde:"+ GeradorFormulas.gerarResultadoPermutacao(getTamanhodaPalavra()) + gerarTrechoInicial(getTamanhodaPalavra()));

                    }

                } else {
                    inputAnagrama.setError("Insira apenas letras sem acento");
                }

            } else {
                MainActivity.hideKeyboard(getActivity());
            }
        }
    }

    private int calcularResultadoAnagrama(int tamanhoPalavra,HashMap<String,Integer> arrayNumeroDeLetras) {

        int valorPalavra = Calculadora.gerarResultadoPermutacao(tamanhoPalavra);
        int somaValorLetras = 1;

        for(int quantidadeRepet: arrayNumeroDeLetras.values()){
            somaValorLetras *= Calculadora.gerarResultadoPermutacao(quantidadeRepet);
        }

        return valorPalavra / somaValorLetras;

    }

    private boolean verificarTemQuantPalavrasMaiorUm(HashMap<String,Integer> listPalavras) {

        for(int valor : listPalavras.values()){

            if(valor > 1) return true;

        }
        return false;
    }

    private HashMap<String,Integer> contarPalavrasIguais(String palavra) {

        int length = palavra.length();
        HashMap<String,Integer> returnLetrasIguais = new HashMap<>();
        int[] contLetrasIguais = new int[222];

        for(int i = 0;i < length;i++){
            contLetrasIguais[palavra.charAt(i)]++;
        }

        char[] palavraDoArray = new char[length];

        for(int i = 0;i < length; i++){

            int cont = 0;
            palavraDoArray[i] = palavra.charAt(i);

            for(int j = 0; j <= i;j++){
                if(palavra.charAt(i) == palavraDoArray[j]){
                    cont++;
                }
            }

            if(cont == 1){
                String letra = String.valueOf(palavra.charAt(i));
                int valor = contLetrasIguais[palavra.charAt(i)];
                returnLetrasIguais.put(letra,valor);
            }
        }

        return returnLetrasIguais;

    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}