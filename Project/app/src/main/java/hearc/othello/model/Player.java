package  hearc.othello.model;

import java.io.Serializable;

/**
 * Contains the player indications
 *
 * @author Ellenberger Patrick and Moll Adrian edited by Vulliemin Kevin and Ajjali Wassim
 */
public abstract class Player implements Serializable
{
	protected int id;
	protected String namePlayer;
	protected int score;

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

	public int getScore(){
		return score;
	}

	public void setScore(int newScore){
		score = newScore;
	}

	@Override
	public String toString() {
		return "("+id+","+namePlayer+")";
	}

	public abstract Move nextPlay(GameBoard gameBoard);
}