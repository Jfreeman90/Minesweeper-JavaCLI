import java.util.*;

public class Main {

    public static void main(String[] args){
        System.out.println("Minesweeper");
        // -----------DIFFICULTY----------
        //initialise scanner
        Scanner sc= new Scanner(System.in);
        boolean diffCheck = false;
        int diff = 0;
        System.out.println("Choose your difficulty --- 1) EASY, 2) MEDIUM, 3) HARD----Enter 1,2 or 3.");
        do{
            if(sc.hasNextInt()){
                diff = sc.nextInt();
                if (diff>=1 && diff <=3) {
                    diffCheck = true;
                } else{
                    System.out.println("Enter a valid value: 1, 2 or 3.");
                }
            }else{
                sc.nextLine();
                System.out.println("Enter a valid value: 1, 2 or 3.");
            }
        }while(!diffCheck);

        //difficulty
        Difficulty difficulty = new Difficulty();

        if (diff==1){
            System.out.println("Difficulty Chosen: EASY");
            difficulty.setDifficulty("EASY");
        } else if (diff==2){
            System.out.println("Difficulty Chosen: MEDIUM");
            difficulty.setDifficulty("MEDIUM");
        } else {
            System.out.println("Difficulty Chosen: HARD");
            difficulty.setDifficulty("HARD");
        }

        //set the difficulty values based on the difficulty selected
        difficulty.setSize();
        difficulty.setBombs();
        difficulty.setFlags();
        //create board based off the difficulty chosen by the user.
        Board gameBoard = new Board(difficulty.getSize(), difficulty.getBombs(), difficulty.getFlags());

        //initiate the grid by placing the bombs.
        gameBoard.setBombs();
        //set up the hidden board to track how many bombs are near each grid.
        gameBoard.bombCheck();
        //check the hidden board has been constructed correctly.
        //gameBoard.printHiddenBoard();

        //start timer
        gameBoard.startTimer();

        //game loop while game is in play keep asking player for their new move until game is over.
        while(gameBoard.getGameInPlay()) {
            //current players board
            gameBoard.printPlayerBoard();
            //ask player to enter move
            gameBoard.getNextMove();
            //check for player winning after each move played.
            gameBoard.checkForWin();
        }
    }
}
