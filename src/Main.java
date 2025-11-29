import sudokuVerifiers.base.SudokuVerifier;
import sudokuVerifiers.base.VerifierFactory;

import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        long startTime = System.nanoTime();
        try {
            SudokuVerifier game = VerifierFactory.getVerifier(SudokuLoader.loadFromCSV(new File(args[0])), Integer.parseInt(args[1]));
            System.out.println(game.verify());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        long endTime = System.nanoTime();
        System.out.println("Number of treads: " + args[1] + " time: " + (float)(endTime - startTime) / 1000000000);
    }
}