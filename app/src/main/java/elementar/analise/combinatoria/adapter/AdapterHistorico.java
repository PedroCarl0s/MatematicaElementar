package elementar.analise.combinatoria.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import elementar.analise.combinatoria.ItemClick.ItemClickListener;
import elementar.analise.combinatoria.model.OpConjuntos;
import elementar.matematica.pedrock.matemticaelementar.R;

public class AdapterHistorico extends RecyclerView.Adapter<AdapterHistorico.MyViewHolder> {

    private static AdapterHistorico instance = null;
    private List<OpConjuntos> listHistorico;
    private ItemClickListener itemClickListener;

    public static AdapterHistorico getInstance(List<OpConjuntos> list){
        if(instance == null){
            instance = new AdapterHistorico(list);
        }
        return instance;
    }

    private AdapterHistorico(List<OpConjuntos> list){
        listHistorico = list;

    }

    //inicio o xml do adpater
    @NonNull
    @Override
    public AdapterHistorico.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater myInflater = LayoutInflater.from(parent.getContext());
        view = myInflater.inflate(R.layout.adapterhistorio,parent,false);
        return new MyViewHolder(view);

    }


    //seto os valores para os componentes do adapter
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final OpConjuntos conjuntos = listHistorico.get(position);

        if(conjuntos.getConjuntoU() != null){

            holder.conjuntoU.setVisibility(View.VISIBLE);
            holder.conjuntoU.setText("U = { "+conjuntos.getConjuntoU()+" }");

        }

        holder.conjuntoA.setText("A = { "+conjuntos.getConjuntoA()+" }");
        holder.conjuntoB.setText("B = { "+conjuntos.getConjuntoB()+" }");
        holder.listResultado.setText(conjuntos.getRespostaConjuntos().toString());


    }

    @Override
    public int getItemCount() {
        return listHistorico.size();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public void addListItem(OpConjuntos opConjuntos, int position){
        this.listHistorico.add(opConjuntos);
        notifyItemInserted(position);
    }

    //inicio os componentes do xml do adpater
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView conjuntoA, conjuntoB, conjuntoU;
        TextView listResultado;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.adapterHistorico);
            conjuntoA = itemView.findViewById(R.id.textConjA);
            conjuntoB = itemView.findViewById(R.id.textConjB);
            conjuntoU = itemView.findViewById(R.id.textConjU);
            listResultado = itemView.findViewById(R.id.ResultadoHistorico);
            linearLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(itemClickListener != null){
                itemClickListener.onItemClickListiner(getAdapterPosition());
            }
        }
    }
}
