package stc.khabib.lec2;

import java.util.Arrays;

/**
 * Класс реализующий Гномью сортировку.
 * https://dickgrune.com/Programs/gnomesort.html
 */
public class GnomeSort extends Sorting {
    private static void sort(Integer[] arr) {
        int i = 0;
        while (i < arr.length) {
            if (i == 0 || arr[i - 1] <= arr[i]) {
                i++;
            } else {
                swap(arr, i, i - 1);
                i--;
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = Sorting.generateRandomArray(20);
        System.out.println("Unsorted: " + Arrays.toString(arr));
        GnomeSort.sort(arr);
        System.out.println("Is Sorted: " + isSorted(arr));
        System.out.println("Sorted: " + Arrays.toString(arr));
    }
}
