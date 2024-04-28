package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    // Constants for game
    // number of ROWS by COLS cell constants
    public static final int ROWS = 3;
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // constants for dimensions used for drawing
    // cell width and height
    public static final int CELL_SIZE = 100;
    // drawing canvas
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    // Naughts and Crosses are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;

    /* declare game object variables */
    // the game board
    private Board board;

    // Enumeration for game states
    public enum GameState {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }

    private GameState currentState;

    // the current player
    private Player currentPlayer;
    // for displaying game status message
    private JLabel statusBar;

    /** Constructor to setup the UI and game components on the panel */
    public GameMain() {
        // Add mouse listener to the panel
        addMouseListener(this);

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        // Layout of the panel is in border layout
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        // Account for statusBar height in overall height
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Create a new instance of the game "Board" class
        board = new Board(ROWS, COLS);

        // Call the method to initialize the game board
        initGame();
    }

    public static void main(String[] args) {
        // Run GUI code in Event Dispatch thread for thread safety.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create a main window to contain the panel
                JFrame frame = new JFrame(TITLE);

                // create the new GameMain panel and add it to the frame
                GameMain gameMain = new GameMain();
                frame.add(gameMain);

                // set the default close operation of the frame to exit_on_close
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /** Custom painting codes on this JPanel */
    public void paintComponent(Graphics g) {
        // fill background and set colour to white
        super.paintComponent(g);
        setBackground(Color.WHITE);
        // ask the game board to paint itself
        board.paint(g);

        // Set status bar message based on game state
        if (currentState == GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == Player.CROSS) {
                // Display "X"'s Turn message on the status bar
                statusBar.setText("X's Turn");
            } else {
                // Display "O"'s Turn message on the status bar
                statusBar.setText("O's Turn");
            }
        } else if (currentState == GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Initialise the game-board contents and the current status of GameState and Player) */
    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                // all cells empty
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.PLAYING;
        currentPlayer = Player.Cross;
    }

    /** After each turn check to see if the current player hasWon by putting their symbol in that position,
     * If they have the GameState is set to won for that player
     * If no winner then isDraw is called to see if deadlock, if not GameState stays as PLAYING.
     */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check for win after play
        if (board.hasWon(thePlayer, row, col)) {
            // Check which player has won and update the currentState to the appropriate GameState for the winner
            if (thePlayer == Player.CROSS) {
                currentState = GameState.CROSS_WON;
            } else {
                currentState = GameState.NOUGHT_WON;
            }
        } else if (board.isDraw()) {
            // Set the currentState to the draw GameState
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to currentState of playing
    }


    /**
     * Event handler for the mouse click on the JPanel. If selected cell is valid and Empty then current player is added to cell content.
     * UpdateGame is called which will call the methods to check for winner or Draw. if none then GameState remains playing.
     * If win or Draw then call is made to method that resets the game board.  Finally a call is made to refresh the canvas so that new symbol appears
     */

    public void mouseClicked(MouseEvent e) {
        // get the coordinates of where the click event happened
        int mouseX = e.getX();
        int mouseY = e.getY();
        // Get the row and column clicked
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;
        if (currentState == GameState.PLAYING) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS && board.cells[rowSelected][colSelected].content == Player.Empty) {
                // move
                board.cells[rowSelected][colSelected].content = currentPlayer;
                // update currentState
                updateGame(currentPlayer, rowSelected, colSelected);
                // Switch player
                if (currentPlayer == Player.CROSS) {
                    currentPlayer = Player.NOUGHT;
                } else {
                    currentPlayer = Player.CROSS;
                }
            }
        } else {
            // game over and restart
            initGame();
        }

        // Redraw the graphics on the UI
        repaint();


    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Auto-generated, event not used

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Auto-generated, event not used

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Auto-generated,event not used

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Auto-generated, event not used

    }

}
