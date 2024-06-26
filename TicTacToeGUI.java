import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeGUI extends JFrame implements ActionListener {
    private static final int BOARD_SIZE = 3;
    private static final char USER_SYMBOL = 'X';
    private static final char COMPUTER_SYMBOL = 'O';
    private JButton[][] buttons;
    private char[][] board;
    private boolean userTurn = true;

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 3));
        initializeBoard();
        displayBoard();
    }

    private void initializeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
                board[i][j] = ' ';
            }
        }
    }

    private void displayBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return board[row][col] == ' ';
    }

    private boolean isWin(char symbol) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }
        if ((board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol)) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void displayResult(char symbol) {
        if (symbol == USER_SYMBOL) {
            JOptionPane.showMessageDialog(this, "Congratulations! You win!");
        } else if (symbol == COMPUTER_SYMBOL) {
            JOptionPane.showMessageDialog(this, "Computer wins! Better luck next time!");
        } else {
            JOptionPane.showMessageDialog(this, "It's a draw!");
        }
        resetGame();
    }

    private void userMove(int row, int col) {
        if (!isValidMove(row, col)) return;
        board[row][col] = USER_SYMBOL;
        buttons[row][col].setText(String.valueOf(USER_SYMBOL));
        if (isWin(USER_SYMBOL)) {
            displayResult(USER_SYMBOL);
        } else if (isBoardFull()) {
            displayResult(' ');
        } else {
            userTurn = false;
            computerMove();
        }
    }

    private void computerMove() {
        int row, col;
        do {
            row = (int) (Math.random() * BOARD_SIZE);
            col = (int) (Math.random() * BOARD_SIZE);
        } while (!isValidMove(row, col));
        board[row][col] = COMPUTER_SYMBOL;
        buttons[row][col].setText(String.valueOf(COMPUTER_SYMBOL));
        if (isWin(COMPUTER_SYMBOL)) {
            displayResult(COMPUTER_SYMBOL);
        } else if (isBoardFull()) {
            displayResult(' ');
        } else {
            userTurn = true;
        }
    }

    private void resetGame() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
        }
        userTurn = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!userTurn) return;
        JButton buttonClicked = (JButton) e.getSource();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (buttons[i][j] == buttonClicked) {
                    userMove(i, j);
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI game = new TicTacToeGUI();
            game.setVisible(true);
        });
    }
}