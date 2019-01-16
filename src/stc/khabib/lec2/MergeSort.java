package stc.khabib.lec2;

import java.util.Arrays;

public class MergeSort extends Sorting {
    public static void sort(Integer[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    private static void mergeSort(Integer[] arr, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            mergeSort(arr, lo, mid);
            mergeSort(arr, mid + 1, hi);
            merge(arr, lo, mid, hi);
        }
    }

    private static void merge(Integer[] arr, int lo, int mid, int hi) {
        int leftSize = mid - lo + 1;
        int rightSize = hi - mid;

        Integer[] left = new Integer[leftSize];
        Integer[] right = new Integer[rightSize];

        for (int i = 0; i < leftSize; i++) {
            left[i] = arr[lo + i];
        }

        for (int i = 0; i < rightSize; i++) {
            right[i] = arr[mid + 1 + i];
        }


        int i = 0, j = 0, k = lo;
        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }

            k++;
        }

        while (i < leftSize) {
            arr[k] = left[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            arr[k] = right[j];
            j++;
            k++;
        }

    }


    public static void main(String[] args) {
        Integer[] arr = Sorting.generateRandomArray(10);
        System.out.println("Unsorted: " + Arrays.toString(arr));
        MergeSort.sort(arr);
        System.out.println("Is Sorted: " + isSorted(arr));
        System.out.println("Sorted: " + Arrays.toString(arr));
    }
}
