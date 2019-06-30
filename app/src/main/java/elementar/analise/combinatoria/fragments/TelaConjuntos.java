package elementar.analise.combinatoria.fragments;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import elementar.analise.combinatoria.SpinnerAdapterCheckBox;
import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaConjuntos extends AppCompatActivity {

    private Spinner spinner;
    private SpinnerAdapterCheckBox adapet;
    private SparseArray<String> array = new SparseArray<>();
    private MathView resultadoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_conjuntos);
        init();

    }

    private void init(){

        instanceComponentes();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            resultadoFinal.setText("asdfasfsdffsdfsdfasdfsdfasdf{,2,2,2,2}asdfasf\n" +
                    "sdffsdfsdfasdfsdfasdf{,2,2,2,\n" +
                    "2}asdfasfsdffsdfsdfasdfsdfasdf{,2,2,2,2}");
            array.append(0, "UNIÃO");
            array.append(1, "INTERSEÇÃO");
            array.append(2, "DIFERENÇA");
            array.append(3, "COMPLEMENTAR");
            array.append(4, "DIFERENÇA SIMPLES");
            array.append(5, "CONJ. DAS PARTES");
            adapet = new SpinnerAdapterCheckBox(this, array, "opções");
            spinner.setAdapter(adapet);

            Button b = findViewById(R.id.btnCalcular);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextInputLayout tc = findViewById(R.id.conjuntoC);
                    if (isComplementar()) {
                        tc.setVisibility(View.VISIBLE);
                    } else {
                        tc.setVisibility(View.GONE);
                    }

                }
            });
        }

    }

    public boolean isComplementar(){

        for(String array:adapet.getCheckedArray().split("-"))
            if (array.equalsIgnoreCase("complementar")) {
                return true;
            }
        return false;
    }

    private void instanceComponentes(){
        spinner = findViewById(R.id.mySpinner);
        resultadoFinal = findViewById(R.id.resultado_arranjoFinal);

    }

    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
