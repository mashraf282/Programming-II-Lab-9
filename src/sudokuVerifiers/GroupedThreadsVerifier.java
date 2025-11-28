package sudokuVerifiers;

public class GroupedThreadsVerifier extends StreamVerifier implements SudokuVerifier, Runnable {
    public GroupedThreadsVerifier(int[][] grid) {
        super(grid);
    }

    public VerificationResult verify(){
        return null;
    }

    @Override
    public void run(){}
}