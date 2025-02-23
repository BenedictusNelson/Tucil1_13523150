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
    
    // ANSI color codes
    private static final String ANSI_RESET  = "\u001B[0m";
    private static final String ANSI_RED    = "\u001B[31m";
    private static final String ANSI_GREEN  = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE   = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN   = "\u001B[36m";
    private static final String ANSI_WHITE  = "\u001B[37m";
    /**
     * Konstruktor PuzzleSolver.
     * I.S.: Parameter rows, cols, dan array pieces terdefinisi
     * F.S.: Board puzzle diinisialisasi ukuran rows x cols, terisi dengan '.'
     */
    public PuzzleSolver(int rows, int cols, PuzzlePiece[] pieces) {
        this.rows = rows;
        this.cols = cols;
        this.pieces = pieces;
        this.board = new char[rows][cols];
        this.used = new boolean[pieces.length];
        this.attemptCount = 0;
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
        for (int[][] shape : getAllTransformations(piece.getShapes().get(0))) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                attemptCount++; //hitung jumlah percobaan
                    if (canPlace(shape, r, c)) {
                        placePiece(shape, r, c, piece.getLabel());
                        used[pieceIndex] = true;
                        if (backtrack(pieceIndex + 1)) return true;
                        removePiece(shape, r, c);
                        used[pieceIndex] = false;
                    }
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
        int rCount = shape.length, cCount = shape[0].length;
        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
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
        int rCount = shape.length, cCount = shape[0].length;
        for (int r = 0; r < rCount; r++) {
            for (int c = 0; c < cCount; c++) {
                if (shape[r][c] == 1) {
                    board[startRow + r][startCol + c] = '.';
                }
            }
        }
    }
    
    //transformation
    private List<int[][]> getAllTransformations(int[][] shape) {
        List<int[][]> transformations = new ArrayList<>();
        List<int[][]> rotations = getRotations(shape);
        for (int[][] rot : rotations) {
            if (!contains(transformations, rot))
                transformations.add(rot);
            int[][] mirrored = mirror(rot);
            if (!contains(transformations, mirrored))
                transformations.add(mirrored);
        }
        return transformations;
    }

    private List<int[][]> getRotations(int[][] shape) {
        List<int[][]> rotations = new ArrayList<>();
        rotations.add(shape);
        int[][] rotated = shape;
        for (int i = 0; i < 3; i++) {
            rotated = rotate90(rotated);
            if (!contains(rotations, rotated))
                rotations.add(rotated);
        }
        return rotations;
    }

    private int[][] rotate90(int[][] shape) {
        int r = shape.length, c = shape[0].length;
        int[][] res = new int[c][r];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[j][r - 1 - i] = shape[i][j];
            }
        }
        return res;
    }

    private int[][] mirror(int[][] shape) {
        int r = shape.length, c = shape[0].length;
        int[][] res = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                res[i][c - 1 - j] = shape[i][j];
            }
        }
        return res;
    }

    private boolean contains(List<int[][]> list, int[][] candidate) {
        for (int[][] t : list) {
            if (areSame(t, candidate)) return true;
        }
        return false;
    }

    private boolean areSame(int[][] a, int[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) return false;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) return false;
            }
        }
        return true;
    }

    /**
     * Mencetak board puzzle ke konsol
     * I.S.: Board (solusi) telah terbentuk
     * F.S.: Board dicetak ke konsol
     */
    public void printBoard() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                char ch = board[r][c];
                String color = getColor(ch);
                System.out.print(color + ch + ANSI_RESET);
            }
            System.out.println();
        }
    }
    
    private String getColor(char ch) {
        switch (Character.toUpperCase(ch)) {
            case 'A': return ANSI_RED;
            case 'B': return ANSI_GREEN;
            case 'C': return ANSI_YELLOW;
            case 'D': return ANSI_BLUE;
            case 'E': return ANSI_PURPLE;
            case 'F': return ANSI_CYAN;
            case 'G': return ANSI_WHITE;
            case 'H': return ANSI_RED;
            case 'I': return ANSI_GREEN;
            case 'J': return ANSI_YELLOW;
            case 'K': return ANSI_BLUE;
            case 'L': return ANSI_PURPLE;
            case 'M': return ANSI_CYAN;
            case 'N': return ANSI_WHITE;
            case 'O': return ANSI_RED;
            case 'P': return ANSI_GREEN;
            case 'Q': return ANSI_YELLOW;
            case 'R': return ANSI_BLUE;
            case 'S': return ANSI_PURPLE;        
            case 'T': return ANSI_CYAN;
            case 'U': return ANSI_WHITE;    
            case 'V': return ANSI_RED;
            case 'W': return ANSI_GREEN;
            case 'X': return ANSI_YELLOW;
            case 'Y': return ANSI_BLUE;
            case 'Z': return ANSI_PURPLE;
            default:  return ANSI_RESET;
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