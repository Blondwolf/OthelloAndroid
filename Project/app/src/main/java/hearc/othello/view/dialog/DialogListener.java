package hearc.othello.view.dialog;

import android.app.Dialog;
import android.os.Bundle;

/**
 * Created by Kevin on 17.12.2015.
 */
public interface DialogListener {

    /***
     * Handle MainDialog children's result call
     * @param caller
     * @param result
     */
    void onDialogResult(MainDialog caller, Bundle result);
}
