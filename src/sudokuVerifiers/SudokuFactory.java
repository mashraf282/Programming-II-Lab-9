package sudokuVerifiers;

public class SudokuFactory {
    private int[][] grid;
    public SudokuFactory(int[][] grid){
        this.grid = grid;
    }
    public SudokuVerifier getSudokuVerifier(int type){
        if(type == 0) return new SingleThreadVerifier(grid);
        if(type == 3) return new EachTypeVerifier(grid);
        if(type == 27) return new EachUnitVerifier(grid);
        return null;
    }
}
