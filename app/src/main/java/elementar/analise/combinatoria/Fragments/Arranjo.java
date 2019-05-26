package elementar.analise.combinatoria.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import elementar.analise.combinatoria.Calculadora;
import elementar.analise.combinatoria.GeradorFormulas;
import elementar.matematica.pedrock.matemticaelementar.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import io.github.kexanie.library.MathView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Arranjo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Arranjo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Arranjo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private View view;

    private static TextInputLayout inputElementos, inputPosicoes;
    private static TextInputEditText txtElementos, txtPosicoes;

    private static MathView formulaArranjo, resultadoArranjo;
    private static String valorElementos, valorPosicoes;
    private Button button_calcular;
    private boolean jaCalculou = false;
    private OnFragmentInteractionListener mListener;

    private LottieAnimationView animationWrite, animationSwipe;
    private final int DELAY_TIME = 2000;

    public Arranjo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Arranjo.
     */
    // TODO: Rename and change types and number of parameters
    public static Arranjo newInstance(String param1, String param2) {
        Arranjo fragment = new Arranjo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     }

    // Inicializa componentes de Input, MathView e Button
    public void init() {
        this.inputElementos = view.findViewById(R.id.elementos_arranjo);
        this.inputPosicoes = view.findViewById(R.id.posicoes_arranjo);

        this.txtElementos = view.findViewById(R.id.txt_elementos);
        this.txtPosicoes = view.findViewById(R.id.txt_posicoes);


        this.formulaArranjo = view.findViewById(R.id.formula_arranjo);
        this.resultadoArranjo = view.findViewById(R.id.resultado_arranjo);

        this.button_calcular = view.findViewById(R.id.btn_calcular);

        String formulaArranjo = "$$\\normalsize \\bold{\\text{Formula do Arranjo}}$$" + " $${A(n, p)} = \\frac{n!} {(n-p)!}, n \\geqslant p$$";

        String teste = "$$ x = \\begin{cases}" +
                " n = \\text{numero de elementos a arranjar }  \\\\" +
                " p = \\text{numero de posicoes a arranjar }" +
                " \\end{cases}$$";

        String letra = "$$\\normalsize \\bold{\\text{Formula do Arranjo}}$$";

        this.formulaArranjo.setText(formulaArranjo);
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    public void calcularArranjo(View view) {

        if (Calculadora.validarEntradas(inputElementos, inputPosicoes)) {
            MainActivity.hideKeyboard(getActivity());

            if (jaCalculou) {

                // Campos de entrada com os mesmos valores, não é necessário recalcular
                if (Arranjo.getNumeroElementos().equals(valorElementos) && Arranjo.getNumeroPosicoes().equals(valorPosicoes)) {

                // Uma ou as duas entradas distintas, é necessário calcular
                } else {
                    setResultado();

                    valorElementos = Arranjo.getNumeroElementos();
                    valorPosicoes = Arranjo.getNumeroPosicoes();

                    jaCalculou = true;
                }

            // Muda estado da variável jaCalculou e calcula (apenas no primeiro cálculo)
            } else {
                setResultado();

                jaCalculou = true;
                valorElementos = Arranjo.getNumeroElementos();
                valorPosicoes = Arranjo.getNumeroPosicoes();
            }

        }
    }

    private void startAnimationWrite(View view, LottieAnimationView animationView, String jsonFile) {
        animationView = view.findViewById(R.id.animation_write);
        animationView.setAnimation(jsonFile);
        animationView.setProgress(1.0f);
        animationView.playAnimation();
    }

    private void startAnimationSwipe(View view, LottieAnimationView animationView, String jsonFile) {
        animationView = view.findViewById(R.id.animation_swipe);
        animationView.setAnimation(jsonFile);
        animationView.setProgress(1.0f);
        animationView.playAnimation();
    }

    private void setResultado() {
        startAnimationWrite(view, animationWrite, "write.json");
        //startAnimationSwipe(view, animationSwipe, "swipe.json");

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                resultadoArranjo.setText(GeradorFormulas.gerarResultado());

            }
        }, DELAY_TIME);
    }


    public static String getNumeroElementos() {
        int elementos;

        try {
            elementos =  Integer.parseInt(inputElementos.getEditText().getText().toString());

        } catch (Exception e) {
            return "";
        }

        return Integer.toString(elementos);
    }

    public static String getNumeroPosicoes() {
        int posicoes;

        try {
            posicoes = Integer.parseInt(inputPosicoes.getEditText().getText().toString());

        } catch (Exception error) {
            return "";
        }

        return Integer.toString(posicoes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_arranjo, container, false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        init();

        // Método onClick do botão para calcular o Arranjo
        button_calcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularArranjo(view);
            }
        });

        // Troca o título do TextInput ao clicar
        txtElementos.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    inputElementos.setHint("N");

                } else {
                    inputElementos.setHint("Elementos a arranjar");
                }
            }
        });

        // Troca o título do TextInput ao clicar
        txtPosicoes.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    inputPosicoes.setHint("P");

                } else {

                    inputPosicoes.setHint("Posições a arranjar");
                }
            }
        });
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("elementos", Arranjo.getNumeroElementos());
        outState.putString("posicoes", Arranjo.getNumeroPosicoes());
        outState.putString("latex", resultadoArranjo.getText());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            MainActivity.hideKeyboard(getActivity());

            this.txtElementos.setText(savedInstanceState.getString("elementos"));
            this.txtPosicoes.setText(savedInstanceState.getString("posicoes"));

            MathView calculoRecuperado = view.findViewById(R.id.resultado_arranjo);
            calculoRecuperado.setText(savedInstanceState.getString("latex"));
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}