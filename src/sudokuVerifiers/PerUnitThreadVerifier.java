package sudokuVerifiers;

public class PerUnitThreadVerifier extends StreamVerifier implements SudokuVerifier {
    public PerUnitThreadVerifier(int[][] grid){
        super(grid);
    }

    public VerificationResult verify(){
        return null;
    }


}