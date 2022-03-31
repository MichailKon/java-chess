import static java.lang.Math.abs;

public class Pawn extends Figure {
    public Pawn(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        if (getY() != y1) return false;

        if (isWhite()) {
            return x1 == getX() + 1 || (x1 == getX() + 2 && getX() == 1);
        } else {
            return x1 == getX() - 1 || (x1 == getX() - 2 && getX() == 6);
        }
    }

    @Override
    public boolean canAttack(int x1, int y1) {
        if (abs(getY() - y1) != 1) {
            return false;
        }
        return x1 == getX() + (isWhite() ? 1 : -1);
    }

    @Override
    public String toString() {
        return isWhite() ? "P" : "p";
    }
}
