package elementar.teoria.dos.conjuntos;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;

public class TelaConjuntos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
