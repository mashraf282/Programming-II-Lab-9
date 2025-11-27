package Verifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class EachUnitVerifier implements SudokuVerifier {
    private final int[][] board;
    public EachUnitVerifier(int[][] board){
        this.board = board;
    }


    @Override
    public VerificationResult verify() {

        FutureTask<VerificationResult>[] rowTasks = new FutureTask[9];
        FutureTask<VerificationResult>[] columnTasks = new FutureTask[9];
        FutureTask<VerificationResult>[] boxTasks = new FutureTask[9];

         for(int i = 0; i < 9; i++){
             final int index = i;

             rowTasks[i] = new FutureTask<>(() -> rowVerify(index));
             new Thread(rowTasks[i]).start();

             columnTasks[i] = new FutureTask<>(() -> columnVerify(index));
             new Thread(columnTasks[i]).start();
         }

         for(int i = 0; i < 3; i++)
             for(int j = 0; j < 3; j++){
                 final int boxRow = i;
                 final int boxCol = j;
                 boxTasks[i * 3 + j] = new FutureTask<>(() -> boxVerify(boxRow, boxCol));
                 new Thread(boxTasks[i * 3 + j]).start();
             }

         VerificationResult finalResult = new VerificationResult();
         try{
             for(int i = 0; i < 9; i++){
                 finalResult.merge(rowTasks[i].get());
                 finalResult.merge(columnTasks[i].get());
                 finalResult.merge(boxTasks[i].get());
             }
         } catch (Exception e) {
             throw new RuntimeException(e);
         }

         return finalResult;

    }

    private VerificationResult rowVerify(int index) {
        VerificationResult result = new VerificationResult();
        Map<Integer, List<Integer>> numberPositions = new HashMap<>();

        for (int j = 0; j < 9; j++) {
            int num = board[index][j];
            numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(j);
        }
        for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet())
            if (e.getValue().size() > 1)
                result.addRowError(index, e.getKey(), e.getValue());

        return result;
    }

    private VerificationResult columnVerify(int index) {
        VerificationResult result = new VerificationResult();
        Map<Integer, List<Integer>> numberPositions = new HashMap<>();

        for (int i = 0; i < 9; i++) {
            int num = board[i][index];
            numberPositions.computeIfAbsent(num, k -> new ArrayList<>()).add(i);
        }

        for (Map.Entry<Integer, List<Integer>> e : numberPositions.entrySet())
            if (e.getValue().size() > 1)
                result.addColumnError(index, e.getKey(), e.getValue());

        return result;
    }

    private VerificationResult boxVerify(int boxRow, int boxCol) {
        VerificationResult result = new VerificationResult();
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

        return result;
    }
}
