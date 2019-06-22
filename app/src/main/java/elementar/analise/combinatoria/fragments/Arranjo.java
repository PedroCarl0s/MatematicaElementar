package elementar.analise.combinatoria.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.geradores.GeradorArranjo;
import elementar.analise.combinatoria.geradores.GeradorFormulas;
import elementar.analise.combinatoria.myBottomSheet;
import elementar.lottie.LottieController;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.analise.combinatoria.controller.TextInputController;
import io.github.kexanie.library.MathView;


public class Arranjo extends Fragment implements TextWatcher {

    private View view;
    private Calculadora calculadora = Calculadora.getInstance();
    private GeradorArranjo gerador = new GeradorArranjo();

    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaArranjo, resultadoArranjo, resultadoFinalSimples;
    private static int valorElementos, valorPosicoes;
    private static Button btnCalcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;
    private static final int ERRO_CONVERSAO = -10000;

    private static myBottomSheet bottomSheet;
    private static BottomSheetBehavior behavior;
    private static RelativeLayout relativeLayout;
    private String calculoFinal = "";
    private boolean liberarCalculo = false;
    private boolean calculoLandScape = false;

    private String valorAnterior = "";


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

        txtElementos.addTextChangedListener(this);
        txtPosicoes.addTextChangedListener(this);

        // Método onClick do botão para calcular o Arranjo
        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                calcularArranjo(Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes());

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
                    calcularArranjo(Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes());

                    return true;
                }
                return false;
            }
        });

        //metodo que não deixa o bottomSheetBehavior ficar no modo STATA_HIDEN
        bottomSheet.bottomSheetCallback(behavior);

    }

    // Inicializa componentes de Input, MathView e Button
    public void init() {

        bottomSheet = new myBottomSheet(view,getResources().getConfiguration().orientation,R.id.bottomsheet);
        if(bottomSheet.verificarOrientacaoVertical(getOrientation())){
            behavior = bottomSheet.getMyBottomSheetBehavior();
            relativeLayout = view.findViewById(R.id.bottomsheet);
            resultadoFinalSimples = view.findViewById(R.id.resultado_arranjoFinal);

        }

        this.inputElementos = view.findViewById(R.id.elementos_arranjo);
        this.inputPosicoes = view.findViewById(R.id.posicoes_arranjo);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);


        this.formulaArranjo = view.findViewById(R.id.formula_arranjo);
        this.resultadoArranjo = view.findViewById(R.id.resultado_arranjoPasso);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        String formulaArranjo = "$$\\normalsize \\bold{Formula}$$" + " $${A(n, p)} = \\frac{n!} {(n-p)!}, n \\geqslant p$$";

        this.formulaArranjo.setText(formulaArranjo);
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    public void calcularArranjo(int valorElementos, int valorPosicoes) {

        if (calculadora.validarElementosEPosicoes(Arranjo.getNumeroElementos(), Arranjo.getNumeroPosicoes() ,inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com os mesmos valores, não é necessário recalcular
                if ((Arranjo.getNumeroElementos() == this.valorElementos) && Arranjo.getNumeroPosicoes() == this.valorPosicoes && verificarTextButton("calcular",btnCalcular)) {
                    inputElementos.setHint("Elementos a arranjar");
                    inputPosicoes.setHint("Posições a arranjar");

                    showToastMessage("O valor já foi calculado!");
                    // Uma ou as duas entradas distintas, é necessário calcular
                } else {

                    mostrarResultado(valorElementos,valorPosicoes);
                    jaCalculou = true;
                    this.valorElementos = Arranjo.getNumeroElementos();
                    this.valorPosicoes = Arranjo.getNumeroPosicoes();

                }

                // Muda estado da variável jaCalculou e calcula (apenas no primeiro cálculo)
            } else {

                mostrarResultado(valorElementos,valorPosicoes);

                jaCalculou = true;
                this.valorElementos = Arranjo.getNumeroElementos();
                this.valorPosicoes = Arranjo.getNumeroPosicoes();
            }

        } else {
            MainActivity.hideKeyboard(getActivity());
        }
    }

    private void mostrarResultado(int valorElementos, int valorPosicoes){

        if(!bottomSheet.verificarOrientacaoVertical(getOrientation())) {

            setResultado(valorElementos, valorPosicoes, null,resultadoArranjo);

        }else{

            setResultado(valorElementos, valorPosicoes,resultadoFinalSimples,resultadoArranjo);

        }

    }



    private void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void setResultado(final int valorElementos, final int valorPosicoes, final MathView resultado,final MathView resultadoPasso) {

        inputElementos.setHint("Elementos a arranjar");
        inputPosicoes.setHint("Posições a arranjar");

        if(verificarTextButton("calcular",btnCalcular)){

            if(bottomSheet.verificarOrientacaoVertical(getOrientation())){
                if(relativeLayout.getVisibility() == View.INVISIBLE){
                    relativeLayout.setVisibility(View.VISIBLE);
                }
            }


            animationWrite = view.findViewById(R.id.animation_write);
            animationSwipe = view.findViewById(R.id.animation_swipe);

            animationWrite.setVisibility(View.VISIBLE);
            animationSwipe.setVisibility(View.VISIBLE);

            // Inicia a animação de escrita
            LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.7f, 0);


            // Delay para mostrar animação + resultado
//            handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {

                    liberarCalculo = true;

                    if(bottomSheet.verificarOrientacaoVertical(getOrientation())){

                        resultado.setText(GeradorFormulas.gerarResultadoFinal("A",valorElementos,valorPosicoes,Calculadora.gerarResultadoCalculoPermutacao(valorElementos,valorPosicoes)));
                        if(resultadoPasso != null) {

                            resultadoPasso.setText(gerador.gerarResultadoArranjo(valorElementos, valorPosicoes));

                        }
                    }else{

                        calculoLandScape = true;
                        resultadoPasso.setText(gerador.gerarResultadoArranjo(valorElementos, valorPosicoes));


                    }
                    LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 2);

