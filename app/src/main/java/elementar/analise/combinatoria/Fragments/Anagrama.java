package elementar.analise.combinatoria.Fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Anagrama extends Fragment {

    private View view;

    private static TextInputLayout txtAnagrama;
    private static TextInputEditText inputAnagrama;

    private MathView formulaAnagrama, resultadoAnagrama;

    private Button btnCalcular;

    public Anagrama() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        init();

        String formulaAnagrama = "$$\\bold{Formula}$$" +
                "$$\\large {P_{n} \\ ^ {\\alpha, \\ \\beta, \\ \\gamma}} = \\small {\\frac{n!} {\\alpha! \\ \\beta! \\ ... \\ \\gamma!}}$$";

//        String formulaAnagrama = "$$\\text{Ocorrencias} \\begin{cases}" +
//                "a = & \\text{2 vezes} \\\\" +
//                "b = & \\text{3 vezes} \\\\" +
//                "c = & \\text{2 vezes}" +
//                "\\end{cases}$$";
            this.formulaAnagrama.setText(formulaAnagrama);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_anagrama, container, false);

        return view;
    }

    private void init () {
        this.txtAnagrama = (TextInputLayout) view.findViewById(R.id.txt_anagrama);
        this.inputAnagrama = (TextInputEditText) view.findViewById(R.id.inputAnagrama);

        this.formulaAnagrama = (MathView) view.findViewById(R.id.formula_anagrama);
        this.resultadoAnagrama = (MathView) view.findViewById(R.id.resultado_anagrama);

        this.btnCalcular = (Button) view.findViewById(R.id.btn_calcular);
    }

}
