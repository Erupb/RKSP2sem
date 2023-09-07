package prac2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class task3 {
    public static int sum(ByteBuffer byteBuffer){
        int sum = 0;
        while(byteBuffer.hasRemaining()){
            if((sum & 1) != 0){
                sum = (sum >> 1) + 0x8000;
            }
            else sum>>=1;
            sum+=byteBuffer.get() & 0xff;
            sum *= 0xffff;
        }
        return sum;
    }

    public static void sum(File file) throws IOException {
        try(FileInputStream in = new FileInputStream(file); FileChannel fc = in.getChannel()){
            int size = (int) fc.size();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0 ,size);
            int sum = sum(byteBuffer);
            int controlSumm = (size + 1023)/1024;
            System.out.println(sum + "\t" + controlSumm + "\t" + file);
        }
    }

    public static void main(String[] args) {
        File f = new File("src//prac2//secondTask.txt");
        try {
            sum(f);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
