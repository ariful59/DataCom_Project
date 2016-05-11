package project;

import java.awt.Frame;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

public class ReceiverMain extends Frame {
	public void doJob()throws IOException{
		FileInputClass fin = new FileInputClass();
		FileOutputClass fout = new FileOutputClass();
		String fileAddress ="";
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
		    File selectedFile = fileChooser.getSelectedFile();
		    System.out.println("Selected file: " + selectedFile.getAbsolutePath());
		    fileAddress = selectedFile.getAbsolutePath();
		}
		
		fin.setFileName("Files/Temp_File/temp.txt");
		fout.setFileName(fileAddress);
		
		Receiver rcvr = new Receiver();
		int count = 0;
		String str="";
		while(!fin.isEOF()){
			str = "";
			str = fin.takeInput(2832);
			count = str.length();
			if(count == 2832){
				count = 0;
				str = rcvr.getReceiver(str);
				fout.writeFile(str);
			}
		}
		if(count !=0){
			str = rcvr.getReceiver(str);
			fout.writeFile(str);
			count = 0;
		}
		fout.closeFile();
	}
}
