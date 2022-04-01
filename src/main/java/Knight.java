import static java.lang.Math.abs;

public class Knight extends Figure {
    public Knight(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    public Knight(boolean b) {
        super(b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        int dx = abs(getX() - x1), dy = abs(getY() - y1);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public String toString() {
        return isWhite() ? "N" : "n";
    }
}
