package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JOptionPane;

class Framing{
	public long tim;
	public int f_number;
	public String data;
	Framing(int a , long b , String c){
		f_number = a;
		tim = b;
		data = c;
	}
}

public class GBNSender {
	DataInputStream dIn;
	DataOutputStream dOut;
	Socket socket;
	Queue <Framing> Q;
	//Queue <String> saveingInp;
	long frame_time;
	int frame_number;
	Thread t;
	FrameTime timing;
	boolean EOF;
	GBNSender() throws UnknownHostException, IOException{
		//String SADD = JOptionPane.showInputDialog("Enter IP Address(keep null for local)\n");
		//if(SADD.length() == 0 || SADD == null)SADD = "127.0.0.1";
		//SADD = "192.168.43.46";
		System.out.println("HERE");
		socket = new Socket(SaveSettings.IPADDRESS,8080);
		System.out.println("connect");
		dOut = new DataOutputStream(socket.getOutputStream());
		dIn = new DataInputStream(socket.getInputStream());
		Q = new LinkedList<Framing>();
		//saveingInp = new LinkedList<String>();
		frame_number = 0;
		EOF = false;
		t = new ACK();
		t.start();
		timing = new FrameTime();
		timing.start();
	}
	class ACK extends Thread{
		public void run(){
			int ack_received = 0;
			while(true){
				try {
					ack_received = dIn.readInt();
					System.out.println("ACK : "+ack_received+" Frame Size :"+Q.size());
					if(ack_received <0){
						synchronized(Q){
						while(true){
							//synchronized(Q){
								if(Q.isEmpty())break;
							//}
							Q.remove();
							//Thread.yield();
						}
						}
						return;
					}
				} catch (IOException e) {
					continue;
				}
				synchronized(Q){
					while(!Q.isEmpty()){
						Framing f = Q.remove();
						if(f.f_number>ack_received){
							Q.add(f);
							break;
						}
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void send(String str) throws IOException, InterruptedException{
		if(str.equals("*"))EOF = true;
		if(EOF){
			
			while(!Q.isEmpty()){
				//System.out.println("EOF BUT NOT EMPTY");
				Thread.yield();
			}
			dOut.writeInt(-1);
			t.join();
			timing.thread_cutoff = true;
			timing.join();
			return;
		}
		System.out.println(str);
		boolean flag = false;
		while(!flag){
			synchronized(Q){
				if(Q.size() < 4)flag=true;
			}
			Thread.sleep(50);
			System.out.println("..");
			//Thread.yield();
		}
		System.out.println("frame_number : "+frame_number);
		dOut.writeInt(frame_number);
		dOut.writeInt((int)str.length());
		dOut.writeBytes(str);
		synchronized(Q){
			Q.add(new Framing(frame_number , System.currentTimeMillis()%10000,str));
		}
		frame_number++;
		frame_number%=5;
	}
	public void CLOSE() throws IOException, InterruptedException{
		Thread.sleep(100);
		t.join();
		timing.join();
		dOut.close();
		dIn.close();
		socket.close();
		
	}
	
	class FrameTime extends Thread{
		public boolean thread_cutoff;
		FrameTime(){
			thread_cutoff = false;
		}
		public void run(){
			while(!thread_cutoff){
				synchronized(Q){
					if(!Q.isEmpty()){
						Framing f = Q.peek();
						long cur_time = System.currentTimeMillis()%10000;
						if(cur_time - f.tim > 500){
							int SZ = Q.size();
							for(int i=0;i<SZ;i++){
								f = Q.remove();
								try{
								dOut.writeInt(f.f_number);
								dOut.writeInt((int)f.data.length());
								dOut.writeBytes(f.data);
								}catch(IOException e){
									System.out.println(e);
								}
								f.tim = (System.currentTimeMillis()%10000);
								Q.add(f);
							}
						}
					}
				}
			}
		}
	}
}
