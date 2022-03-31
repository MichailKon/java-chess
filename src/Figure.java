public abstract class Figure {
    private int x, y;
    private final boolean white;

    public Figure createFigure(char c, int x, int y) {
        final boolean color = c != Character.toLowerCase(c);
        return switch (c) {
            case 'b', 'B' -> new Bishop(x, y, color);
            case 'k', 'K' -> new King(x, y, color);
            case 'n', 'N' -> new Knight(x, y, color);
            case 'p', 'P' -> new Pawn(x, y, color);
            case 'q', 'Q' -> new Queen(x, y, color);
            case 'r', 'R' -> new Rook(x, y, color);
            default -> throw new IllegalStateException("Unexpected value: " + c);
        };
    }

    public Figure(int x, int y, boolean white) {
        this.x = x;
        this.y = y;
        this.white = white;
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
