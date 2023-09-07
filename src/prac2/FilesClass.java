package prac2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class FilesClass {
    public static void copy(File copyFrom, File copyTo) throws IOException {
        Files.copy(copyFrom.toPath(), copyTo.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public static void main(String[] args) {
        File copyFrom = new File("src//prac2//secondTask.txt");
        File copyTo = new File("src//prac2//secT4.txt");
        try {
            long startTime = System.nanoTime();
            copy(copyFrom, copyTo);
            long elapsedTime = System.nanoTime() - startTime;
            System.out.print("File copied");
            System.out.println(" in: " + elapsedTime / 1000000);
            long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
            System.out.println("Bytes used: " + usedBytes);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        /*File copied in: 35
Bytes used: 3023568*/
    }
}
