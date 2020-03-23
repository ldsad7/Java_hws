package hw5.ex1;

/**
 * Задана строка, состоящая из символов латинского алфавита (от 'a' до 'z').
 * Необходимо реализовать функцию, переставляющую гласные в этой строке так,
 * чтобы они следовали в обратном порядке.
 * Результат вывести на экран.
 * <p>
 * Например, для строки "palindrome" на экран должно быть выведено "pelondrima".
 */
public class Task {
    private static final String VOWELS = "aeiouy";
    public static char[] charArray;

    public static void main(String[] args) {
        if (args.length != 1)
            throw new IllegalArgumentException("There should be exactly 1 string");
        String str = args[0];

        charArray = str.toCharArray();
        rearrangeVowelsInCharArray();
        System.out.println(String.valueOf(charArray));
    }

    private static void rearrangeVowelsInCharArray() {
        int i = 0, j = charArray.length - 1;
        while (i < j) {
            while (i < j && VOWELS.indexOf(charArray[i]) == -1)
                ++i;
            while (i < j && VOWELS.indexOf(charArray[j]) == -1)
                --j;
            if (i < j)
                swapElementsInCharArray(i++, j--);
        }
    }

    private static void swapElementsInCharArray(int i, int j) {
        char tmp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = tmp;
    }
}
