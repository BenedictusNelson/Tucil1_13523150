import javax.swing.JFileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal != JFileChooser.APPROVE_OPTION) {
                System.out.println("Tidak ada file yang dipilih");
                return;
            }

            File chosenFile = fileChooser.getSelectedFile();
            
            try (Scanner sc = new Scanner(chosenFile)) {
                int N = sc.nextInt();
                int M = sc.nextInt();  
                int P = sc.nextInt();
                sc.nextLine();
                
                String mode = sc.nextLine().trim();

                if (!mode.equalsIgnoreCase("DEFAULT")) {
                    System.out.println("Mode yang didukung hanya DEFAULT.");
                    return;
                }
    
                List<String> remainingLines = new ArrayList<>();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (!line.trim().isEmpty()) {
                        remainingLines.add(line.trim());
                    }
                }
          
                if (remainingLines.isEmpty()) {
                    throw new IllegalArgumentException("Tidak ada data puzzle piece setelah mode");
                }
    
                PuzzlePiece[] pieces = parseDefaultPieces(remainingLines, P);
                if (pieces.length < P) {
                    throw new IllegalArgumentException("Kurang data untuk puzzle piece" + pieces.length + ".");
                }
    
                PuzzleSolver solver = new PuzzleSolver(N, M, pieces);
                long startTime = System.currentTimeMillis();
                boolean solved = solver.solve();
                long endTime = System.currentTimeMillis();
                if (solved) {
                    System.out.println("Solusi:");
                    solver.printBoard();
                } else {
                    System.out.println("Tidak ada solusi.");
                }
                System.out.println("Waktu pencarian: " + (endTime - startTime) + " ms");
                System.out.println("Banyak kasus yang ditinjau: " + solver.getAttemptCount());
    
                try (Scanner in = new Scanner(System.in)) {
                    System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
                    String answer = in.nextLine().trim();
                    if (answer.equalsIgnoreCase("ya")) {
                        System.out.println("Solusi disimpan.");
                    } else {
                        System.out.println("Solusi tidak disimpan.");
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println("File tidak ditemukan: " + chosenFile.getAbsolutePath());
            }
        }
    
        private static PuzzlePiece[] parseDefaultPieces(List<String> lines, int P) {
            PuzzlePiece[] pieces = new PuzzlePiece[P];
            int count = 0;
            int index = 0;
            while (count < P && index < lines.size()) {
                String firstLine = lines.get(index);
                char label = firstLine.charAt(0);
                List<String> pieceLines = new ArrayList<>();
                pieceLines.add(firstLine);
                index++;

                while (index < lines.size() && lines.get(index).charAt(0) == label) {
                    pieceLines.add(lines.get(index));
                    index++;
                }

                int maxLen = 0;
                for (String s : pieceLines) {
                    if (s.length() > maxLen) {
                        maxLen = s.length();
                    }
                }
                int rows = pieceLines.size();
                int[][] shape = new int[rows][maxLen];
                for (int i = 0; i < rows; i++) {
                    String s = pieceLines.get(i);
                    while (s.length() < maxLen) {
                        s += " ";
                    }
                    for (int j = 0; j < maxLen; j++) {
                        shape[i][j] = (s.charAt(j) == label) ? 1 : 0;
                    }
                }
                PuzzlePiece piece = new PuzzlePiece(label);
                piece.addShape(shape);
                pieces[count++] = piece;
            }
            return pieces;
        }
    }
    