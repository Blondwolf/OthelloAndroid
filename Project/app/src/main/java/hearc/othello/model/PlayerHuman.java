package hearc.othello.model;

/**
 * Created by Kevin on 12.11.2015.
 */
public class PlayerHuman extends Player{


    public PlayerHuman(int playerID, GameBoard gameBoard, String namePlayer) {
        super(playerID, gameBoard, namePlayer);
    }

    @Override
    public Move nextPlay() {
        return null;
    }
}
