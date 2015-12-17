/*
 * Decompiled with CFR 0_102.
 * 
 * Could not load the following classes:
 *  Othello.Move
 *  Participants.ChabbouFernandes.GameBoard
 *  Participants.ChabbouFernandes.Node
 */
package hearc.othello.model.AI;

import hearc.othello.model.GameBoard;
import hearc.othello.model.Move;

public class IA {
    private static int MAX = Integer.MAX_VALUE;
    private static int[][] matrix;

    static {
        matrix = new int[][]{{250, -50, 50, 20, 20, 50, -50, 250},
                {-50, -100, -10, -10, -10, -10, -100, -50},
                {50, -10, 1, 1, 1, 1, -10, 50},
                {20, -10, 1, 1, 1, 1, -10, 20},
                {20, -10, 1, 1, 1, 1, -10, 20},
                {50, -10, 1, 1, 1, 1, -10, 50},
                {-50, -100, -10, -10, -10, -10, -100, -50},
                {250, -50, 5, 2, 2, 5, -50, 250}};
    }

    /*	Entree du programme d'IA */
    public static Move getBestMove(GameBoard gameBoard, int depth, int playerID) {
        Node nodeRoot = new Node(null);
        Node nodeBestChild = null;

        //Construction de l'arbre de possibilit�s
        nodeBestChild = searchBestNode(gameBoard, playerID, nodeRoot, depth, playerID, 1);

        return nodeBestChild.getMove();
    }

    private static Node searchBestNode(GameBoard gameBoard, int playerID, Node node, int actualDepth, int actualPlayer, int minMax) {
        Node nodeBestChild = null;
        node.setEvaluation(-minMax*MAX);

        //Fonction terminale, on evalue les branches
        if (actualDepth == 0 || gameBoard.getPossibleMoves(actualPlayer).isEmpty()) {
            int value = evaluate(gameBoard, playerID);
            node.setEvaluation(value);
            return node;//Inutile car c'est la FEUILLE mais on doit renvoyer une valeur
        }

        //On parcourt l'eventail des coups possibles
        for (Move move : gameBoard.getPossibleMoves(actualPlayer)) {
            //Simulation du coup suivant
            GameBoard gameBoardNextMove = gameBoard.clone();
            gameBoardNextMove.addCoin(move, actualPlayer);

            Node nodeChildren = new Node(move);
            node.addChildNode(nodeChildren);

            //On cr�e les fils
            searchBestNode(gameBoardNextMove, playerID, nodeChildren, actualDepth-1, 1-actualPlayer, -minMax);

            //On cherche le meilleur fils
            if (nodeChildren.getEvaluation() * minMax > node.getEvaluation() * minMax){
//            	System.out.println(nodeChildren.getEvaluation() * minMax+" > "+node.getEvaluation() * minMax);
                node.setEvaluation(nodeChildren.getEvaluation());
                nodeBestChild = nodeChildren;

                //Coupure Alpha-beta
                if (nodeBestChild.getEvaluation() * minMax> node.getEvaluation() * minMax){
                    break;
                }
            }
        }

//    	System.out.println("Actual best level "+actualDepth+" is :"+nodeBestChild.getEvaluation()+" for :"+minMax);
        return nodeBestChild;//On renvoit le meilleur coup
    }

    public static int evaluate(GameBoard game, int playerID) {
        int nbrPieces = 0; 	//nombre de pi�ces
        int nbrMove = 0; 	//nombre de mouvement possible
        int value = 0; 		//valeur des pi�ces selon leur value

        nbrPieces = game.getCoinCount(playerID);
        nbrMove = game.getPossibleMoves(playerID).size();
        value = calculStrategicalBoardScore(game, playerID, matrix);

        //Si c'est le d�but de la partie, on essaye de ne pas prendre trop de pi�ces afin de privil�gier la mobilit�
        if (game.getCoinCount(playerID) + game.getCoinCount(1-playerID) < 32)
        {
            return 3 * nbrMove + value;
        }
        else//Si plus de la moiti� du plateau est rempli, on change de strat�gie pour le nombre de pi�ces
        {
            return 3 * nbrPieces + 2 * nbrMove + 5 * value;
        }
    }

    private static int calculStrategicalBoardScore(GameBoard game, int playerID, int[][] grilleEval) {
        int score = 0;

        for (int i = 0; i < GameBoard.BOARD_SIZE; i++) {
            for (int j = 0; j < GameBoard.BOARD_SIZE; j++) {
                if (playerID == game.getPlayerIDAtPos(i, j)) {
                    score += grilleEval[i][j];
                }
            }
        }

        return score;
    }
}