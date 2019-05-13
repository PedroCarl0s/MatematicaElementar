package elementar.analise.combinatoria;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;

public class TelaCombinatoria extends AppCompatActivity {

    /*
    MathView permutationPlot;
    String n = "10",  p = "5";

    String arranjoForm = "Fórmula do Arranjo"
            + "$$A(n, p) = \\frac{n!} {(n-p)!}, \\ n \\geqslant p$$";

    String combinationForm = "Fórmula da Combinação:"
            + "$$C(n, p) = \\frac{n!} {p! \\ (n-p)!}, \\ n \\geqslant p$$";

    String primeiraImpressao = "Resultado" + "$$A(" + n + ", " + p +
            ") = \\frac{" + n + "!" + "} {(" + n + "-" + p + ")!}$$";


    public String resultado() {

        int elementos = 10;
        int posicoes = 5;

        StringBuilder numerador = new StringBuilder();

        // Percorre do valor do elemento até a subtração (n-p)!, para depois simplificar os valores
        for (int e = elementos; e >= (elementos-posicoes); e--) {
            numerador.append(Integer.toString(e));
            numerador.append(".");
        }

        // Deleta o último caracter (ponto final em excesso)
        numerador.delete(numerador.length()-1, numerador.length());

        numerador.append("!");

        String fim = numerador.toString();

        return fim;
    }
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_combinatoria);

        //permutationPlot = (MathView) findViewById(R.id.resultado_arranjo);
        //permutationPlot.setText(arranjoForm+ combinationForm);
        //permutationPlot.setText(arranjoForm + combinationForm + primeiraImpressao);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
