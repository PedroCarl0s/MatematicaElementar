package elementar.analise.combinatoria.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.latex.GeradorFormulas;
import elementar.matematica.pedrock.matemticaelementar.LottieController;
import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.matematica.pedrock.matemticaelementar.TextInputController;
import io.github.kexanie.library.MathView;


public class Arranjo extends Fragment {

    private View view;
    private Handler handler;
    private Calculadora calculadora = Calculadora.getInstance();
    private GeradorFormulas geradorFormulas = GeradorFormulas.getInstance();

    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaArranjo, resultadoArranjo;
    private static int valorElementos, valorPosicoes;
    private Button btnCalcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;
    private static final int ERRO_CONVERSAO = -10000;

    public Arranjo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_arranjo, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;
        LottieController.changeAnimationVisibility(view, animationSwipe, ID_SWIPE, currentOrientation);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();

        init();

        // Método onClick do botão para calcular o Arranjo
        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularArranjo(view, Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes());
            }
        });

        // Troca o título do TextInput ao clicar
        TextInputController.setLabelTextInput(this.inputElementos, this.txtElementos, "Valor de n", "Elementos a arranjar");

        // Troca o título do TextInput ao clicar
        TextInputController.setLabelTextInput(this.inputPosicoes, this.txtPosicoes, "Valor de p", "Posições a arranjar");

        txtPosicoes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    calcularArranjo(view, Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes());

                    return true;
                }
                return false;
            }
        });

    }

    // Inicializa componentes de Input, MathView e Button
    public void init() {
        this.inputElementos = view.findViewById(R.id.elementos_arranjo);
        this.inputPosicoes = view.findViewById(R.id.posicoes_arranjo);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);


        this.formulaArranjo = view.findViewById(R.id.formula_arranjo);
        this.resultadoArranjo = view.findViewById(R.id.resultado_arranjo);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        String formulaArranjo = "$$\\normalsize \\bold{Formula}$$" + " $${A(n, p)} = \\frac{n!} {(n-p)!}, n \\geqslant p$$";

//        String teste = "$$ x = \\begin{cases}" +
//                " n = \\text{numero de elementos a arranjar }  \\\\" +
//                " p = \\text{numero de posicoes a arranjar }" +
//                " \\end{cases}$$";

        this.formulaArranjo.setText(formulaArranjo);
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    public void calcularArranjo(View view, int valorElementos, int valorPosicoes) {

        if (calculadora.validarEntradasPermutacao(Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes() ,inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com os mesmos valores, não é necessário recalcular
                if ((Arranjo.getNumeroElementos() == this.valorElementos) && Arranjo.getNumeroPosicoes() == this.valorPosicoes) {
                    inputElementos.setHint("Elementos a arranjar");
                    inputPosicoes.setHint("Posições a arranjar");

                    showToastMessage("O valor já foi calculado!");

                // Uma ou as duas entradas distintas, é necessário calcular
                } else {
                    setResultado(valorElementos, valorPosicoes);

                    this.valorElementos = Arranjo.getNumeroElementos();
                    this.valorPosicoes = Arranjo.getNumeroPosicoes();

                    jaCalculou = true;
                }

            // Muda estado da variável jaCalculou e calcula (apenas no primeiro cálculo)
            } else {
                setResultado(valorElementos, valorPosicoes);

                jaCalculou = true;
                this.valorElementos = Arranjo.getNumeroElementos();
                this.valorPosicoes = Arranjo.getNumeroPosicoes();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setResultado(final int valorElementos, final int valorPosicoes) {
        inputElementos.setHint("Elementos a arranjar");
        inputPosicoes.setHint("Posições a arranjar");

        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);

        // Inicia a animação de escrita
        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.5f, 0);

        // Delay para mostrar animação + resultado
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoArranjo.setText(geradorFormulas.gerarResultadoArranjo(valorElementos, valorPosicoes));
                LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 2);

            }

        }, DELAY_TIME);

        // Cancela as animações
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);
    }

    public static int getNumeroElementos() {
        int elementos;

        try {
            elementos =  Integer.parseInt(inputElementos.getEditText().getText().toString());

        } catch (Exception e) {
            return ERRO_CONVERSAO;
        }

        return elementos;
    }

    public static int getNumeroPosicoes() {
        int posicoes;

        try {
            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());

        } catch (Exception error) {
            return ERRO_CONVERSAO;
        }

        return posicoes;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("elementos", Integer.toString(Arranjo.getNumeroElementos()));
        outState.putString("posicoes", Integer.toString(Arranjo.getNumeroPosicoes()));
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