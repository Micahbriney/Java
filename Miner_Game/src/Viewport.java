public final class Viewport
{
    private int col;
    private int numCols;
    private int numRows;
    private int row;

    public Viewport(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
    }

    public boolean contains(Point p) {
        return  p.getY() >= this.row &&
                p.getY() <  this.row + this.numRows &&
                p.getX() >= this.col &&
                p.getX() <  this.col + this.numCols;
    }

    public int     getCol() {
        return col;
    }

    public int     getRow() {
        return row;
    }

    public int     getNumCols() {
        return numCols;
    }

    public int     getNumRows() {
        return numRows;
    }

    public void    shift(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Point   viewportToWorld(int col, int row) {
        return new Point(col + this.col, row + this.row);
    }

    public Point   worldToViewport(int col, int row) {
        return new Point(col - this.col, row - this.row);
    }

}
