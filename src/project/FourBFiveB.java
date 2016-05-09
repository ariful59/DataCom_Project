package project;

public class FourBFiveB {
	private  String dataSeq [] = {"0000", "0001", "0010", "0011", "0100",
            "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101",
            "1110", "1111"};
	private  String enCode [] = {"11110", "01001", "10100", "10101", "01010",
            "01011", "01110", "01111", "10010", "10011", "10110", "10111", "11010",
            "11011", "11100", "11101"};
	public  String encode(String str){
		String encodingString = "";
		for(int i=0;i<str.length();i+=4){
			String temp = str.substring(i,i+4);
			for(int ind = 0; ind<16;ind++)if(dataSeq[ind].equals(temp)){
				encodingString+=(enCode[ind]);
				break;
			}
		}
		return encodingString;
	}
	public String decode(String str){
		String decodingString = "";
		for(int i=0;i<str.length();i+=10){
			String temp = str.substring(i, i+5);
			for(int ind=0;ind<16;ind++)if(temp.equals(enCode[ind])){
				decodingString+=(dataSeq[ind]);
				break;
			}
			temp = str.substring(i+5,i+10);
			for(int ind=0;ind<16;ind++)if(temp.equals(enCode[ind])){
				decodingString+=(dataSeq[ind]);
				break;
			}
		}
		return decodingString;
	}
}
