package Verifier;

public class EachTypeVerifier extends Thread implements SudokuVerifier{
    private int[][] board;
    public EachTypeVerifier(int[][] board){
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
