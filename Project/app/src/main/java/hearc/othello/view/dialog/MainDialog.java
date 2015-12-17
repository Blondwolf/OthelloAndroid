package hearc.othello.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import hearc.othello.R;

/**
 * Created by Kevin on 13.11.2015.
 */
public abstract class MainDialog extends Dialog implements View.OnClickListener{

    private DialogListener listener;
    private String title;

    public MainDialog(Context context, DialogListener listener, int layout, String title) {
        super(context);
        setOwnerActivity((Activity) context);
        setContentView(layout);
        setTitle(title);

        this.listener = listener;
        this.title = title;

        initGraphicElement();
        show();
    }

    protected abstract void initGraphicElement();

    public void callListener(Bundle bundle){
        listener.onDialogResult(this, bundle);
    }

    public String getTitle(){
        return title;
    }
}
