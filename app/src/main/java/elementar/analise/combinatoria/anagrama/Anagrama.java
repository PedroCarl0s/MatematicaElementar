package elementar.analise.combinatoria.anagrama;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import elementar.analise.combinatoria.calculadoras.Calculadora;
import elementar.analise.combinatoria.myBottomSheet;

import elementar.analise.combinatoria.activitys.MainActivity;
import elementar.analise.combinatoria.controller.TextInputController;

import elementar.matematica.pedrock.matemticaelementar.R;

import elementar.lottie.LottieController;

import io.github.kexanie.library.MathView;


public class Anagrama extends Fragment {

    private View view;

    private static TextInputLayout inputAnagrama;
    private static TextInputEditText txtAnagrama;
    private static LottieAnimationView animationWrite, animationSwipe;

    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private String palavraGuardada = "";

    private MathView formulaAnagrama, resultadoAnagrama, resultadoFinalSimples;

    private Calculadora calculadora = Calculadora.getInstance();

    private static GeradorAnagrama gerador = new GeradorAnagrama();

    private Button btnCalcular;

    private static myBottomSheet bottomSheet;
    private static BottomSheetBehavior behavior;
    private static RelativeLayout relativeLayout;
    private String calculoFinal = "";
    private boolean liberarCalculo = false;
    private boolean calculoLandScape = false;

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
        animationSwipe.setVisibility(View.GONE);

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

