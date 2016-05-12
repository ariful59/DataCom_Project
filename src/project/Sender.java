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
		String tmp = cnv.toBinary("DH-H");
		String tail= cnv.toBinary("D-T");
		if(SaveSettings.SAVE_DATALINK_SCHEME == 1){ //CRC-32 selected
			CRC C = new CRC();
			str = C.encode(str);
		}
		else if(SaveSettings.SAVE_DATALINK_SCHEME == 0){
			Ham_dis ham = new Ham_dis();
			str = ham.encode(tmp+str+tail);
			return phh(str);
		}
		return phh(tmp + str + tail);
	}
	
	//Physical Layer
	private String phh(String str){
		String tmp = cnv.toBinary("PH-H");
		String Block = "";
		if(SaveSettings.SAVE_BLOCK_CODING == 0){
			FourBFiveB Four = new FourBFiveB();
			System.out.println(str.length());
			Block = Four.encode(str);
		}
		if(SaveSettings.SAVE_PHYSICALLINK == 0){
			NRZ_L nrz_l = new NRZ_L();
			str = nrz_l.encode(Block);
		}
		if(SaveSettings.SAVE_PHYSICALLINK == 1){
			NRZ_I nrz_i = new NRZ_I();
			str = nrz_i.encode(Block);
		}
		if(SaveSettings.SAVE_PHYSICALLINK == 2){
			RZ rz = new RZ();
			str = rz.encode(Block);
		}
		if(SaveSettings.SAVE_PHYSICALLINK == 3){
			Manchester man = new Manchester();
			str = man.encode(Block);
		}
		if(SaveSettings.SAVE_PHYSICALLINK == 4){
			Diff_Manchester diff_man = new Diff_Manchester();
			str = diff_man.encode(Block);
		}
		
		return (tmp +str);
		//return str;
	}
	//Call this to get OSI sender
	public String getSender(String str){
		return ah(str);
	}
}
