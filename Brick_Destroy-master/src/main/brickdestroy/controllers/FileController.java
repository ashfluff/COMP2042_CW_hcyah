package main.brickdestroy.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * This class contains methods that saves all high scores into a file as well as retrieves it.
 */

public class FileController {

    /**
     * This method appends scores to a file named score.txt.
     * Each score will be appended in a new lines.
     * @param score The player's score after game over or after completing the game
     * @throws IOException
     */
    public static void appendToFile(int score) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(".\\scores.txt", true));
        writer.write(score + "\n");

        writer.close();
    }

    /**
     * This method reads the array of integers from the file scores.txt which are the player's scores after game over or
     * completing the game
     * @return An array of integers
     * @throws IOException
     */
    public static Integer[] readFromFile() throws IOException {
         Stream<String> stream = Files.lines(Paths.get(".\\scores.txt"));
         return stream.map(x -> Integer.valueOf(x)).sorted().toArray(Integer[]::new);
    }

}
