package elementar.analise.combinatoria.animationrecyclerview;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.recyclerview.widget.RecyclerView;

import elementar.analise.combinatoria.adapter.AdapterHistorico;
import elementar.matematica.pedrock.matemticaelementar.R;

public class MyAnimationRecyclerView {

    private RecyclerView myRecycler;
    private Context myContext;
    private AdapterHistorico adapterHistorico;

    public MyAnimationRecyclerView(RecyclerView recyclerView){

        this.myRecycler = recyclerView;

    }

    public void runAnimation(int type){

        myContext = myRecycler.getContext();
        LayoutAnimationController controllerAnimation = null;

        if(type == 0){
            controllerAnimation = AnimationUtils.loadLayoutAnimation(myContext, R.anim.layout_fall_down);

        }else if(type == 1){
            controllerAnimation = AnimationUtils.loadLayoutAnimation(myContext,R.anim.layout_slide_from_bottom);
        }else if(type == 2){
            controllerAnimation = AnimationUtils.loadLayoutAnimation(myContext,R.anim.layout_slide_from_right);
        }

//        set Animation
        myRecycler.setLayoutAnimation(controllerAnimation);
        myRecycler.getAdapter().notifyDataSetChanged();
        myRecycler.scheduleLayoutAnimation();

    }

}
