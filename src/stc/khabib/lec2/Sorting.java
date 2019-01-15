package stc.khabib.lec2;

import java.util.Arrays;
import java.util.Random;

public class Sorting {
    private final int CUTOFF = 20;

    public static void main(String[] args) {
        Integer[] arr = Sorting.generateRandomArray(100);
        System.out.println("Unsorted: " + Arrays.toString(arr));
        Sorting s = new Sorting();
        s.quickSort(arr);
        System.out.println("Is Sorted: " + s.isSorted(arr));
        System.out.println("Sorted: " + Arrays.toString(arr));
    }

    /**
     * Функция генерирует массив случайных чисел заданной длинны
     *
     * @param length Длина массива
     * @return Массив случайных целых чисел
     */
    private static Integer[] generateRandomArray(int length) {
        Integer[] arr = new Integer[length];
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(1000);
        }
        return arr;
    }

    /**
     * Соритирует массив используя алгоритм сортировки вставками
     * При малых размерах массива сортировка встваками работает быстрее чем быстрая сортировка
     * из-за дополнительных накладных расходов на рекурсивные вызовы.
     *
     * @param arr Массив для сортировки
     */
    private void insertionSort(Integer[] arr, int lo, int hi) {
//        System.out.println("insertion: lo: " + lo + ", hi: " + hi);
        for (int i = lo; i < hi; i++) {
            Integer current = arr[i];

            int j = i - 1;
            for (; j >= 0 && arr[j] > current; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = current;
        }
    }

    private int partition(Integer[] arr, int lo, int hi) {
        int pivot = arr[hi];
        int pivotIndex = lo;
        for (int i = lo; i < hi; i++) {
            if (arr[i] <= pivot) {
                swap(arr, i, pivotIndex);
                pivotIndex++;
            }
        }
        swap(arr, pivotIndex, hi);
        return pivotIndex;
    }

    private void quickSort(Integer[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(Integer[] arr, int lo, int hi) {
//        System.out.println("quicksort: lo: " + lo + ", hi: " + hi);
        if (hi - lo <= CUTOFF) {
            this.insertionSort(arr, lo, hi + 1);
            return;
        }
        if (lo < hi) {
            int j = this.partition(arr, lo, hi);
            quickSort(arr, lo, j - 1);
            quickSort(arr, j + 1, hi);
        }
    }

    private void swap(Integer[] arr, int i, int j) {
        Integer tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    /**
     * Проверяет отсортирован ли заданный массив
     *
     * @param arr Массив для проверки
     * @return отсортирован или нет
     */
    private boolean isSorted(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }
}

