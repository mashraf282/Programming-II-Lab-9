package sudokuVerifiers;

import sudokuVerifiers.base.StreamVerifier;
import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerificationResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

public class PerUnitThreadVerifier extends StreamVerifier implements SudokuVerifier {
    public PerUnitThreadVerifier(int[][] grid) {
        super(grid);
    }

    @Override
    public VerificationResult verify() {
        return null;
    }
}