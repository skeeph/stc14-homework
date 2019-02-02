package khabib.lec2;

import java.util.Arrays;

public class QuickSort extends Sorting {
    private static final int CUTOFF = 20;

    /**
     * Соритирует массив используя алгоритм сортировки вставками
     * При малых размерах массива сортировка встваками работает быстрее чем быстрая сортировка
     * из-за дополнительных накладных расходов на рекурсивные вызовы.
     *
     * @param arr Массив для сортировки
     */
    private static void insertionSort(Integer[] arr, int lo, int hi) {
        for (int i = lo; i < hi; i++) {
            Integer current = arr[i];

            int j = i - 1;
            for (; j >= 0 && arr[j] > current; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = current;
        }
    }

    /**
     * Выполняет рекурсивную быструю сортировку части массива.
     * Для массивов небольшой длины используется сортировка вставками
     *
     * @param arr Массив
     * @param lo  Номер первого элемента сортируемой части
     * @param hi  Номер последнего элемента сортируемой части
     */
    private static void quickSort(Integer[] arr, int lo, int hi) {
        if (hi - lo <= CUTOFF) {
            insertionSort(arr, lo, hi + 1);
            return;
        }
        if (lo < hi) {
            int j = partition(arr, lo, hi);
            quickSort(arr, lo, j - 1);
            quickSort(arr, j + 1, hi);
        }
    }

    /**
     * Публичный метод для выполнения быстрой сортировки
     *
     * @param arr Массив для сортировки
     */
    public static void sort(Integer[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    public static void main(String[] args) {
        Integer[] arr = generateRandomArray(100);
        System.out.println("Unsorted: " + Arrays.toString(arr));
        QuickSort.sort(arr);
        System.out.println("Is Sorted: " + isSorted(arr));
        System.out.println("Sorted: " + Arrays.toString(arr));
    }


    /**
     * Выбирает какой-то элемент из массива, и переставляет остальные элементы массива
     * таким образом, чтобы все элементы массива меньшие или равные находились слева от выбранного,
     * а все большие - справа. Возвращает индекс выбранного элемента в полученном массиве
     *
     * @param arr Массив
     * @param lo  индекс первого элемента в сортируемой части массива
     * @param hi  индекс последнего элемента в сортируемой части массива
     * @return индекс pivot'а в полученном массиве
     */
    private static int partition(Integer[] arr, int lo, int hi) {
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
}
