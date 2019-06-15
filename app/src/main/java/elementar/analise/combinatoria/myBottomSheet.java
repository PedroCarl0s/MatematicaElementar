package elementar.analise.combinatoria;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

import elementar.matematica.pedrock.matemticaelementar.R;

public class myBottomSheet {

    private View myViewBottomSheet;
    private BottomSheetBehavior myBottomSheetBehavior;
    private boolean expanded = false;

    public myBottomSheet(View view,int orientation, int idBootom){

        instanciarBottomSheet(view,orientation,idBootom);

    }

    public void usarBottomSheet(int myOrientation){

        if(verificarOrientacao(myOrientation)) {
            //faz o bottom sheet expandir e recuar
            if (myBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                myBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                myBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

    }


    public void usarBottomSheet(int myOrientation,BottomSheetBehavior myBehavior){

        if(verificarOrientacao(myOrientation)) {
            //faz o bottom sheet expandir e recuar
            if (myBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                myBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                myBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

    }

    private void instanciarBottomSheet(View view,int orientation, int idBootom){

        if(verificarOrientacao(orientation)){

            this.myViewBottomSheet = view.findViewById(idBootom);
            this.myBottomSheetBehavior = BottomSheetBehavior.from(myViewBottomSheet);

        }

    }

    // verifica a orientação da tela
    public boolean verificarOrientacao(int orientation){
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public boolean isExpanded() {
            Log.i("bomba","expandindo");
            myBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        expanded = true;
                    }else{
                        expanded = false;
                    }
                }

                @Override
                public void onSlide(View bottomSheet, float slideOffset) {
                }
            });

            return expanded;

    }

    public BottomSheetBehavior getMyBottomSheetBehavior(){
        return myBottomSheetBehavior;
    }


}
