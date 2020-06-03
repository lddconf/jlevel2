import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    /**
     * Init array with ones
     * @param array array to init
     */
    public static void initArray( float[] array ) {
        Arrays.fill(array, 1.f);
    }

    /**
     * Make computation routine
     * @param array IO data array
     * @param offsetOfI computation init value parameter
     */
    public static void compute(float[] array, int offsetOfI) {
        for (int i = 0; i < array.length; i++, offsetOfI++) {
            array[i] = (float)(array[i]*Math.sin(0.2 + offsetOfI/5)*Math.cos(0.2 + offsetOfI/5)*Math.cos(0.4f + offsetOfI/2));
        }
    }

    /**
     * Serial computation routine
     * Print calculation time to Console
     * @return computation result
     */
    public static float[] serialComputationAndPrint() {
        float[] array = new float[size];
        initArray(array);

        long start = System.currentTimeMillis();
        compute(array, 0);
        System.out.printf( "Execution time[serial]: %d milliseconds\n", ( System.currentTimeMillis() - start ));
        return array;
    }

    /**
     * Parallel computation routine. Use 2 thread chunk parallelization.
     * Print calculation time to Console
     * @return computation result
     */
    public static float[] parallelComputationAndPrint() {
        float[] array = new float[size];
        float[] array1 = new float[h];
        float[] array2 = new float[h];
        initArray(array);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                compute(array1, 0);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                compute(array2, h);
            }
        });

        long start = System.currentTimeMillis();
        System.arraycopy(array, 0, array1, 0, h);
        System.arraycopy(array, h, array2, 0, h);
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.arraycopy(array1, 0, array, 0, h);
            System.arraycopy(array2, 0, array, h, h);
        }
        System.out.printf( "Execution time[parallel]: %d milliseconds\n", ( System.currentTimeMillis() - start ));
        return array;
    }


    /**
     * Main function
     * @param args program arguments
     */
    public static void main(String[] args) {
        //Test serial version
        float[] serialResult = serialComputationAndPrint();

        //Test parallel version
        float[] parallelResult = parallelComputationAndPrint();

        //Compare results
        if ( Arrays.equals(serialResult, parallelResult) ) {
            System.out.println("Same arrays. Test passed!");
        } else {
            System.out.println("Different arrays. Test failed!");
        }
    }
}
