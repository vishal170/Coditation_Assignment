package com.cellState.LiveOrDead;

public class CellState {
    // The value representing a dead cell
    public final static int DeadCell    = 0x00;

    // The value representing a live cell
    public final static int LiveCell    = 0x01;

    /**
     * Start the Cell State Live or Dead example
     *
     * @param args      arguments, not used for this example
     */
    public static void main(String[] args) {

        // test the cell life or dead implementation
        CellState cellState = new CellState();
        cellState.test(5);
    }
    /**
     * Test the live or dead implementation, change the array
     * values to test each condition in the cell life.
     *
     * @param nrIterations      the number of times the board should be played
     */
    private void test(int nrIterations) {

        // example to start playing board with life and dead cells
        int[][] cellBoard = {{DeadCell, DeadCell, DeadCell, DeadCell, DeadCell},
                {DeadCell, DeadCell, DeadCell, LiveCell, DeadCell},
                {DeadCell, DeadCell, LiveCell, LiveCell, DeadCell},
                {DeadCell, DeadCell, DeadCell, LiveCell, DeadCell},
                {DeadCell, DeadCell, DeadCell, DeadCell, DeadCell}};

        System.out.println("Cell generation:");
        printBoard(cellBoard);

        for (int i = 0 ; i < nrIterations ; i++) {
            System.out.println();
            cellBoard = getNextBoard(cellBoard);
            printBoard(cellBoard);
        }
    }
    /**
     * Output of one cellBoard field using System.out
     *
     * @param board The cellBoard to be printed using System.out
     */
    private void printBoard(int[][] board) {

        for (int i = 0, e = board.length ; i < e ; i++) {

            for (int j = 0, f = board[i].length ; j < f ; j++) {
                System.out.print(Integer.toString(board[i][j]) + ",");
            }
            System.out.println();
        }
    }

    /**
     * get the next game board, this will calculate if cells live on or die or new
     * ones should be created by reproduction.
     *
     * @param board The current board field
     * @return A newly created game buffer
     */
    public int[][] getNextBoard(int[][] board) {

        // The board does not have any values so return the newly created
        // playing field.

        int nrRows = board.length;
        int nrCols = board[0].length;

        if (nrRows == 0 || nrCols == 0) {
            throw new IllegalArgumentException("Board must have a positive number of rows and/or columns");
        }

        // temporary board to store new values
        int[][] tempBoard = new int[nrRows][nrCols];

        for (int row = 0 ; row < nrRows ; row++) {

            for (int col = 0 ; col < nrCols ; col++) {
                tempBoard[row][col] = getNewCellState(board[row][col], getLiveNeighbours(row, col, board));
            }
        }
        return tempBoard;
    }
    /**
     * Get the number of the live neighbours given from the cell position
     *
     * @param cellRow       the column position of the cell
     * @param cellCol       the row position of the cell
     * @return the number of live neighbours given the position in the array
     */
    private int getLiveNeighbours(int cellRow, int cellCol, int[][] board) {

        int liveNeighbours = 0;
        int rowEnd = Math.min(board.length , cellRow + 2);
        int colEnd = Math.min(board[0].length, cellCol + 2);

        for (int row = Math.max(0, cellRow - 1) ; row < rowEnd ; row++) {

            for (int col = Math.max(0, cellCol - 1) ; col < colEnd ; col++) {

                // make sure to exclude the cell itself from calculation
                if ((row != cellRow || col != cellCol) && board[row][col] == LiveCell) {
                    liveNeighbours++;
                }
            }
        }
        return liveNeighbours;
    }


    /**
     * Get the new state of the cell given the current state and
     * the number of live neighbours of the cell.
     *
     * @param currentState          The current state of the cell, either DEAD or ALIVE
     * @param liveNeighbours    The number of live neighbours of the given cell.
     *
     * @return The new state of the cell given the number of live neighbours
     *         and the current state
     */
    private int getNewCellState(int currentState, int liveNeighbours) {

        int newState = currentState;

        switch (currentState) {
            case LiveCell:

                /*
                Any live cell with fewer than two live neighbors dies,
                as if by loneliness
                 */
                if (liveNeighbours < 2) {
                    newState = DeadCell;
                }
                /*
                Any live cell with more than three live neighbors
                dies, as if by overcrowding.
                 */
                if (liveNeighbours > 3) {
                    newState = DeadCell;
                }
                /*
                Any live cell with two or three live neighbors lives, unchanged
                to the next generation
                 */
                if (liveNeighbours == 2 || liveNeighbours == 3) {
                    newState = LiveCell;
                }
                break;

            case DeadCell:
                // Any dead cell with exactly three live neighbors comes to life
                if (liveNeighbours == 3) {
                    newState = LiveCell;
                }
                break;

            default:
                throw new IllegalArgumentException("State of cell must be either LIVE or DEAD");
        }
        return newState;
    }
}
