package elementar.analise.combinatoria.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.latex.GeradorAnagrama;
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
        return inputAnagrama.getEditText().getText().toString();
    }

    private void setResultado() {
        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);

        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.5f, 0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoAnagrama.setText("RESULTADO AQUI");
                LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1.5f, 0);
            }

        }, DELAY_TIME);

        // Cancela as animações
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);
    }

    private void calcularAnagrama() {

        // Campo não vazio
        if (!calculadora.verificarCampoVazio(inputAnagrama)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                if (Anagrama.getEntradaAnagrama().equals(palavraDigitada)) {
                    MainActivity.hideKeyboard(getActivity());
                    showToastMessage("O anagrama já foi calculado!");

                } else {
                    setResultado();

                    palavraDigitada = Anagrama.getEntradaAnagrama();
                    jaCalculou = true;
                }

            } else {
                setResultado();

                jaCalculou = true;
                palavraDigitada = Anagrama.getEntradaAnagrama();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

}