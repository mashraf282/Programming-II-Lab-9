import sudokuVerifiers.SudokuVerifier;
import sudokuVerifiers.VerificationResult;
import sudokuVerifiers.VerifierFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int mode = 0;
        try {
            SudokuVerifier game = VerifierFactory.getVerifier(SudokuLoader.loadFromCSV(new File("input.csv")), mode);
            System.out.println(game.verify());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}