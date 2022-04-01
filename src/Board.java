import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {
    public static final int size = 8;
    Figure[][] board = new Figure[size][size];
    private boolean white;

    Board() {
        white = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new NoFigure(i, j, false);
            }
        }

        for (int j = 0; j < size; j++) {
            board[1][j] = new Pawn(1, j, true);
            board[6][j] = new Pawn(6, j, false);
            board[0][j] = switch (j) {
                case 0, 7 -> new Rook(0, j, true);
                case 1, 6 -> new Knight(0, j, true);
                case 2, 5 -> new Bishop(0, j, true);
                case 3 -> new Queen(0, j, true);
                case 4 -> new King(0, j, true);
                default -> throw new IllegalStateException("Unexpected value: " + j);
            };
            board[7][j] = switch (j) {
                case 0, 7 -> new Rook(7, j, false);
                case 1, 6 -> new Knight(7, j, false);
                case 2, 5 -> new Bishop(7, j, false);
                case 3 -> new Queen(7, j, false);
                case 4 -> new King(7, j, false);
                default -> throw new IllegalStateException("Unexpected value: " + j);
            };
        }
    }

    public void setPiece(int x, int y, Figure f) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IndexOutOfBoundsException("Bad pos for board: " + x + " " + y);
        }
        board[x][y] = f;
        f.setX(x);
        f.setY(y);
    }

    public Figure getPiece(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            throw new IndexOutOfBoundsException("Bad pos for board: " + x + " " + y);
        }
        return board[x][y];
    }

    private boolean isBadCoords(int x, int y) {
        return 0 > x || x >= size || 0 > y || y >= size;
    }

    private boolean isClearDir(int x, int y, int x1, int y1) {
        int dx = Integer.signum(x1 - x), dy = Integer.signum(y1 - y);
        x += dx;
        y += dy;
        while (x != x1 || y != y1) {
            if (!(board[x][y] instanceof NoFigure)) {
                return false;
            }
            x += dx;
            y += dy;
        }
        return true;
    }

    private IntPair getKingPos(boolean white) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] instanceof King && board[i][j].isWhite() == white) {
                    return new IntPair(i, j);
                }
            }
        }
        throw new IllegalStateException("Can't find King with white=" + white);
    }

    private boolean underAttack(int x, int y) {
        for (int i1 = 0; i1 < size; i1++) {
            for (int j1 = 0; j1 < size; j1++) {
                if (!(board[i1][j1] instanceof NoFigure)) {
                    if (canTheorAttack(i1, j1, x, y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isCheck(boolean white) {
        IntPair kingPos = getKingPos(white);
        return underAttack(kingPos.getFirst(), kingPos.getSecond());
    }

    public boolean canTheorMove(int x, int y, int x1, int y1) {
        if (isBadCoords(x, y) || isBadCoords(x1, y1)) return false;
        if (!board[x][y].canMove(x1, y1)) return false;
        if (!(board[x1][y1] instanceof NoFigure)) return false;
        if (!(board[x][y] instanceof Knight)) {
            return isClearDir(x, y, x1, y1);
        }
        return true;
    }

    public boolean canTheorAttack(int x, int y, int x1, int y1) {
        if (isBadCoords(x, y) || isBadCoords(x1, y1)) return false;
        if (!board[x][y].canAttack(x1, y1)) return false;
        if (board[x1][y1] instanceof NoFigure) return false;
        if (board[x][y].isWhite() == board[x1][y1].isWhite()) return false;
        if (!(board[x][y] instanceof Knight)) {
            return isClearDir(x, y, x1, y1);
        }
        return true;
    }

    public boolean canMove(int x, int y, int x1, int y1, boolean attack) {
        if (!attack) {
            if (!canTheorMove(x, y, x1, y1)) return false;
        } else {
            if (!canTheorAttack(x, y, x1, y1)) return false;
        }
        Figure was = board[x][y];
        Figure was1 = board[x1][y1];
        setPiece(x, y, new NoFigure(x1, y1, false));
        setPiece(x1, y1, was);
        boolean res = !isCheck(board[x1][y1].isWhite());
        setPiece(x, y, was);
        setPiece(x1, y1, was1);
        return res;
    }

    public boolean canMoveOrAttack(int x, int y, int x1, int y1) {
        return canMove(x, y, x1, y1, true) || canMove(x, y, x1, y1, false);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(" |")
                .append(IntStream.range(0, 8)
                        .mapToObj(x -> String.valueOf((char) ('A' + x)))
                        .collect(Collectors.joining("|"))).append("| \n");
        res.append(new String(new char[20]).replace('\0', '-')).append("\n");
        for (int i = size - 1; i >= 0; i--) {
            res.append(i + 1).append("|");
            res.append(Arrays.stream(board[i])
                    .map(Object::toString)
                    .collect(Collectors.joining("|")));
            res.append("|").append(i + 1).append('\n');
            res.append(new String(new char[20]).replace('\0', '-')).append("\n");
        }
        res.append(" |").append(IntStream.range(0, 8)
                .mapToObj(x -> String.valueOf((char) ('A' + x)))
                .collect(Collectors.joining("|"))).append("| \n");
        return res.toString();
    }

    public boolean checkLose(boolean color) {
        IntPair kingPos = getKingPos(color);
        if (!underAttack(kingPos.getFirst(), kingPos.getSecond())) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int i1 = 0; i1 < size; i1++) {
                    for (int j1 = 0; j1 < size; j1++) {
                        if (board[i][j] instanceof NoFigure) continue;
                        if (board[i][j].isWhite() != color) continue;
                        if (canMoveOrAttack(i, j, i1, j1)) return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkPat(boolean color) {
        IntPair kingPos = getKingPos(color);
        if (underAttack(kingPos.getFirst(), kingPos.getSecond())) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                for (int i1 = 0; i1 < size; i1++) {
                    for (int j1 = 0; j1 < size; j1++) {
                        if (i == i1 && j == j1) continue;
                        if (board[i][j] instanceof NoFigure) continue;
                        if (board[i][j].isWhite() != color) continue;
                        if (canMoveOrAttack(i, j, i1, j1)) return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean makeMove(int x, int y, int x1, int y1) {
        if (isBadCoords(x, y)) return false;
        if (getPiece(x, y) instanceof NoFigure) return false;
        if (getPiece(x, y).isWhite() != white) return false;
        if (canMoveOrAttack(x, y, x1, y1)) {
            setPiece(x1, y1, board[x][y]);
            setPiece(x, y, new NoFigure(x, y, false));
            white = !white;
            return true;
        }
        return false;
    }

    public void promote(int x, int y, Figure figure) {
        setPiece(x, y, figure);
    }

    public boolean isWhite() {
        return white;
    }
}
