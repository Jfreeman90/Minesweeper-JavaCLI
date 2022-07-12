import java.util.Random;
import java.util.Scanner;

public class Board {
    //default size
    int size;
    //board shown is what the player sees and interacts with in command line.
    int[][] boardShown;
    //hidden board hold the created game information.
    int[][] boardHidden;
    //number of bombs the user is avoiding
    int bombs;
    //number of flags a user can use.
    int flags;
    //checks if the game is in play or has ended.
    boolean gameInPlay=true;
    //how many moves the player has made.
    int playerMoveCount=0;

    //create the board object by choosing the number of bombs you would like
    public Board(int size, int bombs, int flags){
        this.size=size;
        this.flags=flags;
        this.bombs=bombs;
        this.boardShown =new int[this.size][this.size];
        //hidden board hold the created game information
        this.boardHidden = new int[this.size][this.size];
    }

    //put the bombs into the boards.
    public void setBombs(){
        int k =0;
        while (k < this.bombs){
            Random random = new Random();
            int g = random.nextInt(this.size);
            int j = random.nextInt(this.size);
            //System.out.println("co-ordinate(index starting at 0,0 in top left) for bomb: (" +j +" , " + g+" )");

            //bomb already in this position
            if (this.boardHidden[g][j]==10){
                assert true;
            } else {    //place bomb in this position by numbering the grid '10' to indicate a bomb.
                this.boardShown[g][j]=10;
                this.boardHidden[g][j]=10;
                k++;
            }
        }
    }

    //get number of bombs around each square and update the hidden board to hold information
    //0 indicates no bombs near and 10 indicates a bomb in that square.
    public void bombCheck(){
        //check inside squares.
        for (int i=0; i< this.size; i++){
            for(int j=0; j<this.size; j++) {
                //System.out.println("i " + i + "j" + j);

                //if he grid is already a bomb skip it - prevents having to check to count is in the loop below.
                if (this.boardHidden[i][j]==10){
                    assert true;
                } else {

                    int counter =0;
                    //for each square check all 8 squares by ittirating - offset the index bt 1 in all directions and check the new index is
                    //still inside the grid dimensions.
                    for (int iOffset = -1; iOffset <= 1; iOffset++) {
                        for (int jOffset = -1; jOffset <= 1; jOffset++) {
                            int x = i + iOffset;
                            int y = j + jOffset;
                            //System.out.println("x-offset" + iOffset + "y-offset" +jOffset);
                            //System.out.println("i position to check " + x + " j position to check " + y);
                            //only allow to check if x and y lie inside the bounds of the matrix.
                            if ((x >= 0 && x < this.size) && (y >= 0 && y < this.size)) {
                                //add to grid counter if one of the neighbours is a bomb.
                                if(this.boardHidden[x][y]==10){
                                    counter++;
                                }
                            }

                        }
                    }
                    this.boardHidden[i][j]=counter;

                }
            }
        }
    }

    //function that will flood the board if a 0 is clicked and reveal all neighbouring 0's
    //change board in play to 1000 to denote the board area has been flooded to reveal 0's.
    private void floodBoard(int i, int j){
        //System.out.println("i "+i+ " j " + j);
        //for each square check all 8 squares by iterating - offset the index bt 1 in all directions and check the new index is
        //still inside the grid dimensions.
        for (int iOffset = -1; iOffset <= 1; iOffset++) {
            for (int jOffset = -1; jOffset <= 1; jOffset++) {
                int x = i + iOffset;
                int y = j + jOffset;
                //System.out.println("x-offset" + iOffset + "y-offset" +jOffset);
                //System.out.println("i position to check " + x + " j position to check " + y);
                //only allow to check if x and y lie inside the bounds of the matrix.
                if ((x >= 0 && x < this.size) && (y >= 0 && y < this.size)) {
                    if(this.boardHidden[x][y]!=10 && this.boardShown[x][y]!=100 && this.boardShown[x][y]!=-1){
                        this.boardShown[x][y]=100;
                        //System.out.println("x "+x+ " y " + y);
                        //if the board is a 0 continue to flood else stop
                        if (this.boardHidden[x][y]==0) {
                            floodBoard(x, y);
                        }
                    }
                }

            }
        }
    }

    //print players board
    public void printPlayerBoard(){
        //format the top row of grid selections
        for (int i=0; i< this.size; i++){
            System.out.print("___" + "_"+i+"_");
        }
        System.out.println("");
        //format entire grid
        for (int i=0; i< this.size; i++){
            System.out.print(i+"|");
            for(int j=0; j<this.size; j++){
                //System.out.print(" i "+ i +" j " +j);
                //if board is 100 it has been clicked, check for any empty space.
                if (this.boardShown[i][j]==100 && this.boardHidden[i][j]==0){
                    System.out.print(" " + "   " + "  ");
               } else if (this.boardShown[i][j]==100){
                    System.out.print(" " + " "+this.boardHidden[i][j]+" " + "  ");
               } else if (this.boardShown[i][j]==-1) {   //flag.
                    System.out.print(" " + " "+"F"+" " + "  ");
                }
                else {
                    System.out.print(" " + " # " + "  ");
                }

            }
            System.out.println("");
        }
    }

