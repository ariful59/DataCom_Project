package project;


import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class SenderMain extends Frame{
	public void doJob() throws IOException{
		FileInputClass inp = new FileInputClass();
		FileOutputClass otp = new FileOutputClass();
		BinaryConverter cnv = new BinaryConverter();
		String fileAddress ="Files/Input_Files/in1.txt";
		
		/*JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    fileAddress = selectedFile.getAbsolutePath();
		}
		*/
		
		inp.setFileName(fileAddress);
		otp.setFileName("Files/Temp_File/temp.txt");
		SaveSettings.SAVE_INPUT_NUMBER = 0;
		//SendSocket sending = new SendSocket();
		GBNSender sending = new GBNSender();
		Sender sndr = new Sender();
		while(!inp.isEOF()){
			String str = inp.takeInput(150);
			for(int i=str.length();i<150;i++)str+=" ";
			str = cnv.toBinary(str);
			str = sndr.getSender(str);
			System.out.println("sender length : " + str.length());
			SaveSettings.SAVE_INPUT_NUMBER = Math.max(SaveSettings.SAVE_INPUT_NUMBER, str.length());
			//otp.writeFile(str);
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
		System.out.println("CLOSED Sender");
		System.out.println(SaveSettings.SAVE_INPUT_NUMBER);
		otp.closeFile();
	}
}
