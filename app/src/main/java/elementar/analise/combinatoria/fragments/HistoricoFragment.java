package elementar.analise.combinatoria.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elementar.analise.combinatoria.activitys.TelaConjuntos;
import elementar.analise.combinatoria.adapter.AdapterHistorico;
import elementar.analise.combinatoria.model.OpConjuntos;
import elementar.matematica.pedrock.matemticaelementar.R;

public class HistoricoFragment extends Fragment {

    private View view;
    private ListView historico;
    private ImageButton back;
    private RecyclerView myRecycler;



    public HistoricoFragment() { }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String[] s = new String[]{"Uniao","Intersecsao"};
        view = inflater.inflate(R.layout.fragment_historico, container, false);
        init();
        actionWidgets();
        return view;
    }

    public void init(){

        myRecycler = view.findViewById(R.id.recyclerViewId);
        List<OpConjuntos> listTemporaria = new ArrayList<>();
        listTemporaria.add(new OpConjuntos("2","3,4", null,Arrays.asList("A U B = {3,2,}")));
        listTemporaria.add(new OpConjuntos("2","3,4", null,Arrays.asList("A U B = {3,2,}")));
        listTemporaria.add(new OpConjuntos("2","3,4", null,Arrays.asList("A U B = {3,2,}")));
        listTemporaria.add(new OpConjuntos("2","3,4", null,Arrays.asList("A U B = {3,2,}")));
        listTemporaria.add(new OpConjuntos("2","3,4", null,Arrays.asList("A U B = {3,2,}")));
        listTemporaria.add(new OpConjuntos("2","3,4", "9,8,7",Arrays.asList("A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}")));

//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}, A = {2}, B = {3,4}, A U B = { 2,3,4}");
//        listTemporaria.add("A = {2}, B = {3,4}, A U B = { 2,3,4}");
        AdapterHistorico recyclerAdapter = AdapterHistorico.getInstance(listTemporaria);
        myRecycler.setAdapter(recyclerAdapter);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(layout);
        back = view.findViewById(R.id.back);
    }

    public void actionWidgets(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelaConjuntos.fab.show();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });


    }

}
