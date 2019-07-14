package elementar.analise.combinatoria.activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elementar.analise.combinatoria.floatingbutton.MenuFloatingButton;
import elementar.analise.combinatoria.fragments.HistoricoFragment;
import elementar.analise.combinatoria.geradores.GeradorOperacoesConjuntos;
import elementar.analise.combinatoria.model.OpConjuntos;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaConjuntos extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
        init();

    }

    private void init(){

        instanceComponentes();
        setSingleEvent(grupoGrid);
        onClicks();
        takeIntentFragments();

    }

    @SuppressLint("RestrictedApi")
    private void instanceComponentes(){

        intent = getIntent();

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

        if(relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo){

            relativeLayout.setVisibility(View.GONE);

        }

        fab = findViewById(R.id.botao_info);
        fabHistorico = findViewById(R.id.fab_historico);
        fabRemoveAll = findViewById(R.id.fab_removeAll);

        menuFloatingButton.addFloating(fabHistorico);
        menuFloatingButton.addFloating(fabRemoveAll);

        grupoGrid = findViewById(R.id.menuconjunto);

        initBackGroundCards(grupoGrid);

        menuFloatingButton.controleMenuFab(fab);

    }

    private void onClicks(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                menuFloatingButton.controleMenuFab(fab);

            }
        });

        fabHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFloatingButton.hideFloating(fab);
                openFragments(new HistoricoFragment());
                fab.hide();

            }
        });

        fabRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuFloatingButton.hideFloating(fab);
                removerAllValueInput(conjuntoA,conjuntoB,conjuntoU);
            }
        });

        btn_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidoCalcular(arraySelect)){
                    Toast.makeText(getApplicationContext(),"Calculor Feito",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),"selecione um card para fazer o Calculor",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void takeIntentFragments(){

        if(intent.hasExtra("conjA")){
            conjuntoA.getEditText().setText(intent.getStringExtra("conjA"));
            conjuntoB.getEditText().setText(intent.getStringExtra("conjB"));
            if(intent.hasExtra("conjU")){
                conjuntoU.getEditText().setText(intent.getStringExtra("conjU"));
            }
        }

    }

    private void openFragments(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.contentConjuntos,fragment);
        fragmentTransaction.commit();
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
                    makeCalc(arraySelect,conjuntoA,conjuntoB,conjuntoU);
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

    private StringBuilder makeCalc(int[] arraySelected,TextInputLayout inputA,TextInputLayout inputB,TextInputLayout inputU) {

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
            if(arraySelected[0] == 0){
                if(arraySelected[1] == 1){
                    operacoesConjuntos.calcularUniaoIntersecao(inputA,inputB);
                }else if(arraySelected[3] == 3){
                    operacoesConjuntos.calcularUniaoComplementar(inputA,inputB,inputU);
                }

            }
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
        if(checkVisibiliteFab()) {

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }else{

            fab.show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(null);
            fragmentManager.popBackStack();

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

}
