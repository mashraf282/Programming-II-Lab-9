package sudokuVerifiers;

import sudokuVerifiers.base.StreamVerifier;
import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerificationResult;

public final class SingleThreadedVerifier extends StreamVerifier implements SudokuVerifier {

    public SingleThreadedVerifier(int[][] grid) {
        super(grid);
    }

    public VerificationResult verify() {
        var ret = new VerificationResult();
        for (int i = 0; i < 27; i++) {
            if(i < 9) ret.putToRows(i, verifyStream(mappedGrid.get(i)));
            else if(i < 18) ret.putToColumns(i - 9, verifyStream(mappedGrid.get(i)));
            else ret.putToBoxes(i - 18, verifyStream(mappedGrid.get(i)));
        }
        return ret;
    }
}