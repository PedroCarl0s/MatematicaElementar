package elementar.analise.combinatoria;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class myBottomSheet {

    private View myViewBottomSheet;
    private BottomSheetBehavior myBottomSheetBehavior;
    private boolean expanded = false;

    public myBottomSheet(View view,int orientation, int idBootom){

        instanciarBottomSheet(view,orientation,idBootom);

    }

    public void usarBottomSheet(int myOrientation){

        if(verificarOrientacaoVertical(myOrientation)) {
            //faz o bottom sheet expandir e recuar
            if (myBottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                myBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else if(myBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN){
                myBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

    }


    public void usarBottomSheet(int myOrientation,BottomSheetBehavior myBehavior){

        if(verificarOrientacaoVertical(myOrientation)) {
            //faz o bottom sheet expandir e recuar
            if (myBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                myBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }else{
                myBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }

        }

    }

    private void instanciarBottomSheet(View view,int orientation, int idBootom){

        if(verificarOrientacaoVertical(orientation)){

            this.myViewBottomSheet = view.findViewById(idBootom);
            this.myBottomSheetBehavior = BottomSheetBehavior.from(myViewBottomSheet);

        }

    }

    // verifica a orientação da tela
    public boolean verificarOrientacaoVertical(int orientation){
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public void bottomSheetCallback(final BottomSheetBehavior bottomSheetBehavior) {

            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(View bottomSheet, int newState) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    }
                }

                @Override
                public void onSlide(View bottomSheet, float slideOffset) {
                }
            });


    }

    public BottomSheetBehavior getMyBottomSheetBehavior(){
        return myBottomSheetBehavior;
    }


}
