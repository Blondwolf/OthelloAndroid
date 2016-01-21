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

    public static final int DEFAULT = 1;
    public static final int NO_MOVE = 2;
    public static final int IMPOSSIBLE_MOVE = 3;
    public static final int END_GAME = 4;

    int state;
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

    private void initGame(int mode, Player p1, Player p2){
        this.state = DEFAULT;
        this.mode = mode;

        actualPlayer = this.p1 = p1;
        enemyPlayer = this.p2 = p2;

        if(actualPlayer instanceof PlayerAI){
            Move nextEnemyMove = enemyPlayer.nextPlay(gameboard);
            playMove(nextEnemyMove);
        }

        updatePlayersScore();
    }

    /*      Public    */

    public GameBoard getGameBoard(){
        return gameboard;
    }

    public Player getPlayer(int ID){
        return (p1.getID() == ID) ? p1 : p2;
    }

    public Player getActualPlayer(){
        return actualPlayer;
    }

    public Player getEnemyPlayer(){
        return enemyPlayer;
    }

    public boolean isActualPlayer(Player player){
        return actualPlayer == player;
    }

    public boolean isFinished(){
        return state == END_GAME;
    }

    public void playMove(Move nextMove){
        //Possible move algo
        if(gameboard.isMovePossible(nextMove.getLine(), nextMove.getColumn(), actualPlayer.getID())) {
             //Play move
            gameboard.addCoin(nextMove, actualPlayer.getID());

            //Check if move is possible
            //No moves for enemy
            if(gameboard.getPossibleMoves(enemyPlayer.getID()).isEmpty()) {
                if (gameboard.getPossibleMoves(actualPlayer.getID()).isEmpty())
                    state = END_GAME;
                else
                    state = NO_MOVE;
            }
            //enemy moves are possible
            else{
                //Changing player turn
                Player tempPlayer = actualPlayer;
                actualPlayer = enemyPlayer;
                enemyPlayer = tempPlayer;

                //Check if IA for automatical move
                if(actualPlayer instanceof PlayerAI){//Check mode instead
                    //TODO : In case IA is low, show the movement first
                    update();

                    //Automatical play from IA
                    Move nextEnemyMove = actualPlayer.nextPlay(gameboard);
                    playMove(nextEnemyMove);
                }
            }
        }
        else{
            state = IMPOSSIBLE_MOVE;
        }

        update();
    }

    /*      Private    */
    private void update(){
        updatePlayersScore();
    }

    private void updatePlayersScore(){
        p1.setScore(gameboard.getCoinCount(p1.getID()));
        p2.setScore(gameboard.getCoinCount(p2.getID()));
    }

    public int getState() {
        return state;
    }

    public int getMode() {
        return mode;
    }
}
