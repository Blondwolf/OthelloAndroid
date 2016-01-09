package hearc.othello.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import hearc.othello.R;
import hearc.othello.view.activity.HomeActivity;

/**
 * Created by Kevin on 13.11.2015.
 */
public class LocalDialog extends MainDialog implements View.OnClickListener{

    boolean first;
    EditText editNamePlayer1, editNamePlayer2;
    ImageView imgCoinPlayer1, imgCoinPlayer2;
    Button btnCancel, btnApply;

    public LocalDialog(Context context, DialogListener listener) {
        super(context, listener, R.layout.layout_dialog_local, "Partie locale");
    }

    @Override
    protected void initGraphicElement() {
        editNamePlayer1 = (EditText) findViewById(R.id.edit_player1);
        editNamePlayer2 = (EditText) findViewById(R.id.edit_player2);

        imgCoinPlayer1 = (ImageView) findViewById(R.id.img_player1);
        imgCoinPlayer2 = (ImageView) findViewById(R.id.img_player2);

        btnApply = (Button) findViewById(R.id.apply);
        btnCancel = (Button) findViewById(R.id.cancel);

        imgCoinPlayer1.setOnClickListener(this);
        imgCoinPlayer2.setOnClickListener(this);
        btnApply.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        first = false;//just for player switching
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_player1 ://No break
            case R.id.img_player2 :
                switchImage(imgCoinPlayer1, imgCoinPlayer2);
                break;
            case R.id.apply:
                Bundle bundle = new Bundle();
                bundle.putString("player1", editNamePlayer1.getText().toString());
                bundle.putString("player2", editNamePlayer2.getText().toString());
                callListener(bundle);
            case R.id.cancel:
                dismiss();
                break;
        }
    }

    private void switchImage(ImageView img1, ImageView img2) {
        //Switch image
        if(first) {
            img1.setImageResource(R.drawable.circle_red);
            img2.setImageResource(R.drawable.circle_blue);
        }
        else {
            img1.setImageResource(R.drawable.circle_blue);
            img2.setImageResource(R.drawable.circle_red);
        }

        //Switch the editText to match assignation in the sending
        EditText temp = editNamePlayer1;
        editNamePlayer1 = editNamePlayer2;
        editNamePlayer2 = temp;

        first = !first;//Switch
    }
}
