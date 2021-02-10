import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {


        Scanner scanner = new Scanner(System.in);

    //Creating the board
        //Difficulty
        System.out.println("Set your difficulty (Easy, Medium, Hard):");
        String difficulty = scanner.nextLine();
        switch (difficulty) {
            case "Easy":
                Minesweeper.setDifficulty(1);
                break;
            case "Medium":
                Minesweeper.setDifficulty(2);
                break;
            case "Hard":
                Minesweeper.setDifficulty(3);
                break;
            default :
                break;
        }

        //Size of the board

        System.out.println("Enter the size of board (rows & columns)");
        System.out.print("Rows : ");
        int rows = scanner.nextInt();
        System.out.print("Columns : " );
        int cols = scanner.nextInt();

        Board board = new Board(rows,cols);
    //Let's run the game!

       while (!Cell.gameEnded) {
           board.displayBoard();
           if(Cell.gameEnded) break;

           //Sweeping

            System.out.println("\n Enter the row & column of the cell to uncover, or enter negative row & column to validate :");
            System.out.print("Row : " );
            int x = scanner.nextInt();
            System.out.print("Column :" );
            int y = scanner.nextInt();
                //Illegal argument
                if (x == 0 || x == rows+1) {
                    System.out.println("Can't sweep the border!");
                    continue;
                } //Borders
                if (y == 0 || y == cols+1) {
                    System.out.println("Can't sweep the border!");
                    continue;
                }
                if (x > rows +2) {
                    System.out.println("Stay on the board!");
                    continue;
                } //Outside borders
                if (y > cols +2) {
                   System.out.println("Stay on the board!");
                   continue;
                }

                //Check game win
                if (x < 0 || y < 0) {
                    boolean win = board.checkGameWin();
                    if (win) {
                        System.out.println("You won!");
                        break;
                    }
                    else System.out.println("Nice try buddy");
                }


            if ( x >= 0 || y >= 0) board.sweep(x,y);
            //Thread.sleep(100);

           //Game win conditions -> if each cell is uncovered or is a mine.



       }



    }
}
