package connect4;
import javax.swing.*;
import java.awt.*;

public class connect4 {
    private static final int ROWS = 6;
    private static final int COLUMNS = 7;
    private static final char EMPTY = '.';
    private static final char PLAYER1 = 'X';
    private static final char PLAYER2 = 'O';
    private static char[][] board = new char[ROWS][COLUMNS];
    private static char currentPlayer = PLAYER1;
    private static JButton[] columnButtons = new JButton[COLUMNS];
    private static JLabel[][] gridLabels = new JLabel[ROWS][COLUMNS];
    
    public static void main(String[] args) {
        initializeBoard();
        createGUI();
    }

    private static void initializeBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Connect 4");
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1, COLUMNS));
        for (int i = 0; i < COLUMNS; i++) {
            JButton button = new JButton("Drop");
            columnButtons[i] = button;
            final int col = i;
            button.addActionListener(e -> handleMove(col));
            topPanel.add(button);
        }
        
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ROWS, COLUMNS));
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                JLabel label = new JLabel(" ", SwingConstants.CENTER);
                label.setFont(new Font("Arial", Font.BOLD,30));
                label.setOpaque(true);
                label.setBackground(Color.LIGHT_GRAY);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gridLabels[i][j] = label;
                gridPanel.add(label);
            }
        }
        
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void handleMove(int column) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY) {
                board[i][column] = currentPlayer;
                gridLabels[i][column].setText(String.valueOf(currentPlayer));
                gridLabels[i][column].setForeground(currentPlayer == PLAYER1 ? Color.RED : Color.BLUE);
                if (checkWin(currentPlayer)) {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    resetGame();
                    return;
                }
                currentPlayer = (currentPlayer == PLAYER1) ? PLAYER2 : PLAYER1;
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Column is full! Choose another.");
    }

    private static boolean checkWin(char player) {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == player) {
                    if (checkDirection(row, col, 1, 0, player) ||
                        checkDirection(row, col, 0, 1, player) ||
                        checkDirection(row, col, 1, 1, player) ||
                        checkDirection(row, col, 1, -1, player)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean checkDirection(int row, int col, int rowDir, int colDir, char player) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * rowDir;
            int c = col + i * colDir;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLUMNS && board[r][c] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    private static void resetGame() {
        initializeBoard();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                gridLabels[i][j].setText(" ");
            }
        }
        currentPlayer = PLAYER1;
    }
}
