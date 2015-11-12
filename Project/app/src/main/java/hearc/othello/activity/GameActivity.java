package hearc.othello.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;

import hearc.othello.R;
import hearc.othello.model.GameBoard;

public class GameActivity extends AppCompatActivity implements Button.OnClickListener {

    private boolean player1Turn = true;
    private TableLayout tableLayout;
    private GameBoard gameboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        tableLayout = (TableLayout) findViewById(R.id.graphic_gameboard);

        gameboard = new GameBoard();
        updateGraphicGameBoard();
    }

    @Override
    public void onClick(View v) {
        ImageView cell = (ImageView) v;

        //TODO : Checker si la cellule est deja pleine

        //Changement d'image, a optimiser
        if(player1Turn){
            cell.setImageResource(R.drawable.circle_red);
        }
        else{
            cell.setImageResource(R.drawable.circle_blue);
        }

        player1Turn = !player1Turn;
    }

    private void addGraphicalCoin(){

    }

    private ImageView getCaseView(int row, int col){
        ImageView imgView;

        //Get case view from coord.
        TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
        imgView = (ImageView) tableRow.getChildAt(col);

        //if (view instanceof TableRow) //Peut etre mieux

        return imgView;
    }

    private void updateLogicalGameBoard(){

    }

    private void updateGraphicGameBoard(){
        ImageView imgView;

        for (int i=0; i<tableLayout.getChildCount();i++){

        }

        for (int i = 0; i < GameBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.BOARD_SIZE; j++) {
                int cellValue = gameboard.getPlayerIDAtPos(i, j);

                if (GameBoard.NO_COIN != cellValue) {
                    imgView = getCaseView(i, j);
                    if(GameBoard.BLUE_COIN == cellValue) {
                        imgView.setImageResource(R.drawable.circle_blue);
                    }
                    else if(GameBoard.RED_COIN == cellValue){
                        imgView.setImageResource(R.drawable.circle_red);
                    }
                }
            }
        }
    }
}
