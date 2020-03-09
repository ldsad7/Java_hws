package hw3.ex1;

import java.util.Arrays;
import java.util.Random;

public class Task {
    public static void main(String[] args) {
        Square[] squares = new Square[]{
                new Square(1, 2),
                new Square(3),
                new Square(),
//                new Square(-1, 2),
//                new Square(4, -1),
//                new Square(-2, -1),
//                new Square(5, 8),
//                new Square(8, 5),
//                new Square(9, 10)
        };
        Random r = new Random();
        int SIZE = 8;

        for (Square square : squares) {
            System.out.println(String.format(
                    "Before: %s, [x, y] (%s), x (%d), y (%d)", square,
                    Arrays.toString(square.getCoords()), square.getXCoord(), square.getYCoord())
            );
            int randomX = r.nextInt(SIZE);
            int randomY = r.nextInt(SIZE);
            square.setCoords(randomX, randomY);
            System.out.println(String.format(
                    "After: %s, [x, y] (%s), x (%d), y (%d)", square,
                    Arrays.toString(square.getCoords()), square.getXCoord(), square.getYCoord())
            );
        }

        Square[] knightMoves = new Square[]{
                new Square(2),
                new Square(1, 0),
                new Square(0, 2),
                new Square(2, 3),
                new Square(4),
                new Square(6, 5),
                new Square(4, 6),
                new Square(6, 7),
//                new Square(1)
        };

        try {
            implementsKnightsMoves(knightMoves);
        } catch (IllegalMoveException e) {
            System.err.print(e.getMessage());
        }
    }

    public static void implementsKnightsMoves(Square[] squares) throws IllegalMoveException {
        for (int i = 0; i < squares.length - 1; ++i) {
            if (!squares[i].checkIfExistsKnightsMove(squares[i + 1])) {
                throw new IllegalMoveException(squares[i], squares[i + 1]);
            }
        }
        System.out.println("OK");
    }
}