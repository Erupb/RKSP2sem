package prac1;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class pr1_e3 {
    public static class File{
        String type;
        int size;

        File(String type, int size){
            this.size = size;
            this.type = type;
        }
    }

    public static class Generator{
        private ExecutorService executor = Executors.newSingleThreadExecutor();
        String[] types = new String[]{"XML", "JSON", "XLS"};

        public Future<Boolean> generate(ArrayDeque<File> arrayDeque, int amount){
            return executor.submit(() -> {
               for(int i=0; i < amount; i++){
                   int timer = (int) Math.round(100 + Math.random()*900);
                   int size = (int) Math.round(10 + Math.random()*90);
                   int type = new Random().nextInt(types.length);

                   try {
                       Thread.sleep(timer);
                   } catch (InterruptedException e){
                       e.printStackTrace();
                   }
                   while (arrayDeque.size()>=5){
                       try{
                           Thread.sleep(100);
                       } catch (InterruptedException e){
                           e.printStackTrace();
                       }
                   }
                   arrayDeque.add(new File(types[type], size));
                   System.out.println("File with type " + types[type] + " with size " + size + " is generated!");
               }
               System.out.println("Files generated");
               return true;
            });
        }
    }

    public static class Processor{
        int fileCounter =0;
        String type;
        private ExecutorService executor = Executors.newSingleThreadExecutor();

        Processor(String type){
            this.type = type;
        }

        private void process(File file){
            executor.submit(()->{
                try{
                    Thread.sleep(file.size * 7);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("File with type " + file.type + " with size " + file.size + " is processed!");
                fileCounter++;
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        int fileAmount = 20;
        ArrayDeque<File> arrayDeque = new ArrayDeque<>();
        Generator generator = new Generator();
        Processor processorXML = new Processor("XML");
        Processor processorJSON = new Processor("JSON");
        Processor processorXLS = new Processor("XLS");
        Processor[] processors = new Processor[]{processorXML, processorJSON, processorXLS};
        Future<Boolean> future = generator.generate(arrayDeque, fileAmount);
        executor.submit(() -> {
           while (!future.isDone() || !arrayDeque.isEmpty()){
               if(!arrayDeque.isEmpty()){
                   File file = arrayDeque.poll(); // Получаем первое значение в очереди и удаляем его из нее
                   for(Processor processor : processors){
                       if(Objects.equals(processor.type, file.type)){
                           processor.process(file);
                       }
                   }
               }
           }
        });
        generator.executor.shutdown();
        executor.shutdown();

        //Мы можем создать все файлы, но не успеть их обработать. И чтобы случайно не положить один из процессоров до конца его обработки, ждем пока число обработанных файлов совпадет с количеством сгенерированных
        while((processorJSON.fileCounter + processorXLS.fileCounter + processorXML.fileCounter)!=fileAmount){
            Thread.sleep(100);
        }
        for(Processor processor : processors){
            processor.executor.shutdown();
        }
        System.out.println("All files processed.");
    }
}
