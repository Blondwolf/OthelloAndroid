package hearc.othello.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hearc.othello.R;
import hearc.othello.activity.GameActivity;
import hearc.othello.activity.InstructionActivity;
import hearc.othello.tools.AndroidTools;

public class HomeActivity extends AppCompatActivity implements Button.OnClickListener{

    //final Context context = this;

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

        switch (v.getId()){
            case R.id.vsIA:

                final Dialog dialog = new Dialog(HomeActivity.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.dialog_ia);
                // Set dialog title
                dialog.setTitle("Parie Vs IA");
                dialog.show();
                Button newBtn = (Button) dialog.findViewById(R.id.newGame);
                // if new button is clicked, close the custom dialog
                newBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        intent = new Intent(HomeActivity.this, GameActivity.class);
                        startActivity(intent);
                    }
                });
                break;
            case R.id.vsLocal:
                //
                final Dialog dialoglocal = new Dialog(HomeActivity.this);
                // Include dialog.xml file
                dialoglocal.setContentView(R.layout.dialog_local);
                // Set dialog title
                dialoglocal.setTitle("Partie en Local");
                dialoglocal.show();
                Button newGameLocal = (Button) dialoglocal.findViewById(R.id.newGamelocal);
                // if new button is clicked, close the custom dialog
                newGameLocal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AndroidTools.Toast(HomeActivity.this, "VS Local");
                    }
                });
                break;
            case R.id.vsWifi:
                AndroidTools.Toast(this, "VS Wifi");
                break;
            case R.id.instruction:
                intent = new Intent(HomeActivity.this, InstructionActivity.class);
                startActivity(intent);
                break;

        }


    }
}
