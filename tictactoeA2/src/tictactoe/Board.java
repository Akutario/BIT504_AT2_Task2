package tictactoe;

import java.awt.Color;
import java.awt.Graphics;

public class Board {
    private static final int GRID_WIDTH = 8;
    private static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

    Cell[][] cells;

    public Board() {
        cells = new Cell[GameMain.ROWS][GameMain.COLS];
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col] = new Cell(row, col);
            }
        }
    }

    public boolean isDraw() {
        boolean allCellsFilled = true;
        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                if (cells[row][col].getContent() == Player.Empty) {
                    allCellsFilled = false;
                    break;
                }
            }
        }
        return allCellsFilled && !hasWon(Player.Cross) && !hasWon(Player.Nought);
    }

    public boolean hasWon(Player thePlayer) {
        // Check for win horizontally
        for (int row = 0; row < GameMain.ROWS; row++) {
            if (cells[row][0].getContent() == thePlayer && 
                cells[row][1].getContent() == thePlayer && 
                cells[row][2].getContent() == thePlayer) {
                return true; // Found a win
            }
        }

        // Check for win vertically
        for (int col = 0; col < GameMain.COLS; col++) {
            if (cells[0][col].getContent() == thePlayer && 
                cells[1][col].getContent() == thePlayer && 
                cells[2][col].getContent() == thePlayer) {
                return true; // Found a win
            }
        }

        // Check for win diagonally (from top-left to bottom-right)
        if (cells[0][0].getContent() == thePlayer && 
            cells[1][1].getContent() == thePlayer && 
            cells[2][2].getContent() == thePlayer) {
            return true; // Found a win
        }

        // Check for win diagonally (from top-right to bottom-left)
        if (cells[0][2].getContent() == thePlayer && 
            cells[1][1].getContent() == thePlayer && 
            cells[2][0].getContent() == thePlayer) {
            return true; // Found a win
        }

        // No win found
        return false;
    }


    public void makeMove(Player player, int row, int col) {
        cells[row][col].setContent(player);
    }

    public void paint(Graphics g) {
        // Set the background color to black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, GameMain.CANVAS_WIDTH, GameMain.CANVAS_HEIGHT);

        // Draw the grid lines
        g.setColor(Color.DARK_GRAY);
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

        // Check for a win and draw the winning line
        for (int row = 0; row < GameMain.ROWS; row++) {
            if (cells[row][0].getContent() != Player.Empty &&
                cells[row][0].getContent() == cells[row][1].getContent() &&
                cells[row][0].getContent() == cells[row][2].getContent()) {
                // Draw a green line across the winning row
                g.setColor(Color.GREEN);
                g.fillRect(0, row * GameMain.CELL_SIZE + GameMain.CELL_SIZE / 2 - GRID_WIDTH_HALF,
                        GameMain.CANVAS_WIDTH, GRID_WIDTH);
                break;
            }
        }
        for (int col = 0; col < GameMain.COLS; col++) {
            if (cells[0][col].getContent() != Player.Empty &&
                cells[0][col].getContent() == cells[1][col].getContent() &&
                cells[0][col].getContent() == cells[2][col].getContent()) {
                // Draw a green line across the winning column
                g.setColor(Color.GREEN);
                g.fillRect(col * GameMain.CELL_SIZE + GameMain.CELL_SIZE / 2 - GRID_WIDTH_HALF, 0,
                        GRID_WIDTH, GameMain.CANVAS_HEIGHT);
                break;
            }
        }

        // Check for diagonal wins
        if (cells[0][0].getContent() != Player.Empty &&
            cells[0][0].getContent() == cells[1][1].getContent() &&
            cells[0][0].getContent() == cells[2][2].getContent()) {
            // Draw a green line across the diagonal (top-left to bottom-right)
            g.setColor(Color.GREEN);
            g.drawLine(0, 0, GameMain.CANVAS_WIDTH, GameMain.CANVAS_HEIGHT);
        }
        if (cells[0][2].getContent() != Player.Empty &&
            cells[0][2].getContent() == cells[1][1].getContent() &&
            cells[0][2].getContent() == cells[2][0].getContent()) {
            // Draw a green line across the diagonal (top-right to bottom-left)
            g.setColor(Color.GREEN);
            g.drawLine(0, GameMain.CANVAS_HEIGHT, GameMain.CANVAS_WIDTH, 0);
        }
    }


}

