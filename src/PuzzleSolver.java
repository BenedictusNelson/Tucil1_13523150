import java.util.List;
import java.util.ArrayList;

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

        return false;
    }
    
    /**
     * cek board apa udah penuh
     * I.S.: board puzzle terdefinisi
     * F.S.: true jika tidak ada sel kosong ('.'), false jika ada
     */
    private boolean boardIsFull() {

    }
    
    /**
     * cek apa shape dapat ditempatkan pada board di posisi (startRow, startCol).
     * I.S.: matrix shape dan posisi board terdefinisi
     * F.S.: return true jika shape dapat ditempatkan tanpa tumpang tindih, false sebaliknya.
     */
    private boolean canPlace(int[][] shape, int startRow, int startCol) {

    }
    
    /**
     * penempatan shape pada board di posisi (startRow, startCol)
     * I.S.: matrix shape dan posisi board 
     * F.S.: board diperbarui, sel-sel yang sesuai 
     */
    private void placePiece(int[][] shape, int startRow, int startCol, char label) {

    }
    
    /**
     * hapus shape dari board pada posisi (startRow, startCol).
     * I.S.: matrix shape dan posisi board
     * F.S.: Sel-sel yang sebelumnya diisi dengan label dikembalikan ke '.'
     */
    private void removePiece(int[][] shape, int startRow, int startCol) {

    }
    
    /**
     * Mencetak board puzzle ke konsol
     * I.S.: Board (solusi) telah terbentuk
     * F.S.: Board dicetak ke konsol
     */
    public void printBoard() {

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
