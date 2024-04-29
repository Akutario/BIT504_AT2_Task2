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
        int playerRow = -1;
        int playerCol = -1;

        for (int row = 0; row < GameMain.ROWS; row++) {
            for (int col = 0; col < GameMain.COLS; col++) {
                if (cells[row][col].getContent() == thePlayer) {
                    playerRow = row;
                    playerCol = col;
                    break;
                }
            }
        }

        if (cells[playerRow][0].getContent() == thePlayer && cells[playerRow][1].getContent() == thePlayer
                && cells[playerRow][2].getContent() == thePlayer)
            return true;

        if (cells[0][playerCol].getContent() == thePlayer && cells[1][playerCol].getContent() == thePlayer
                && cells[2][playerCol].getContent() == thePlayer)
            return true;

        if (cells[0][0].getContent() == thePlayer && cells[1][1].getContent() == thePlayer
                && cells[2][2].getContent() == thePlayer)
            return true;

        if (cells[0][2].getContent() == thePlayer && cells[1][1].getContent() == thePlayer
                && cells[2][0].getContent() == thePlayer)
            return true;

        return false;
    }

    public void makeMove(Player player, int row, int col) {
        cells[row][col].setContent(player);
    }

    public void paint(Graphics g) {
        g.setColor(Color.gray);
        for (int row = 1; row < GameMain.ROWS; ++row) {
            g.fillRoundRect(0, GameMain.CELL_SIZE * row - GRID_WIDTH_HALF, GameMain.CANVAS_WIDTH - 1, GRID_WIDTH,
                    GRID_WIDTH, row);
        }
        for (int col = 1; col < GameMain.COLS; ++col) {
            g.fillRoundRect(GameMain.CELL_SIZE * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, GameMain.CANVAS_HEIGHT - 1,
                    GRID_WIDTH, col);
        }

        for (int row = 0; row < GameMain.ROWS; ++row) {
            for (int col = 0; col < GameMain.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}

