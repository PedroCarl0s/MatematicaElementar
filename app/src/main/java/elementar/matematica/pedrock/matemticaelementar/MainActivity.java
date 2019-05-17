package elementar.matematica.pedrock.matemticaelementar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import elementar.analise.combinatoria.TelaCombinatoria;
import elementar.teoria.dos.conjuntos.TelaConjuntos;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void abrirAnaliseCombinatoria(View view) {
        //Toast.makeText(getApplicationContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, TelaCombinatoria.class));
        finish();
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