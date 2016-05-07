package project;
/**
 * 
 * @author anando
 * This Class is called from Interface.java (265 line). This call will call
 * all Selected Receiver Protocol.
 */
public class ReceiverMain {
	public void startWork(){
		CRCReceiver crc = new CRCReceiver();
		try{
			crc.doCRC();
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
