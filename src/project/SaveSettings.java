package project;
/**
 * This Class is to save all Setup options which was selected during GUI I/O Operation.
 *
 */
/**
 * 
 * Save.txt file Serial:
 * 1.SAVE_MODE
 * 2.SAVE_DATALINK_PROTOCOL
 * 3.SAVE_DATALINK_SCHEME
 * 4.SAVE_PHYSICALLINK
 *
 */

public class SaveSettings {
	SaveSettings(){
		
	}
	public static Integer SAVE_MODE; //1 for Sender. 2 for Receiver
	public static Integer SAVE_DATALINK_SCHEME; // 0 for Hamming Distance . 1 for CRC
	public static Integer SAVE_DATALINK_PROTOCOL; //0 for GO BACK N . 1 for Selective Repeat
	public static Integer SAVE_PHYSICALLINK; //0 = NRZ-L , 1 = NRZ-I , 2 = RZ , 3 = Manchester , 4 = Differential Manchaster , 5 = MLT-3 , 6 =4B/5B , 7 = 8B/10B
}
