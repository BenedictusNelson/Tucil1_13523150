// import javax.swing.JFileChooser;
// import java.io.File;
// import java.io.FileNotFoundException;
import java.util.Scanner;
// import java.util.List;
// import java.util.ArrayList;
//import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("masukkan N, M, P: ");
        int N = sc.nextInt();
        int M = sc.nextInt();  
        int P = sc.nextInt();
        sc.nextLine();
        System.out.println("masukkan board: ");
        // String mode = sc.nextLine().trim();

        PuzzlePiece[] pieces = new PuzzlePiece[P];
        for (int i = 0; i < P; i++) {
            // String data = sc.nextLine();
            pieces[i] = new PuzzlePiece((char)('A' + i));
        }
        PuzzleSolver solver = new PuzzleSolver(N, M, pieces);
        if (solver.solve())
            solver.printBoard();
        else
            System.out.println("No solution found!");
        
        sc.close();
    }
}