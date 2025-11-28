import sudokuVerifiers.SudokuFactory;
import sudokuVerifiers.SudokuVerifier;
import sudokuVerifiers.VerificationResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        SudokuFactory factory = new SudokuFactory(SudokuLoader.loadFromCSV(new File(args[0])));
        SudokuVerifier verifier = factory.getSudokuVerifier(Integer.parseInt(args[1]));

        FutureTask<VerificationResult> task = new FutureTask<>(verifier::verify);

        Thread t = new Thread(task);
        t.start();

        VerificationResult result;
        try {
            result = task.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if(result.isValid())
            System.out.println("The Sudoku solution is valid.");
        else {
            System.out.println("The Sudoku solution is invalid.");
            System.out.println(result);
        }

    }
}