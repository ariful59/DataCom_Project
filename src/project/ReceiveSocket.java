package project;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

public class ReceiveSocket {
	private ServerSocket server;
	private Socket socket;
	private Queue<String> Q;
	private DataInputStream dIn;
	private boolean EOF;
	ReceiveSocket() throws IOException{
		server = new ServerSocket(8080);
		Q = new LinkedList<String>();
		EOF = false;
	}
	public void Start() throws IOException{
		socket = server.accept();
		dIn = new DataInputStream(socket.getInputStream());
		while(true){
			int size = dIn.readInt();
			if(size<0){
				EOF = true;
				break;
			}
			String chunk = "";
			for(int i=0;i<size;i++){
				int read = dIn.read();
				if(read <0 || read >127)break;
				chunk +=(char)read;
			}
			Q.add(chunk);
		}
	}
	public boolean isEOF(){
		return (EOF && Q.isEmpty());
	}
	public String getString(){
		return Q.remove();
	}
	public void Close() throws IOException{
		socket.close();
		server.close();
		dIn.close();
		Q.clear();
	}
}
