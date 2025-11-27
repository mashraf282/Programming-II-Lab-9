package Verifier;

import java.util.*;

public class SingleThreadVerifier implements SudokuVerifier {

    private final int[][] board;

    public SingleThreadVerifier(int[][] board) {
        this.board = board;
    }

    @Override
    public VerificationResult verify() {
        VerificationResult result = new VerificationResult();

        // Check rows
        for (int i = 0; i < 9; i++) {
            Map<Integer, List<Integer>> numberPositions = new HashMap<>();

            for (int j = 0; j < 9; j++) {
                int num = board[i][j];

                numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(j);
            }

            for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet()) {
                if (e.getValue().size() > 1) {
                    result.addRowError(i, e.getKey(), e.getValue());
                }
            }
        }

        // Check columns
        for (int j = 0; j < 9; j++) {
            Map<Integer, List<Integer>> numberPositions = new HashMap<>();

            for (int i = 0; i < 9; i++) {
                int num = board[i][j];

                numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
            }

            for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet()) {
                if (e.getValue().size() > 1) {
                    result.addColumnError(j, e.getKey(), e.getValue());
                }
            }
        }

        // Check boxes
        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {

                Map<Integer, List<Integer>> numberPositions = new HashMap<>();

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int num = board[boxRow * 3 + i][boxCol * 3 + j];
                        int pos = i * 3 + j;

                        numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(pos);
                    }
                }

                int boxIndex = boxRow * 3 + boxCol;

                for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet()) {
                    if (e.getValue().size() > 1) {
                        result.addBoxError(boxIndex, e.getKey(), e.getValue());
                    }
                }
            }
        }

        return result;
    }
}
