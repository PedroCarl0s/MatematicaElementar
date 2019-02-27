package elementar.matematica.pedrock.matemticaelementar;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TelaOperacoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_operacoes);

        //dialogIntrucoes();

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);

        dialog.setTitle("Instruções para utilização");
        dialog.setMessage("Você deverá inserir os elementos do conjunto, separados por vírgula.\n\nExemplo: O conjunto A = {1, 2, 3, 4, 5}, deverá ser digitado dessa forma abaixo:\n\n1,2,3,4,5");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_info);

        dialog.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.create();
        dialog.show();


    }

    public void dialogIntrucoes() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog_Alert);

        dialog.setTitle("Instruções para utilização");
        dialog.setMessage("Você deverá inserir os elementos do conjunto, separados por vírgula.\n\nExemplo: O conjunto A = {1, 2, 3, 4, 5}, deverá ser digitado dessa forma abaixo:\n\n1,2,3,4,5");
        dialog.setCancelable(false);
        dialog.setIcon(android.R.drawable.ic_dialog_info);

        dialog.setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dialog.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
