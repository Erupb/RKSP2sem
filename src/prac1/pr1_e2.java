package prac1;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class pr1_e2 {
    public static Future<Integer> square(Integer inputNum, ExecutorService executor){
        return executor.submit(() -> {
            int timer = (int) Math.round(1 + Math.random()*4);
            System.out.println("Please wait " + timer + " seconds");
            Thread.sleep(timer * 1000);
            return inputNum*inputNum;
        });
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Scanner sc = new Scanner(System.in);

        while (true){
            int inputNum = sc.nextInt();
            Future<Integer> future = square(inputNum, executor);
            while(!future.isDone()){
                Thread.sleep(1000);
            }
            Integer res = future.get();
            System.out.println(res);

            if(res==0){
                break;
            }
        }
        executor.shutdown();
    }
}
