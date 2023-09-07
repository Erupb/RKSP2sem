package prac2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class FileChannel2 {
    public static void copy(File copyFrom, File copyTo) throws IOException{
        try(FileChannel fileChannelFrom = new FileInputStream(copyFrom).getChannel();
            FileChannel fileChannelTo = new FileOutputStream(copyTo).getChannel()){
            fileChannelTo.transferFrom(fileChannelFrom,0, fileChannelTo.size());
        }
    }

    public static void main(String[] args) {
        File copyFrom = new File("src//prac2//secondTask.txt");
        File copyTo = new File("src//prac2//secT2.txt");
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
    }
    /*File copied in: 1
Bytes used: 3023568*/
}
