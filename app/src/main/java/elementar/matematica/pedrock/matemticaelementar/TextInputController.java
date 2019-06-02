package elementar.matematica.pedrock.matemticaelementar;


import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class TextInputController {

    public static void setLabelTextInput(final TextInputLayout inputLayout, TextInputEditText txtEdit, final String label1, final String label2) {
        txtEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {
                    inputLayout.setHint(label1);

                } else {
                    inputLayout.setHint(label2);
                }
            }
        });
    }

}