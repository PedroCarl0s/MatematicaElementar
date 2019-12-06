package elementar.analise.combinatoria.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ScrollDirectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import elementar.analise.combinatoria.ItemClick.ItemClickListener;
import elementar.analise.combinatoria.activitys.TelaConjuntos;
import elementar.analise.combinatoria.adapter.AdapterHistorico;
import elementar.analise.combinatoria.animationrecyclerview.MyAnimationRecyclerView;
import elementar.analise.combinatoria.model.OpConjuntos;
import elementar.matematica.pedrock.matemticaelementar.R;

public class HistoricoFragment extends Fragment implements View.OnClickListener {

    private View view;
    private ImageButton back;
    private RecyclerView myRecycler;
    private AdapterHistorico recyclerAdapter;
    private FloatingActionButton fab;

//    temporaria
    List<OpConjuntos> listTemporaria = new ArrayList<>();

    public HistoricoFragment() { }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);
        init();
        actionWidgets();
        return view;
    }

    public void init(){

        myRecycler = view.findViewById(R.id.recyclerViewId);
        myRecycler.setHasFixedSize(true);

        listTemporaria = getSetOpConjuntosList(10);

        recyclerAdapter = AdapterHistorico.getInstance(listTemporaria);

        myRecycler.setAdapter(recyclerAdapter);
        @SuppressLint("WrongConstant") RecyclerView.LayoutManager layout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        myRecycler.setLayoutManager(layout);
        MyAnimationRecyclerView myAnimationRecyclerView = new MyAnimationRecyclerView(myRecycler);
        myAnimationRecyclerView.runAnimation(1);
        back = view.findViewById(R.id.back);

        fab = view.findViewById(R.id.fab);

        //faz o fab sumir ao rolar o recycler view
        fab.attachToRecyclerView(myRecycler, new ScrollDirectionListener() {
            //saber qual a direção do scroll
            @Override
            public void onScrollDown() {

            }

            @Override
            public void onScrollUp() {

            }
        },new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) myRecycler.getLayoutManager();
//
//                AdapterHistorico adapterHistorico = (AdapterHistorico) myRecycler.getAdapter();
//
//                if(listTemporaria.size() == linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1){
//
//                    List<OpConjuntos> listAux = getSetOpConjuntosList(10);
//
//                    for(int i = 0;i < listAux.size(); i++){
//                        adapterHistorico.addListItem(listAux.get(i),listTemporaria.size());
//                    }
//                }

            }
        });

        fab.setOnClickListener(this);

    }

    public List<OpConjuntos> getSetOpConjuntosList(int qtd){
        String[] conjuntosA = new String[]{"1","2","3","4","5","6","7","8"};
        String[] conjuntosB = new String[]{"9","10","11","12","13","14","15","16"};
        List<OpConjuntos> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            OpConjuntos c = new OpConjuntos( conjuntosA[i % conjuntosA.length], conjuntosB[ i % conjuntosB.length ], null, Arrays.asList("A U B = { conjuntoA + Conjunto B}sdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdf"));
            listAux.add(c);
        }
        return(listAux);
    }

    public void actionWidgets(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TelaConjuntos.fab.show();
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        recyclerAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListiner(int position) {


                recuperarConjuntos(position);

            }
        });

    }

    private void recuperarConjuntos(int position){

        OpConjuntos obj = listTemporaria.get(position);
        String conjuntoA = obj.getConjuntoA();
        String conjuntoB = obj.getConjuntoB();
        String conjuntoU = obj.getConjuntoU();
        List<String> list = obj.getRespostaConjuntos();

        Intent intentRecuperar = new Intent(getContext(), TelaConjuntos.class);

        intentRecuperar.putExtra("conjA",conjuntoA);
        intentRecuperar.putExtra("conjB",conjuntoB);
        intentRecuperar.putExtra("conjU",conjuntoU);

        startActivity(intentRecuperar);

    }

    @Override
    public void onClick(View v) {

        //instancia um AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Deseja remover todo o histórico")
                .setTitle("Apagar");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"futuramente ira remover a lista",Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }
}
