package prac2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ApacheComIO {
    public static void copy(File copyFrom, File copyTo) throws IOException {
        FileUtils.copyFile(copyFrom, copyTo);
    }

    public static void main(String[] args) {
        File copyFrom = new File("src//prac2//secondTask.txt");
        File copyTo = new File("src//prac2//secT3.txt");
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

        /*File copied in: 34
Bytes used: 3484976*/
    }
}
