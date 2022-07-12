import java.util.*;

public class Main {

    public static void main(String[] args){
        System.out.println("Minesweeper");
        // -----------DIFFICULTY----------
        //initialise scanner
        Scanner sc= new Scanner(System.in);
        boolean diffCheck = false;
        int difficulty = 0;
        System.out.println("Choose your difficulty --- 1) EASY, 2)MEDIUM, 3) HARD----Enter 1,2 or 3.");
        do{
            if(sc.hasNextInt()){
                difficulty = sc.nextInt();
                if (difficulty>=1 && difficulty <=3) {
                    diffCheck = true;
                } else{
                    System.out.println("Enter a valid value: 1, 2 or 3.");
                }
            }else{
                sc.nextLine();
                System.out.println("Enter a valid value: 1, 2 or 3.");
            }
        }while(!diffCheck);

        if (difficulty==1){
            System.out.println("Difficulty Chosen: EASY");
        } else if (difficulty==2){
            System.out.println("Difficulty Chosen: MEDIUM");
        } else {
            System.out.println("Difficulty Chosen: HARD");
        }



        System.out.println("Difficulty Chosen: " + difficulty);

        //create board based off the difficulty chosen by the user.
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
