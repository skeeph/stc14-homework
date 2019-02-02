package khabib.lec2;

import java.util.Arrays;

/**
 * Сортировка слиянием
 */
public class MergeSort extends Sorting {
    public static void sort(Integer[] arr) {
        mergeSort(arr, 0, arr.length - 1);
    }

    /**
     * Выполняет сортировку части массива слиянием
     *
     * @param arr массив
     * @param lo  левая граница сортируемой части
     * @param hi  правая граница сортируемой части
     */
    private static void mergeSort(Integer[] arr, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo + hi) / 2;
            mergeSort(arr, lo, mid);
            mergeSort(arr, mid + 1, hi);
            merge(arr, lo, mid, hi);
        }
    }

    /**
     * Операция слияния.
     * Массивы для слияния передаются как часть массива arr.
     * Левый массив имеет границы arr[lo:mid], правый arr[mid+1:hi]
     *
     * @param arr Массив
     * @param lo  Левая граница рассматриваемой части массива
     * @param mid Граница левого и правого массива
     * @param hi  Правая граница рассматриваемой части массива
     */
    private static void merge(Integer[] arr, int lo, int mid, int hi) {
        int leftSize = mid - lo + 1;
        int rightSize = hi - mid;

        Integer[] left = new Integer[leftSize];
        Integer[] right = new Integer[rightSize];

        System.arraycopy(arr, lo, left, 0, leftSize);

        for (int i = 0; i < rightSize; i++) {
            right[i] = arr[mid + 1 + i];
        }


        int i = 0, j = 0, k = lo;
        while (i < leftSize && j < rightSize) {
            arr[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }

        while (i < leftSize) arr[k++] = left[i++];
        while (j < rightSize) arr[k++] = right[j++];

    }


    public static void main(String[] args) {
        Integer[] arr = generateRandomArray(10);
        System.out.println("Unsorted: " + Arrays.toString(arr));
        MergeSort.sort(arr);
        System.out.println("Is Sorted: " + isSorted(arr));
        System.out.println("Sorted: " + Arrays.toString(arr));
    }
}
