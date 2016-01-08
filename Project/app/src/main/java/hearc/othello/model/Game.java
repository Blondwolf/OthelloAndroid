package hearc.othello.model;

import android.content.Context;

import hearc.othello.model.AI.PlayerAI;
import hearc.othello.tools.AndroidTools;
import hearc.othello.view.activity.GameActivity;

/**
 * Created by Kevin on 08.01.2016.
 */
public class Game {
    int mode;

    GameBoard gameboard;
    Player p1, p2;
    Player actualPlayer, enemyPlayer;

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
    }

    public void playMove(GameActivity gameActivity, Move nextMove){
        //Check if move is possible
        if(gameboard.isMovePossible(nextMove.getLine(), nextMove.getColumn(), actualPlayer.getID())) {
            AndroidTools.Toast(gameActivity.getApplicationContext(), actualPlayer.toString());

            //Play move
            gameboard.addCoin(nextMove, actualPlayer.getID());

            //Check if IA for automatical move
            if(enemyPlayer instanceof PlayerAI){//Check mode instead
                gameActivity.updateGraphic();
                //TODO : Little timer to simulate IA thinking

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
            }

            //Update graphic and score
            gameActivity.updateGraphic();
        }
        else{
            AndroidTools.Toast(gameActivity.getApplicationContext(), "Move : "+nextMove.toString()+" is not possible !");
        }
    }
}
