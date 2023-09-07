package prac2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class task1 {
    public static void readAsLines(Path path) {
        try{
            Files.lines(path).forEach(System.out::println);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Path path = Paths.get("src//prac2//text.txt");
        readAsLines(path);
    }

}
