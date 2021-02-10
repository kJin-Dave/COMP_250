//Implementation containing the display of the cell :)

public class Board {
    private int rows;
    private int columns;
    private Cell[][] board;

    //Constructor -> creates board of rows * columns, and creates random cells with mines.
        //the board has an 2 extra column & rows for the border (border cells)
        //4x4 game board means 6x6 board;


    public Board(int rows, int columns) {
        //Rows & columns show the amount of rows with playable cells
        this.rows = rows;
        this.columns = columns;

        Cell[][] newBoard = new Cell[rows+2][columns+2];

        //Initialize borders
            //first row & last row
            for (int i = 0; i< columns + 2; i++) {
                newBoard[0][i] = new Cell(i);
                newBoard[rows+1][i] = new Cell(i);
            }
            //first & last column
            for (int j = 0; j<rows +2; j++) {
                newBoard[j][0] = new Cell(j);
                newBoard[j][columns+1] = new Cell(j);
            }
            //For now, easier to manually override top left corner (reinitialized to 0 by columns)
            newBoard[0][columns+1] = new Cell(columns+1);


        for (int i = 1; i < rows+1; i++) {
            for (int j = 1; j< columns+1; j++) {
                newBoard[i][j] = new Cell();

            }
        }
        this.board = newBoard;
    }

    //Other methods

        //Count adjacent mines

    public void displayBoard() {

        // If the game has ended, then display the board
        if (Cell.gameEnded) {
            System.out.println("The game ended!");
        }
        //Display all cells from a row, then skip \n
        else {
            System.out.println("The current board :");
        }
        for (int i = 0; i< rows+2; i++) {
            System.out.println("");
            for (int j = 0; j < columns+2; j++) {
                System.out.print(board[i][j] +"\t");
            }
        }
    }

    public void sweep(int row, int col) {
        Cell current = board[row][col];
        if (current.isBorder()) return;
        if (current.isUncovered()) return;

        //Counting adjamines.

        this.board[row][col].unCover();


        //If the cell is mine, game over!
        if (current.hasMine()) {
            Minesweeper.endGame();
            displayBoard();
        }
        countAdjaMines(row,col);


        //Sweep method to be called recursively :
            //If no adjamines, calls sweep on nearby cells until adjaMines != 0

        if (current.getAdjaMines() == 0 && !Cell.gameEnded) {
            sweep(row-1,col-1); //NW
            sweep(row-1,col); //N
            sweep(row-1,col+1); //NE
            sweep(row,col-1); //W
            sweep(row,col+1); //E
            sweep(row+1,col-1); //SW
            sweep(row+1,col); //S
            sweep(row+1,col-1); //SE
        }

        if(current.gameEnded) {
            System.out.println("");
            System.out.println("Boom!");
            System.out.println("You've swept a mine!");
            return;
        }
        if (current.isBorder()){
            System.out.println("Can't uncover this cell!");
            return;
        }

        if(!current.gameEnded) {
            System.out.println("There are " + current.getAdjaMines()+ " mines around it.");
        }
    }

    public void countAdjaMines(int row, int col) { //Look at the cell in board[][] and counts nearby mines.
        Cell current = board[row][col];
        int adjaMines = 0;

        if (board[row-1][col-1].hasMine()) adjaMines++; //NW
        if (board[row-1][col].hasMine()) adjaMines++; //N
        if (board[row-1][col+1].hasMine()) adjaMines++; //NE
        if (board[row][col-1].hasMine()) adjaMines++; //W
        if (board[row][col+1].hasMine()) adjaMines++; //E
        if (board[row+1][col-1].hasMine()) adjaMines++; //SW
        if (board[row+1][col].hasMine()) adjaMines++; //S
        if (board[row+1][col+1].hasMine()) adjaMines++; //SE


        current.setAdjaMines(adjaMines);
    }

    public boolean checkGameWin() {
        for(Cell[] row : board) {
            for (Cell cell : row) {
                if (!cell.isUncovered() && !cell.hasMine() ) { //&& !cell.isBorder()
                    return false;
                }
            }
        }
        return true;
    }
}
