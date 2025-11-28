package sudokuVerifiers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationResult {

    private Map<Integer, Map<Integer, List<Integer>>> rowsErrors = new HashMap<>();
    private Map<Integer, Map<Integer, List<Integer>>> columnsErrors = new HashMap<>();
    private Map<Integer, Map<Integer, List<Integer>>> boxesErrors = new HashMap<>();
    private boolean isValid = true;

    public void addRowError(int row, int number, List<Integer> positions) {
        rowsErrors.computeIfAbsent(row, k -> new HashMap<>()).put(number, positions);
        isValid = false;
    }

    public void addColumnError(int col, int number, List<Integer> positions) {
        columnsErrors.computeIfAbsent(col, k -> new HashMap<>()).put(number, positions);
        isValid = false;
    }

    public void addBoxError(int box, int number, List<Integer> positions) {
        boxesErrors.computeIfAbsent(box, k -> new HashMap<>()).put(number, positions);
        isValid = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // ROWS
        rowsErrors.forEach((row, numberMap) -> {
            numberMap.forEach((num, posList) -> {

                List<Integer> oneBased = posList.stream()
                        .map(p -> p + 1)
                        .toList();

                sb.append("ROW ").append(row + 1)
                        .append(", #").append(num)
                        .append(", ").append(oneBased)
                        .append("\n");
            });
        });

        // COLUMNS
        columnsErrors.forEach((col, numberMap) -> {
            numberMap.forEach((num, posList) -> {

                List<Integer> oneBased = posList.stream()
                        .map(p -> p + 1)
                        .toList();

                sb.append("COLUMN ").append(col + 1)
                        .append(", #").append(num)
                        .append(", ").append(oneBased)
                        .append("\n");
            });
        });

        // BOXES
        boxesErrors.forEach((box, numberMap) -> {
            numberMap.forEach((num, posList) -> {

                List<Integer> oneBased = posList.stream()
                        .map(p -> p + 1)
                        .toList();

                sb.append("BOX ").append(box + 1)
                        .append(", #").append(num)
                        .append(", ").append(oneBased)
                        .append("\n");
            });
        });

        return sb.toString();
    }

    public void merge(VerificationResult result) {
        result.rowsErrors.forEach((row, numberMap) -> {
            numberMap.forEach((num, posList) -> {
                this.addRowError(row, num, posList);
            });
        });

        result.columnsErrors.forEach((col, numberMap) -> {
            numberMap.forEach((num, posList) -> {
                this.addColumnError(col, num, posList);
            });
        });

        result.boxesErrors.forEach((box, numberMap) -> {
            numberMap.forEach((num, posList) -> {
                this.addBoxError(box, num, posList);
            });
        });
    }

    public boolean isValid() {
        return isValid;
    }
}
