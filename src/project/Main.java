package project;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		JOptionPane pane = new JOptionPane();
		SaveSettings.IPADDRESS=pane.showInputDialog("Enter IP address.(keep null for local host)");
		if(SaveSettings.IPADDRESS.equals("")){
			SaveSettings.IPADDRESS = "127.0.0.1";
		}
		System.out.println("hello DataCom");
		Interface face = new Interface();
		face.showInterface();
	}

}
