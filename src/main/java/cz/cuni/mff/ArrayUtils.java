package cz.cuni.mff;

/**
 * Created by honza on 07/05/2017.
 */
public class ArrayUtils {

    public static int findElement(final int[] array, final int value) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }
}
