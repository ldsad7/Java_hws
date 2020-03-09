package hw3.ex1;

public class IllegalMoveException extends Exception {
    public IllegalMoveException(Square prev, Square next) {
        super(String.format("The Knight doesn’t walk like that: %s -> %s\n", prev, next));
    }
}
