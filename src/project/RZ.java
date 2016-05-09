package datacom;

public class RZ{
    String out;
    //Boolean positive;

    public RZ(){

        out= "";
        //positive=true;
    }


    String encode(String str){

        for(int i=0;i<str.length();i++){

            if(str.charAt(i)=='1'){
                out+=" +O";
            }

            else
                out+=" -O";
        }

        return out;
    }

    String decode(String str){

        for(int i=1;i<str.length();i+=3){

            if(str.charAt(i)=='+'){
                out+="1";
            }
            else
                out+="0";
        }

        return out;
    }
}
