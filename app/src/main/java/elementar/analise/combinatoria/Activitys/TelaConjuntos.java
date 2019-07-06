package elementar.analise.combinatoria.Activitys;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.SpinnerAdapterCheckBox;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaConjuntos extends AppCompatActivity {

    private TextInputLayout conjuntoA;
    private TextInputLayout conjuntoB;
    private TextInputLayout conjuntoU;

    private Button btn_calc;

    private MathView animationWhite, animetionSwipe, resultadoFinal, resultadoPasso;

    private FloatingActionButton fab;

    private GridLayout grupoGrid;

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
        init();

    }

    private void init(){

        instanceComponentes();

    }

    public boolean isComplementar(){
        return false;
    }

    private void instanceComponentes(){

        conjuntoA = findViewById(R.id.conjuntoA);
        conjuntoB = findViewById(R.id.conjuntoB);
        conjuntoU = findViewById(R.id.conjuntoC);

    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
