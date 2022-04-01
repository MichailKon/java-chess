public class NoFigure extends Figure {
    public NoFigure(int i1, int j1, boolean b) {
        super(i1, j1, b);
    }

    public NoFigure(boolean b) {
        super(b);
    }

    @Override
    public boolean canMove(int x1, int y1) {
        return false;
    }

    @Override
    public String toString() {
        return ".";
    }
}
