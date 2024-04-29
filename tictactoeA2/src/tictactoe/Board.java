package tictactoe;


import java.awt.Color;
import java.awt.Graphics;

public class Board {
    private static final int GRID_WIDTH = 8; // Define GRID_WIDTH
    private static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Define GRID_WIDTH_HALF

    // Change visibility of cells to package-private
    Cell[][] cells;

    /** Constructor to create the game board */
    public Board() {
        // Initialize the cells array using ROWS and COLS constants
        cells = new Cell[GameMain.ROWS][GameMain.COLS];

        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }
    /** Return true if it is a draw (i.e., no more EMPTY cells) */
    public boolean isDraw() {
        // Check if there is a winner, if yes, it's not a draw
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].getContent() != null) {
                    Player currentPlayer = cells[row][col].getContent();
                    if (hasWon(currentPlayer, row, col)) {
                        return false;
                    }
                }
            }
        }

        // Check whether all cells are filled
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].getContent() == null) {
                    return false;
                }
            }
        }
        return true;
    }


    /** Return true if the current player "thePlayer" has won after making their move */
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        // check if player has 3-in-that-row
        if (cells[playerRow][0].getContent() == thePlayer && cells[playerRow][1].getContent() == thePlayer
                && cells[playerRow][2].getContent() == thePlayer)
            return true;

        // Check if the player has 3 in the playerCol.
        if (cells[0][playerCol].getContent() == thePlayer && cells[1][playerCol].getContent() == thePlayer
                && cells[2][playerCol].getContent() == thePlayer)
            return true;

        // 3-in-the-diagonal
        if (cells[0][0].getContent() == thePlayer && cells[1][1].getContent() == thePlayer
                && cells[2][2].getContent() == thePlayer)
            return true;

        // Check the diagonal in the other direction
        if (cells[0][2].getContent() == thePlayer && cells[1][1].getContent() == thePlayer
                && cells[2][0].getContent() == thePlayer)
            return true;

        // no winner, keep playing
        return false;
    }

    /**
     * Draws the grid (rows then columns) using constant sizes, then call on the Cells to paint themselves into the grid
     */
    public void paint(Graphics g) {
        // draw the grid
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF, GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, row);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, col);
        }

        // Draw the cells
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}
