package elementar.analise.combinatoria;

import android.content.res.Configuration;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class myBottomSheet {

    private View myViewBottomSheet;
    private BottomSheetBehavior myBottomSheetBehavior;

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

    private void instanciarBottomSheet(View view,int orientation, int idBootom){

        if(verificarOrientacao(orientation)){

            this.myViewBottomSheet = view.findViewById(idBootom);
            this.myBottomSheetBehavior = BottomSheetBehavior.from(myViewBottomSheet);

        }

    }

    // verifica a orientação da tela
    private boolean verificarOrientacao(int orientation){
        return orientation == Configuration.ORIENTATION_PORTRAIT;
    }

}
