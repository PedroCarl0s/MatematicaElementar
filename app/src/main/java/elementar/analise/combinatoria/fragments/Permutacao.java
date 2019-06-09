package elementar.analise.combinatoria.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
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

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.latex.GeradorFormulas;
import elementar.lottie.LottieController;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.analise.combinatoria.controller.TextInputController;
import io.github.kexanie.library.MathView;


public class Permutacao extends Fragment {

    private View view;

    private static MathView formulaPermutacao;
    private static MathView resultadoPermutacao;

    private static TextInputLayout inputPermutacao;
    private static TextInputEditText txtPermutacao;

    private Button btnCalcular;
    private Handler handler;
    private final int DELAY_TIME = 750;

    private LottieAnimationView animationWrite;
    private final int ID_WRITE = R.id.animation_write;

    private boolean jaCalculou = false;
    private String valorEntrada;

    private Calculadora calculadora = Calculadora.getInstance();


    public Permutacao() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_permutacao, container, false);

        return view;
    }

    private void init() {
        this.formulaPermutacao = (MathView) view.findViewById(R.id.formula_permutacao);
        this.resultadoPermutacao = (MathView) view.findViewById(R.id.resultado_permutacao);

        this.inputPermutacao = (TextInputLayout) view.findViewById(R.id.input_permutacao);
        this.txtPermutacao = (TextInputEditText) view.findViewById(R.id.txt_permutacao);

        this.btnCalcular = (Button) view.findViewById(R.id.btn_calcular);
        this.animationWrite = (LottieAnimationView) view.findViewById(R.id.animation_write);

        String formula = "$$\\normalsize \\bold{Formula}$$"
                + "$$ n! = n.(n-1).(n-2) \\ ... \\ 2.1$$";

        formulaPermutacao.setText(formula);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
        TextInputController.setLabelTextInput(this.inputPermutacao, this.txtPermutacao, "Fatorial de", "Valor de n");

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularPermutacao();
            }
        });

        txtPermutacao.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    calcularPermutacao();

                    return true;
                }
                return false;
            }
        });

    }

    private void calcularPermutacao() {

        if (calculadora.validarEntradaPermutacao(inputPermutacao)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                if (Permutacao.getEntradaPermutacao().equals(valorEntrada)) {
                    MainActivity.hideKeyboard(getActivity());

                    inputPermutacao.setHint("Fatorial de");
                    showToastMessage("A permutação já foi calculada!");

                } else {
                    setResultado();

                    valorEntrada = Permutacao.getEntradaPermutacao();
                    jaCalculou = true;
                }

            } else {
                setResultado();

                jaCalculou = true;
                valorEntrada = Permutacao.getEntradaPermutacao();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setResultado() {
        inputPermutacao.setHint("Fatorial de");
        animationWrite.setVisibility(View.VISIBLE);

        startAnimation(view, animationWrite, ID_WRITE, "write.json", 2f, 0);

        final int entradaPermutacao = Integer.parseInt(Permutacao.getEntradaPermutacao());

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoPermutacao.setText(GeradorFormulas.gerarResultadoPermutacao(entradaPermutacao));
            }

        }, DELAY_TIME);

        LottieController.cancelLottieAnimation(animationWrite);
    }

    public static String getEntradaPermutacao() {
        int permutacao;

        try {
            permutacao = Integer.parseInt(inputPermutacao.getEditText().getText().toString());

            inputPermutacao.getEditText().setText(Integer.toString(permutacao));

        } catch (Exception e) {
            return "";
        }

        return Integer.toString(permutacao);
    }

    private static String getResultadoLatex() {
        return resultadoPermutacao.getText();
    }

    private void startAnimation(View view, LottieAnimationView animationView, int id, String jsonFile, float speed, int loops) {
        animationView = view.findViewById(id);
        animationView.setAnimation(jsonFile);
        animationView.setSpeed(speed);
        animationView.setRepeatCount(loops);
        animationView.playAnimation();
    }

    // Salva a entrada da permutação + resultado
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("entrada_permutacao", getEntradaPermutacao());
        outState.putString("resultado_permutacao", getResultadoLatex());
    }

    // Recupera e modifica o campo da entrada + MathView do resultado
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard((getActivity()));

            txtPermutacao.setText(savedInstanceState.getString("entrada_permutacao"));

            MathView calculoRecuperado = view.findViewById(R.id.resultado_permutacao);
            calculoRecuperado.setText(savedInstanceState.getString("resultado_permutacao"));

        }
    }
}