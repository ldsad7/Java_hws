package hw3.ex1;

import java.util.Arrays;
import java.util.Random;

public class Task {
    public static void main(String[] args) {
        Square[] squares = new Square[]{
                new Square(1, 2),
                new Square(3),
                new Square(),
                new Square("a1"),
                new Square("h8"),
//                new Square("i8"),
//                new Square("a0"),
//                new Square("Z1"),
//                new Square("h9"),
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

        String[] knightMoves = new String[]{
                "c3", "b1", "a3", "b1", "c3", "d5", "e7", "d5", "b4", // "a1"
        };

        try {
            implementsKnightsMoves(knightMoves);
            System.out.println("OK");
        } catch (IllegalMoveException e) {
            System.err.print(e.getMessage());
        }
    }

    public static Square[] convertToSquares(String[] coords) {
        Square[] squares = new Square[coords.length];
        for (int i = 0; i < coords.length; ++i) {
            squares[i] = new Square(coords[i]);
        }
        return squares;
    }

    public static void implementsKnightsMoves(String[] coords) throws IllegalMoveException {
        Square[] squares = convertToSquares(coords);
        for (int i = 0; i < squares.length - 1; ++i) {
            if (!squares[i].checkIfExistsKnightsMove(squares[i + 1])) {
                throw new IllegalMoveException(squares[i], squares[i + 1]);
            }
        }
    }
}