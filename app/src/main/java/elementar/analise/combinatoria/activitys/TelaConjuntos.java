package elementar.analise.combinatoria.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import elementar.analise.combinatoria.Dialog.MyAlertDialog;
import elementar.analise.combinatoria.floatingbutton.MenuFloatingButton;
import elementar.analise.combinatoria.fragments.HistoricoFragment;
import elementar.lottie.LottieController;
import elementar.operacoes.conjuntos.GeradorOperacoesConjuntos;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaConjuntos extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private TextInputLayout conjuntoA;
    private TextInputLayout conjuntoB;
    private TextInputLayout conjuntoU;

    private Button btn_calc;

    private LottieAnimationView animationSwipe;
    private final int ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;

    private MathView resultadoFinal, resultadoPasso;

    public static FloatingActionButton fab;
    private static FloatingActionButton fabHistorico;
    private static FloatingActionButton fabRemoveAll;

    private GridLayout grupoGrid;

    private RelativeLayout relativeLayout;

    private final int VALUE_UNIQUE = -1;

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

    private ConstraintLayout keyboardNumber;

    private int positionEditTextAtual = 0;

    private boolean initKeyboard = true;

    private Map<Integer, String> arrayIndexName = new HashMap<>();
    private HashMap<String,String> arrayNameCalcule = new HashMap<>();
    private Map<String, String> resultados = new HashMap<>();

    private final String[] arrayNames = {
            "União","Interseção","Complementar","Diferença/A-B","Diferença/B-A","Conjunto das partes","Cartesiano/AxB","Cartesiano/BxA","Análise Combinatória"
    };

    private List<Button> buttonListNumber;

    private EditText editTextDefault;

    private final int[] listIdNumber = new int[]{R.id.btn1,R.id.btn2,R.id.btn3,R.id.btnInfinity,
            R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn0,
            R.id.btn7,R.id.btn8,R.id.btn9,R.id.btnDelete,
            R.id.btnComma2,R.id.btnSpace,R.id.btnGo};

    private String[] text = new String[]{""};
    private int positionAtual =VALUE_UNIQUE;

    private int idEditText = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
        init();

    }

    private void init(){

        mountHashNameIndex();

        findViewAll();
        disableInputKeyboard(conjuntoA);
        disableInputKeyboard(conjuntoB);
        disableInputKeyboard(conjuntoU);

        if (relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo) {
            relativeLayout.setVisibility(View.GONE);
        }

        setSingleEvent(grupoGrid);
        onClicks();
        takeDataIntentFragments();
    }

    @SuppressLint("ClickableViewAccessibility")
    public void disableInputKeyboard(TextInputLayout textInputLayout){
        desabilityCopyOrPaste(textInputLayout.getEditText());
        textInputLayout.getEditText().setOnTouchListener(this);
    }

    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility"})
    private void findViewAll(){

        findViewButtonAll();

        intent = getIntent();

        keyboardNumber = findViewById(R.id.keyboarnumeric);

        menuFloatingButton = new MenuFloatingButton(this);

        operacoesConjuntos = new GeradorOperacoesConjuntos();

        conjuntoA = findViewById(R.id.conjuntoA);
        conjuntoB = findViewById(R.id.conjuntoB);
        conjuntoU = findViewById(R.id.conjuntoC);

        btn_calc = findViewById(R.id.btnCalcular);


        resultadoPasso = findViewById(R.id.resultado_conjuntosPasso);
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

        clickAllButtonKeyboard(0);

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

    public void clickAllButtonKeyboard(int position){

        this.positionAtual = position;
        for (int i = 0; i < buttonListNumber.size(); i++){
            buttonListNumber.get(i).setOnClickListener(this);
        }
    }

    private void findViewButtonAll(){

        buttonListNumber = new ArrayList<>();

        Button buttonKeyboard;

        for (int i = 0; i < listIdNumber.length; i++){

            buttonKeyboard = findViewById(listIdNumber[i]);
            buttonListNumber.add(buttonKeyboard);

        }

    }

    private void desabilityCopyOrPaste(final EditText editText){

        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {

            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            public boolean onActionItemClicked(ActionMode actionMode, MenuItem item) {
                return false;
            }

            public void onDestroyActionMode(ActionMode actionMode) {
            }
        });

    }

    private void visibilityKeyboard(ConstraintLayout constraintLayoutNumber){

        desabiliteFabMenu();
        if(constraintLayoutNumber.getVisibility() != View.VISIBLE) constraintLayoutNumber.setVisibility(View.VISIBLE);

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
                conjuntoU.getEditText().setText("");
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
        if(fragmentManager !=  null) fragmentManager.popBackStack();
    }

    private void initBackGroundCards(GridLayout gridLayout){

        for(int i = 0;i < gridLayout.getChildCount(); i++){
            gridLayout.getChildAt(i).setBackgroundResource(R.drawable.backgroundbord);
        }

    }

    private void verificarComplementar(TextInputLayout conjunto,int[] array){
        conjunto.setVisibility(array[INDEX_COMPLEMENTAR] == VALOR_COMPLEMENTAR ? View.VISIBLE : View.GONE);
        if(conjunto.getVisibility() == View.GONE) conjunto.getEditText().setText("");
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

        if(arraySelect[valor] == VALUE_UNIQUE){
            arraySelect[valor] = valor;
            changeBackGround(cardView, R.drawable.backgroundbordselected);

        }else if(arraySelect[valor] == valor){
            arraySelect[valor] = VALUE_UNIQUE;
            changeBackGround(cardView, R.drawable.backgroundbord);

        }
    }

    private void changeBackGround(CardView cardView, int drawable){
        cardView.setBackgroundResource(drawable);
    }

    private StringBuilder makeOperationSet(int position,String inputA,String inputB,String inputU){

        Log.i("[AQUI]", "POSITION" + position);
        switch(position){


            case 0:
                Log.i("[AQUI]UNIAO", inputA + "|" + inputB);
                return operacoesConjuntos.calcularUniao(inputA,inputB);
            case 1:
                Log.i("[AQUI] INTERSEÇÃO", inputA + "|" + inputB);
                return operacoesConjuntos.calcularIntersecao(inputA,inputB);
            case 2:
                Log.i("[AQUI] COMPLEMENTAR", inputA + "|" + inputB);
                return operacoesConjuntos.calcularComplementar(inputA,inputB,inputU);
            case 3:
                Log.i("[AQUI] DIFERENÇA A/B", inputA + "|" + inputB);
                return operacoesConjuntos.calcularDiferencaAB(inputA,inputB);
            case 4:
                Log.i("[AQUI] DIFERENÇA B/A", inputA + "|" + inputB);
                return operacoesConjuntos.calcularDiferencaBA(inputB,inputA);
            default:
                return new StringBuilder("N/A");
        }
    }

    private HashMap<String, String> makeCalc(int[] arraySelected, String inputA, String inputB, String inputU) {
        HashMap<String, String> resultados = new HashMap<>();

        if (isValorUnicoArray(arraySelected)){
            Log.i("MAKED", "TRUE");

            StringBuilder calcule = new StringBuilder();

            String operacao = this.arrayIndexName.get(arraySelected[posicaoArray]);
            String resultado = makeOperationSet(arraySelected[posicaoArray], inputA,inputB,inputU).toString();

            resultados.put(operacao, resultado);

            return resultados;

        } else {
            Log.i("MAKED", "FALSE");
            resultados = calculateMultipleValues(arraySelected,inputA,inputB,inputU,arrayNameCalcule);

            return resultados;
        }

    }

    private boolean isValorUnicoArray(int arrayCards[]){

        int cont = 0;
        for(int i = 0;i < arrayCards.length; i++){

            if(arrayCards[i] != VALUE_UNIQUE){
                cont++;
                posicaoArray = i;
            }

            if(cont > 1){
                return false;
            }
        }

        return true;

    }

    private HashMap<String, String> mountValuesMultiplesCalcules(Map<String,String> mapValues){

        HashMap<String, String> resultados = new HashMap<>();


        for (Map.Entry<String,String> entry: mapValues.entrySet()) {
            resultados.put(entry.getKey(), entry.getValue());
        }

        if (resultados.size() == 0) resultados.put("N/A", "N/A");

        return resultados;
    }

    private HashMap<String, String> calculateMultipleValues(int values[], String inputA, String inputB, String inputU, HashMap<String,String> valuesNames){

        String calcule = "";
        for(int i = 0; i < values.length; i++){
            if(arraySelect[i] != VALUE_UNIQUE){
                calcule = makeOperationSet(i,inputA,inputB,inputU).toString();
                valuesNames.put(arrayIndexName.get(i), calcule);
            }
        }
        return valuesNames;

    }

    private boolean isValidoCalcular(int[] array){
        for(int valor:array){
            if(valor != VALUE_UNIQUE) return true;

        }

        return false;
    }

    private void mountHashNameIndex(){
        for(int i = 0; i < arraySelect.length; i++){
            arrayIndexName.put(i,arrayNames[i]);
        }
    }
    @Override
    public void onBackPressed() {

        if(!desabiliteFabMenu()){

            if(keyboardNumber.getVisibility() == View.VISIBLE ){

                keyboardNumber.setVisibility(View.INVISIBLE);
                initKeyboard = true;

            }else{

                if(checkVisibiliteFab()) {
                    MyAlertDialog myAlertDialog = new MyAlertDialog(this,"Voltar ao início","Deseja voltar para a tela inicial?","Sim","Não");
                    myAlertDialog.backHome(this);

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
            Toast.makeText(getApplicationContext(),"Preencha pelo menos um dos campos!",Toast.LENGTH_SHORT).show();

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
                calculate();
                break;

            case R.id.btnDelete:
                removeLastcharEditText();
                break;

            case R.id.btnGo:
                int idConjuntoA = conjuntoA.getEditText().getId();
                int idConjuntoB = conjuntoB.getEditText().getId();
                int idConjuntoC = conjuntoU.getEditText().getId();

                if(editTextDefault.getId() == idConjuntoA){
                    nextEditText(R.id.conjuntoB);
                }else if(editTextDefault.getId() == idConjuntoB){
                    if(conjuntoU.getVisibility() != View.VISIBLE){
                        calculateButtonGo();
                    }else{
                        nextEditText(R.id.conjuntoC);
                    }
                }else if(editTextDefault.getId() == idConjuntoC){
                    calculateButtonGo();
                }
                break;

            default:
                final int index = indexButton(v.getId());
                final String value = buttonListNumber.get(index).getText().toString();
                addCharText(value);
                break;
        }
    }

    private void calculate(){

        if(isValidoCalcular(arraySelect)){

            if ( relativeLayout.getVisibility() == View.GONE ) relativeLayout.setVisibility(View.VISIBLE);

            if ( resultadoPasso.getVisibility() == View.GONE ) resultadoPasso.setVisibility(View.VISIBLE);

            String inputA = convert(conjuntoA);
            String inputB = convert(conjuntoB);
            String inputU = convert(conjuntoU);

            if(!inputA.isEmpty() && !inputB.isEmpty() && (!inputU.isEmpty() && conjuntoU.getVisibility() == View.VISIBLE || inputU.isEmpty() && conjuntoU.getVisibility() == View.GONE)){

                // Ativando a animação de swipeup
                animationSwipe = findViewById(R.id.animation_swipe);
                animationSwipe.setVisibility(View.VISIBLE);

                HashMap<String, String> resultados = makeCalc(arraySelect, convert(conjuntoA), convert(conjuntoB), convert(conjuntoU));


                StringBuilder resultadoLatex = new StringBuilder();

                for (Map.Entry<String,String> entry: resultados.entrySet()) {
                    resultadoLatex.append(entry.getKey() + " = " + "{" + entry.getValue()+ "}");
                }

                resultadoPasso.setText(resultadoLatex.toString());

                // Inicia a animação de deslizar
                LottieController.startLottieAnimation(getWindow().getDecorView().getRootView(), animationSwipe, ID_SWIPE, "swipeup.json", 1f, 4);

                // Cancela as animações
                LottieController.cancelLottieAnimation(this.animationSwipe);


            } else {
                Toast.makeText(getApplicationContext(),"Preencha todos os campos!",Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(getApplicationContext(),"Selecione um card para fazer o cálculo!",Toast.LENGTH_SHORT).show();
        }

    }

    private void nextEditText(int id){
        editTextDefault = ((TextInputLayout) findViewById(id)).getEditText();
        editTextDefault.setFocusable(true);
        editTextDefault.requestFocus();
    }

    private void calculateButtonGo(){

        keyboardNumber.setVisibility(View.GONE);
        calculate();
    }

    private void removeLastcharEditText(){

        StringBuilder newWorld = new StringBuilder();

        char[] array = editTextDefault.getText().toString().toCharArray();

        if(array.length > 0){

            for(int i = 0;i < array.length -1;i++){

                if(i != positionEditTextAtual){

                    newWorld.append(array[i]);

                }

            }

            positionEditTextAtual -= 1;

            if(positionEditTextAtual == VALUE_UNIQUE) positionEditTextAtual = 0;

            editTextDefault.setText(newWorld);
            setcursorLast(positionEditTextAtual);

        }
    }

    private void addCharText(String world){

        boolean blockPosition = false;

        StringBuilder newWorld = new StringBuilder();

        char[] array = editTextDefault.getText().toString().toCharArray();

        if(array.length == 0) {
            newWorld.append(world);

        }else if(array.length == positionEditTextAtual){
            newWorld.append(editTextDefault.getText().toString());
            newWorld.append(world);

        }else{
            blockPosition = true;
            int cont = 0;

            for(char worldChar : array){
                if (cont == positionEditTextAtual) {
                    newWorld.append(world);
                }
                newWorld.append(worldChar);
                cont++;
            }

        }

        if(!blockPosition) positionEditTextAtual = newWorld.length();

        editTextDefault.setText(newWorld.toString());

        setcursorLast(positionEditTextAtual);

    }

    private void setcursorLast(int position){
        editTextDefault.setSelection(position);
    }

    private int indexButton(int id){
        for(int i = 0; i < buttonListNumber.size();i++){
            if(buttonListNumber.get(i).getId() == id) return i;
        }

        return VALUE_UNIQUE;
    }

    private int positionEdtiText(MotionEvent event){

        float x = event.getX();
        float y = event.getY();

        int touchPosition = editTextDefault.getOffsetForPosition(x, y);

        if (touchPosition>0){
            editTextDefault.setSelection(touchPosition);
        }
        return touchPosition;
    }


    // Torna as animações visíveis para serem usadas novamente
    private void setVisibleAnimations() {
        this.animationSwipe = findViewById(R.id.animation_swipe);
        animationSwipe.setVisibility(View.VISIBLE);
    }

    // Cancela as animações Lottie
    private void cancelAnimations() {
        LottieController.cancelLottieAnimation(animationSwipe);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        visibilityKeyboard(keyboardNumber);

        editTextDefault = findViewById(v.getId());

        final String correction = operacoesConjuntos.checkComma(editTextDefault.getText().toString());

        if(!correction.equals(""))editTextDefault.setText(correction);

        positionEditTextAtual = positionEdtiText(event);

        int inType = editTextDefault.getInputType(); // backup the input type
        editTextDefault.setInputType(InputType.TYPE_NULL); // disable soft input
        editTextDefault.onTouchEvent(event); // call native handler
        editTextDefault.setInputType(inType); // restore input type
        return true; // consume touch even
    }
}