package hearc.othello.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import hearc.othello.R;
import hearc.othello.model.Game;
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

        mode = v.getId();
        switch (mode){
            case R.id.vsLocal:
                new NewOrLoadDialog(this, this).show();
                break;
            case R.id.vsIA:
                new NewOrLoadDialog(this, this).show();
                break;
            case R.id.vsWifi:
                new NetworkDialog(this, this).show();
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
                        switch (mode){
                            case R.id.vsLocal:
                                new LocalDialog(this, this).show();
                                break;
                            case R.id.vsIA:
                                new AIDialog(this, this).show();
                                break;
                        }
                        break;
                    case R.id.loadGame:
                        //TODO : ~Choose save file
                        //TODO : redirect with mode -> bad conception

                        List<String> list = Arrays.asList(getFilesDir().list());
                        if(list.contains("game_"+mode)) {
                            intent = initGameIntent();
                            intent.putExtra("load_file", "game_");
                            launchGame(intent);
                        }
                        else{
                            Tools.Toast(this, "No Game founded");
                        }
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
                launchGame(intent);
                break;

            case "Configurer l'IA":
                intent = initGameIntent();
                intent.putExtra("IA_level2", result.getInt("IA_level"));
                intent.putExtra("NameP1", "Player");
                intent.putExtra("NameP2", "IA");
                launchGame(intent);
                break;
        }
    }

    /*         Privates Functions       */
    private Intent initGameIntent(){
        return new Intent(HomeActivity.this, GameActivity.class);
    }

    public void launchGame(Intent intent){
        switch (mode){
            case R.id.vsLocal:
                intent.putExtra("Type", mode);//TODO : dans strings
                startActivity(intent);
                break;
            case R.id.vsIA:
                intent.putExtra("Type", mode);//TODO : dans strings
                startActivity(intent);
                break;
        }
    }
}
