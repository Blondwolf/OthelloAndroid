package hearc.othello.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Kevin on 06.11.2015.
 */
public class AndroidTools {
    
    public static void Toast(Context context, String text){
        Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}
