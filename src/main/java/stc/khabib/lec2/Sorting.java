package stc.khabib.lec2;

import java.util.Random;

abstract class Sorting {
    /**
     * Функция генерирует массив случайных чисел заданной длинны
     *
     * @param length Длина массива
     * @return Массив случайных целых чисел
     */
    static Integer[] generateRandomArray(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("Нельзя создать массив отрицательной длины");
        }
        Integer[] arr = new Integer[length];
        Random rnd = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rnd.nextInt(1000);
        }
        return arr;
    }


    /**
     * Меняет местами элементы под номерами i и j в массиве.
     *
     * @param arr массив
     * @param i   номер первого элемента
     * @param j   номер второго элемента
     */
    static void swap(Integer[] arr, int i, int j) {
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
    static boolean isSorted(Integer[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false;
            }
        }
        return true;
    }
}

