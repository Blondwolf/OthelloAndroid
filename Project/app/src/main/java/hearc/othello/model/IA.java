/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  Othello.Move
 *  Participants.ChabbouFernandes.GameBoard
 *  Participants.ChabbouFernandes.Node
 */
package hearc.othello.model;

public class IA {
	private static int MAX = Integer.MAX_VALUE;
    private static int[][] matrix;
    
    static {
        matrix = new int[][]{{50, -1, 5, 2, 2, 5, -1, 50},
        					{-1, -10, 1, 1, 1, 1, -10, -1},
        					{5, 1, 1, 1, 1, 1, 1, 5},
        					{2, 1, 1, 1, 1, 1, 1, 2},
        					{2, 1, 1, 1, 1, 1, 1, 2},
        					{5, 1, 1, 1, 1, 1, 1, 5},
        					{-1, -10, 1, 1, 1, 1, -10, -1},
        					{50, -1, 5, 2, 2, 5, -1, 50}};
    }
    
    /*	Entr�e du programme d'IA */
    public static Move getBestMove(GameBoard gameBoard, int depth, int playerID) {
        //Recherche du meilleur noeud avec algo Alpha-Beta
        Node nodeBest = IA.alphaBeta(gameBoard, depth, 1, new Node(null), MAX, playerID);
        return nodeBest.getMove();
    }

    //Fonction r�cursive de construction d'arbre selon Alpha-Beta
    public static Node alphaBeta(GameBoard gameBoard, int actualDepth, int minMax, Node nodeParent, int bestValueParent, int playerID) {
    	//Cr�ation du noeud fils
        Node nodeBest = new Node(null);
        
        //Fonction terminale, on �value les branches
        if (actualDepth == 0 || gameBoard.getPossibleMoves(playerID).isEmpty()) {
            int evaluation = IA.evaluate(gameBoard, playerID);
            nodeParent.setEvaluation(evaluation);
            return nodeParent;
        }
        
        //D�finition du min et max
        int bestValue = minMax * -MAX;
        
        //On cr�e l'arbre de coups possible
        for (Move move : gameBoard.getPossibleMoves(playerID)) {
        	//Simulation du coup suivant
            GameBoard gameBoardNextMove = gameBoard.clone();
            gameBoardNextMove.addCoin(move, playerID);
            
            Node nodeChildren = new Node(move);
            nodeParent.addChildNode(nodeChildren);
            
            Node nodeSubChildren = IA.alphaBeta(gameBoardNextMove, actualDepth - 1, -minMax, nodeChildren, bestValue, 1 - playerID);
            if (nodeSubChildren.getEvaluation() * minMax > bestValue * minMax){
	            bestValue = nodeSubChildren.getEvaluation();
	            nodeBest = nodeChildren;
	            nodeBest.setEvaluation(nodeSubChildren.getEvaluation());
	            
	            if (bestValue > bestValueParent){
	            	break;
	            }
            }
        }
        return nodeBest;
    }

    public static int evaluate(GameBoard gameBoard, int playerID) {
        int deltaCoin = gameBoard.getCoinCount(playerID)-gameBoard.getCoinCount(1-playerID);
    	return deltaCoin;
    }
}
