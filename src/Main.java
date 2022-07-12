import java.util.*;

public class Main {

    public static void main(String[] args){
        System.out.println("Minesweeper in Command Line.");
        //create board
        Board gameBoard = new Board(5, 5, 5);

        //initiate the grid by placing the bombs.
        gameBoard.setBombs();
        //set up the hidden board to track how many bombs are near each grid.
        gameBoard.bombCheck();
        //check the hidden board has been constructed correctly.
        //gameBoard.printHiddenBoard();

        //game loop while game is in play keep asking player for their new move until game is over.
        while(gameBoard.gameInPlay) {
            //current players board
            gameBoard.printPlayerBoard();
            //ask player to enter move
            gameBoard.getNextMove();
            //check for player winning after each move played.
            gameBoard.checkForWin();
        }
    }
}
