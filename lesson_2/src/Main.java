public class Main {
    public static void main(String[] args) {

        //Correct array
        String[][] inputArray0 = {
            {"1",  "2",  "3",  "4"},
            {"5",  "6",  "7",  "8"},
            {"9",  "10", "11", "12"},
            {"13", "14", "15", "16"}
        };

        //Not 4x4 array
        String[][] inputArray1 = {
                {"1",  "2",  "3",  "4"}
        };

        //Not 4x4 array
        String[][] inputArray2 = {
                {"1",  "2",  "3",  "4"},
                {"5",  "6",  "7",  "8"},
                {"9",  "10", "11", "12"},
                {"13", "14"}
        };

        //Not integer array
        String[][] inputArray3 = {
                {"1",  "2",  "3",  "4"},
                {"5",  "6",  "7",  "8"},
                {"9",  "10", "11", "12"},
                {"13", "14", "15", "a"}
        };

        //Pack in 3D array for automatization test routines
        String[][][] testSequence = {
                inputArray0,
                inputArray1,
                inputArray2,
                inputArray3
        };

        //Test all cases
        for (int i = 0; i < testSequence.length; i++) {
            try {
                System.out.println("-------------------------------------------------------");
                System.out.println( "Sum is [for array " + i + "]: " + transformToInt(testSequence[i]) );
            } catch (MyArrayDataException e) {
                System.out.println("Error occurred [for array " + i + "]: " + e.toString());
            } catch (MyArraySizeException e ) {
                System.out.println("Error occurred [for array " + i + "]: " +e.toString());
            }
        }
    }

    /**
     * Sum all input array values
     * @param data - input string array with integer values
     * @return sum of array values
     * @throws MyArraySizeException invalid input array size provided. 4x4 array expected.
     * @throws MyArrayDataException not integer input array element provided.
     */
    private static int transformToInt(String[][] data) throws MyArraySizeException, MyArrayDataException {
        if ( data.length != 4 ) throw new MyArraySizeException(data.length, -1);
        int result = 0;
        int i = 0;
        int j = 0;

        try {
            for (i = 0; i < 4; i++) {
                if (data[i].length != 4) throw new MyArraySizeException(data.length, data[i].length);
                for (j = 0; j < 4; j++) {
                    result += Integer.valueOf(data[i][j]);
                }
            }
        } catch (NumberFormatException e) {
            throw new MyArrayDataException(i, j);
        }
        return result;
    }
}
