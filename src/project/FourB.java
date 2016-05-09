package project;

import java.util.Random;

public class FourB {

    String ret,tmp;

    final String seq[]={"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};
    final String enc[]= {"11110","01001","10100","10101","01010","01011","01110","01111","10010","10011","10110","10111","11010","11011","11100","11101"};

    Random rand;
    boolean errorFlag;
    int putErrorCount,gotErrorCount;


    public FourB(){
        ret="";
        tmp="";
        rand= new Random();
        putErrorCount=0;
        gotErrorCount=0;
    }

    public String encode(String str){

        for(int i=0;i<str.length();i+=4){
            tmp="";
            int got=-1;

            for(int j=i;j<i+4;j++){
                tmp+=str.charAt(j);
            }

            for(int j=0;j<16;j++){
                if(seq[j].equals(tmp)){
                    got=j;
                    break;
                }
            }

            ret+=enc[got];
        }

        return ret;
    }

    public String decode(String str){
        ret="";
        for(int i=0;i<str.length();i+=5){
            tmp="";
            int got=-1;

            for(int j=i;j<i+5;j++){
                tmp+=str.charAt(j);
            }

            errorFlag= isPutError(tmp);

            if(errorFlag){
                tmp=putError(tmp);
                putErrorCount++;
            }


            for(int j=0;j<16;j++){
                if(enc[j].equals(tmp)){
                    got=j;
                    break;
                }
            }

            if(got==-1){
                gotErrorCount++;
                String string= tmp.substring(0, 4);
                ret+= string;
            }
            else
                ret+=seq[got];
        }

        return ret;
    }

    private boolean isPutError(String tmp) {

        int x= rand.nextInt(100);

        if(x<5)
            return true;
        return false;

    }

    private String putError(String tmp) {

        String out="";

        int x= rand.nextInt(5);

        for(int i=0;i<5;i++){

            if(x!=i){
                out+=tmp.charAt(i);
            }
            else{
                char ch= tmp.charAt(i);

                if(ch=='0'){
                    out+="1";
                }
                else
                    out+="0";
            }
        }

        return out;
    }

    public void printError(){

        System.out.println(putErrorCount+ " "+ gotErrorCount+" "+ (double)gotErrorCount/(double)putErrorCount*100);

    }
   }
