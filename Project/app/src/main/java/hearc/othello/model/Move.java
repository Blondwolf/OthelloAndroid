package hearc.othello.model ;

// ****************************************************
//  Simplification des coups (i,j)
// ****************************************************

public class Move
{
	// Attributs de la classe ;
	private int line;
	private int column;

	// Constructeurs de la Classe ;
	public Move () {}
	public Move (int line, int column)
	{ this.line = line ; this.column = column ; }

	public int getLine(){
		return line;
	}

	public int getColumn(){
		return column;
	}

	@Override
	public String toString() {
		return getLine()+"/"+getColumn();
	}
}