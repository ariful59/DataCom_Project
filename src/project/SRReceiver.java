package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class SRReceiver {
	private Socket socket;
	private ServerSocket server;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private Queue<String>Q;
	private boolean EOF;
	private Random rand;
	private boolean marked[];
	private RECTHREAD t;
	private RClean cln;
	int R;
	SRReceiver() throws IOException{
		Q = new LinkedList<String>();
		server = new ServerSocket(8080);
		EOF = false;
		rand = new Random();
		marked = new boolean[9];
		R = 1;
		t = new RECTHREAD();
		t.start();
		cln = new RClean();
	}
	class RClean extends Thread{
		RClean(){
			this.start();
		}
		public void run(){
			while(!EOF){
				if(R>4)for(int i=1;i<=4;i++)marked[i]=false;
				else for(int i=5;i<=8;i++)marked[i]=false;
			}
		}
	}
	class RECTHREAD extends Thread{
		@Override
		public void run(){
			boolean NACKSEND = false;
			try {
				socket = server.accept();
				dIn = new DataInputStream(socket.getInputStream());
				dOut = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			while(true){
				int frame_number =0;
				try{
					frame_number = dIn.readInt();
					//if(frame_number>8)continue;
					System.out.println("Receiver Frame Number: "+frame_number);
					
				}catch(EOFException e){
					continue;
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(frame_number == -100){
					try {
						dOut.writeInt(-100);
					} catch (IOException e) {
						e.printStackTrace();
					}
					EOF = true;
					try {
						cln.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
					
				}
				if(frame_number >=R && (frame_number - R +1 <=4) ){
					if(frame_number != R){
						NACKSEND = true;
						try {
							dOut.writeInt(-R);
							int SIZE = dIn.readInt();
							String str = "";
							for(int i=0;i<SIZE;i++)str+=(char)dIn.read();
							if(!marked[frame_number])
								Q.add(str);
							marked[frame_number] = true;
						} catch (IOException e) {e.printStackTrace();}
					}
					if(frame_number == R){
						NACKSEND = false;
						try{
							int SIZE = dIn.readInt();
							String str = "";
							for(int i=0;i<SIZE;i++)str+=(char)dIn.read();
							if(!marked[frame_number - R])Q.add(str);
							for(int i=frame_number;i<frame_number+4 && i<9;i++){
								if(!marked[i])break;
								R%=8;
								R++;
							}
							dOut.writeInt(R);
						}catch(IOException excp){excp.printStackTrace();}
					}
				}
			}
		}
	}
	public boolean isEOF(){
		return (EOF && Q.isEmpty());
	}
	
	public String getString(){
		if(Q.isEmpty())return "";
		return Q.remove();
	}
	public void CLOSE() throws IOException, InterruptedException{
		Thread.sleep(100);
		//fout.closeFile();
		t.join();
		dIn.close();
		dOut.close();
		server.close();
		socket.close();
		Q.clear();
	}
}
