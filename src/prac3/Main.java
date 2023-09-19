package prac3;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.schedulers.NewThreadScheduler;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args){
        //Exercise1();
        Exercise2();
        Exercise3();
        Exercise4();
    }

    public static void Exercise1(){
        final Exercise1.TempSensor[] TempSensor = new Exercise1.TempSensor[1];
        new Thread(() -> TempSensor[0] = new Exercise1.TempSensor()).start();
        final Exercise1.CO2Sensor[] CO2Sensor = new Exercise1.CO2Sensor[1];
        new Thread(() -> CO2Sensor[0] = new Exercise1.CO2Sensor()).start();
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        final boolean[] Alarm = {false, false};
        TempSensor[0].Temperature.subscribeOn(new NewThreadScheduler()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 25) {
                    Alarm[0] = true;
                    if (Alarm[1]) System.out.println("ALARM!!!");
                    else System.out.println("Температура превысила норму");
                } else {
                    Alarm[0] = false;
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        CO2Sensor[0].CO2.subscribeOn(new NewThreadScheduler()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable disposable) {
            }

            @Override
            public void onNext(Integer integer) {
                if (integer > 70) {
                    Alarm[1] = true;
                    if (Alarm[0]) System.out.println("ALARM!!!");
                    else System.out.println("CO2 превысил норму");
                } else {
                    Alarm[1] = false;
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        });
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Exercise1{
        public static class TempSensor{
            public Observable<Integer> Temperature;

            public TempSensor(){
                Observable<Integer> source = Observable.create(emitter -> {
                    Random rnd = new Random();
                    while(true){
                        emitter.onNext(rnd.nextInt(15) + 15);
                        Thread.sleep(1000);
                    }
                });
                Temperature = source;
            }
        }

        public static class CO2Sensor{
            public Observable<Integer> CO2;

            public CO2Sensor(){
                Observable<Integer> source = Observable.create(emitter -> {
                    Random rnd = new Random();
                    while(true){
                        emitter.onNext(rnd.nextInt(70) + 30);
                        Thread.sleep(1000);
                    }
                });
                CO2 = source;
            }
        }
    }

    public static void Exercise2(){
        Random rnd = new Random();

        System.out.println("task 2.1.3");
        var sourceStream = java.util.stream.IntStream.range(0, rnd.nextInt(1000)).map(x -> rnd.nextInt());
        var finalStream = sourceStream.limit(10);
        finalStream.forEach(s -> System.out.println("2.1.3: " + s));

        System.out.println("task 2.2.3");
        var sourceStreamT2 = java.util.stream.IntStream.range(0, rnd.nextInt(1000)).map(x -> rnd.nextInt(8) + 1);
        var sourceStreamT22 = java.util.stream.IntStream.range(0, rnd.nextInt(1000)).map(x -> rnd.nextInt(8) + 1);
        var sourceArrayT2 = sourceStreamT22.toArray();
        AtomicInteger i = new AtomicInteger();
        var finalStreamT2 = sourceStreamT2.flatMap(x -> java.util.stream.IntStream.of(x, sourceArrayT2[i.getAndIncrement()]));
        finalStreamT2.forEach(s -> System.out.println("2.2.3: " + s));

        System.out.println("task 2.3.3");
        var sourceStream3 = java.util.stream.IntStream.range(0, rnd.nextInt(1000)).map(x -> rnd.nextInt());
        System.out.println("2.3.3: " + sourceStream3.reduce((x,y) -> y).getAsInt());
    }

    public static void Exercise3(){
        Random rnd = new Random();
        for(int i=0; i<Exercise3.userFriends.length; i++){
            Exercise3.userFriends[i] = new Exercise3.UserFriend(rnd.nextInt(), rnd.nextInt());
        }

        Integer[] userIds = new Integer[10];
        for (int i = 0; i < userIds.length; i++) {
            userIds[i] = Exercise3.userFriends[rnd.nextInt(Exercise3.userFriends.length)].userId;
        }
        var observable = Observable.fromArray(userIds).flatMap(x -> Exercise3.getFriends(x));
        observable.forEach(s -> System.out.println(s));
    }

    public static class Exercise3{
        public static Exercise3.UserFriend[] userFriends = new UserFriend[100];

        public static class UserFriend{
            public int userId = 0;
            public int friendId = 0;

            public UserFriend(int userId, int friendId){
                this.userId = userId;
                this.friendId = friendId;
            }

            @Override
            public String toString() {
                return "UserFriend{" +
                        "userId=" + userId +
                        ", friendId=" + friendId +
                        '}';
            }
        }
        public static Observable<UserFriend> getFriends(int userId){
            return Observable.fromArray(userFriends).filter(x -> x.userId == userId);
        }
    }

    public static void Exercise4(){
        Exercise4.Generator generator = new Exercise4.Generator();
        Exercise4.Handler XMLHandler = new Exercise4.Handler(generator.Files, "XML");
        Exercise4.Handler JSONHandler = new Exercise4.Handler(generator.Files, "JSON");
        Exercise4.Handler XLSHandler = new Exercise4.Handler(generator.Files, "XLS");

        new Thread(() -> XMLHandler.Start()).start();
        new Thread(() -> JSONHandler.Start()).start();
        new Thread(() -> XLSHandler.Start()).start();


    }

    public static class Exercise4 {
        public static class File {

            public String FileType;
            public int FileSize;

            public File(String fileType, int fileSize) {
                FileType = fileType;
                FileSize = fileSize;
            }
        }

        public static class Generator {
            public Observable<File> Files;

            public Generator() {
                Files = Observable.create(emitter -> {
                    Random rnd = new Random();
                    while (true) {
                        emitter.onNext(new File(FileTypes[rnd.nextInt(FileTypes.length)], rnd.nextInt(89) + 10));
                        Thread.sleep(rnd.nextInt(900) + 100);
                    }
                });
            }

            public void Start() {
                Files = Observable.create(emitter -> {
                    Random rnd = new Random();
                    while (true) {
                        if (Files.count().blockingGet() < 5) {
                            emitter.onNext(new File(FileTypes[rnd.nextInt(FileTypes.length)], rnd.nextInt(89) + 10));
                        }
                        Thread.sleep(rnd.nextInt(900) + 100);
                    }
                });
            }

            private static final String[] FileTypes = new String[] { "XML", "JSON", "XLS" };
            // private static final int[] FileSizes = new int[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 };
        }

        public static class Handler {
            Observable<File> FileQueue;
            String FileType;

            public Handler(Observable<File> fileQueue, String fileType) {
                FileQueue = fileQueue;
                FileType = fileType;
                // FileSize = fileSizes;
            }

            public void Start() {
                FileQueue.subscribe(new Observer<File>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onNext(File file) {
                        if (file.FileType.equals(FileType)) {
                            System.out.println(file.FileType + " " + "create with size" + " " + file.FileSize + " " + "generated");
                            try {
                                Thread.sleep(file.FileSize * 70);
                                System.out.println(file.FileType + " is processed");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable throwable) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
            }
        }
    }
}
