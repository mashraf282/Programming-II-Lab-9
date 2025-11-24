import java.util.HashSet;
import java.util.Set;

public class SudokuVerifier extends Thread{

    private final int[][] board;
    private volatile boolean isValid;
    private final int mode;

    public SudokuVerifier(int[][] board, int mode) {
        this.board = board;
        this.mode = mode;
        this.isValid = true;
    }

    @Override
    public void run() {
        switch (mode) {
            case 0:
                verifySudoku();
                break;
            case 3:
                verify3Threads();
                break;
            case 27:
                verify27Threads();
                break;
            default:
                throw new IllegalArgumentException("Mode must be 0, 3, or 27");
        }
    }

    private void verify3Threads() {
        Thread[] threads = new Thread[3];
        threads[0] = new Thread(() -> {
            if(!verifyAllRows()){
                isValid = false;
            }
        });
        threads[1] = new Thread(() -> {
            if(!verifyAllColumns()) {
                isValid = false;
            }
        });
        threads[2] = new Thread(() -> {
            if(!verifyAllBoxes()) {
                isValid = false;
            }
        });

        for(Thread thread : threads){
            thread.start();
        }
        for(Thread thread : threads){
            try {
                thread.join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void verify27Threads() {
        Thread[] threads = new Thread[27];
        int index = 0;

        for(int i = 0; i < 9; i++){
            final int rowIndex = i;
            threads[index++] = new Thread(() -> {
                if(!verifyRow(rowIndex)){
                    isValid = false;
                }
            });
        }

        for(int i = 0; i < 9; i++){
            final int colIndex = i;
            threads[index++] = new Thread(() -> {
                if(!verifyColumn(colIndex)){
                    isValid = false;
                }
            });
        }

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                final int boxRow = i;
                final int boxCol = j;
                threads[index++] = new Thread(() -> {
                    if(!verifyBox(boxRow, boxCol)){
                        isValid = false;
                    }
                });
            }
        }

        for(Thread thread : threads){
            thread.start();
        }
        for(Thread thread : threads){
            try {
                thread.join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean verifyRow(int rowIndex){
        int[] row = board[rowIndex];
        Set<Integer> seen = new HashSet<>();
        for(int num : row){
            if(num < 1 || num > 9 || seen.contains(num)){
                return false;
            }
            seen.add(num);
        }
        return true;
    }

    private boolean verifyColumn(int colIndex){
        Set<Integer> seen = new HashSet<>();
        for(int i = 0; i < 9; i++){
            int num = board[i][colIndex];
            if(num < 1 || num > 9 || seen.contains(num)){
                return false;
            }
            seen.add(num);
        }
        return true;
    }

    private boolean verifyBox(int boxRow, int boxCol){
        Set<Integer> seen = new HashSet<>();
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                int num = board[boxRow * 3 + i][boxCol * 3 + j];
                if(num < 1 || num > 9 || seen.contains(num)){
                    return false;
                }
                seen.add(num);
            }
        }
        return true;
    }

    private boolean verifyAllRows(){
        for(int i = 0; i < 9; i++){
            if(!verifyRow(i)){
                return false;
            }
        }
        return true;
    }

    private boolean verifyAllColumns(){
        for(int i = 0; i < 9; i++){
            if(!verifyColumn(i)){
                return false;
            }
        }
        return true;
    }

    private boolean verifyAllBoxes(){
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(!verifyBox(i, j)){
                    return false;
                }
            }
        }
        return true;
    }

    private void verifySudoku(){
        isValid = verifyAllRows() && verifyAllColumns() && verifyAllBoxes();
    }

    public boolean isValid() {
        return isValid;
    }


}
