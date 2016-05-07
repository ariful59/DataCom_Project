package project;

import java.io.IOException;

/**
 * 
 * @author anando
 * This Class is called from Interface.java (168 line). 
 * This Class will be used to call all selected protocol from GUI
 */
class SenderMain {
	public void startWork(){
		CRCSender crc = new CRCSender();
		try{
			crc.doCRC();
		}catch(IOException e){
			System.out.println(e);
		}
	}
}
