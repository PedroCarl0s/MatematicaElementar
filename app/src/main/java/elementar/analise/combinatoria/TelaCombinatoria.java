package elementar.analise.combinatoria;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaCombinatoria extends AppCompatActivity {

    MathView permutationPlot;
    String n = "10",  p = "5";

    String arranjoForm = "Fórmula do Arranjo"
            + "$$A(n, p) = \\frac{n!} {(n-p)!}$$";

    String combinationForm = "Fórmula da Combinação:"
            + "$$C(n, p) = \\frac{n!} {p! \\ (n-p)!}$$";



    String primeiraImpressao = "Resultado" + "$$A(" + n + ", " + p +
            ") = \\frac{" + n + "!" + "} {(" + n + "-" + p + ")!}$$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_combinatoria);


        permutationPlot = (MathView) findViewById(R.id.resultado_arranjo);
        //permutationPlot.setText(arranjoForm+ combinationForm);
        permutationPlot.setText(primeiraImpressao + arranjoForm + combinationForm + primeiraImpressao + arranjoForm + combinationForm + primeiraImpressao + arranjoForm + combinationForm);



    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
