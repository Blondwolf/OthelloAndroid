package hearc.othello.model ;

// ****************************************************
//  Simplification des coups (i,j)
// ****************************************************

public class Move
{
	//Attributs de la classe
	private int x;
	private int y ;
	
	//Constructeurs de la Classe
	public Move(){};

	public Move (int x, int y){
		setMove(x, y);
	}

	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public void setMove(int x, int y){
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
