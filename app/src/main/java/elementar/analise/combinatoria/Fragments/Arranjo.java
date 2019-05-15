package elementar.analise.combinatoria.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import elementar.analise.combinatoria.Calculadora;
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

    private static MathView formulaArranjo, resultadoArranjo;
    private static String valorElementos, valorPosicoes;
    private Button button_calcular;
    private OnFragmentInteractionListener mListener;
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    // Gera o resultado em formato LaTeX para impressão na tela
    public String gerarArranjoLaTeX() {

        String numeradorElementos = Calculadora.gerarFatorialElementos();
        String denominadorPosicoes = Calculadora.gerarElementosMenosPosicoes();

        this.valorElementos = getNumeroElementos();
        this.valorPosicoes = getNumeroPosicoes();

        String resultado = "Resultado" +
                "$$A(" + this.valorElementos + ", " + this.valorPosicoes + ") = \\frac{" + this.valorElementos + "!} " +
                "{(" + Calculadora.gerarElementosMenosPosicoes() + ")!}$$";


        String segundoPasso = "$$A(" + this.valorElementos + ", " + this.valorPosicoes + ") = \\frac{" + numeradorElementos + "!} " +
                "{" + Calculadora.resultadoElementosMenosPosicoes() + "!}$$";

        return resultado + segundoPasso;
    }

    // Inicializa componentes de Input, MathView e Button
    public void init() {
        this.inputElementos = view.findViewById(R.id.elementos_arranjo);
        this.inputPosicoes = view.findViewById(R.id.posicoes_arranjo);


        this.formulaArranjo = view.findViewById(R.id.formula_arranjo);
        this.resultadoArranjo = view.findViewById(R.id.resultado_arranjo);

        this.button_calcular = view.findViewById(R.id.btn_calcular);

        String formulaArranjo = "Fórmula do Arranjo" + " $$A(n, p) = \\frac{n!} {(n-p)!}$$";
        this.formulaArranjo.setText(formulaArranjo);;
    }

    // Responsável por solicitar o cálculo e impressão no formato LaTeX
    public void calcularArranjo(View view) {
        if (Calculadora.validarEntradas(inputElementos, inputPosicoes)) {
            resultadoArranjo.setText(gerarArranjoLaTeX());
        }
    }

    public static String getNumeroElementos() {
        return inputElementos.getEditText().getText().toString();
    }

    public static String getNumeroPosicoes() {
        return inputPosicoes.getEditText().getText().toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_arranjo, container, false);
        init();

        // Método onClick do botão para calcular o Arranjo
        button_calcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularArranjo(view);
            }
        });

        return view;
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