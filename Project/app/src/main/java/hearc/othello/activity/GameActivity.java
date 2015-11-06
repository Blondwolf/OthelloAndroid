package hearc.othello.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import hearc.othello.R;
import hearc.othello.model.GameBoard;

public class GameActivity extends AppCompatActivity implements Button.OnClickListener {

    private GameBoard gameboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);


    }

    @Override
    public void onClick(View v) {
        ImageView cell = (ImageView) v;
        cell.setImageResource(R.drawable.circle_red);//Ok
    }
}
