public class Minesweeper {
    private static int difficulty = 1; // Difficulty part of the Minesweeper class
    private Board board;

    public static void setDifficulty(int difficulty) {
        Minesweeper.difficulty = difficulty;
    }

    public static int getDifficulty() {
        return difficulty;
    }


    public static void endGame() {
        Cell.gameEnded = true;
    }


}
