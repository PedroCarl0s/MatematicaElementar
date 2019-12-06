package elementar.analise.combinatoria.permutacao;

import android.content.res.Configuration;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.geradores.GeradorFormulas;
import elementar.analise.combinatoria.myBottomSheet;
import elementar.lottie.LottieController;

import elementar.analise.combinatoria.activitys.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;

import elementar.analise.combinatoria.controller.TextInputController;

import io.github.kexanie.library.MathView;


public class Permutacao extends Fragment {

    private View view;
    private Calculadora calculadora = Calculadora.getInstance();
    private GeradorPermutacao gerador = new GeradorPermutacao();

    private TextInputLayout inputElementos, inputPosicoes;
    private TextInputEditText txtElementos, txtPosicoes;

    private MathView formulaPermutacao, resultadoPermutacao, resultadoFinalSimples;
    private int valorElementos, valorPosicoes;
    private Button btnCalcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;
    private final int ERRO_CONVERSAO = -10000;

    private myBottomSheet bottomSheet;
    private BottomSheetBehavior behavior;
    private RelativeLayout relativeLayout;

    private Toast toastMessage;


    private String calculoFinal = "";
    private boolean liberarCalculo = false;
    private boolean calculoLandScape = false;

    private String valorAnterior = "";


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

        int currentOrientation = getResources().getConfiguration().orientation;
        LottieController.changeAnimationVisibility(view, animationSwipe, ID_SWIPE, currentOrientation);


        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

        init();

        // Método onClick do botão para calcular o Permutacao
        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calcularPermutacao(getNumeroElementos(), getNumeroPosicoes());

            }
        });

        // Troca o título do TextInput ao clicar
        TextInputController.setLabelTextInput(this.inputElementos, this.txtElementos, "Valor de n", "n=Elementos a permutar");

        // Troca o título do TextInput ao clicar
        TextInputController.setLabelTextInput(this.inputPosicoes, this.txtPosicoes, "Valor de p", "p=Posições a permutar");

        txtPosicoes.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    calcularPermutacao(getNumeroElementos(), getNumeroPosicoes());

                    return true;
                }
                return false;
            }
        });

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {

            //Método que impede o bottomSheetBehavior ficar no modo STATUS_HIDEN
            bottomSheet.bottomSheetCallback(behavior);
        }

    }

    // Inicializa componentes de Input, MathView,Button e Toast

    private void init() {

        bottomSheet = new myBottomSheet(view,getResources().getConfiguration().orientation,R.id.bottomsheet);

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {

            behavior = bottomSheet.getMyBottomSheetBehavior();
            relativeLayout = view.findViewById(R.id.bottomsheet);

            if (relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo) {
                relativeLayout.setVisibility(View.INVISIBLE);
            }

            resultadoFinalSimples = view.findViewById(R.id.resultado_permutacaoFinal);


            if (!jaCalculou){
                resultadoFinalSimples.setText("$$\\bold{Resultado}$$");
            }

        }


        this.inputElementos = view.findViewById(R.id.elementos_permutacao);
        this.inputPosicoes = view.findViewById(R.id.posicoes_permutacao);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);


        this.formulaPermutacao = view.findViewById(R.id.formula_permutacao);
        this.resultadoPermutacao = view.findViewById(R.id.resultado_permutacaoPasso);

        this.toastMessage = Toast.makeText(getContext(), null, Toast.LENGTH_SHORT);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        String formulaPermutacao = "$$\\normalsize \\bold{Formula}$$" + " $${P(n, p)} = \\frac{n!} {(n-p)!}, n \\geqslant p$$";

        this.formulaPermutacao.setText(formulaPermutacao);
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    private void calcularPermutacao(int valorElementos, int valorPosicoes) {

        if (calculadora.validarElementosEPosicoes(getNumeroElementos(), getNumeroPosicoes() ,inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com os mesmos valores, não é necessário recalcular
                if ((getNumeroElementos() == this.valorElementos) && getNumeroPosicoes() == this.valorPosicoes) {
                    inputElementos.setHint("n=Elementos a permutar");
                    inputPosicoes.setHint("p=Posições a permutar");

                    showToastMessage("O valor já foi calculado!");

                // Uma ou as duas entradas distintas, é necessário calcular
                } else {

                    mostrarResultado(valorElementos,valorPosicoes);
                    jaCalculou = true;
                    this.valorElementos = getNumeroElementos();
                    this.valorPosicoes = getNumeroPosicoes();
                }

                // Muda estado da variável jaCalculou e calcula (apenas no primeiro cálculo)
            } else {

                mostrarResultado(valorElementos,valorPosicoes);

                jaCalculou = true;
                this.valorElementos = getNumeroElementos();
                this.valorPosicoes = getNumeroPosicoes();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void mostrarResultado(int valorElementos, int valorPosicoes){

        if (!bottomSheet.verificarOrientacaoVertical(getOrientation())) {
            setResultado(valorElementos, valorPosicoes, null, resultadoPermutacao);

        } else {
            setResultado(valorElementos, valorPosicoes, resultadoFinalSimples, resultadoPermutacao);

        }
    }


    private void showToastMessage(String message) {
        this.toastMessage.setText(message);
        this.toastMessage.show();
    }

    private void setResultado(final int valorElementos, final int valorPosicoes, final MathView resultado, final MathView resultadoPasso) {

        if (relativeLayout.getVisibility() == View.INVISIBLE) {
            relativeLayout.setVisibility(View.VISIBLE);
        }

        MainActivity.hideKeyboard(getActivity());

        inputElementos.setHint("n=Elementos a permutar");
        inputPosicoes.setHint("p=Posições a permutar");

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {
            if (relativeLayout.getVisibility() == View.INVISIBLE) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        }


        animationWrite = view.findViewById(R.id.animation_write);
        animationSwipe = view.findViewById(R.id.animation_swipe);

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);

        // Inicia a animação de escrita
        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.7f, 0);

        liberarCalculo = true;

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {
            resultado.setText(GeradorFormulas.gerarResultadoFinal("P", valorElementos, valorPosicoes, gerador.getResultadoFinalPermutacao(valorElementos, valorPosicoes)));

            if (resultadoPasso != null) {
                resultadoPasso.setText(gerador.gerarResultadoPermutacao(valorElementos, valorPosicoes));
            }

        } else {

            calculoLandScape = true;

            resultadoPasso.setText(gerador.gerarResultadoPermutacao(valorElementos, valorPosicoes));
        }

        // Inicia a animação de deslizar
        LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 4);

        // Cancela as animações
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);

    }

    private int getNumeroElementos() {
        int elementos;

        try {

            elementos =  Integer.parseInt(inputElementos.getEditText().getText().toString());
            inputElementos.getEditText().setText(Integer.toString(elementos));

        } catch (Exception e) {
            return ERRO_CONVERSAO;
        }

        return elementos;
    }

    private int getNumeroPosicoes() {
        int posicoes;

        try {

            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());
            inputPosicoes.getEditText().setText(Integer.toString(posicoes));

        } catch (Exception error) {
            return ERRO_CONVERSAO;
        }

        return posicoes;
    }


    //verificar orientação
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private int getOrientation(){
        return getResources().getConfiguration().orientation;
    }

}