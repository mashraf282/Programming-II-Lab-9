import java.io.BufferedReader;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
         // dummy data for testing
//         String[] testArgs = new String[2];
//         testArgs[0] = "input.csv";
//         testArgs[1] = "27";

        SudokuLoader loader = new SudokuLoader(args[0]);
        SudokuVerifier verifier = new SudokuVerifier(loader.getBoardAsInt(), Integer.parseInt(args[1]));
        verifier.start();
        try {
            verifier.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Sudoku is valid: " + verifier.isValid());


    }
}