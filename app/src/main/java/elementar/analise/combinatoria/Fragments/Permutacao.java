package elementar.analise.combinatoria.Fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
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
import elementar.analise.combinatoria.GeradorFormulas;
import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;


public class Permutacao extends Fragment {

    private View view;

    private static MathView formulaPermutacao, resultadoPermutacao;

    private static TextInputEditText txtPermutacao;
    private static TextInputLayout inputPermutacao;

    private Button btnCalcular;
    private Handler handler;
    private final int DELAY_TIME = 750;

    private LottieAnimationView animationWrite;
    private final int id_write = R.id.animation_write;

    private boolean jaCalculou = false;
    private String valorEntrada;


    public Permutacao() {
        // Required empty public constructor
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
        this.txtPermutacao = (TextInputEditText) view.findViewById(R.id.inputPermutacao);
        this.inputPermutacao = (TextInputLayout) view.findViewById(R.id.txt_permutacao);
        this.btnCalcular = (Button) view.findViewById(R.id.btn_calcular);
        this.animationWrite = (LottieAnimationView) view.findViewById(R.id.animation_write);


        String formula = "$$\\normalsize \\bold{\\text{Formula da Permutacao}}$$"
                + "$$ n! = n.(n-1).(n-2) \\ ... \\ 2.1$$";

        formulaPermutacao.setText(formula);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
        changeTextInputTitle();

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularPermutacao();
            }
        });
    }

    // Troca o título do TextInput ao clicar
    private void changeTextInputTitle() {
        txtPermutacao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputPermutacao.setHint("Fatorial de");

                } else {
                    inputPermutacao.setHint("Insira o valor de n");
                }

            }
        });
    }

    private void calcularPermutacao() {

        if (Calculadora.validarEntradaPermutacao(inputPermutacao)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                if (Permutacao.getEntradaPermutacao().equals(valorEntrada)) {
                    showToastMessage("O valor já foi calculado!");

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
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setResultado() {
        animationWrite = view.findViewById(R.id.animation_write);

        animationWrite.setVisibility(View.VISIBLE);
        startAnimation(view, animationWrite, id_write, "write.json", 2f, 0);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoPermutacao.setText(GeradorFormulas.gerarResultadoPermutacao());
                //startAnimation(view, animationWrite, id_write, "write,json", 1.5f, 0);
            }

        }, DELAY_TIME);

    }

    public static String getEntradaPermutacao() {
        int permutacao;

        try {
            permutacao = Integer.parseInt(inputPermutacao.getEditText().getText().toString());

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

            this.txtPermutacao.setText(savedInstanceState.getString("entrada_permutacao"));
            this.resultadoPermutacao.setText(savedInstanceState.getString("resultado_permutacao"));
        }
    }
}