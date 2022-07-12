import java.util.Scanner;

public class Difficulty {
    //Attributes
    String difficulty;
    int size;
    int bombs;
    int flags;

    //Constructor
    public Difficulty(){

    }

    public void setDifficulty(String difficulty){
        this.difficulty=difficulty;
    }

    public String getDifficulty() {
        return this.difficulty;
    }

    public void setSize(){
        if (this.difficulty.equals("EASY")){
            this.size =5;
        } else if (this.difficulty.equals("MEDIUM")){
            this.size=10;
        } else {
            this.size=20;
        }
    }

    public int getSize() {
        return this.size;
    }

    public void setBombs(){
        if (this.difficulty.equals("EASY")){
            this.bombs=5;
        } else if (this.difficulty.equals("MEDIUM")){
            this.bombs=10;
        } else {
            this.bombs=25;
        }
    }

    public int getBombs() {
        return this.bombs;
    }

    public void setFlags(){
        if (this.difficulty.equals("EASY")){
            this.flags=5;
        } else if (this.difficulty.equals("MEDIUM")){
            this.flags=10;
        } else {
            this.flags=20;
        }
    }

    public int getFlags() {
        return this.flags;
    }


}
