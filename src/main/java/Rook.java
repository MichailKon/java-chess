public class Rook extends Figure {
    public Rook(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    public Rook(boolean b) {
        super(b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        return getX() == x1 || getY() == y1;
    }

    @Override
    public String toString() {
        return isWhite() ? "R" : "r";
    }
}
