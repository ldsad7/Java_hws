package hw1.ex1;

//import java.util.Arrays;

/**
 * Задан массив целых чисел. Необходимо переставить наименьшее из этих чисел в начало массива, а наибольшее - в конец.
 * Массивы в Java определяются с помощью указания их размера в квадратных скобках:
 * int[] v = new int[77];
 * Размер можно не указывать, но тогда нужно определить сами элементы в фигурных скобках:
 * int[] v = new int[] {6, 28, 1};
 * Обращаться к элементам можно по индексу:
 * System.out.println(v[0]); // выводим первый по счету элемент
 * v[1] = 16; // изменяем значение элемента с индексом 1
 * Размер массива:
 * v.length;
 */
public class Task {
    public static void main(String[] args) {
        int[] arr = castToIntArray(args);
        printArray(arr, false);
        convertArrayInPlace(arr);
        printArray(arr, true);
    }

    public static int[] castToIntArray(String[] arr) {
//        int[] newArr = new int[]{};
//        try {
//            newArr = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
//        } catch (NumberFormatException e) {
//            System.err.println("There is an argument that is not an integer.");
//            System.exit(1);
//        }
        int[] newArr = new int[arr.length];
        for (int i = 0; i < arr.length; ++i) {
            try {
                newArr[i] = Integer.parseInt(arr[i]);
            } catch (NumberFormatException e) {
                System.err.println("Argument \"" + arr[i] + "\" must be an integer.");
                System.exit(1);
            }
        }
        return newArr;
    }

    public static void printArray(int[] arr, boolean flag) {
        String prefix = flag ? "After" : "Before";
        System.out.print(prefix + ": ");
//        System.out.println(Arrays.toString(arr));
//        System.out.println('[' + String.join(", ", Arrays.stream(arr).mapToObj(String::valueOf).toArray(String[]::new)) + ']');
        System.out.print("[");
        String sep = "";
        for (int elem : arr) {
            System.out.print(sep + elem);
            sep = ", ";
        }
        System.out.println("]");
    }

    public static void convertArrayInPlace(int[] arr) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, minIndex, maxIndex;
        minIndex = maxIndex = -1;

        for (int i = 0; i < arr.length; ++i) {
            if (arr[i] < min) {
                min = arr[i];
                minIndex = i;
            }
            if (arr[i] >= max) {
                max = arr[i];
                maxIndex = i;
            }
        }
        replaceMinAndMaxElements(arr, minIndex, maxIndex);
    }

    public static void replaceMinAndMaxElements(int[] arr, int minIndex, int maxIndex) {
        if (maxIndex != -1) {
            if (minIndex > maxIndex) {
                swapTwoElementsInArray(arr, minIndex, maxIndex);

                // Is it possible to do it in a method without wrapping with an object?
                int tmp = minIndex;
                minIndex = maxIndex;
                maxIndex = tmp;
            }
            for (; maxIndex < arr.length - 1; ++maxIndex) {
                swapTwoElementsInArray(arr, maxIndex, maxIndex + 1);
            }
        }
        if (minIndex != -1) {
            for (; minIndex > 0; --minIndex) {
                swapTwoElementsInArray(arr, minIndex, minIndex - 1);
            }
        }
    }

    public static void swapTwoElementsInArray(int[] arr, int firstIndex, int secondIndex) {
        if (firstIndex != -1 && secondIndex != -1) {
            int tmp = arr[firstIndex];
            arr[firstIndex] = arr[secondIndex];
            arr[secondIndex] = tmp;
        }
    }
}
