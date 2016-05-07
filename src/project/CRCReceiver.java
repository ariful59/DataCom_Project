package project;

import java.io.IOException;

public class CRCReceiver {
	public void doCRC()throws IOException{
		FileInputClass fin = new FileInputClass();
		FileOutputClass fout = new FileOutputClass();
		fin.setFileName("Files/Temp_File/temp.txt");
		fout.setFileName("Files/Output_Files/outCRC.txt");
		
		Receiver rcvr = new Receiver();
		int count = 0;
		String str="";
		while(!fin.isEOF()){
			str = "";
			str = fin.takeInput(1400);
			count = str.length();
			if(count == 1400){
				count = 0;
				CRC C = new CRC();
				str = C.decode(str);
				if(str.equals("")){
					fout.writeFile("???????????????");
					continue;
				}
				
				BinaryConverter cnv = new BinaryConverter();
				str = cnv.fromBinary(str);
				str = rcvr.getReceiver(str);
				fout.writeFile(str);
			}
		}
		if(count !=0){
			CRC C = new CRC();
			str = C.decode(str);
			if(str.equals("")){
				fout.writeFile("???????????????");
			}
			else{
				BinaryConverter cnv = new BinaryConverter();
				str = cnv.fromBinary(str);
				str = rcvr.getReceiver(str);
				fout.writeFile(str);
			}
			count = 0;
		}
		fout.closeFile();
	}
}
