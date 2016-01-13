package hearc.othello.model;

import java.io.Serializable;

import hearc.othello.model.AI.PlayerAI;
import hearc.othello.tools.Tools;
import hearc.othello.view.activity.GameActivity;

/**
 * Created by Kevin on 08.01.2016.
 */
public class Game implements Serializable{
    public static final int PLAYER1 = 0;
    public static final int PLAYER2 = 1;

    int mode;

    GameBoard gameboard;
    Player p1, p2;
    Player actualPlayer, enemyPlayer;

    /*      Constructors    */

    //New Game
    public Game(int mode, Player p1, Player p2){
        gameboard = new GameBoard();
        initGame(mode, p1, p2);
    }

    //Load Game
    public Game(int mode, GameBoard gameboard, Player p1, Player p2){
        this.gameboard = gameboard;
        initGame(mode, p1, p2);
    }

    private void initGame(int mode, Player p1, Player p2){
        this.mode = mode;
        actualPlayer = this.p1 = p1;
        enemyPlayer = this.p2 = p2;
        updatePlayersScore();
    }

    /*      Public    */

    public GameBoard getGameBoard(){
        return gameboard;
    }

    public Player getPlayer(int ID){
        return (p1.getID() == ID) ? p1 : p2;
    }

    public void playMove(GameActivity gameActivity, Move nextMove){
        //Check if move is possible
        if(gameboard.isMovePossible(nextMove.getLine(), nextMove.getColumn(), actualPlayer.getID())) {
            //AndroidTools.Toast(gameActivity.getApplicationContext(), actualPlayer.toString());

            //Play move
            gameboard.addCoin(nextMove, actualPlayer.getID());

            //Check if IA for automatical move
            if(enemyPlayer instanceof PlayerAI){//Check mode instead
                //TODO : Little timer to simulate IA thinking

                //In case IA is low, show the movement first
                update(gameActivity);

                //Automatical play from IA
                Move nextEnemyMove = enemyPlayer.nextPlay(gameboard);
                gameboard.addCoin(nextEnemyMove, enemyPlayer.getID());
            }
            //Else is HumanPlayer
            else{
                //Changing player turn
                Player tempPlayer = actualPlayer;
                actualPlayer = enemyPlayer;
                enemyPlayer = tempPlayer;

                //TODO : check if nextPlayer have possibleMov
            }
        }
        else{
            Tools.Toast(gameActivity.getApplicationContext(), "Move : " + nextMove.toString() + " is not possible !");
        }

        update(gameActivity);
    }

    /*      Private    */
    private void update(GameActivity gameActivity){
        updatePlayersScore();
        gameActivity.updateGraphic();
    }

    private void updatePlayersScore(){
        p1.setScore(gameboard.getCoinCount(p1.getID()));
        p2.setScore(gameboard.getCoinCount(p2.getID()));
    }
}
