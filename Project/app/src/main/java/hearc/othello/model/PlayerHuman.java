package hearc.othello.model;

/**
 * Created by Kevin on 12.11.2015.
 */
public class PlayerHuman extends Player{


    public PlayerHuman(int playerID, String namePlayer) {
        super(playerID, namePlayer);
    }

    @Override
    public Move nextPlay(GameBoard gameBoard) {
        return null;
    }
}
