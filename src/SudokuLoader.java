import java.io.BufferedReader;
import java.io.FileReader;

public class SudokuLoader {
    private String[][] board;

    public SudokuLoader(String filePath) {
        board = new String[9][9];
        loadBoardFromCSV(filePath);
    }

    private void loadBoardFromCSV(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            for(int row = 0; row < 9; row++) {
                line = br.readLine();
                String[] values = line.split(",");
                for (int col = 0; col < 9; col++) {
                    board[row][col] = values[col].trim();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String[][] getBoard() {
        return board;
    }

    public int[][] getBoardAsInt() {
        int[][] intBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                intBoard[i][j] = Integer.parseInt(board[i][j]);
            }
        }
        return intBoard;
    }
}
