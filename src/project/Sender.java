package project;
/**
 * This is universal sender. Every protocol will use it.
 * To use it convert a string into binary string. Then send that to getSender() function.
 * getSender() will return binary OSI sender.
 */

public class Sender {
	private BinaryConverter cnv;
	Sender(){
		cnv = new BinaryConverter();
	}
	
	//Application Layer
	private String ah(String str){
		String tmp = cnv.toBinary("A-H");
		return ph(tmp + str);
	}
	
	//Presentation Layer
	private String ph(String str){
		String tmp = cnv.toBinary("P-H");
		return sh(tmp + str);
	}
	
	//
	private String sh(String str){
		String tmp = cnv.toBinary("S-H");
		return th(tmp + str);
	}
	
	//Transport Layer
	private String th(String str){
		String tmp = cnv.toBinary("T-H");
		return nh(tmp + str);
	}
	
	//Network Layer
	private String nh(String str){
		String tmp = cnv.toBinary("N-H");
		return dh(tmp + str);
	}
	
	//Datalink Layer
	private String dh(String str){
		String tmp = cnv.toBinary("D-H");
		String tail= cnv.toBinary("D-T");
		return phh(tmp + str + tail);
	}
	
	//Physical Layer
	private String phh(String str){
		//String tmp = cnv.StringToBinary("PH-H");
		//return (tmp + str);
		return str;
	}
	//Call this to get OSI sender
	public String getSender(String str){
		return ah(str);
	}
}
