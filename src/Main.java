import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerifierFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        int mode = 3;
        try {
            SudokuVerifier game = VerifierFactory.getVerifier(SudokuLoader.loadFromCSV(new File("input.csv")), mode);
            System.out.println(game.verify());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}