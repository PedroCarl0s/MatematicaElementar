package elementar.analise.combinatoria.floatingbutton;

import android.content.Context;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import elementar.matematica.pedrock.matemticaelementar.R;

public class MenuFloatingButton {

    private List<FloatingActionButton> arrayFloating;
    private int[] standards = new int[]{R.dimen.standard_55,R.dimen.standard_105,R.dimen.standard_155};
    private boolean isFABOpen = false;
    private boolean showMenu = false;
    private Context myContext;

    public MenuFloatingButton(Context context){

        myContext = context;
        arrayFloating = new ArrayList<>();
    }

    public void addFloating(FloatingActionButton floating){
        arrayFloating.add(floating);
        arrayFloating.get(arrayFloating.size()-1).animate().translationY(0);
    }

    public void controleMenuFab(FloatingActionButton fab){

        initAnimateOpenCloseFab(fab,isFABOpen);

        if(!isFABOpen){
            showFABMenu();
            showMenu = false;
        }else{
            closeFABMenu();
            showMenu = true;

        }

    }
    
    public boolean isShowMenu(){
        return showMenu;
    }

    private void showFABMenu(){
        isFABOpen = true;
        for(int i = 0;i < arrayFloating.size();i++){
            arrayFloating.get(i).animate().translationY(myContext.getResources().getDimension(standards[i]));
        }
    }

    private void closeFABMenu(){
        isFABOpen = false;
        for(int i = 0;i < arrayFloating.size();i++){
            arrayFloating.get(i).animate().translationY(0);
        }
    }

    private void initAnimateOpenCloseFab(FloatingActionButton fab, boolean click){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Interpolator interpolador = AnimationUtils.loadInterpolator(myContext,
                    android.R.interpolator.fast_out_slow_in);

            fab.animate()
                    .rotation(click ? 45f : 0)
                    .setInterpolator(interpolador)
                    .start();
        }

    }

    public void showAndHide(FloatingActionButton fab){

        if(fab.isShown()){

            fab.hide();

        }else{

            fab.show();

        }

    }

}
