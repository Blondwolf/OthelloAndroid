package hearc.othello.model.AI;


import hearc.othello.model.GameBoard;
import hearc.othello.model.Player;
import hearc.othello.model.Move;

/**
 * Managages the player
 * @author Vulliemin Kevin, inspired by Ellenberger Patrick and Moll Adrian
 *
 */
public class PlayerAI extends Player {

	private static final String NAME_IA = "IA";
	private int depth;

	public PlayerAI(int id, GameBoard gameBoard, int level) {
		super(id, gameBoard, NAME_IA);

		this.id = id;
		this.depth = level;

		this.gameBoard = new GameBoard();
	}

	/**
	 * Method called when the IA has to play
	 */
	public Move nextPlay(){
		//Get the best move (null if no move possible)
		Move bestMove = IA.getBestMove(gameBoard.clone(), depth, id);
		return bestMove;
	}

}