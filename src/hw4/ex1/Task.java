package hw4.ex1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;

/**
 * В текстовом файле слова могут быть разделены одним или несколькими
 * пробелами и символами перевода строки. Необходимо реализовать программу,
 * считающую количество слов в файле и выводящую результат на экран.
 * Путь к файлу задается первым аргументом командной строки (args[0]).
 */
public class Task {
    public static void main(String[] args) throws IllegalArgumentException, IOException {
        if (args.length != 1)
            throw new IllegalArgumentException("There should be exactly 1 argument");
        Path path = checkFile(args[0]);
        int count = countNumberOfWords(path);
        System.out.println("Number of words: " + count);
    }

    public static Path checkFile(String pathToFile) throws IllegalArgumentException {
        Path path = Paths.get(pathToFile);
        if (Files.notExists(path))
            throw new IllegalArgumentException("File \"" + path.getFileName() + "\" doesn't exist");
        if (!Files.isRegularFile(path, LinkOption.NOFOLLOW_LINKS))
            throw new IllegalArgumentException("\"" + path.getFileName() + "\" is not a file");
        if (!Files.isReadable(path))
            throw new IllegalArgumentException("File \"" + path.getFileName() + "\" is not readable");
        return path;
    }

    public static int countNumberOfWords(Path path) throws IOException {
        int count = 0;
        Pattern p = Pattern.compile("[^\\s]+");

        try (BufferedReader r = Files.newBufferedReader(path)) {
            String line;
            while ((line = r.readLine()) != null) {
                count += p.matcher(line).results().count();
            }
        }
        return count;
    }
}