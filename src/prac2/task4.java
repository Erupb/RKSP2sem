package prac2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static prac2.task3.sum;

public class task4 {
    public static void readLines(Map<Path, List<String>> fileList, Path filePath){
        if(Files.exists(filePath)){
            try{
                List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
                fileList.put(filePath, lines);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void compareFiles(Map<Path, List<String>> fileList, Path filePath) {
        if(Files.exists(filePath)) {
            try {
                List<String> newLines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
                List<String> oldLines = fileList.get(filePath);
                for (String line : oldLines) {
                    if (!newLines.contains(line)) {
                        System.out.println(line + " was deleted!");
                    }
                }
                for (String line : newLines) {
                    if (!oldLines.contains(line)) {
                        System.out.println(line + " was added!");
                    }
                }
                fileList.put(filePath, newLines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Map<Path, List<String>> fileList = new HashMap<>();
        WatchService watchService = FileSystems.getDefault().newWatchService();

        Path path = Paths.get("src//prac2//taskFour");

        path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;

        while((key = watchService.take()) != null){
            for(WatchEvent<?> event : key.pollEvents()){
                System.out.println("Event: " + event.kind() + ", file affected: " + event.context());
                if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE){
                    Path filePath = Paths.get("src//prac2//taskFour" + event.context());
                    readLines(fileList, filePath);
                }
                if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY){
                    Path filePath = Paths.get("src//prac2//taskFour" + event.context());
                    compareFiles(fileList, filePath);
                }
                if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE){
                    Path filePath = Paths.get("src//prac2//taskFour" + event.context());
                    //System.out.println(sum(filePath));
                    fileList.remove(filePath);
                }
            }
            key.reset();
        }
    }


}
