package elementar.analise.combinatoria.combinacao;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import elementar.analise.combinatoria.calculadoras.CalculadoraCombinacao;
import elementar.analise.combinatoria.controller.TextInputController;
import elementar.analise.combinatoria.geradores.GeradorCombinacao;
import elementar.lottie.LottieController;
import elementar.analise.combinatoria.activitys.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;



public class Combinacao extends Fragment {

    private View view;
    private Handler handler;
    private GeradorCombinacao gerador = new GeradorCombinacao();
    private Calculadora calculadoraGeral = Calculadora.getInstance();

    private CalculadoraCombinacao calculadora = CalculadoraCombinacao.getInstance();


    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaCombinacao, resultadoCombinacao;
    private static int valorElementos, valorPosicoes;
    private Button btnCalcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;
    private static final int ERRO_CONVERSAO = -100000;

    public Combinacao() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_combinacao, container, false);

        int currentOrientation = getResources().getConfiguration().orientation;
        LottieController.changeAnimationVisibility(view, animationSwipe, ID_SWIPE, currentOrientation);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        init();

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calcularCombinacao(Combinacao.getNumeroElementos(), Combinacao.getNumeroPosicoes());
            }
        });

        // Troca o titulo do TextInputElementos ao clicar
        TextInputController.setLabelTextInput(this.inputElementos, this.txtElementos, "Valor de n", "Elementos a Combinar");

        // Troca o título do TextInputPosicoes ao clicar
        TextInputController.setLabelTextInput(this.inputPosicoes, this.txtPosicoes, "Valor de P", "Posições a Combinar");

        txtPosicoes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    calcularCombinacao(Combinacao.getNumeroElementos(), Combinacao.getNumeroPosicoes());

                    return true;
                }
                return false;
            }
        });
    }


    private void init() {
        this.inputElementos = view.findViewById(R.id.elementos_combinacao);
        this.inputPosicoes = view.findViewById(R.id.posicoes_combinacao);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);

        this.formulaCombinacao = view.findViewById(R.id.formula_combinacao);
        this.resultadoCombinacao = view.findViewById(R.id.resultado_combinacao);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        String formulaCombinacao = "$$\\normalsize \\bold{Formula}$$" + "$${C(n, p)} = \\frac{n!} {p! \\ (n-p)!}, n \\geqslant p$$";

        this.formulaCombinacao.setText(formulaCombinacao);
    }

    public static int getNumeroElementos() {
        int elementos;

        try {
            elementos = Integer.parseInt(inputElementos.getEditText().getText().toString());

        } catch (Exception e) {
            return ERRO_CONVERSAO;
        }

        return elementos;
    }

    public static int getNumeroPosicoes() {
        int posicoes;

        try {
            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());

        } catch (Exception e) {
            return ERRO_CONVERSAO;
        }

        return posicoes;
    }

    public void calcularCombinacao(int valorElementos, int valorPosicoes) {

        if (calculadoraGeral.validarElementosEPosicoes(Combinacao.getNumeroElementos(), Combinacao.getNumeroPosicoes(), inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com o mesmo valor, não é necessário recalcular
                if ((Combinacao.getNumeroElementos() == this.valorElementos) && (Combinacao.getNumeroPosicoes() == this.valorPosicoes)) {
                    inputElementos.setHint("Elementos a Combinar");
                    inputPosicoes.setHint("Posições a Combinar");

                    showToastMessage("O valor já foi calculado!");

                // Uma ou as duas entradas distintas, é necessário calcular
                } else {
                    setResultado(valorElementos, valorPosicoes);

                    this.valorElementos = Combinacao.getNumeroElementos();
                    this.valorPosicoes = Combinacao.getNumeroPosicoes();
                }

            // Muda estado da variável jaCalculou e calcula a Combinação (apenas no primeiro cálculo)
            } else {
                setResultado(valorElementos, valorPosicoes);

                jaCalculou = true;

                this.valorElementos = Combinacao.getNumeroElementos();
                this.valorPosicoes = Combinacao.getNumeroPosicoes();
            }


        } else {
            MainActivity.hideKeyboard(getActivity());
        }


    }

    private void setResultado(final int valorElementos, final int valorPosicoes) {
        inputElementos.setHint("Elementos a combinar");
        inputPosicoes.setHint("Posições a combinar");

        setVisibleAnimations();

        // Inicia a animação de escrita
        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.7f, 0);

        // Delay para mostrar animação + resultado
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoCombinacao.setText(gerador.gerarResultadoCombinacao(valorElementos, valorPosicoes));
                LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 2);
            }

        }, DELAY_TIME);

        // Cancela as animações Lottie
        cancelAnimations();

    }


    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Torna as animações visíveis para serem usadas novamente
    private void setVisibleAnimations() {
        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);
    }

    // Cancela as animações Lottie
    private void cancelAnimations() {
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("elementos", Integer.toString(Combinacao.getNumeroElementos()));
        outState.putString("posicoes", Integer.toString(Combinacao.getNumeroPosicoes()));
        outState.putBoolean("jaCalculou",jaCalculou);
        outState.putString("latex", resultadoCombinacao.getText());

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard(getActivity());

            this.txtElementos.setText(savedInstanceState.getString("elementos"));
            this.txtPosicoes.setText(savedInstanceState.getString("posicoes"));
            this.jaCalculou = savedInstanceState.getBoolean("jaCalculou");
            MathView calculoRecuperado = view.findViewById(R.id.resultado_combinacao);
            calculoRecuperado.setText(savedInstanceState.getString("latex"));
        }
    }

}