package prac1;


import java.util.Random;

public class pr1_0 {
    public static int[] fillArr(){
        //int[] arr = new int[10000];
        Random random = new Random();

        int[] arr = random.ints(10000, 10,100000).toArray();
        /*for (int i=0; i<10000; i++){
            arr[i] = (int) Math.round(Math.random());
        }*/
        return arr;
    }

    public static int min(int[] arr) throws InterruptedException{
        int min = 1000000;
        for(int i=0; i<10000; i++){
            if(arr[i]<min){
                min = arr[i];
            }
            Thread.sleep(1);
        }

        return min;
    }

    public static void main(String[] args) throws InterruptedException {
        int[] arr = fillArr();
        long start_time = System.nanoTime();
        System.out.println(min(arr));
        long used_time = System.nanoTime() - start_time;
        System.out.println("Time used for algorithm: " + used_time / 1000000);
        long usedBytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Memory used: " + usedBytes);

        /*
            12
            Time used for algorithm: 20470
            Memory used: 3944816
         */
    }
}
