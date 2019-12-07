package elementar.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import elementar.analise.combinatoria.Dialog.MyAlertDialog;
import elementar.analise.combinatoria.activitys.MainActivity;
import elementar.matematica.pedrock.matemticaelementar.R;
import me.jfenn.attribouter.Attribouter;


public class ScreenAbout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Attribouter.from(this)
                .withFile(R.xml.about)
                .show();

    }

    @Override
    public void onBackPressed() {
        MyAlertDialog myAlertDialog = new MyAlertDialog(this,"Voltar ao início","Deseja voltar para a tela inicial?","Sim","Não");
        myAlertDialog.backHome(this);

    }
}
