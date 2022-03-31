import static java.lang.Math.abs;

public class King extends Figure {
    public King(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        return abs(x1 - getX()) <= 1 && abs(y1 - getY()) <= 1;
    }

    @Override
    public String toString() {
        return isWhite() ? "K" : "k";
    }
}
