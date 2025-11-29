

package sudokuVerifiers;

import sudokuVerifiers.base.StreamVerifier;
import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerificationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class GroupedThreadsVerifier extends StreamVerifier implements SudokuVerifier {
    public GroupedThreadsVerifier(int[][] grid) {
        super(grid);
    }

    @Override
    public VerificationResult verify() {
        var rowTask = new FutureTask<>(() -> verifyCategory(mappedGrid.subList(0, 9)));
        var columnTask = new FutureTask<>(() -> verifyCategory(mappedGrid.subList(9, 18)));
        var boxTask = new FutureTask<>(() -> verifyCategory(mappedGrid.subList(18, 27)));

        new Thread(rowTask).start();
        new Thread(columnTask).start();
        new Thread(boxTask).start();

        var ret = new VerificationResult();

        try {
            Map<Integer, Map<Integer, List<Integer>>> rowResults = rowTask.get();
            Map<Integer, Map<Integer, List<Integer>>> colResults = columnTask.get();
            Map<Integer, Map<Integer, List<Integer>>> boxResults = boxTask.get();

            rowResults.forEach(ret::putToRows);
            colResults.forEach(ret::putToColumns);
            boxResults.forEach(ret::putToBoxes);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ret;
    }

    private Map<Integer, Map<Integer, List<Integer>>> verifyCategory(List<int[]> list) {
        var ret = new HashMap<Integer, Map<Integer, List<Integer>>>();
        for (int i = 0; i < 9; i++ ){
            var violations = verifyStream(list.get(i));
            if(!violations.isEmpty())
                ret.put(i, verifyStream(list.get(i)));
        }
        return ret;
    }

}
