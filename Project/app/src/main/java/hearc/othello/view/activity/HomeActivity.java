package hearc.othello.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import hearc.othello.R;
import hearc.othello.tools.AndroidTools;
import hearc.othello.view.dialog.DialogListener;
import hearc.othello.view.dialog.LocalDialog;
import hearc.othello.view.dialog.MainDialog;
import hearc.othello.view.dialog.NewOrLoadDialog;

public class HomeActivity extends AppCompatActivity implements Button.OnClickListener, DialogListener{

    /*         Creation        */
    /*private Mode mode;
    public enum Mode{
        VS, IA, WIFI
    }*/
    private int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_home);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    /*         Events Handlers        */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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
                AndroidTools.Toast(this, "Partie en Wifi");
                break;
            case R.id.instruction:
                intent = new Intent(HomeActivity.this, InstructionActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onDialogResult(MainDialog caller, Bundle result) {
        //Identify dialog
        switch (caller.getTitle()){
            case "New or load?":
                int buttonId = result.getInt("newOrLoad");
                switch (buttonId){
                    case R.id.newGame:
                        switch (mode){
                            case R.id.vsLocal:
                                Dialog dialog = new LocalDialog(this, this);
                                dialog.show();
                                break;
                            case R.id.vsIA:
                                //Dialog dialog = new IADialog(this);
                                launchIAGame();
                                break;
                        }

                        break;
                    case R.id.loadGame:
                        //TODO : Load preferences
                        break;
                    default:
                        //DISMISS
                }
                break;

            case "Partie locale":
                //Get back infos
                String p1 = result.getString("player1");
                String p2 = result.getString("player2");
                launchLocalGame(p1, p2);
                break;
        }
    }

    /*         Privates Functions       */
    private void launchGameActivity(String namePlayer1, String namePlayer2, int mode){
        Intent intent;
        intent = new Intent(HomeActivity.this, GameActivity.class);
        intent.putExtra("Type", mode);//TODO : dans strings
        intent.putExtra("NameP1", namePlayer1);
        intent.putExtra("NameP2", namePlayer2);
        startActivity(intent);
    }

    public void launchIAGame(){
        launchGameActivity("Player", "IA", R.id.vsIA);
    }

    public void launchLocalGame(String namePlayer1, String namePlayer2){
        launchGameActivity(namePlayer1, namePlayer2, R.id.vsLocal);
    }
}
