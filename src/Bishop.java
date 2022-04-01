public class Bishop extends Figure {
    public Bishop(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    public Bishop(boolean color) {
        super(color);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        return getX() - getY() == x1 - y1 || getX() + getY() == x1 + y1;
    }

    @Override
    public String toString() {
        return isWhite() ? "B" : "b";
    }
}
