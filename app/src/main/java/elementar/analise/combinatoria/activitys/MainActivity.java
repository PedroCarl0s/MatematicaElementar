package elementar.analise.combinatoria.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import elementar.lottie.LottieController;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.analise.combinatoria.activitys.TelaCombinatoria;
import elementar.lottie.LottieController;
import elementar.matematica.pedrock.matemticaelementar.R;
import elementar.analise.combinatoria.activitys.TelaConjuntos;

public class MainActivity extends AppCompatActivity {

    private final int  DELAY_TIME = 1100;
    public static LottieAnimationView animationClock;
    public final int CLOCK_ID = R.id.animation_clock;
    private GridLayout mainGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainGrid = (GridLayout) findViewById(R.id.mainGrid);
        setSingleEvent(mainGrid);
    }

    private void setSingleEvent(GridLayout mainGrid) {

        // Percorre todos os filhos do GridLayout Pai
        for (int atual = 0; atual < mainGrid.getChildCount(); atual++) {

            CardView cardView = (CardView) mainGrid.getChildAt(atual);
            final int choice = atual;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(choice);
                }
            });

        }
    }

    public void openActivity(int choice) {


        switch (choice) {

            case 0:
                escolherActivity(TelaCombinatoria.class);

            case 1:
                escolherActivity(TelaConjuntos.class);

            iniciarActivity();

            default:
                break;
        }


    }

    public void iniciarActivity() {

        TextView textView = findViewById(R.id.textGrid);
        textView.setVisibility(View.INVISIBLE);

        animationClock = findViewById(R.id.animation_clock);

        // Inicia a animação Lottie
        LottieController.startLottieAnimation(animationClock, CLOCK_ID, "clock.json", 1.5f, 0);

        // Desativa a Activity (impedir clique em outro botão)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

    }

    public void escolherActivity(final Class activity){

        new Thread() {
            @Override
            public void run() {

                try {
                    Thread.sleep(DELAY_TIME );

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Inicia a Activity
                try {
                    startActivity(new Intent(MainActivity.this, activity));
                    finish();
                    animationClock.cancelAnimation();

                } catch (Exception e) {

                }
            }

        }.start();

    }

    // Método para esconder o teclado após clicar em calcular
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMananger = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        // Verificar se a view está não está com foco
        View currentFocusedView = activity.getCurrentFocus();

        // Se não estiver com foco, então o teclado vai ser escondido
        if (currentFocusedView != null) {
            inputMananger.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}