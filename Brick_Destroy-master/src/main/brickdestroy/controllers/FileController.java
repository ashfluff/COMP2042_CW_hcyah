package main.brickdestroy.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileController {

    public static void appendToFile(int score) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\scores.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

    public static String[] readFromFile() throws IOException {
         Stream<String> stream = Files.lines(Paths.get(".\\scores.txt"));
         return stream.sorted().toArray(String[]::new);
         //to convert array to string
        //String.join("\n", stream.sorted().toArray(String[]::new));
    }

}
