package Verifier;

import java.util.*;
import java.util.concurrent.FutureTask;

public class EachTypeVerifier implements SudokuVerifier {
    private int[][] board;

    public EachTypeVerifier(int[][] board) {
        this.board = board;
    }

    @Override
    public VerificationResult verify() {

        FutureTask<VerificationResult> rowTask = new FutureTask<>(this::rowVerify);
        FutureTask<VerificationResult> columnTask = new FutureTask<>(this::columnVerify);
        FutureTask<VerificationResult> boxTask = new FutureTask<>(this::boxVerify);

        new Thread(rowTask).start();
        new Thread(columnTask).start();
        new Thread(boxTask).start();

        VerificationResult finalResult = new VerificationResult();

        try {

            VerificationResult rowResult = rowTask.get();
            VerificationResult columnResult = columnTask.get();
            VerificationResult boxResult = boxTask.get();

            finalResult.merge(rowResult);
            finalResult.merge(columnResult);
            finalResult.merge(boxResult);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    private VerificationResult rowVerify() {
        VerificationResult result = new VerificationResult();

        for (int i = 0; i < 9; i++) {
            Map<Integer, List<Integer>> numberPositions = new HashMap<>();

            for (int j = 0; j < 9; j++) {
                int num = board[i][j];
                numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(j);
            }
            for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet())
                if (e.getValue().size() > 1)
                    result.addRowError(i, e.getKey(), e.getValue());
        }
        return result;
    }

        private VerificationResult columnVerify() {
            VerificationResult result = new VerificationResult();

            for (int j = 0; j < 9; j++) {
                Map<Integer, List<Integer>> numberPositions = new HashMap<>();

                for (int i = 0; i < 9; i++) {
                    int num = board[i][j];
                    numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
                }

                for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet())
                    if (e.getValue().size() > 1)
                        result.addColumnError(j, e.getKey(), e.getValue());
            }
            return result;
        }

        private VerificationResult boxVerify() {
            VerificationResult result = new VerificationResult();

            for (int boxRow = 0; boxRow < 3; boxRow++)
                for (int boxCol = 0; boxCol < 3; boxCol++) {
                    Map<Integer, List<Integer>> numberPositions = new HashMap<>();

                    for (int i = 0; i < 3; i++)
                        for (int j = 0; j < 3; j++) {
                            int num = board[boxRow * 3 + i][boxCol * 3 + j];
                            int pos = i * 3 + j;
                            numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(pos);
                        }

                    int boxIndex = boxRow * 3 + boxCol;
                    for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet())
                        if (e.getValue().size() > 1)
                            result.addBoxError(boxIndex, e.getKey(), e.getValue());
                }
            return result;
        }
    }