                    if (!Anagrama.getEntradaAnagrama().isEmpty()) {
                        inputAnagrama.setError(null);
                        calcularAnagrama();

                        return true;

                    } else {

                        inputAnagrama.setError("Insira apenas letras!");
                        MainActivity.hideKeyboard(getActivity());

                        return false;
                    }
                }

                return false;

            }
        });

        this.formulaAnagrama.setText(gerador.gerarFormula());

        if(bottomSheet.verificarOrientacaoVertical(getOrientation())){

            //metodo que não deixa o bottomSheetBehavior ficar no modo STATA_HIDEN
            bottomSheet.bottomSheetCallback(behavior);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_anagrama, container, false);

        return view;
    }

    private void init () {

        bottomSheet = new myBottomSheet(view,getResources().getConfiguration().orientation,R.id.bottomsheetAnagrama);

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {

            behavior = bottomSheet.getMyBottomSheetBehavior();
            relativeLayout = view.findViewById(R.id.bottomsheetAnagrama);

            if (relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo) {

                relativeLayout.setVisibility(View.INVISIBLE);

            }
            resultadoFinalSimples = view.findViewById(R.id.resultado_AnagramaFinal);
            resultadoFinalSimples.setText("$$\\bold{Resultado}$$");


        }

        this.inputAnagrama = view.findViewById(R.id.input_anagrama);
        this.txtAnagrama = view.findViewById(R.id.txt_anagrama);
        this.formulaAnagrama = view.findViewById(R.id.formula_anagrama);

        this.resultadoAnagrama = view.findViewById(R.id.resultado_anagrama);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        this.animationWrite = view.findViewById(R.id.animation_write);
        this.animationSwipe = view.findViewById(R.id.animation_swipe);
    }


    // Retorna a palavra que foi digitada
    private static String getEntradaAnagrama() {

        String palavraDigitada = inputAnagrama.getEditText().getText().toString().toLowerCase();

        palavraDigitada = gerador.removerAcentosESimbolos(palavraDigitada);

        inputAnagrama.getEditText().setText(palavraDigitada);

        return palavraDigitada;
    }

    private int getTamanhodaPalavra() {
        return inputAnagrama.getEditText().getText().toString().toUpperCase().length();
    }

    private void setResultado(final HashMap<String,Integer> hashLetraEQuant, final Long resultadoFinal,MathView resultado,MathView resultadoPasso) {

        if (relativeLayout.getVisibility() == View.INVISIBLE){
            relativeLayout.setVisibility(View.VISIBLE);
        }

        MainActivity.hideKeyboard(getActivity());

        animationWrite.setVisibility(View.VISIBLE);
        animationSwipe.setVisibility(View.VISIBLE);


        LottieController.startLottieAnimation(view, animationWrite, ID_WRITE, "write.json", 1.5f, 0);


        liberarCalculo = true;

        if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {

            resultado.setText(gerador.gerarResultadoFinal(hashLetraEQuant,resultadoFinal,getTamanhodaPalavra()));

            resultadoPasso.setText(gerador.gerarDescricaoVariaveis(hashLetraEQuant) + gerador.gerarAplicacaoValores(hashLetraEQuant,getTamanhodaPalavra()) + gerador.gerarResultadoFinal(hashLetraEQuant,resultadoFinal,getTamanhodaPalavra()));


        } else {

            calculoLandScape = true;
            resultadoPasso.setText(gerador.gerarDescricaoVariaveis(hashLetraEQuant) + gerador.gerarAplicacaoValores(hashLetraEQuant,getTamanhodaPalavra()) + gerador.gerarResultadoFinal(hashLetraEQuant,resultadoFinal,getTamanhodaPalavra()));


        }

        //inicia as animações
        LottieController.startLottieAnimation(view, animationSwipe, ID_SWIPE, "swipeup.json", 1f, 4);

        // Cancela as animações
        LottieController.cancelLottieAnimation(animationWrite);
        LottieController.cancelLottieAnimation(animationSwipe);
    }


    private String gerarTrechoInicial(int tamanhoPalavra) {
        String trecho = "$$A(" + tamanhoPalavra + ", " + tamanhoPalavra + ") = P_{" + tamanhoPalavra + "}$$";

        return trecho;
    }

    private void calcularAnagrama() {

        // Campo não vazio
        if (!calculadora.verificarCampoVazio(inputAnagrama)) {

            inputAnagrama.setError(null);
            MainActivity.hideKeyboard(getActivity());


            // Retorna um HashMap de letra e valor
            HashMap<String, Integer> novoArrayQuantPalavras = contarPalavrasIguais(getEntradaAnagrama());

            long resultadoFinal = calcularResultadoAnagrama(getTamanhodaPalavra(), novoArrayQuantPalavras);
            // Verifica se tem letras repetidas

            if(!isJaCalculou(getEntradaAnagrama(),palavraGuardada,null)){

                if (bottomSheet.verificarOrientacaoVertical(getOrientation())) {

                    setResultado(novoArrayQuantPalavras,resultadoFinal,resultadoFinalSimples,resultadoAnagrama);

                } else {

                    setResultado(novoArrayQuantPalavras,resultadoFinal,null,resultadoAnagrama);

                }

                palavraGuardada = getEntradaAnagrama();
            }


        } else {

            inputAnagrama.setError("O campo está vazio!");
            MainActivity.hideKeyboard(getActivity());
        }

    }

    private long calcularResultadoAnagrama(long tamanhoPalavra,HashMap<String,Integer> arrayNumeroDeLetras) {

        long valorPalavra = calculadora.gerarResultadoFatorial(tamanhoPalavra);
        long somaValorLetras = 1;

        for(long quantidadeRepet: arrayNumeroDeLetras.values()){
            somaValorLetras *= calculadora.gerarResultadoFatorial(quantidadeRepet);
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

    private boolean isJaCalculou(String palavra,String palavraGuardade,TextInputLayout input){

        if(palavra.equals(palavraGuardade)){

            showToastMessage("O valor já foi calculado!");
            return true;

        }

        return false;

    }

    //guardando as informações
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("inputAnagrama", Anagrama.getEntradaAnagrama());
        outState.putString("palavraGuardada",palavraGuardada);
        outState.putString("calculoFinal",this.calculoFinal);
        outState.putBoolean("liberarCalculo",this.liberarCalculo);

        //verificar se ja foi calculado
        if(liberarCalculo) {

            if(bottomSheet.verificarOrientacaoVertical(getOrientation())){

                outState.putString("calculoFinal",resultadoFinalSimples.getText());
                outState.putString("latexPasso",resultadoAnagrama.getText());

            }else{
                outState.putBoolean("calculoLandScape",this.calculoLandScape);
                outState.putString("latexPasso",resultadoAnagrama.getText());

            }

        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard(getActivity());

            inputAnagrama.getEditText().setText(savedInstanceState.getString("inputAnagrama"));
            this.palavraGuardada = savedInstanceState.getString("palavraGuardada");
            this.calculoFinal = savedInstanceState.getString("calculoFinal");
            this.liberarCalculo = savedInstanceState.getBoolean("liberarCalculo");
            this.calculoLandScape = savedInstanceState.getBoolean("calculoLandScape");


            resultadoAnagrama = view.findViewById(R.id.resultado_anagrama);

            //iniciar o bottomSheet
            bottomSheet = new myBottomSheet(view,getOrientation(),R.id.bottomsheetAnagrama);
            if(bottomSheet.verificarOrientacaoVertical(getOrientation())) {

                resultadoFinalSimples = view.findViewById(R.id.resultado_AnagramaFinal);
            }

            //verificar se ja foi calculado para guardar

            if(this.liberarCalculo){

                if(!bottomSheet.verificarOrientacaoVertical(getOrientation())){

                    resultadoAnagrama.setText(savedInstanceState.getString("latexPasso"));

                }else{

                    if(this.calculoLandScape){

                        HashMap<String, Integer> novoArrayQuantPalavras = contarPalavrasIguais(getEntradaAnagrama());
                        long resultadoFinal = calcularResultadoAnagrama(getTamanhodaPalavra(), novoArrayQuantPalavras);

                        setResultado(novoArrayQuantPalavras,resultadoFinal,resultadoFinalSimples,resultadoAnagrama);


                    }else{

                        resultadoFinalSimples.setText(this.calculoFinal);

                    }

                    resultadoAnagrama.setText(savedInstanceState.getString("latexPasso"));

                    bottomSheet.usarBottomSheet(getOrientation(),behavior);

                }
            }
        }

    }

    private int getOrientation(){
        return getContext().getResources().getConfiguration().orientation;
    }


}