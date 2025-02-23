// import java.util.List;
// import java.util.ArrayList;

/*
    Konstruktor:
      I.S.: Parameter rows, cols, dan array pieces
      F.S.: Board puzzle diinisialisasi sebagai matriks rows x cols yang terisi karakter '.'
      
    Main
      - solve: I.S.: Board kosong, pieces belum ditempatkan
               F.S.: (papan terisi penuh)
      - printBoard: I.S.: solusi
                    F.S.: board dicetak ke konsol
      
      - canPlace: cek apa matrix shape bisa ditempatkan di board di posisi tertentu
      - placePiece: naro shape di board
      - removePiece: hapus shape dri board
*/
public class PuzzleSolver {
    private int rows, cols;
    private char[][] board;
    private PuzzlePiece[] pieces;
    private boolean[] used;
    private long attemptCount;
    
    /**
     * Konstruktor PuzzleSolver.
     * I.S.: Parameter rows, cols, dan array pieces terdefinisi
     * F.S.: Board puzzle diinisialisasi ukuran rows x cols, terisi dengan '.'
     */
    public PuzzleSolver(int rows, int cols, PuzzlePiece[] pieces) {
        this.rows = rows;
        this.cols = cols;
        this.pieces = pieces;
        board = new char[rows][cols];
        used = new boolean[pieces.length];
        attemptCount = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                board[r][c] = '.';
            }
        }
    }
    
    /**
     * brute force
     * I.S.: board kosong & semua pieces ada
     * F.S.: true jika solusi ditemukan (board terisi penuh), false jika tidak
     */
    public boolean solve() {
        return backtrack(0);
    }
    
    // backtracking
    private boolean backtrack(int pieceIndex) {
        if (pieceIndex == pieces.length)return boardIsFull();
        PuzzlePiece piece = pieces[pieceIndex];
        int[][] shape = piece.getShapes().get(0);
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                attemptCount++;  // Update iterasi
                if (canPlace(shape, r, c)) {
                    placePiece(shape, r, c, piece.getLabel());
                    used[pieceIndex] = true;
                    if (backtrack(pieceIndex + 1)) return true;
                    removePiece(shape, r, c);
                    used[pieceIndex] = false;
                }
            }
        }
        return false;
    }
    
    /**
     * cek board apa udah penuh
     * I.S.: board puzzle terdefinisi
     * F.S.: true jika tidak ada sel kosong ('.'), false jika ada
     */
    private boolean boardIsFull() {
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                if (board[r][c] == '.') return false;
        return true;
    }

    /**
     * cek apa shape dapat ditempatkan pada board di posisi (startRow, startCol).
     * I.S.: matrix shape dan posisi board terdefinisi
     * F.S.: return true jika shape dapat ditempatkan tanpa tumpang tindih, false sebaliknya.
     */
    private boolean canPlace(int[][] shape, int startRow, int startCol) {
        int rCount = shape.length, cCount = shape[0].length;
        if (startRow + rCount > rows || startCol + cCount > cols) return false;
        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
                if (shape[r][c] == 1 && board[startRow + r][startCol + c] != '.')
                    return false;
            }
        }
        return true;
    }
    
    /**
     * penempatan shape pada board di posisi (startRow, startCol)
     * I.S.: matrix shape dan posisi board 
     * F.S.: board diperbarui, sel-sel yang sesuai 
     */
    private void placePiece(int[][] shape, int startRow, int startCol, char label) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    board[startRow + r][startCol + c] = label;
                }
            }
        }
    }
    
    /**
     * hapus shape dari board pada posisi (startRow, startCol).
     * I.S.: matrix shape dan posisi board
     * F.S.: Sel-sel yang sebelumnya diisi dengan label dikembalikan ke '.'
     */
    private void removePiece(int[][] shape, int startRow, int startCol) {
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[0].length; c++) {
                if (shape[r][c] == 1) {
                    board[startRow + r][startCol + c] = '.';
                }
            }
        }
    }
    
    /**
     * Mencetak board puzzle ke konsol
     * I.S.: Board (solusi) telah terbentuk
     * F.S.: Board dicetak ke konsol
     */
    public void printBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print(board[r][c]);
            }
            System.out.println();
        }
    }
    
    /**
     * Mengembalikan jumlah attempts yang telah dilakukan
     * I.S.: variabel attemptCount 
     * F.S.: return nilai attemptCount 
     */
    public long getAttemptCount() {
        return attemptCount;
    }
}