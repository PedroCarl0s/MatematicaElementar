package elementar.matematica.pedrock.matemticaelementar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.airbnb.lottie.LottieAnimationView;

import elementar.analise.combinatoria.TelaCombinatoria;
import elementar.teoria.dos.conjuntos.TelaConjuntos;

public class MainActivity extends AppCompatActivity {

    private final int  DELAY_TIME = 2100;
    private static LottieAnimationView animationClock;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void abrirAnaliseCombinatoria(View view) {

        // Inicia a animação Lottie
        startAnimationClock(animationClock, "clock.json");

        // Desativa a Activity (impedir clique em outro botão)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        // Inicia a Thread (delay + pré-carregamento da activity)
        thread = new Thread(runnable);
        thread.start();
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            try {
                Thread.sleep(DELAY_TIME );

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Inicia a Activity TelaCombinatoria
            try {
                startActivity(new Intent(MainActivity.this, TelaCombinatoria.class));
                finish();

            } catch (Exception e) {

            }
        }

    };

    private void startAnimationClock(LottieAnimationView animationView, String jsonFile) {
        animationView = findViewById(R.id.animation_clock);
        animationView.setAnimation(jsonFile);
        animationView.setProgress(1.0f);
        animationView.playAnimation();
    }

    public void abrirOperacoesConjuntos(View view) {
        startActivity(new Intent(this, TelaConjuntos.class));
        finish();
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