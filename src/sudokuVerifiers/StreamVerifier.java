package sudokuVerifiers;

import java.util.*;
import java.util.stream.IntStream;

public abstract class StreamVerifier {
    protected VerificationResult result;
    protected List<int[]> mappedGrid;

    public StreamVerifier(int[][] grid) {
        //map then reduce
        //map:
        mappedGrid = new ArrayList<int[]>(Arrays.asList(grid)); //add rows
        // for i = 0 -> 9 pass every row (int[]) and replace it by row[i] int
        IntStream.range(0, 9).forEach(i -> mappedGrid.add(Arrays.stream(grid).mapToInt(row -> row[i]).toArray())); //add columns
        for (int box = 0; box < 9; box++) {
            int[] boxArray = new int[9];
            int index = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    boxArray[index++] = grid[(box / 3) * 3 + i][(box % 3) * 3 + j];
                }
            }
            mappedGrid.add(boxArray);
        }
    }

    //reduce
    protected Map<Integer, List<Integer>> verifyStream(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++)
            map.computeIfAbsent(arr[i], e -> new ArrayList<Integer>()).add(i);
        map.entrySet().removeIf(entry -> entry.getValue().size() < 2);
        //same thing below
//        for(Iterator<Map.Entry<Integer, List<Integer>>> it = map.entrySet().iterator(); it.hasNext(); ) {
//            Map.Entry<Integer, List<Integer>> entry = it.next();
//            if(entry.getValue().size() < 2) {
//                it.remove();
//            }
        return map;
    }

    public VerificationResult getVerificationResult() {
        return result;
    }
}