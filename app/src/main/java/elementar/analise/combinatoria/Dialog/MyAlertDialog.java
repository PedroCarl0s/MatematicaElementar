package elementar.analise.combinatoria.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import elementar.matematica.pedrock.matemticaelementar.activity.MainActivity;

public class MyAlertDialog {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private String nameButtonPositive;
    private String nameButtonNegative;
    private String title;
    private String message;
    private Context context;

    public MyAlertDialog(Context context,String title, String message, String nameButtonPositive, String nameButtonNegative) {

        this.nameButtonPositive = nameButtonPositive;
        this.nameButtonNegative = nameButtonNegative;
        this.title = title;
        this.message = message;
        this.context = context;

        builder = new AlertDialog.Builder(context);

    }

    public MyAlertDialog(Context context){

        this(context,"title","messsage","positive","negative");

    }

    public void backHome(){

        builder.setTitle(this.title);
        builder.setMessage(this.message);

        builder.setPositiveButton(nameButtonPositive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);

            }
        });

        builder.setNegativeButton(nameButtonNegative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setMessage(String message){
        this.message = message;
    }
}