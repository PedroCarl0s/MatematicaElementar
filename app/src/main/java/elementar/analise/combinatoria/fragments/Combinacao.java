package elementar.analise.combinatoria.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class Combinacao extends Fragment {

    private View view;
    private Handler handler;

    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaCombinacao, resultadoCombinacao;
    private static int valorElementos, valorPosicoes;
    private Button btnCalcular;
    private boolean jaCalculou = false;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int ID_WRITE = R.id.animation_write, ID_SWIPE = R.id.animation_swipe;
    private final int DELAY_TIME = 750;
    private static final int ERRO_CONVERSAO = -100000;

    public Combinacao() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_combinacao, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        init();
    }

    private void init() {
        this.inputElementos = view.findViewById(R.id.elementos_combinacao);
        this.inputPosicoes = view.findViewById(R.id.posicoes_combinacao);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);

        this.formulaCombinacao = view.findViewById(R.id.formula_combinacao);
        this.resultadoCombinacao = view.findViewById(R.id.resultado_combinacao);

        this.btnCalcular = view.findViewById(R.id.btn_calcular);

        String formulaCombinacao = "$$\\normalsize \\bold{Formula}$$" + "$${C(n, p)} = \\frac{n!} {p! \\ (n-p)!}, n \\geqslant p$$";

        this.formulaCombinacao.setText(formulaCombinacao);
    }

}