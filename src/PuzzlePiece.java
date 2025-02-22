import java.util.ArrayList;
import java.util.List;

public class PuzzlePiece {
    private char label;
    private List<int[][]> shapes;

    public PuzzlePiece(char label) {
        this.label = label;
        this.shapes = new ArrayList<>();
    }

    public char getLabel() {
        return label;
    }

    public List<int[][]> getShapes() {
        return shapes;
    }

    public void addShape(int[][] shape) {
        this.shapes.add(shape);
    }
}
