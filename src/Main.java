import Verifier.SudokuFactory;
import Verifier.SudokuVerifier;
import Verifier.VerificationResult;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) {
         // dummy data for testing
//         String[] testArgs = new String[2];
//         testArgs[0] = "input.csv";
//         testArgs[1] = "27";

        SudokuLoader loader = new SudokuLoader("input.csv");
        SudokuFactory factory = new SudokuFactory(loader.getBoardAsInt());
        SudokuVerifier verifier = factory.getSudokuVerifier(0);
        FutureTask<VerificationResult> task = new FutureTask<>(verifier::verify);
        Thread t = new Thread(task);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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