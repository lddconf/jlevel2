import java.security.InvalidParameterException;

/**
 * MyArrayDataException for not integer value error handling
 */
public class MyArrayDataException extends RuntimeException {
    private int x;
    private int y;

    /**
     *
     * @param x - row index of invalid value
     * @param y - column index of invalid value
     */
    public MyArrayDataException(int x, int y) {
        super("Invalid value at position: [" + x + " , " + y + "]");
    }

    /**
     * Get row index of invalid value
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Get column index of invalid value
     * @return y
     */
    public int getY() {
        return y;
    }
}
