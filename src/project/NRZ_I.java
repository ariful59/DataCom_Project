package datacom;

public class NRZ_I {
    String out;
    Boolean positive;

    public NRZ_I(){

        out= "";
        positive=true;
    }


    String encode(String str){

        for(int i=0;i<str.length();i++){

            if(str.charAt(i)=='1'){
                positive= !positive;
            }

            if(positive){
                out+="+";
            }
            else
                out+="-";
        }

        return out;
    }

    String decode(String str){

        for(int i=0;i<str.length();i++){

            if(positive){
                if(str.charAt(i)=='+'){
                    out+="0";

                }
                else{
                    out+="1";
                    positive=!positive;
                }
            }
            else{
                if(str.charAt(i)=='-'){
                    out+="0";
                }
                else{
                    out+="1";
                    positive=!positive;
                }
            }

        }

        return out;
    }
}
