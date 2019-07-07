package elementar.analise.combinatoria.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import elementar.matematica.pedrock.matemticaelementar.R;

public class HistoricoFragment extends Fragment {

    private View view;
    private ListView historico;
    private ArrayAdapter adapter;
    private String[] array = new String[]{"novo","novo","novo"};

    public HistoricoFragment() { }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);
        return view;
    }
}
