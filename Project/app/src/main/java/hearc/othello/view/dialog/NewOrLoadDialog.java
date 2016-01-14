package hearc.othello.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import hearc.othello.R;
import hearc.othello.view.activity.HomeActivity;

/**
 * Created by Kevin on 26.11.2015.
 */
public class NewOrLoadDialog extends MainDialog implements View.OnClickListener {

    public static final int DISMISS = -1;
    public static final int NEW = 0;
    public static final int LOAD = 1;

    public NewOrLoadDialog(Context context, DialogListener listener) {
        super(context, listener, R.layout.layout_dialog_new_load, "New or load?");
    }

    @Override
    protected void initGraphicElement() {
        Button newGame, loadGame, cancel;

        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
        loadGame = (Button) findViewById(R.id.loadGame);
        loadGame.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.cancel) {
            dismiss();
        }
        else{
            //Send button id back to activity
            Bundle bundle = new Bundle();
            bundle.putInt("newOrLoad", v.getId());

            dismiss();
            callListener(bundle);
        }

    }
}
