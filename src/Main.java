import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        Scanner sc = new Scanner(System.in);

        while (!board.checkLose(false) && !board.checkLose(true)) {
            System.out.println(board);
            System.out.println("Make your move");
            List<String> line = Arrays.stream(sc.nextLine().split(" ")).toList();
            if (line.size() != 2 || line.get(0).length() != 2 || line.get(1).length() != 2) {
                System.out.println("Try again");
                continue;
            }
            String from = line.get(0);
            String to = line.get(1);
            int y1 = from.charAt(0) - 'A';
            int x1 = from.charAt(1) - '0' - 1;
            int y2 = to.charAt(0) - 'A';
            int x2 = to.charAt(1) - '0' - 1;
            if (board.makeMove(x1, y1, x2, y2)) {
                System.out.println("Good move");
            } else {
                System.out.println("Can't do this move");
            }
        }

        System.out.println(board);

        if (board.checkLose(false)) {
            System.out.println("White won\n");
        } else {
            System.out.println("Black won\n");
        }
    }
}
