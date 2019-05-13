package elementar.analise.combinatoria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


    private TextInputLayout inputElementos = getView().findViewById(R.id.elementos_arranjo);
    private TextInputLayout inputPosicoes = getView().findViewById(R.id.posicoes_arranjo);

    private MathView formulaArranjo = (MathView) getView().findViewById(R.id.formula_arranjo);
    private MathView resultadoArranjo = (MathView) getView().findViewById(R.id.resultado_arranjo);

    private String valorElementos = inputElementos.getEditText().toString();
    private String valorPosicoes = inputPosicoes.getEditText().toString();

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

        String formulaArranjo = "Fórmula do Arranjo" + "$$A(n, p) = \\frac{n!} {(n-p)!}$$";
        this.formulaArranjo.setText(formulaArranjo);
    }

    public String gerarArranjoLaTeX() {

        String numeradorElementos = Calculadora.gerarFatorialElementos(inputElementos, inputPosicoes);
        String denominadorPosicoes = Calculadora.gerarElementosMenosPosicoes(inputElementos, inputPosicoes);

        String resultado = "Resultado" +
                "$$A(" + this.valorElementos + ", " + this.valorPosicoes + ") = \\frac{" + this.valorElementos + "!} " +
                "{(" + this.valorElementos + "-" + this.valorPosicoes + ")!}$$";

        return resultado;

    }

    public void calcularArranjo(View view) {

        if (Calculadora.validarEntrada(inputElementos, inputPosicoes)) {
            this.resultadoArranjo.setText(gerarArranjoLaTeX());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_arranjo, container, false);

    /*
        MathView formula;
        int n = 10, p = 5;

        // Fórmula de Arranjo com código LaTeX
        String formulaArranjo = "Fórmula do Arranjo"
                + "$$A(n, p) = \\frac{n!} {(n-p)!}$$";

        formula = view.findViewById(R.id.formula_arranjo);
        formula.setText(formulaArranjo);
        */
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
