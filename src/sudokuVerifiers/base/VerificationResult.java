package sudokuVerifiers.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VerificationResult {
    private Map<Integer, Map<Integer, List<Integer>>> rows;
    private Map<Integer, Map<Integer, List<Integer>>> columns;
    private Map<Integer, Map<Integer, List<Integer>>> boxes;

    public VerificationResult() {
        rows = new HashMap<>();
        columns = new HashMap<>();
        boxes = new HashMap<>();
    }

    @Override
    public String toString() {
        var ret = nestedMapToString(rows, "Row ") +
                nestedMapToString(columns, "Column ") +
                nestedMapToString(boxes, "Box ");
        if(ret.isEmpty()) return "VALID";
        return ret;
    }

    public void putToRows(int rowIndex, Map<Integer, List<Integer>> row) {
        rows.put(rowIndex, row);
    }

    public void putToColumns(int columnIndex, Map<Integer, List<Integer>> column) {
        columns.put(columnIndex, column);
    }

    public void putToBoxes(int boxIndex, Map<Integer, List<Integer>> box) {
        boxes.put(boxIndex, box);
    }

    private static String nestedMapToString(Map<Integer, Map<Integer, List<Integer>>> map, String prefix) {
        StringBuilder ret = new StringBuilder();
        map.forEach((index, output) -> output
                .forEach((number, indices) -> ret
                .append(prefix)
                .append(index + 1) //0 to 1 indexed
                .append(", #")
                .append(number)
                .append(", ")
                .append(Arrays.toString(indices.stream().map(i -> i + 1).toArray())) //0 to 1 indexed
                .append("\n")));
        return ret.toString();
    }

    public void setRows(Map<Integer, Map<Integer, List<Integer>>> rows) {
        this.rows = rows;
    }

    public void setColumns(Map<Integer, Map<Integer, List<Integer>>> columns) {
        this.columns = columns;
    }

    public void setBoxes(Map<Integer, Map<Integer, List<Integer>>> boxes) {
        this.boxes = boxes;
    }
}