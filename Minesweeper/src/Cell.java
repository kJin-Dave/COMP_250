public class Cell {
    private boolean covered;
    private boolean hasMine;
    private int adjaMines; //to be used in the Board class
    public static boolean gameEnded;

    private double threshold = 0.10;
    private double random = Math.random();


    //Constructor -> should be able to generate random mines -> let's try with only mine as input
        //Difficulty (easy, medium, hard) -> (x1 ,x2 ,x3) amount of mines.
    public Cell() {
        this.setMine(); //Each cell has chance to be a mine
        this.covered = true;

    }
    //Border cell

    private boolean isBorder;
    private int borderNumber;

    public Cell(int borderNumber) {
        this.isBorder = true;
        this.borderNumber = borderNumber;
        this.hasMine = false;
        this.adjaMines = -1;
        this.covered = false;

    }

    //Other methods
    public void setMine() {

        if (this.random < threshold *Minesweeper.getDifficulty()) {
            this.hasMine = true;
        }
    }

    public void unCover() { //unCover will be called recursively

        this.covered = false;
    }

    //toString

    public String toString() {
        //empty spaces before & after for formatting (notably with border cells)


        //If the game ends, show the hit & all the mines
        if (this.hasMine && gameEnded && !covered) return " X "; //hasMine -> not a border or a normal cell. game
        else if (this.hasMine && this.covered && gameEnded) return " M ";
        else if (this.isBorder) return "|"+ this.borderNumber + "|";
        else if (this.covered == true) return  " □ ";
        else return " " + adjaMines + " ";
    }
    //Setters & Getters

    public boolean hasMine() {
        return this.hasMine;
    }

    public boolean isBorder() {
        return this.isBorder;
    }

    public void setAdjaMines(int adjaMines){
        this.adjaMines = adjaMines;
    }

    public int getAdjaMines() {
        return this.adjaMines;
    }

    public boolean isUncovered() {
        return !covered;
    }
}