    //ask for move
    public void getNextMove(){
        //initialise scanner
        Scanner sc= new Scanner(System.in);
        //get the place user would like to select the grid.
        // -----------ROW----------
        System.out.println("Select a grid to reveal (row and columns start at 0) ");
        boolean rowCheck = false;
        int i = 0;
        do{
            System.out.println("Enter Row Number: ");
            if(sc.hasNextInt()){
                i = sc.nextInt();
                rowCheck = true;
            }else{
                sc.nextLine();
                System.out.println("Enter a valid Integer value");
            }
        }while(!rowCheck);
        //----------COL-------------
        boolean colCheck = false;
        int j = 0;
        do{
            System.out.println("Enter Column Number: ");
            if(sc.hasNextInt()){
                j = sc.nextInt();
                colCheck = true;
            }else{
                sc.nextLine();
                System.out.println("Enter a valid Integer value");
            }
        }while(!colCheck);
        System.out.println("Co-ordinates selected (row,col):  (" +i+","+j+")");

        //ask user if they would like to flag or not
        System.out.print("Would you like to place a flag at this spot? Y/N - Current flags remaining: " + this.flags + " ");
        String flagCheck= sc.next().toUpperCase();
        System.out.println(flagCheck.toUpperCase());

        if (i>=0 && i <= this.size-1 && j>=0 && j<=this.size) {
            //flag check
            if (flagCheck.equals("Y")){
                //user wants to plant flag in this spot, only allow if the tile hasn't been revealed yet.
                //System.out.println("Y");
                if (this.boardShown[i][j]!=100) {
                    this.boardShown[i][j] = -1;
                    this.playerMoveCount++;
                    this.flags--;
                } else {
                    System.out.println("You cant place on an already revealed square.");
                }
            } else if ((flagCheck.equals("N"))) {
                //System.out.println("N");
                //check if the user has already input this value
                if (this.boardShown[i][j]==100){
                    System.out.println("Already attempted this grid space try again");
                } else{
                    //valid move update  visible board to 100 to denote clicked.
                    //System.out.println(boardHidden[i][j]);
                    this.playerMoveCount++;
                    //if tile clicked is 0 then check all surrounding and flood to reveal all areas.
                    if (this.boardHidden[i][j]==0){
                        this.boardShown[i][j]=100;
                        floodBoard(i, j);
                    }else if (this.boardHidden[i][j]==10){
                        this.gameInPlay=false;
                        System.out.println("You found a bomb!");
                        System.out.println("------------GAME OVER -----------");
                        System.out.println("--------Game board below---------");
                        printHiddenBoard();
                    } else {// update square clicked and revealed
                        this.boardShown[i][j]=100;}
                }
            } else {
                System.out.println("Invalid input - PLease type Y or N only.");
            }
        }else {
            System.out.println("INVALID MOVE (out of bounds) TRY AGAIN");
        }
    }

    //print hidden board
    public void printHiddenBoard(){
        for (int i=0; i< this.size; i++){
            for(int j=0; j<this.size; j++){
                //System.out.print(" i "+ i +" j " +j);
                if (this.boardHidden[i][j]==10){
                    System.out.print("  " + "B" + "   ");
                } else {
                    System.out.print("  " + this.boardHidden[i][j] + "   ");
                }
            }
            System.out.println("");
        }
    }

    //check for revealed squares and returns how many have been revealed.
    private int revealedCount(){
        int counter=0;
        for (int i=0; i< this.size; i++){
            for(int j=0; j<this.size; j++) {
                if (this.boardShown[i][j]==100 || this.boardShown[i][j]==-1){
                    counter++;
                }
            }
        }
        return counter;
    }

    //check for game winning grid state.
    public void checkForWin(){
        int allValidSquares= this.size * this.size - this.bombs;
        //System.out.println("Player move count: " + this.playerMoveCount);
        //System.out.println("Tiles revealed: " + revealedCount());
        //System.out.println("All possible squares that can be clicked: " + allValidSquares);
        if (revealedCount()==allValidSquares){
            System.out.println("YOU HAVE FOUND ALL OF THE BOMBS");
            System.out.println("---CONGRATULATIONS YOU WON!!---");
            System.out.println("You took " + playerMoveCount +" attempts.");
            System.out.println("-------------------------------");
            printPlayerBoard();
            this.gameInPlay=false;

        }
    }
}
