package hearc.othello.view.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import hearc.othello.R;

/**
 * Created by Kevin on 13.11.2015.
 */
public class AIDialog extends MainDialog implements View.OnClickListener {

    Spinner spinner;
    Button cancel;
    Button apply;

    public AIDialog(Context context, DialogListener listener) {
        super(context, listener, R.layout.layout_dialog_ia, "Configurer l'IA");
    }

    @Override
    protected void initGraphicElement() {
        spinner = (Spinner) findViewById(R.id.spinner_IA_level);
        apply = (Button) findViewById(R.id.apply);
        cancel = (Button) findViewById(R.id.cancel);

        apply.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.apply) {
            //Send button id back to activity
            int valueIA = spinner.getSelectedItemPosition();

            Bundle bundle = new Bundle();
            bundle.putInt("IA_level", (valueIA + 2));

            dismiss();
            callListener(bundle);
        }
        else if(v.getId() == R.id.cancel) {
            dismiss();
        }
    }
}
