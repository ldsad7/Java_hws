package hw1.ex2;

import java.util.Locale;

/**
 * Задача 1*
 * Написать метод, форматирующий и выводящий на экран заданный размер в байтах в человекочитаемом виде.
 * Человекочитаемый вид:
 * {целая часть <= 1024}.{дробная часть макс. 1 знак} {единица измерения}
 * Например:
 * printBytes(23) -> "23.0 B"
 * printBytes(1024) -> "1.0 KB"
 * printBytes(53692044905543) -> "48.8 TB"
 * Для вывода только одного знака дробной части вещественного числа можно воспользоваться методом String.format:
 * String.format("%.1f", 1.23456)
 */
public class Task {
    // I accidentally read before this course the post about it: https://habr.com/ru/post/478878/
    // but will realize it by KISS and with known mistakes :)
    public static final int NUM_OF_SIZES = 7;
    public static final String[] sizeNames = new String[]{"", "K", "M", "G", "T", "P", "E"};
    public static float[] sizes = new float[NUM_OF_SIZES];

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("There is no arguments.");
            System.exit(1);
        }
        long numOfBytes = 0;
        try {
            numOfBytes = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.err.println("First argument must be a long.");
            System.exit(1);
        }
        printBytes(numOfBytes);
    }

    public static void printBytes(long numOfBytes) {
        String sign = "";
        if (numOfBytes < 0) {
            sign = "-";
            numOfBytes = Math.abs(numOfBytes);
        }
        long pow = 1;
        float base = (float) Math.pow(2, 10);
        boolean lowerThan2Power60 = false;
        for (int i = 0; i < NUM_OF_SIZES; ++i) {
            sizes[i] = pow;
            if (i > 0 && numOfBytes < sizes[i] && numOfBytes != Long.MIN_VALUE) {
                printToOut(numOfBytes, sign, i - 1);
                lowerThan2Power60 = true;
                break;
            }
            pow *= base;
        }
        if (!lowerThan2Power60) {
            if (numOfBytes == Long.MIN_VALUE) {
                printToOut(numOfBytes, "", NUM_OF_SIZES - 1);
            } else {
                printToOut(numOfBytes, sign, NUM_OF_SIZES - 1);
            }
        }
    }

    public static void printToOut(long numOfBytes, String sign, int index) {
        System.out.println(
                String.format(Locale.ROOT, "%s%.1f %sB\n", sign, (numOfBytes / sizes[index]), sizeNames[index])
        );
    }
}

