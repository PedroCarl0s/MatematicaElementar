package elementar.analise.combinatoria.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.geradores.GeradorOperacoesConjuntos;
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

    private FloatingActionButton fab,fabHistorico,fabRemoveAll;

    private GridLayout grupoGrid;

    private RelativeLayout relativeLayout;

    private boolean liberarCalculo = false;

    private int[] arraySelect = new int[]{-1,-1,-1,-1,-1,-1};

    private final int VALOR_COMPLEMENTAR = 3;

    private final int INDEX_COMPLEMENTAR = 3;

    private int posicaoArray = 0;

    private GeradorOperacoesConjuntos operacoesConjuntos;

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

    }

    @SuppressLint("RestrictedApi")
    private void instanceComponentes(){

        operacoesConjuntos = new GeradorOperacoesConjuntos();

        conjuntoA = findViewById(R.id.conjuntoA);
        conjuntoB = findViewById(R.id.conjuntoB);
        conjuntoU = findViewById(R.id.conjuntoC);

        btn_calc = findViewById(R.id.btnCalcular);

        animationWhite = findViewById(R.id.animation_write3);
        animetionSwipe = findViewById(R.id.animation_swipe);

        resultadoFinal = findViewById(R.id.resultado_conjuntosFinal);
        resultadoFinal.setText("$$\\bold{Resultado}$$");
        resultadoPasso = findViewById(R.id.resultado_conjutosPasso);
        resultadoPasso.setVisibility(View.GONE);

        relativeLayout = findViewById(R.id.bottomsheetConjunto);

        if(relativeLayout.getVisibility() == View.VISIBLE && !liberarCalculo){

            relativeLayout.setVisibility(View.GONE);

        }

        fab = findViewById(R.id.botao_info);
        fabHistorico = findViewById(R.id.fab_historico);
        fabRemoveAll = findViewById(R.id.fab_removeAll);
        fabHistorico.setVisibility(View.GONE);
        fabRemoveAll.setVisibility(View.GONE);

        grupoGrid = findViewById(R.id.gridLayout);

        initBackGroundCards(grupoGrid);

    }

    private void onClicks(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    private void addArray(CardView card, int valor){

        if(arraySelect[valor] == -1){

            arraySelect[valor] = valor;
            changeBackGround(card, R.drawable.backgroundbordselected);

        }else if(arraySelect[valor] == valor){

            arraySelect[valor] = -1;
            changeBackGround(card, R.drawable.backgroundbord);

        }
    }

    private void changeBackGround(CardView card, int drawable){
        card.setBackgroundResource(drawable);
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
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
