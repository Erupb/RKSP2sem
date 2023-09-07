package prac2;

import java.io.*;

public class FileIOStream {
    public static void copy(File src, File destination) throws IOException{
        try(InputStream is = new FileInputStream(src);
        OutputStream out = new FileOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int len;
            while((len = is.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
        }
    }

    public static void main(String[] args) {
        File copyFrom = new File("src//prac2//secondTask.txt");
        File copyTo = new File("src//prac2//secT1.txt");

        try{
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
    /*File copied in: 555
Bytes used: 3023568*/
}
