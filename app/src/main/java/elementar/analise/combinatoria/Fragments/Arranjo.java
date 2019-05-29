package elementar.analise.combinatoria.Fragments;

import android.animation.Animator;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.GeradorFormulas;
import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;


public class Arranjo extends Fragment {

    private View view;
    private Handler handler;

    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaArranjo, resultadoArranjo;
    private static String valorElementos, valorPosicoes;
    private Button button_calcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int id_write = R.id.animation_write, id_swipe = R.id.animation_swipe;
    private final int DELAY_TIME = 750;

    public Arranjo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     }

    // Inicializa componentes de Input, MathView e Button
    public void init() {
        this.inputElementos = view.findViewById(R.id.elementos_arranjo);
        this.inputPosicoes = view.findViewById(R.id.posicoes_arranjo);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);


        this.formulaArranjo = view.findViewById(R.id.formula_arranjo);
        this.resultadoArranjo = view.findViewById(R.id.resultado_arranjo);

        this.button_calcular = view.findViewById(R.id.btn_calcular);

        String formulaArranjo = "$$\\normalsize \\bold{\\text{Formula do Arranjo}}$$" + " $${A(n, p)} = \\frac{n!} {(n-p)!}, n \\geqslant p$$";

        String teste = "$$ x = \\begin{cases}" +
                " n = \\text{numero de elementos a arranjar }  \\\\" +
                " p = \\text{numero de posicoes a arranjar }" +
                " \\end{cases}$$";

        String letra = "$$\\normalsize \\bold{\\text{Formula do Arranjo}}$$";

        this.formulaArranjo.setText(formulaArranjo);
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    public void calcularArranjo(View view) {

        if (Calculadora.validarEntradas(inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com os mesmos valores, não é necessário recalcular
                if (Arranjo.getNumeroElementos().equals(valorElementos) && Arranjo.getNumeroPosicoes().equals(valorPosicoes)) {
                    showToastMessage("O valor já foi calculado!");

                // Uma ou as duas entradas distintas, é necessário calcular
                } else {
                    setResultado();

                    valorElementos = Arranjo.getNumeroElementos();
                    valorPosicoes = Arranjo.getNumeroPosicoes();

                    jaCalculou = true;
                }

            // Muda estado da variável jaCalculou e calcula (apenas no primeiro cálculo)
            } else {
                setResultado();

                jaCalculou = true;
                valorElementos = Arranjo.getNumeroElementos();
                valorPosicoes = Arranjo.getNumeroPosicoes();
            }

        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void startAnimation(View view, LottieAnimationView animationView, int id ,String jsonFile, float speed, int loops) {
        animationView = view.findViewById(id);
        animationView.setAnimation(jsonFile);
        animationView.setSpeed(speed);
        animationView.setRepeatCount(loops);
        animationView.playAnimation();
    }

    private void setResultado() {
        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);

        startAnimation(view, animationWrite, id_write ,"write.json",1.5f, 0);

        // Delay para mostrar animação + resultado
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoArranjo.setText(GeradorFormulas.gerarResultado());
                startAnimation(view, animationSwipe, id_swipe, "swipeup.json", 1f, 2);
            }

        }, DELAY_TIME);

        cancelLottieAnimation(animationWrite);
        cancelLottieAnimation(animationSwipe);
    }


    // Cancela e esconde a animações Lottie
    private void cancelLottieAnimation(final LottieAnimationView animationView) {
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animationView.cancelAnimation();
                animationView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }


        });
    }


    public static String getNumeroElementos() {
        int elementos;

        try {
            elementos =  Integer.parseInt(inputElementos.getEditText().getText().toString());

        } catch (Exception e) {
            return "";
        }

        return Integer.toString(elementos);
    }

    public static String getNumeroPosicoes() {
        int posicoes;

        try {
            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());

        } catch (Exception error) {
            return "";
        }

        return Integer.toString(posicoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_arranjo, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        init();

        // Método onClick do botão para calcular o Arranjo
        button_calcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularArranjo(view);
            }
        });

        // Troca o título do TextInput ao clicar
        txtElementos.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    inputElementos.setHint("N");

                } else {
                    inputElementos.setHint("Elementos a arranjar");
                }
            }
        });

        // Troca o título do TextInput ao clicar
        txtPosicoes.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputPosicoes.setHint("P");

                } else {

                    inputPosicoes.setHint("Posições a arranjar");
                }
            }
        });
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("elementos", Arranjo.getNumeroElementos());
        outState.putString("posicoes", Arranjo.getNumeroPosicoes());
        outState.putString("latex", resultadoArranjo.getText());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard(getActivity());

            this.txtElementos.setText(savedInstanceState.getString("elementos"));
            this.txtPosicoes.setText(savedInstanceState.getString("posicoes"));

            MathView calculoRecuperado = view.findViewById(R.id.resultado_arranjo);
            calculoRecuperado.setText(savedInstanceState.getString("latex"));
        }

    }

}