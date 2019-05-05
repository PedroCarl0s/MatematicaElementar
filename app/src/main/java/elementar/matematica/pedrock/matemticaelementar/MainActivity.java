package elementar.matematica.pedrock.matemticaelementar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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


}
