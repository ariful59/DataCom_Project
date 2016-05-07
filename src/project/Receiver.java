package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 * 
 * Call getReceiver() to get OSI receiver.
 * Send String frame. It will decode
 */
public class Receiver {
	//Application Layer
	private String ah(String str){
		String ans = str.substring(3);
		return ans;
	}
	
	//Presentation Layer
	private String ph(String str){
		String ans = str.substring(3);
		return ah(ans);
	}
	
	//
	private String sh(String str){
		String ans = str.substring(3);
		return ph(ans);
	}
	
	//Transport Layer
	private String th(String str){
		String ans = str.substring(3);
		return sh(ans);
	}
	
	//Network Layer
	private String nh(String str){
		String ans = str.substring(3);
		return th(ans);
	}
	
	//DataLink Layer
	private String dh(String str){
		int len = str.length();
		String ans = str.substring(3, len - 3);
		return nh(ans);
	}
	
	//Physical Layer
	private String phh(String str){
		//String ans = str.substring(4);
		//return dh(ans);
		return dh(str);
	}
	
	public String getReceiver(String str){
		return phh(str);
	}
}
