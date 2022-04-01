public abstract class Figure {
    private int x, y;
    private final boolean white;
    private boolean moved = false;

    public static Figure createFigure(char c, boolean color) {
        return switch (c) {
            case 'b', 'B' -> new Bishop(color);
            case 'k', 'K' -> new King(color);
            case 'n', 'N' -> new Knight(color);
            case 'p', 'P' -> new Pawn(color);
            case 'q', 'Q' -> new Queen(color);
            case 'r', 'R' -> new Rook(color);
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    public Figure(int x, int y, boolean white) {
        this.x = x;
        this.y = y;
        this.white = white;
    }

    public Figure(boolean white) {
        this(0, 0, white);
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return white;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean canMove(int x1, int y1) {
        return false;
    }

    public boolean canAttack(int x1, int y1) {
        return canMove(x1, y1);
    }
}
