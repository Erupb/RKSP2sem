package prac1;

import java.util.*;
public class Main{
        public static class Whisper{
            //В скольких бандах побывал дух
            public int bandsAmount = 1;
            public int id;
            public int bandNum;

            public Whisper(int id){
                this.id = id;
            }

            public void addBandAmount(){
                bandsAmount++;
            }

            public int getBandsAmount(){
                return bandsAmount;
            }

            public int getId(){
                return id;
            }

            public int getBandNum() {
                return bandNum;
            }

            public void setBandNum(int bandNum) {
                this.bandNum = bandNum;
            }
        }

        /*public static class Band{
            public List<Whisper> whispers = new ArrayList<Whisper>();

            public Band(Whisper whisper){
                whispers.add(whisper);
            }

            public void add(Whisper whisper){
                whispers.add(whisper);
            }

            public List<Whisper> getWhispers() {
                return whispers;
            }
        }*/

        public static void main(String[] args){
            Scanner sc = new Scanner(System.in);

            int whispersCount = sc.nextInt();
            int questionsCount = sc.nextInt();

            /*Band[] bands = new Band[whispersCount];*/
            Whisper[] whispers = new Whisper[whispersCount];

            //Объявляем и инициализируем всех духов
            for(int i=0; i<whispersCount; i++){
                whispers[i] = new Whisper(i+1);
                whispers[i].setBandNum(i+1);
            }

            /*//Объявляем и инициализируем все банды, в которых изначально по одному духу
            for(int i=0; i<whispersCount; i++){
                bands[i] = new Band(whispers[i]);
                whispers[i].addBandAmount();
            }*/

            /*for(int i=0; i<whispersCount; i++){
                System.out.println(bands[i].getWhispers().get(0).getId());
            }
            for(int i=0; i<whispersCount; i++){
                System.out.println(whispers[i].getId());
            }*/
            int questionType;
            int whisp1, whisp2;

            for(int i=0; i<questionsCount; i++){
                questionType = sc.nextInt();
                if (questionType == 1){
                    whisp1 = sc.nextInt();
                    whisp2 = sc.nextInt();
                    if(whispers[whisp1-1].getBandsAmount()==1 && whispers[whisp2-1].getBandsAmount()==1) {
                        if (whispers[whisp1 - 1].getBandNum() != whispers[whisp2 - 1].getBandNum()) {
                            whispers[whisp2 - 1].setBandNum(whispers[whisp1 - 1].getBandNum());
                            for(int j=0; j<whispersCount;j++){
                                if(whispers[j].getBandNum() == whispers[whisp1 - 1].getBandNum()){
                                    whispers[j].addBandAmount();
                                }
                            }
                            /*whispers[whisp2 - 1].addBandAmount();
                            whispers[whisp1 - 1].addBandAmount();*/
                        }
                    }
                    if(whispers[whisp1-1].getBandsAmount()==1 && whispers[whisp2-1].getBandsAmount()>1){
                        if (whispers[whisp1 - 1].getBandNum() != whispers[whisp2 - 1].getBandNum()) {
                            whispers[whisp1 - 1].setBandNum(whispers[whisp2 - 1].getBandNum());
                            for(int j=0; j<whispersCount;j++){
                                if(whispers[j].getBandNum() == whispers[whisp1 - 1].getBandNum()){
                                    whispers[j].addBandAmount();
                                }
                            }
                        }
                    }
                    if(whispers[whisp1-1].getBandsAmount()>1 && whispers[whisp2-1].getBandsAmount()==1){
                        if (whispers[whisp2 - 1].getBandNum() != whispers[whisp1 - 1].getBandNum()) {
                            whispers[whisp2 - 1].setBandNum(whispers[whisp1 - 1].getBandNum());
                            for(int j=0; j<whispersCount;j++){
                                if(whispers[j].getBandNum() == whispers[whisp1 - 1].getBandNum()){
                                    whispers[j].addBandAmount();
                                }
                            }
                            /*whispers[whisp2 - 1].addBandAmount();
                            whispers[whisp1 - 1].addBandAmount();*/
                        }
                    }
                    /*for(int j=0;j<whispersCount; j++){
                        System.out.print(whispers[j].getId() + " в " + whispers[j].getBandNum() + ", ");

                    }
                    System.out.println();*/
                }
                if (questionType == 2){
                    whisp1 = sc.nextInt();
                    whisp2 = sc.nextInt();

                    if(whispers[whisp1 - 1].getBandNum() == whispers[whisp2 - 1].getBandNum()){
                        System.out.println("YES");
                    } else System.out.println("NO");
                }
                if (questionType == 3){
                    whisp1 = sc.nextInt();

                    System.out.println(whispers[whisp1-1].getBandsAmount());
                    /*for(int j=0;j<whispersCount; j++){
                        System.out.print(whispers[j].getId() + " в " + whispers[j].getBandNum() + ", ");
                    }*/
                }
            }


        }
}
