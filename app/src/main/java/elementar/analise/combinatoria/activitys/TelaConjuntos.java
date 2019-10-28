package elementar.analise.combinatoria.activitys;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.gridlayout.widget.GridLayout;

import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.floatingbutton.MenuFloatingButton;
import elementar.analise.combinatoria.fragments.HistoricoFragment;
import elementar.analise.combinatoria.geradores.GeradorOperacoesConjuntos;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaConjuntos extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private TextInputLayout conjuntoA;
    private TextInputLayout conjuntoB;
    private TextInputLayout conjuntoU;

    private Button btn_calc;

    private LottieAnimationView animationWhite, animetionSwipe;

    private MathView resultadoFinal, resultadoPasso;

    public static FloatingActionButton fab;
    private static FloatingActionButton fabHistorico;
    private static FloatingActionButton fabRemoveAll;

    private GridLayout grupoGrid;

    private RelativeLayout relativeLayout;

    private boolean liberarCalculo = false;

    private int[] arraySelect = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1};

    private final int VALOR_COMPLEMENTAR = 2;

    private final int INDEX_COMPLEMENTAR = 2;

    private int posicaoArray = 0;

    private GeradorOperacoesConjuntos operacoesConjuntos;

    private MenuFloatingButton menuFloatingButton;

    private Intent intent;

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;

    private BottomSheetBehavior bottomSheetBehavior;

    private ConstraintLayout keyboardNumber, keyboardAlphaNumber;

    private int idAtual = 0;

    private boolean initKeyboard = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
        init();

    }

    private void init(){

        findViewAll();
        disableInputKeyboard(conjuntoA);
        disableInputKeyboard(conjuntoB);
        disableInputKeyboard(conjuntoU);

//        visibilityKeyboard(keyboardAlphaNumber,keyboardNumber,idAtual);
        if(relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo){

            relativeLayout.setVisibility(View.GONE);

        }

        setSingleEvent(grupoGrid);
        onClicks();
        takeFocusTextInputLayout();
        takeDataIntentFragments();


    }

    @SuppressLint("ClickableViewAccessibility")
    public void disableInputKeyboard(TextInputLayout textInputLayout){
        textInputLayout.getEditText().setOnTouchListener(this);
    }

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    private void findViewAll(){

        intent = getIntent();

        keyboardAlphaNumber = findViewById(R.id.keyboaralphanumeric);
        keyboardNumber = findViewById(R.id.keyboarnumeric);

        menuFloatingButton = new MenuFloatingButton(this);

        operacoesConjuntos = new GeradorOperacoesConjuntos();

        conjuntoA = findViewById(R.id.conjuntoA);
        conjuntoB = findViewById(R.id.conjuntoB);
        conjuntoU = findViewById(R.id.conjuntoC);

        btn_calc = findViewById(R.id.btnCalcular);

        animetionSwipe = findViewById(R.id.animation_swipe);

        resultadoPasso = findViewById(R.id.resultado_conjutosPasso);
        resultadoPasso.setVisibility(View.GONE);

        relativeLayout = findViewById(R.id.bottomsheetConjunto);

        fab = findViewById(R.id.botao_info);
        fabHistorico = findViewById(R.id.fab_historico);
        fabRemoveAll = findViewById(R.id.fab_removeAll);

        menuFloatingButton.addFloating(fabHistorico);
        menuFloatingButton.addFloating(fabRemoveAll);

        grupoGrid = findViewById(R.id.menuconjunto);

        initBackGroundCards(grupoGrid);

        menuFloatingButton.controleMenuFab(fab);

        bottomSheetBehavior = BottomSheetBehavior.from(relativeLayout);

    }

    private void onClicks(){

        fab.setOnClickListener(this);

        fabHistorico.setOnClickListener(this);

        fabRemoveAll.setOnClickListener(this);

        btn_calc.setOnClickListener(this);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

                switch (i){
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fab.hide();
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        fab.show();
                }

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void visibilityKeyboard(ConstraintLayout constraintLayoutAlpha,ConstraintLayout constraintLayoutNumber, int idKeyboard){

        desabiliteFabMenu();

        if(initKeyboard){

            constraintLayoutAlpha.setVisibility(View.VISIBLE);
            initKeyboard = false;

        }else if(idAtual != idKeyboard || idAtual == 0){


            idAtual = idKeyboard;

            if(constraintLayoutAlpha.getVisibility() == View.VISIBLE && constraintLayoutNumber.getVisibility() == View.GONE && idAtual != idKeyboard){

                constraintLayoutAlpha.setVisibility(View.GONE);
                constraintLayoutNumber.setVisibility(View.VISIBLE);

            }else if(constraintLayoutAlpha.getVisibility() == View.GONE && constraintLayoutNumber.getVisibility() == View.VISIBLE && idAtual != idKeyboard){

                constraintLayoutAlpha.setVisibility(View.VISIBLE);
                constraintLayoutNumber.setVisibility(View.GONE);

            }

        }

    }

    private void takeFocusTextInputLayout(){

     ;

    }

    private void takeDataIntentFragments(){

        if(intent.hasExtra("conjA")){
            conjuntoA.getEditText().setText(intent.getStringExtra("conjA"));
            conjuntoB.getEditText().setText(intent.getStringExtra("conjB"));
            if(intent.getStringExtra("conjU") != null && !intent.getStringExtra("conjU").isEmpty()){
                conjuntoU.getEditText().setText(intent.getStringExtra("conjU"));
                conjuntoU.setVisibility(View.VISIBLE);
            }else if(intent.getStringExtra("conjU") != null && intent.getStringExtra("conjU").isEmpty()){
                conjuntoU.setVisibility(View.GONE);
            }
        }

    }

    private boolean desabiliteFabMenu(){

        if(menuFloatingButton.isShowMenu()){
            menuFloatingButton.controleMenuFab(fab);
            return true;
        }
        return false;
    }

    private void openFragments(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.contentConjuntos,fragment);
        fragmentTransaction.commit();
    }

    private void popFragments(){
        fragmentManager.popBackStack();
    }

    private void initBackGroundCards(GridLayout gridLayout){

        for(int i = 0;i < gridLayout.getChildCount(); i++){
            gridLayout.getChildAt(i).setBackgroundResource(R.drawable.backgroundbord);
        }

    }

    private void verificarComplementar(TextInputLayout conjunto,int[] array){
        conjunto.setVisibility(array[INDEX_COMPLEMENTAR] == VALOR_COMPLEMENTAR ? View.VISIBLE : View.GONE);
    }

    private void setSingleEvent(GridLayout mainGrid) {

        // Percorre todos os filhos do GridLayout Pai
        for (int atual = 0; atual < mainGrid.getChildCount(); atual++) {

            final CardView cardView = (CardView) mainGrid.getChildAt(atual);
            final int choice = atual;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addArray(cardView, choice);
                    verificarComplementar(conjuntoU,arraySelect);
                }
            });

        }
    }

    private void addArray(CardView cardView, int valor){

        if(arraySelect[valor] == -1){

            arraySelect[valor] = valor;
            changeBackGround(cardView, R.drawable.backgroundbordselected);

        }else if(arraySelect[valor] == valor){

            arraySelect[valor] = -1;
            changeBackGround(cardView, R.drawable.backgroundbord);

        }
    }

    private void changeBackGround(CardView cardView, int drawable){
        cardView.setBackgroundResource(drawable);
    }

    private StringBuilder makeCalc(int[] arraySelected,String inputA,String inputB,String inputU) {

        if(isValorUnicoArray(arraySelected)){

            switch(arraySelected[posicaoArray]){

                case 0:
                    return operacoesConjuntos.calcularUniao(inputA,inputB);
                case 1:
                    return operacoesConjuntos.calcularIntersecao(inputA,inputB);
                case 2:
                    return operacoesConjuntos.calcularDiferencaAB(inputA,inputB);
                case 3:
                    return operacoesConjuntos.calcularComplementar(inputA,inputB,inputU);
                case 4:
                    return operacoesConjuntos.calcularConjuntoPartes(inputA,inputB);
                case 5:
                    return operacoesConjuntos.calcularDiferencaBA(inputA,inputB);
                default:
                    break;
            }

        }else{
//            Uniao
//            if(arraySelected[0] == 0){
//                if(arraySelected[1] == 1){
//                    operacoesConjuntos.calcularUniaoIntersecao(inputA,inputB);
//                }else if(arraySelected[3] == 3){
//                    operacoesConjuntos.calcularUniaoComplementar(inputA,inputB,inputU);
//                }
//
//            }
        }
        return null;
    }

    private boolean isValorUnicoArray(int arrayCards[]){

        int cont = 0;
        for(int i = 0;i < arrayCards.length; i++){

            if(arrayCards[i] != -1){
                cont++;
                posicaoArray = i;
            }

            if(cont > 1){
                return false;
            }
        }

        return true;

    }

    private boolean isValidoCalcular(int[] array){

        for(int valor:array){

            if(valor != -1) return true;

        }

        return false;
    }

    public void onBackPressed() {

        if(!desabiliteFabMenu()){

            if(keyboardAlphaNumber.getVisibility() == View.VISIBLE || keyboardNumber.getVisibility() == View.VISIBLE){

                keyboardAlphaNumber.setVisibility(View.INVISIBLE);
                keyboardNumber.setVisibility(View.INVISIBLE);
                initKeyboard = true;

            }else{

                if(checkVisibiliteFab()) {

                    startActivity(new Intent(this, MainActivity.class));
                    finish();

                }else{

                    fab.show();
                    popFragments();

                }


            }

        }

    }

    public boolean checkVisibiliteFab(){

        return fab.getVisibility() == View.VISIBLE;

    }

    public void removerAllValueInput(TextInputLayout conjuntoA, TextInputLayout conjuntoB, TextInputLayout conjuntoU){

        if(empty(conjuntoA) && empty(conjuntoB)) {

            Toast.makeText(getApplicationContext(),"Preencha pelo menos um dos campo",Toast.LENGTH_SHORT).show();

        } else if(!empty(conjuntoU) && conjuntoU.getVisibility() == View.VISIBLE) {

            conjuntoU.getEditText().setText(" ");

        }else {

            conjuntoA.getEditText().setText(" ");
            conjuntoB.getEditText().setText(" ");
        }
    }

    public boolean empty(TextInputLayout conjunto){

        return conjunto.getEditText().getText().toString().equalsIgnoreCase("");

    }

    private String convert(TextInputLayout textInputLayout){
        return textInputLayout.getEditText().getText().toString();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.botao_info:
                menuFloatingButton.controleMenuFab(fab);
                break;
            case R.id.fab_historico:
                menuFloatingButton.controleMenuFab(fab);
                openFragments(new HistoricoFragment());
                menuFloatingButton.showAndHide(fab);
                break;
            case R.id.fab_removeAll:
                menuFloatingButton.controleMenuFab(fab);
                removerAllValueInput(conjuntoA,conjuntoB,conjuntoU);
                break;
            case R.id.btnCalcular:
                if(isValidoCalcular(arraySelect)){
                    Toast.makeText(getApplicationContext(),"Calculor Feito = "+makeCalc(arraySelect,convert(conjuntoA),convert(conjuntoB),convert(conjuntoU)),Toast.LENGTH_SHORT).show();
                    if(relativeLayout.getVisibility() == View.GONE){

                        relativeLayout.setVisibility(View.VISIBLE);

                    }

                }else{
                    Toast.makeText(getApplicationContext(),"selecione um card para fazer o Calculor",Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        visibilityKeyboard(keyboardAlphaNumber,keyboardNumber,v.getId());

        EditText editText = findViewById(v.getId());
        int inType = editText.getInputType(); // backup the input type
        editText.setInputType(InputType.TYPE_NULL); // disable soft input
        editText.onTouchEvent(event); // call native handler
        editText.setInputType(inType); // restore input type
        return true; // consume touch even
    }
}
