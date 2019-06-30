package elementar.analise.combinatoria;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import elementar.matematica.pedrock.matemticaelementar.R;

public class SpinnerAdapterCheckBox extends ArrayAdapter {

    private SparseBooleanArray checked = new SparseBooleanArray();
    private SparseArray<String> array;
    private TextView titleView;

    public SpinnerAdapterCheckBox(@NonNull Context context, SparseArray<String> array, String title) {
        super(context,0);
        this.array = array;
        this.titleView = new TextView(context);
        this.titleView.setText(title);
        this.titleView.setTextSize(16);
        this.titleView.setTextColor(Color.BLACK);
        this.titleView.setPadding(100, 3, 0, 3);
    }

    public void setCheckedArray(SparseBooleanArray checked){
        this.checked = checked;
    }

    public String getCheckedKeys(){

        StringBuffer idArray = new StringBuffer();

        for(int index = 0;index < checked.size(); index++){
            idArray.append(array.keyAt(checked.keyAt(index)));
            idArray.append("-");
        }

        idArray.replace(idArray.length()-1,idArray.length(),"");

        return idArray.toString();

    }

    public String getCheckedArray(){

        StringBuffer string = new StringBuffer();

        if(checked.size() > 0){
            for(int index = 0;index < checked.size();index++){
                string.append(array.valueAt(checked.keyAt(index)));
                string.append("-");
            }

            string.replace(string.length()-1,string.length(),"");
        }

        return string.toString();
    }

    private class ViewHolder{
        CheckBox checkBox;
        TextView title;
    }

    private void handleDropDownHolder(final int position, ViewHolder holder){
        holder.title.setText(getItem(position));
        holder.checkBox.setChecked(checked.get(position,false));
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View checkBox) {
                setChecked(checkBox,position);
            }
        });

    }

    private void setChecked(View view, int position){
        if(checked.get(position,false)){
            ((CheckBox) view).setChecked(false);
            checked.delete(position);
        }else{
            ((CheckBox) view).setChecked(true);
            checked.put(position,true);
        }
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public String getItem(int position) {
        return array.valueAt(position);
    }

    class Item extends LinearLayout{

        ViewHolder holder;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        private Item(Context context) {
            super(context);
            setLayoutParams(params);
            setOrientation(LinearLayout.HORIZONTAL);
            setGravity(LinearLayout.TEXT_ALIGNMENT_CENTER);
            setBackgroundColor(Color.WHITE);
            CheckBox checkBox = new CheckBox(context);
            TextView title = new TextView(context);
            title.setLayoutParams(params);
            holder = new ViewHolder();
            holder.title = title;
            holder.checkBox = checkBox;
            setPadding(20,2,0,2);
            addView(checkBox);
            addView(title);

        }

        ViewHolder getHolder(){
            return holder;
        }
    }

    @Override
    public @NonNull View getDropDownView(int position, View view, @NonNull ViewGroup parent) {

        if(view == null){
            view = new Item(getContext());
        }
        handleDropDownHolder(position,((Item) view).getHolder());
        return view;

    }

    @Override
    public @NonNull View getView(int position, View view, @NonNull ViewGroup parent) {

        return titleView;
    }

}
