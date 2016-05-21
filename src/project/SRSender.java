package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JOptionPane;

import project.SRSender.TimeOut;

class SRFraming{
	public long tim;
	public int f_number;
	public String data;
	public TimeOut tout;
	SRFraming(int a , long b , String c){
		f_number = a;
		tim = b;
		data = c;
	}
}

public class SRSender{
	private Queue <SRFraming> Q;
	private Socket socket;
	private DataInputStream dIn;
	private DataOutputStream dOut;
	private boolean EOF;
	private Random rand = new Random();
	ACK ack;
	int number;
	
	SRSender() throws UnknownHostException, IOException{
		if(!SaveSettings.TAKEIP){
			JOptionPane pane = new JOptionPane();
			SaveSettings.IPADDRESS=pane.showInputDialog("Enter IP address.(keep null for local host)");
			if(SaveSettings.IPADDRESS.equals("")){
				SaveSettings.IPADDRESS = "127.0.0.1";
			}
			SaveSettings.TAKEIP = true;
		}
		Q = new LinkedList<SRFraming>();
		socket = new Socket(SaveSettings.IPADDRESS,8080);
		dIn = new DataInputStream(socket.getInputStream());
		dOut = new DataOutputStream(socket.getOutputStream());
		EOF = false;
		number = 0;
		ack = new ACK();
		ack.start();
	}
	
	class ACK extends Thread{
		@Override
		public void run(){
			while(true){
				try {
					int frame_number = dIn.readInt();
					
					if(frame_number == -100){
						System.out.println("EOF IN SENDER SIDE");
						synchronized(Q){
							while(!Q.isEmpty()){
								SRFraming sr = Q.remove();
								sr.tout.AVAILABLE=false;
								sr.tout.join();
							}
						}
						System.out.println("EOF DONE");
						return;
					}
					else if(frame_number <0){
						synchronized(Q){
							int sz = Q.size();
							for(int i=0;i<sz;i++){
								SRFraming sr = Q.remove();
								if(sr.f_number == -frame_number){
									dOut.writeInt(sr.f_number);
									dOut.writeInt(sr.data.length());
									dOut.writeBytes(sr.data);
									sr.tim = System.currentTimeMillis()%10000;
								}
								Q.add(sr);
							}
						}
					}
					else if(frame_number > 0){
						synchronized(Q){
							int sz = Q.size();
							int loc = 0,take = -1;
							for(int i=0;i<sz;i++){
								SRFraming copy = Q.remove();
								if(take == -1 && copy.f_number==frame_number)take = loc;
								loc++;
								Q.add(copy);
							}
							if(take==-1)Q.clear();
							else{
								for(int i=0;i<=take;i++)Q.remove();
							}
						}
					}
					
				} catch (IOException e) {
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class TimeOut extends Thread{
		public boolean AVAILABLE; //thread close
		public SRFraming frame;
		TimeOut(){
			AVAILABLE = false;
		}
		@Override
		public void run(){
			long time = System.currentTimeMillis()%10000;
			while(AVAILABLE){
				if(System.currentTimeMillis()%10000 - time >5000)return;
				if(System.currentTimeMillis()%10000 - frame.tim > 500){
					frame.tim = System.currentTimeMillis()%10000;
					try {
						dOut.writeInt(frame.f_number);
						dOut.writeInt(frame.data.length());
						dOut.writeBytes(frame.data);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						continue;
					}
					//time = System.currentTimeMillis()%10000;
				}
			}
		}
	}
	public void send(String str) throws IOException, InterruptedException{
		if(str.equals("*")){
			EOF = true;
			while(!Q.isEmpty())Thread.yield();
			dOut.writeInt(-100);
			ack.join();
			System.out.println("hi there");
		}
		while(Q.size() >=4)Thread.yield();
		number %=8;
		number ++;
		SRFraming sr = new SRFraming(number, System.currentTimeMillis()%10000,str);
		TimeOut timing = new TimeOut();
		timing.frame = sr;
		sr.tout = timing;
		sr.tout.AVAILABLE = true;
		Q.add(sr);
		sr.tout.start();
		if(rand.nextInt(100) <5){
			dOut.writeInt(number);
			dOut.writeInt(str.length());
			dOut.writeBytes(str);
		}
	}
	public void CLOSE() throws IOException{
		socket.close();
		dOut.close();
		dIn.close();
	}
}