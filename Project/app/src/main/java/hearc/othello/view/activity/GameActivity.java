package hearc.othello.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import hearc.othello.R;
import hearc.othello.model.AI.PlayerAI;
import hearc.othello.model.GameBoard;
import hearc.othello.model.Move;
import hearc.othello.model.Player;
import hearc.othello.model.PlayerHuman;
import hearc.othello.tools.AndroidTools;

public class GameActivity extends AppCompatActivity implements Button.OnClickListener {
    /*      Graphical elements      */
    private TableLayout tableLayout;
    private TextView textTime;
    private TextView textScore;

    /*      Static text elements    */
    private static final String timeText = "Time : ";
    private static final String scoreText = " score ";

    /*      Logical elements        */
    private GameBoard gameboard;
    private Player p1;
    private Player p2;
    private Player actualPlayer;
    private Player enemyPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game);

        //Getting graphical elements
        tableLayout = (TableLayout) findViewById(R.id.graphic_gameboard);
        textTime = (TextView) findViewById(R.id.time);
        textScore = (TextView) findViewById(R.id.score);

        //Initiate the game
        int mode = getIntent().getIntExtra("Type", -1);
        String nameP1 = getIntent().getStringExtra("NameP1");
        String nameP2 = getIntent().getStringExtra("NameP2");
        actualPlayer = p1 = new PlayerHuman(0, gameboard, nameP1);
        if(mode == R.id.vsIA)
            enemyPlayer = p2 = new PlayerAI(1, gameboard, 3);
        else
            enemyPlayer = p2 = new PlayerHuman(1, gameboard, nameP2);

        gameboard = new GameBoard();
        updateGraphic();

        //TODO : launch thread for time
        /*CustomTimerTask myTask = new CustomTimerTask();
        Timer myTimer = new Timer();
        myTimer.schedule(myTask, 3000, 1500);*/
    }

    /***
     * OnClick for the gameBoard's cells
     * @param v : View from the cell where the player want to add a piece
     */
    @Override
    public void onClick(View v) {
        //Get move from cell
        ImageView cell = (ImageView) v;
        Move nextMove = getMoveFromCase(cell);

        //Check if move is possible
        if(gameboard.isMovePossible(nextMove.getLine(), nextMove.getColumn(), actualPlayer.getID())) {
            AndroidTools.Toast(getApplicationContext(), actualPlayer.toString());

            //Play move
            gameboard.addCoin(nextMove, actualPlayer.getID());

            //Check if IA for automatical move
            if(enemyPlayer instanceof PlayerAI){//Check mode instead
                updateGraphic();
                //TODO : Little timer to simulate IA thinking

                //Automatical play from IA
                Move nextEnemyMove = enemyPlayer.nextPlay();
                gameboard.addCoin(nextEnemyMove, enemyPlayer.getID());
            }
            //Else is HumanPlayer
            else{
                //Changing player turn
                Player tempPlayer = actualPlayer;
                actualPlayer = enemyPlayer;
                enemyPlayer = tempPlayer;
            }

            //Update graphic and score
            updateGraphic();
        }
        else{
            AndroidTools.Toast(getApplicationContext(), "Move : "+nextMove.toString()+" is not possible !");
        }
    }

    /***
     * Get move with coords relative to the view supplied
     * @param view : is a ImageView (cell) in a rowView in a TableLayout
     * @return move coordinates
     */
    private Move getMoveFromCase(ImageView view){
        Move move = null;

        for (int j=0; j<tableLayout.getChildCount();j++){
            TableRow rowView = (TableRow) tableLayout.getChildAt(j);
            for(int i=0; i<rowView.getChildCount();i++){
                if(view.equals(rowView.getChildAt(i))){//verifier la comparaison
                    move = new Move(j, i);
                }
            }
        }

        return move;
    }

    /***
     * Get the ImageView (cell) from the given coordinates
     * @param row
     * @param col
     * @return
     */
    private ImageView getCaseView(int row, int col){
        ImageView imgView;

        //Get case view from coord.
        TableRow tableRow = (TableRow) tableLayout.getChildAt(row);
        imgView = (ImageView) tableRow.getChildAt(col);

        //if (view instanceof TableRow) //Peut etre mieux

        return imgView;
    }

    private void updateGraphic(){
        updateGraphicalBoard();
        updateScore();
    }

    private void updateGraphicalBoard(){
        ImageView imgView;

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

    private void updateScore(){
        int scoreP1 = gameboard.getCoinCount(p1.getID());
        int scoreP2 = gameboard.getCoinCount(p2.getID());

        String text = "<b>"+p1.getName() + "</b> " + scoreP1 + scoreText + scoreP2 + " <b>" + p2.getName() + "</b>";
        Spanned htmlText = Html.fromHtml(text);

        textScore.setText(htmlText);
    }
}
