package elementar.analise.combinatoria.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import elementar.analise.combinatoria.activitys.TelaConjuntos;
import elementar.matematica.pedrock.matemticaelementar.R;

public class HistoricoFragment extends Fragment {

    private View view;
    private ListView historico;
    private ArrayAdapter adapter;
    private String[] array = new String[]{"novo","novo","novo"};
    private  ImageButton back;

    public HistoricoFragment() { }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);
        back = view.findViewById(R.id.back);
        teste();
        return view;
    }

    public void teste(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelaConjuntos.fab.show();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
    }

}
