package hw5.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.PriorityQueue;

import static hw4.ex1.Task.checkFile;

/**
 * В колл-центре крупной компании сохраняются логи всех телефонных звонков за день.
 * Каждая строка файла лога состоит из времени начала и времени окончания
 * одного телефонного разговора, разделенных запятой.
 * Время задается в формате POSIX time (количество миллисекунд с 1 января 1970 года).
 * Строки в файле отсортированы по времени начала разговора.
 * <p>
 * Необходимо посчитать пиковое количество одновременных разговоров и вывести его на экран.
 * (Максимальное количество разговоров, которые происходили в одно и то же время.)
 * <p>
 * Пример:
 * <p>
 * [in]
 * 1,12
 * 4,6
 * 7,12
 * 10,32
 * 15,30
 * <p>
 * [out]
 * 3
 */
public class Task {
    public static PriorityQueue<Pair> heap = new PriorityQueue<>(1);
    public static int max = 0;

    public static void main(String[] args) throws IOException {
        if (args.length != 1)
            throw new IllegalArgumentException("There should be exactly 1 filename as an argument");

        Pair pair;
        String str;

        try (BufferedReader reader = Files.newBufferedReader(checkFile(args[0]))) {
            while ((str = reader.readLine()) != null) {
                Result result = parseString(str);
                long lower = result.getLower(), upper = result.getUpper();

                // NB: I consider (9, 10) and (10, 11) as non-intersecting regions
                while ((pair = heap.peek()) != null && pair.getTime() <= lower)
                    updateMaxValue();

                heap.add(new Pair(upper, updatePriorityQueueValues(upper)));
            }
        }

        while (!heap.isEmpty())
            updateMaxValue();

        System.out.println(max);
    }

    public static void updateMaxValue() {
        int numOfCalls;

        if ((numOfCalls = Objects.requireNonNull(heap.poll()).getNumOfCalls()) > max)
            max = numOfCalls;
    }

    public static int updatePriorityQueueValues(long upper) {
        int numOfCalls = 1;
        for (Pair pair : heap) {
            if (upper < pair.getTime())
                numOfCalls++;
            else
                pair.addOneToNumOfCalls();
        }
        return numOfCalls;
    }

    public static Result boxResult(long lower, long upper) {
        return new Result(lower, upper);
    }

    public static Result parseString(String str) {
        String[] splittedString = str.split("\\s*,\\s*", -1);
        if (splittedString.length != 2)
            throw new IllegalArgumentException("String \"" + str + "\" in the file doesn't meet the requirements");
        long lower, upper;
        try {
            lower = Long.parseLong(splittedString[0]);
            upper = Long.parseLong(splittedString[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "The string \"" + splittedString[0] + "\" or the string \"" + splittedString[1]
                            + "\" is not a number" + " or the number of milliseconds is too big");
        }
        if (lower < 0 || upper < 0)
            throw new IllegalArgumentException(
                    "The lower value \"" + lower + "\" and the upper value \"" + upper + "\" should be non-negative");
        if (lower >= upper)
            throw new IllegalArgumentException(
                    "The lower value \"" + lower + "\" should be less than the upper value \"" + upper + "\"");
        return boxResult(lower, upper);
    }

    private static class Result {
        private final long lower;
        private final long upper;

        public Result(long lower, long upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public long getUpper() {
            return this.upper;
        }

        public long getLower() {
            return this.lower;
        }
    }

    private static class Pair implements Comparable<Pair> {
        private final long time;
        private int numOfCalls;

        public Pair(long time, int numOfCalls) {
            this.time = time;
            this.numOfCalls = numOfCalls;
        }

        public long getTime() {
            return this.time;
        }

        public int getNumOfCalls() {
            return this.numOfCalls;
        }

        public void addOneToNumOfCalls() {
            this.numOfCalls++;
        }

        @Override
        public String toString() {
            return "Pair{time=" + this.time + ", numOfCalls='" + this.numOfCalls + '}';
        }

        @Override
        public int compareTo(Pair pair) {
            if (this.time > pair.getTime())
                return 1;
            else if (this.time < pair.getTime())
                return -1;
            return 0;
        }
    }
}
