package sudokuVerifiers;

import java.util.Arrays;

public final class SingleThreadedVerifier extends StreamVerifier implements SudokuVerifier, Runnable {

    public SingleThreadedVerifier(int[][] grid) {
        super(grid);
    }

    public VerificationResult verify() {
        var ret = new VerificationResult();
        for (int i = 0; i < 27; i++) {
//            if (Arrays.stream(mappedGrid.get(i)).sum() == 45) continue; not correct: 9 * 5 = 45
            if(i < 9) ret.putToRows(i, verifyStream(mappedGrid.get(i)));
            else if(i < 18) ret.putToColumns(i - 9, verifyStream(mappedGrid.get(i)));
            else ret.putToBoxes(i - 18, verifyStream(mappedGrid.get(i)));
        }
        return ret;
    }

    @Override
    public void run() {
    }

}