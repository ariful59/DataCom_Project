package project;

public class Diff_Manchester {
	
	String out;
	char current;
    Boolean positive;

    public Diff_Manchester(){

        out= "";
        positive=true;
    }


    String encode(String str){

        for(int i=0;i<str.length();i++){

            if(str.charAt(i)=='1'){
            	
            	if(positive){
            		positive=false;
            		out+="-+";
            	}
            	else{
            		positive=true;
            		out+="+-";
            	}
            }

            else{
            	if(positive){
            		out+="+-";
            	}
            	else{
            		out+="-+";
            	}
            }
                
        }

        return out;
    }

    String decode(String str){

        for(int i=0;i<str.length();i+=2){

            if(str.charAt(i)=='+'){
            	if(positive)
            		out+="0";
            	else{
            		positive=!positive;
            		out+="1";
            	}
            }
            else{
            	if(!positive)
            		out+="0";
            	else{
            		positive=!positive;
            		out+="1";
            	}
            }
                
        }

        return out;
    }
}
