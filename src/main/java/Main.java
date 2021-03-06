import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner sc = new Scanner(System.in);

        while (!board.checkLose(false) && !board.checkLose(true) && !board.checkPat(board.isWhite())) {
            System.out.println(board);
            System.out.println("Now " + (board.isWhite() ? "white" : "black") + " move");
            List<String> line = Arrays.stream(sc.nextLine().split(" ")).map(x -> x.toLowerCase(Locale.ROOT)).toList();
            if (!(line.size() == 2 && line.get(0).length() == 2 && line.get(1).length() == 2) &&
                    !(line.size() == 1 && (line.get(0).equals("oo") || line.get(0).equals("ooo")))) {
                System.out.println("Try again");
                continue;
            }
            if (line.size() == 1) {
                boolean shortCastling = line.get(0).equals("oo");
                if (board.castling(shortCastling)) {
                    System.out.println("Good mode");
                } else {
                    System.out.println("Can't do this move");
                }
                continue;
            }
            String from = line.get(0).toLowerCase(Locale.ROOT);
            String to = line.get(1).toLowerCase(Locale.ROOT);
            int y1 = from.charAt(0) - 'a';
            int x1 = from.charAt(1) - '0' - 1;
            int y2 = to.charAt(0) - 'a';
            int x2 = to.charAt(1) - '0' - 1;
            if (board.makeMove(x1, y1, x2, y2)) {
                if (board.getPiece(x2, y2) instanceof Pawn && (x2 == 0 || x2 == Board.SIZE - 1)) {
                    while (true) {
                        System.out.print("Enter symbol to promote: ");
                        String res = sc.nextLine();
                        if (res.length() != 1) {
                            System.out.println("Enter only one symbol");
                            continue;
                        }
                        try {
                            Figure fig = Figure.createFigure(res.charAt(0), !board.isWhite());
                            board.promote(x2, y2, fig);
                            break;
                        } catch (IllegalStateException e) {
                            System.out.println("Don't know symbol " + res);
                        }
                    }
                }
                System.out.println("Good move");
            } else {
                System.out.println("Can't do this move");
            }
        }

        System.out.println(board);

        if (board.checkLose(false)) {
            System.out.println("White won\n");
        } else if (board.checkLose(true)) {
            System.out.println("Black won\n");
        } else if (board.checkPat(false) || board.checkPat(true)) {
            System.out.println("Pat\n");
        } else {
            throw new IllegalStateException("undefined game result");
        }
    }
}
