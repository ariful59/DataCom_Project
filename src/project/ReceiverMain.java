package project;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ReceiverMain extends Frame {
	public void doJob()throws IOException{
		
		FileOutputClass fout = new FileOutputClass();
		String fileAddress ="Files/Output_Files/outCRC.txt";
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    fileAddress = selectedFile.getAbsolutePath();
		}
		
		
		fout.setFileName(fileAddress);
		
		Receiver rcvr = new Receiver();
		int count = 0;
		String str="";
		
		//GBNReceive receiving = new GBNReceive();
		if(SaveSettings.SAVE_DATALINK_PROTOCOL == 1){
			SRReceiver receiving = new SRReceiver();
			while(!receiving.isEOF()){
				str = "";
				while(!receiving.isEOF()){
					str = receiving.getString();
					if(str.length()>0)break;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				count = str.length();
				System.out.println("receiver Received: " +count);
				if(count >0){
					count = 0;
					str = rcvr.getReceiver(str);
					System.out.println(str);
					fout.writeFile(str);
				}
			}
			if(count !=0){
				str = rcvr.getReceiver(str);
				fout.writeFile(str);
				count = 0;
			}
			System.out.println("receiver finished");
			fout.closeFile();
			try {
				receiving.CLOSE();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			GBNReceive receiving = new GBNReceive();
			while(!receiving.isEOF()){
				str = "";
				while(!receiving.isEOF()){
					str = receiving.getString();
					if(str.length()>0)break;
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				count = str.length();
				System.out.println("receiver Received: " +count);
				if(count >0){
					count = 0;
					str = rcvr.getReceiver(str);
					System.out.println(str);
					fout.writeFile(str);
				}
			}
			if(count !=0){
				str = rcvr.getReceiver(str);
				fout.writeFile(str);
				count = 0;
			}
			System.out.println("receiver finished");
			fout.closeFile();
			try {
				receiving.CLOSE();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.dispose();
		String conf = JOptionPane.showInputDialog("(receiver)Want to Continue (Y/N)");
		if(conf.startsWith("Y") || conf.startsWith("y")){
			Interface face = new Interface();
			face.showInterface();
		}
	}
}
