package hearc.othello.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import hearc.othello.R;
import hearc.othello.model.Player;
import hearc.othello.view.activity.GameActivity;

/**
 * Created by Kevin on 26.11.2015.
 */
public class NewOrEndDialog extends MainDialog implements View.OnClickListener {

    public static final int DISMISS = -1;
    public static final int NEW = 0;

    String textWinner;

    public NewOrEndDialog(Context context, DialogListener listener) {
        super(context, listener, R.layout.layout_dialog_new_end, "New or quit?");
    }

    @Override
    protected void initGraphicElement() {
        Button newGame, cancel;
        TextView labelWinner;
        if(findViewById(R.id.text_winner) instanceof TextView) {
            GameActivity activity = (GameActivity) getOwnerActivity();
            Player winner = activity.getNameWinner();
            labelWinner =(TextView) findViewById(R.id.text_winner);
            labelWinner.setText("The Winner is "+winner.getName()+" !");
        }
        newGame = (Button) findViewById(R.id.newGame);
        newGame.setOnClickListener(this);
        cancel = (Button) findViewById(R.id.quit);
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
            bundle.putInt("newOrEnd", v.getId());

            dismiss();
            callListener(bundle);
        }

    }
}
