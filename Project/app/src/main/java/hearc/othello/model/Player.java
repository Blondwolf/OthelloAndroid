package  hearc.othello.model;

/**
 * Contains the player indications
 *
 * @author Ellenberger Patrick and Moll Adrian edited by Vulliemin Kevin and Ajjali Wassim
 */
public abstract class Player
{
	protected int id;
	protected GameBoard gameBoard;
	protected String namePlayer;

	public Player(int id, String namePlayer)
	{
		this.id = id;
		this.namePlayer = namePlayer;
	}

	public int getID(){
		return id;
	}

	public String getName() {
		return namePlayer;
	}

	@Override
	public String toString() {
		return "("+id+","+namePlayer+")";
	}

	public abstract Move nextPlay(GameBoard gameBoard);
}