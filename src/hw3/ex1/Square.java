package hw3.ex1;

public class Square {
    private int x;
    private int y;
    private String columns = "abcdefgh";
    private String coordNames = "xy";

    public Square(int x, int y) {
        this.setCoords(x, y);
    }

    public Square(String coord) {
        if (coord.length() != 2)
            throw new IllegalArgumentException(String.format("The coordinate (\"%s\") should be 2 chars long", coord));
        int column_index = this.columns.indexOf(coord.charAt(0));
        if (column_index == -1)
            throw new IllegalArgumentException(
                    String.format("The column letter (\"%c\") should be one of the following: \"%s\"", coord.charAt(0), this.columns)
            );
        int row_index = coord.charAt(1) - '0';
        if (row_index < 1 || row_index > 8)
            throw new IllegalArgumentException(String.format("The row coordinate (\"%c\") should be a digit >= 1 and <= 8", coord.charAt(1)));
        this.setCoords(column_index, row_index - 1);
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
        this.setCoord(x, true);
    }

    private void setCoord(int value, boolean first) {
        char coordName = first ? this.coordNames.charAt(0) : this.coordNames.charAt(1);
        if (value < 0 || value >= 8)
            throw new IllegalArgumentException(String.format("%c (%d) should be >= 0 and <= 7", coordName, value));

        if (first)
            this.x = value;
        else
            this.y = value;
    }

    public int getYCoord() {
        return this.y;
    }

    public void setYCoord(int y) {
        this.setCoord(y, false);
    }

    public int[] countAbsDistance(Square square) {
        return new int[]{this.countAbsXDistance(square), this.countAbsYDistance(square)};
    }

    private int countAbsXDistance(Square square) {
        return Math.abs(square.getXCoord() - this.getXCoord());
    }

    private int countAbsYDistance(Square square) {
        return Math.abs(square.getYCoord() - this.getYCoord());
    }

    public boolean checkIfExistsKnightsMove(Square square) {
        int[] distance = this.countAbsDistance(square);
        return (distance[0] == 2 && distance[1] == 1) || (distance[0] == 1 && distance[1] == 2);
    }

    @Override
    public String toString() {
        return String.format("%c%d", this.columns.charAt(this.getXCoord()), this.getYCoord() + 1);
    }
}
