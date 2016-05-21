package project;


import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SenderMain extends Frame{
	public void doJob() throws IOException{
		FileInputClass inp = new FileInputClass();
		
		BinaryConverter cnv = new BinaryConverter();
		String fileAddress ="Files/Input_Files/in1.txt";
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    fileAddress = selectedFile.getAbsolutePath();
		}
		
		
		inp.setFileName(fileAddress);
		
		SaveSettings.SAVE_INPUT_NUMBER = 0;
		
		//GBNSender sending = new GBNSender();
		if(SaveSettings.SAVE_DATALINK_PROTOCOL == 1){
			SRSender sending = new SRSender();
			Sender sndr = new Sender();
			while(!inp.isEOF()){
				String str = inp.takeInput(150);
				for(int i=str.length();i<150;i++)str+=" ";
				str = cnv.toBinary(str);
				str = sndr.getSender(str);
				System.out.println("sender length : " + str.length());
				SaveSettings.SAVE_INPUT_NUMBER = Math.max(SaveSettings.SAVE_INPUT_NUMBER, str.length());
				
				try {
					sending.send(str);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("FINNISED SENT");
				sending.send("*");
				sending.CLOSE();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			GBNSender sending = new GBNSender();
			Sender sndr = new Sender();
			while(!inp.isEOF()){
				String str = inp.takeInput(150);
				for(int i=str.length();i<150;i++)str+=" ";
				str = cnv.toBinary(str);
				str = sndr.getSender(str);
				System.out.println("sender length : " + str.length());
				SaveSettings.SAVE_INPUT_NUMBER = Math.max(SaveSettings.SAVE_INPUT_NUMBER, str.length());
				
				try {
					sending.send(str);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				System.out.println("FINNISED SENT");
				sending.send("*");
				sending.CLOSE();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("CLOSED Sender");
		System.out.println(SaveSettings.SAVE_INPUT_NUMBER);
		
		this.dispose();
		String conf = JOptionPane.showInputDialog("(sender)Want to Continue (Y/N)");
		
		if(conf.startsWith("Y") || conf.startsWith("y")){
			Interface face = new Interface();
			face.showInterface();
		}
	}
}
