package prac1;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class pr1_1 {
    public static int min(int[] arr) {
        int min = 10000000;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        Object lock = new Object();

        int[] arr = pr1_0.fillArr();
        AtomicInteger min = new AtomicInteger();

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        long startTime = System.nanoTime();
        Runnable task = () -> {
            synchronized (lock){
                min.set(min(arr));
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int a : arr){
            executor.submit(task);
        }

        executor.shutdown();

        while (true){
            if(executor.isTerminated()){
                break;
            }
        }
        System.out.println(min);
        long used_time = System.nanoTime() - startTime;
        System.out.println("Time used for algorithm: " + used_time / 1000000);
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Memory used: " + usedBytes);

        /*
            13
            Time used for algorithm: 2461
            Memory used: 9775872
         */
    }
}
