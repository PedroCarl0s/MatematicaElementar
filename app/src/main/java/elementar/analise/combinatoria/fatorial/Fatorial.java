package elementar.analise.combinatoria.fatorial;


import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.os.Handler;
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

import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.geradores.GeradorFatorial;
import elementar.lottie.LottieController;
import elementar.analise.combinatoria.activitys.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.analise.combinatoria.controller.TextInputController;
import io.github.kexanie.library.MathView;


public class Fatorial extends Fragment {

    private View view;

    private static MathView formulaFatorial;
    private static MathView resultadoFatorial;

    private static TextInputLayout inputFatorial;
    private static TextInputEditText txtFatorial;

    private Button btnCalcular;
    private Handler handler;
    private final int DELAY_TIME = 750;

    private LottieAnimationView animationWrite;
    private final int ID_WRITE = R.id.animation_write;

    private boolean jaCalculou = false;
    private String valorEntrada;

    private Calculadora calculadora = Calculadora.getInstance();
    private GeradorFatorial gerador = new GeradorFatorial();


    public Fatorial() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_fatorial, container, false);

        return view;
    }

    private void init() {
        this.formulaFatorial = (MathView) view.findViewById(R.id.formula_fatorial);
        this.resultadoFatorial = (MathView) view.findViewById(R.id.resultado_fatorial);

        this.inputFatorial = (TextInputLayout) view.findViewById(R.id.input_fatorial);
        this.txtFatorial = (TextInputEditText) view.findViewById(R.id.txt_fatorial);

        this.btnCalcular = (Button) view.findViewById(R.id.btn_calcular);
        this.animationWrite = (LottieAnimationView) view.findViewById(R.id.animation_write);

        String formula = "$$\\normalsize \\bold{Formula}$$"
                + "$$ n! = n.(n-1).(n-2) \\ ... \\ 2.1$$";

        formulaFatorial.setText(formula);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
        TextInputController.setLabelTextInput(this.inputFatorial, this.txtFatorial, "Fatorial de", "Valor de n");

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularFatorial();
            }
        });

        txtFatorial.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    calcularFatorial();

                    return true;
                }
                return false;
            }
        });

    }

    private void calcularFatorial() {

        if (calculadora.validarEntradaFatorial(inputFatorial)) {

            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                if (Fatorial.getEntradaFatorial().equals(valorEntrada)) {
                    MainActivity.hideKeyboard(getActivity());

                    inputFatorial.setHint("Fatorial de");
                    showToastMessage("O fatorial já foi calculado!");

                } else {
                    setResultado();

                    valorEntrada = Fatorial.getEntradaFatorial();
                    jaCalculou = true;
                }

            } else {
                setResultado();

                jaCalculou = true;
                valorEntrada = Fatorial.getEntradaFatorial();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setResultado() {

        inputFatorial.setHint("Fatorial de");
        animationWrite.setVisibility(View.VISIBLE);

        startAnimation(view, animationWrite, ID_WRITE, "write.json", 2f, 0);

        final int entradaFatorial = Integer.parseInt(Fatorial.getEntradaFatorial());

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoFatorial.setText(gerador.gerarResultadoFatorialLatex("Resultado", entradaFatorial));
            }

        }, DELAY_TIME);

        LottieController.cancelLottieAnimation(animationWrite);
    }

    public static String getEntradaFatorial() {
        int fatorial;

        try {
            fatorial = Integer.parseInt(inputFatorial.getEditText().getText().toString());

            inputFatorial.getEditText().setText(Integer.toString(fatorial));

        } catch (Exception e) {
            return "";
        }

        return Integer.toString(fatorial);
    }

    private static String getResultadoLatex() {
        return resultadoFatorial.getText();
    }

    private void startAnimation(View view, LottieAnimationView animationView, int id, String jsonFile, float speed, int loops) {
        animationView = view.findViewById(id);
        animationView.setAnimation(jsonFile);
        animationView.setSpeed(speed);
        animationView.setRepeatCount(loops);
        animationView.playAnimation();
    }

}