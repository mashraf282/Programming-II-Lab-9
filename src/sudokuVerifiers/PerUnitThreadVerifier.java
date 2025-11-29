package sudokuVerifiers;

import sudokuVerifiers.base.StreamVerifier;
import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerificationResult;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PerUnitThreadVerifier extends StreamVerifier implements SudokuVerifier {
    public PerUnitThreadVerifier(int[][] grid) {
        super(grid);
    }

    @Override
    public VerificationResult verify() {
        var executor = Executors.newFixedThreadPool(27);
        var ret = new VerificationResult();

        @SuppressWarnings("unchecked")
        Future<Map<Integer, List<Integer>>>[] futures = new Future[27];

        for (int i = 0; i < 27; i++) {
            final int index = i;
            futures[i] = executor.submit(() -> verifyStream(mappedGrid.get(index)));
        }

        try {
            for (int i = 0; i < 27; i++) {
                Map<Integer, List<Integer>> violations = futures[i].get();
                if (!violations.isEmpty()) {
                    if (i < 9) ret.putToRows(i, violations);
                    else if (i < 18) ret.putToColumns(i - 9, violations);
                    else ret.putToBoxes(i - 18, violations);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executor.shutdown();
        }

        return ret;

    }
}