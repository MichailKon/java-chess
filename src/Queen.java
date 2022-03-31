public class Queen extends Figure {
    public Queen(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        return getX() == x1 || getY() == y1 || getX() - getY() == x1 - y1 || getX() + getY() == x1 + y1;
    }

    @Override
    public String toString() {
        return isWhite() ? "Q" : "q";
    }
}
