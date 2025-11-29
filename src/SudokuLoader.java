import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Stream;

public class SudokuLoader {
    public static int[][] loadFromCSV(File file) throws FileNotFoundException {
        var reader = new BufferedReader(new FileReader(file));
        //line stream -> stream of array of strings -> map elements to int -> convert to array -> convert all to matrix
        return reader.lines()
                .map(line -> line.split(","))
                .map(arr -> Arrays.stream(arr).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);
    }
}