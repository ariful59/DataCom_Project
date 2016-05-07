package project;


import java.io.IOException;

public class CRCSender {
	public void doCRC() throws IOException{
		FileInputClass inp = new FileInputClass();
		FileOutputClass otp = new FileOutputClass();
		BinaryConverter cnv = new BinaryConverter();
		inp.setFileName("Files/Input_Files/in1.txt");
		otp.setFileName("Files/Temp_File/temp.txt");
		
		Sender sndr = new Sender();
		while(!inp.isEOF()){
			String str = inp.takeInput(150);
			for(int i=str.length();i<150;i++)str+=" ";
			str = cnv.toBinary(str);
			str = sndr.getSender(str);
			CRC C = new CRC();
			str = C.encode(str);
			otp.writeFile(str);
		}
		otp.closeFile();
	}
}
