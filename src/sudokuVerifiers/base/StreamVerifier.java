package sudokuVerifiers.base;

import java.util.*;

import java.util.stream.IntStream;

public abstract class StreamVerifier {
    protected List<int[]> mappedGrid;

    public StreamVerifier(int[][] grid) {
        //map then reduce
        //map:
        mappedGrid = new ArrayList<>(Arrays.asList(grid)); //add rows
        IntStream.range(0, 9).forEach(i -> mappedGrid.add(Arrays.stream(grid).mapToInt(row -> row[i]).toArray())); //add columns
        IntStream.range(0, 9).forEach(i -> mappedGrid.add(IntStream.range(0, 3) //add boxes
                .flatMap(j -> IntStream.range(0, 3)
                .map(k -> grid[(i / 3) * 3 + j][(i % 3) * 3 + k]))
                .toArray()
        ));
    }

    //reduce
    protected Map<Integer, List<Integer>> verifyStream(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++)
            map.computeIfAbsent(arr[i], e -> new ArrayList<>()).add(i);
        map.entrySet()
                .removeIf(entry -> entry
                .getValue()
                .size() < 2);
        return map;
    }
}