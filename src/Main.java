import Verifier.SudokuFactory;
import Verifier.SudokuVerifier;
import Verifier.VerificationResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {

        SudokuLoader loader = new SudokuLoader(args[0]);

        SudokuFactory factory = new SudokuFactory(loader.getBoardAsInt());
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