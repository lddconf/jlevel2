/**
 * MyArrayOutOfIndex Exception for invalid array size error handling
 */
public class MyArraySizeException extends RuntimeException {

    private int row;
    private int column;
    /**
     * Default constructor
     * @param rowsDim       - provided minimum value of row dim
     * @param columnDim     - provided minimum value of column dim. If less 0 - not handled
     */
    public MyArraySizeException( int rowsDim, int columnDim ) {
        super("2D Array size not 4x4. Provided " +
                 "rows " + rowsDim + ( columnDim >= 0 ? " columns " + columnDim : ""));
        this.row = rowsDim;
        this.column = columnDim;
    }

    /**
     * Get actual row dim
     * @return actual row dim
     */
    public int getRow() {
        return row;
    }

    /**
     * Get actual column dim
     * @return actual column dim
     */
    public int getColumn() {
        return column;
    }
}
