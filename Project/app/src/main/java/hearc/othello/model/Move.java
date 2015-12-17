package hearc.othello.model ;

// ****************************************************
//  Simplification des coups (i,j)
// ****************************************************

public class Move
{
	// Attributs de la classe ;
	public int i,j ;

	// Constructeurs de la Classe ;
	public Move () {}
	public Move (int i, int j)
	{ this.i = i ; this.j = j ; }

	public int getI(){
		return i;
	}

	public int getJ(){
		return j;
	}
}