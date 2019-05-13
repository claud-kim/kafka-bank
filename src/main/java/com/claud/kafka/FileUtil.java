package com.claud.kafka;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

final public class FileUtil {

    private FileUtil() {
    }

    public static List<String> readFile(final String filePath) {

        List<String> lines = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }

        return lines;
    }

    public static void writeFile(final String filePath, final String content) {
        try (FileWriter writer = new FileWriter(filePath);
             BufferedWriter bw = new BufferedWriter(writer)) {

            bw.write(content);

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
