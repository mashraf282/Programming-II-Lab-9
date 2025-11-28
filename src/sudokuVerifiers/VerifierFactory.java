package sudokuVerifiers;

public class VerifierFactory {
    public static SudokuVerifier getVerifier(int[][] grid, int mode) {
        switch (mode){
            case 0 -> { return new SingleThreadedVerifier(grid); }
            case 3 -> { return new GroupedThreadsVerifier(grid); }
            case 27 -> { return new PerUnitThreadVerifier(grid); }
            default -> throw new IllegalArgumentException("Invalid mode only 0, 3, 27");
        }
    }
}
