package hw3.ex1;

public class Square {
    private static final String COLUMNS = "abcdefgh";
    private int x, y;

    public Square(int x, int y) {
        this.setCoords(x, y);
    }

    public Square(String coord) {
        if (coord.length() != 2)
            throw new IllegalArgumentException(String.format("The coordinate (\"%s\") should be 2 chars long", coord));
        this.setCoords(coord.charAt(0) - 'a', coord.charAt(1) - '1');
    }

    public Square(int diag) {
        this.setCoords(diag, diag);
    }

    public Square() {
        this.setCoords(0, 0);
    }

    public void setCoords(int x, int y) {
        this.setXCoord(x);
        this.setYCoord(y);
    }

    public int[] getCoords() {
        return new int[]{this.getXCoord(), this.getYCoord()};
    }

    public int getXCoord() {
        return this.x;
    }

    public void setXCoord(int x) {
        checkCoord(x, 'x');
        this.x = x;
    }

    private void checkCoord(int value, char coordName) {
        if (value < 0 || value >= 8)
            throw new IllegalArgumentException(String.format("%c (%d) should be >= 0 and <= 7", coordName, value));
    }

    public int getYCoord() {
        return this.y;
    }

    public void setYCoord(int y) {
        checkCoord(y, 'y');
        this.y = y;
    }

    private int countAbsXDistance(Square square) {
        return Math.abs(square.getXCoord() - this.getXCoord());
    }

    private int countAbsYDistance(Square square) {
        return Math.abs(square.getYCoord() - this.getYCoord());
    }

    public boolean checkIfExistsKnightsMove(Square square) {
        int dx = this.countAbsXDistance(square);
        int dy = this.countAbsYDistance(square);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public String toString() {
        return String.format("%c%d", COLUMNS.charAt(this.getXCoord()), this.getYCoord() + 1);
    }
}
