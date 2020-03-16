package hw4.ex2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.PriorityQueue;

import static hw4.ex1.Task.checkFile;

/**
 * Задача 4*
 * Вы разрабатываете систему в микросервисной архитектуре, в которой
 * сервисы в процессе работы записывают логи в отдельные файлы.
 * Каждая строка файла лога имеет вид: time message\n
 * где time - время в формате POSIX time (количество миллисекунд с 1 января 1970 года),
 * message - произвольный текст. Строки в логах всегда отсортированы по времени в порядке возрастания.
 * Сервисов в системе много, и вы обнаружили, что при анализе ошибок тратите слишком много времени,
 * так как приходится восстанавливать общий порядок действий в системе по большому количеству разных логов.
 * Для оптимизации работы вы решили реализовать утилиту «слияния» нескольких логов в один общий.
 * Утилита в качестве аргументов командной строки принимает пути к файлам логов (произвольное количество)
 * и записывает в стандартный поток вывода результат:
 * общую последовательность строк лога в порядке возрастания времени.
 * Реализуйте эту утилиту, имея в виду, что логи бывают большими
 * и не всегда могут поместиться в оперативной памяти целиком.
 */
public class Task {
    public static void main(String[] args) throws IOException {
        int length = args.length;
        if (length == 0)
            throw new IllegalArgumentException("No files given");
        BufferedReader[] readers = new BufferedReader[length];
        PriorityQueue<Triple> heap = new PriorityQueue<>(length);
        for (int i = 0; i < length; ++i) {
            readers[i] = Files.newBufferedReader(checkFile(args[i]));
            addElementToHeapQueue(readers[i], heap, i);
        }
        processFiles(readers, heap);
        for (int i = 0; i < length; ++i)
            readers[i].close();
    }

    public static void processFiles(BufferedReader[] readers, PriorityQueue<Triple> heap) throws IOException {
        int index;
        while (!heap.isEmpty()) {
            Triple triple = heap.poll();
            System.out.println(triple.getString());
            index = triple.getIndex();
            addElementToHeapQueue(readers[index], heap, index);
        }
    }

    public static void addElementToHeapQueue(BufferedReader reader, PriorityQueue<Triple> heap, int index) throws IOException {
        String tmp;
        if ((tmp = reader.readLine()) != null)
            heap.add(new Triple(checkString(tmp), tmp, index));
    }

    public static long checkString(String str) {
        try {
            return Long.parseLong(str.split("\\s+")[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException("String \"" + str + "\" in a file doesn't meet the requirements" + " or the number of milliseconds is too big");
        }
    }

    public static class Triple implements Comparable<Triple> {
        private final long time;
        private final String string;
        private final int index;

        public Triple(long time, String string, int index) {
            this.time = time;
            this.string = string;
            this.index = index;
        }

        public long getTime() {
            return this.time;
        }

        public String getString() {
            return this.string;
        }

        public int getIndex() {
            return this.index;
        }

        @Override
        public String toString() {
            return "Triple{time=" + time + ", string='" + string + '}';
        }

        @Override
        public int compareTo(Triple pair) {
            if (this.time > pair.getTime())
                return 1;
            else if (this.time < pair.getTime())
                return -1;
            return this.string.compareTo(pair.getString());
        }
    }
}
