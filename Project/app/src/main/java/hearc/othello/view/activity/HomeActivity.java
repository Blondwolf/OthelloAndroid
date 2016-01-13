package hearc.othello.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

import hearc.othello.R;
import hearc.othello.tools.Tools;
import hearc.othello.view.dialog.AIDialog;
import hearc.othello.view.dialog.DialogListener;
import hearc.othello.view.dialog.LocalDialog;
import hearc.othello.view.dialog.MainDialog;
import hearc.othello.view.dialog.NetworkDialog;
import hearc.othello.view.dialog.NewOrLoadDialog;

public class HomeActivity extends AppCompatActivity implements Button.OnClickListener, DialogListener{

    /*         Creation        */
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
    }

    /*         Events Handlers        */

    @Override
    public void onClick(View v) {
        Intent intent;
        Dialog dialog;

        mode = v.getId();
        switch (mode){
            case R.id.vsLocal:
                dialog = new NewOrLoadDialog(this, this);
                dialog.show();
                break;
            case R.id.vsIA:
                dialog = new NewOrLoadDialog(this, this);
                dialog.show();
                break;
            case R.id.vsWifi:
                dialog = new NetworkDialog(this, this);
                dialog.show();
                break;
            case R.id.instruction:
                intent = new Intent(HomeActivity.this, InstructionActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onDialogResult(MainDialog caller, Bundle result) {
        Intent intent;

        //Identify dialog
        switch (caller.getTitle()){
            case "New or load?":
                int buttonId = result.getInt("newOrLoad");
                switch (buttonId){
                    case R.id.newGame:
                        Dialog dialog;
                        switch (mode){
                            case R.id.vsLocal:
                                dialog = new LocalDialog(this, this);
                                dialog.show();
                                break;
                            case R.id.vsIA:
                                dialog = new AIDialog(this, this);
                                dialog.show();
                                break;
                        }
                        break;
                    case R.id.loadGame:
                        //Tools.readSerializable(file);
                        //TODO : Load preferences
                        break;
                    default:
                        //DISMISS
                }
                break;

            case "Partie locale":
                //Get back infos
                intent = initGameIntent();
                intent.putExtra("NameP1", result.getString("player1"));//Doble-bundle?
                intent.putExtra("NameP2", result.getString("player2"));
                launchLocalGame(intent);
                break;

            case "Configurer l'IA":
                intent = initGameIntent();
                intent.putExtra("IA_level2", result.getInt("IA_level"));
                launchIAGame(intent);
                break;
        }
    }

    /*         Privates Functions       */
    private Intent initGameIntent(){
        return new Intent(HomeActivity.this, GameActivity.class);
    }

    public void launchIAGame(Intent intent){
        intent.putExtra("Type", mode);//TODO : dans strings
        intent.putExtra("NameP1", "Player");
        intent.putExtra("NameP2", "IA");
        startActivity(intent);
    }

    public void launchLocalGame(Intent intent){
        intent.putExtra("Type", mode);//TODO : dans strings
        startActivity(intent);
    }
}