//                }
//
//            }, DELAY_TIME);

            // Cancela as animações
            LottieController.cancelLottieAnimation(animationWrite);
            LottieController.cancelLottieAnimation(animationSwipe);

        }else{

            resultado.setText(gerador.gerarResultadoArranjo(valorElementos, valorPosicoes));
            bottomSheet.usarBottomSheet(getActivity().getResources().getConfiguration().orientation,behavior);

        }

    }

    public static int getNumeroElementos() {
        int elementos;

        try {
            elementos =  Integer.parseInt(inputElementos.getEditText().getText().toString());
            inputElementos.getEditText().setText(Integer.toString(elementos));
        } catch (Exception e) {
            return ERRO_CONVERSAO;
        }

        return elementos;
    }

    public static int getNumeroPosicoes() {
        int posicoes;

        try {
            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());

            inputPosicoes.getEditText().setText(Integer.toString(posicoes));

        } catch (Exception error) {
            return ERRO_CONVERSAO;
        }

        return posicoes;
    }

    //guardando as informações
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("elementos", Integer.toString(Arranjo.getNumeroElementos()));
        outState.putString("posicoes", Integer.toString(Arranjo.getNumeroPosicoes()));
        outState.putBoolean("jaCalculou",jaCalculou);
        outState.putString("calculoFinal",this.calculoFinal);
        outState.putBoolean("liberarCalculo",this.liberarCalculo);

        //verificar se ja foi calculado
        if(liberarCalculo) {

            if(bottomSheet.verificarOrientacaoVertical(getOrientation())){

                this.calculoFinal = resultadoFinalSimples.getText();
                outState.putString("calculoFinal",resultadoFinalSimples.getText());
                outState.putString("latexPasso",resultadoArranjo.getText());

            }else{
                outState.putBoolean("calculoLandScape",this.calculoLandScape);
                outState.putString("latexPasso",resultadoArranjo.getText());

            }

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard(getActivity());

            this.txtElementos.setText(savedInstanceState.getString("elementos"));
            this.txtPosicoes.setText(savedInstanceState.getString("posicoes"));
            this.jaCalculou = savedInstanceState.getBoolean("jaCalculou");
            this.calculoFinal = savedInstanceState.getString("calculoFinal");
            this.liberarCalculo = savedInstanceState.getBoolean("liberarCalculo");
            this.calculoLandScape = savedInstanceState.getBoolean("calculoLandScape");

            resultadoArranjo = view.findViewById(R.id.resultado_arranjoPasso);

            bottomSheet = new myBottomSheet(view,getOrientation(),R.id.bottomsheet);
            if(bottomSheet.verificarOrientacaoVertical(getOrientation())) {
                behavior = bottomSheet.getMyBottomSheetBehavior();
                resultadoFinalSimples = view.findViewById(R.id.resultado_arranjoFinal);
            }

            //verificar se ja foi calculado para guardar
            if(this.liberarCalculo){

                if(!bottomSheet.verificarOrientacaoVertical(getOrientation())){

                    resultadoArranjo.setText(savedInstanceState.getString("latexPasso"));

                }else{

                    if(this.calculoLandScape){

                        setResultado(Integer.parseInt(txtElementos.getText().toString()),Integer.parseInt(txtPosicoes.getText().toString()),resultadoFinalSimples,null);

                    }else{

                        resultadoFinalSimples.setText(this.calculoFinal);

                    }

                    resultadoArranjo.setText(savedInstanceState.getString("latexPasso"));

                    bottomSheet.usarBottomSheet(getOrientation(),behavior);

                }
            }
        }

    }

    //verificar orientação
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private boolean verificarTextButton(String text,Button button){
        return button.getText().equals(text);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        valorAnterior = s.toString();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(!valorAnterior.equalsIgnoreCase(s.toString())){
            verificarInput(inputElementos,inputPosicoes);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {



    }

    private void verificarInput(TextInputLayout inputLayout,TextInputLayout inputLayout2){

        calculadora.validarElementosEPosicoes(getNumeroElementos(),getNumeroPosicoes(),inputLayout,inputLayout2);

    }

    private int getOrientation(){
        return getResources().getConfiguration().orientation;
    }

}