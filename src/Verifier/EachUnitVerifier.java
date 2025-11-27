package Verifier;

public class EachUnitVerifier extends Thread implements SudokuVerifier {
    private int[][] board;
    public EachUnitVerifier(int[][] board){
        this.board = board;
    }

    @Override
    public void run() {

    }

    @Override
    public VerificationResult verify() {
return null;
    }
}
