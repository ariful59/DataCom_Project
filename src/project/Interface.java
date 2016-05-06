package project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;

import com.sun.java_cup.internal.runtime.Scanner;
import com.sun.javafx.tk.Toolkit;

/**
 * 
 * @author anando
 * Here We will create two class. One for Sender and Another for Receiver. This class Will Save Necessary
 * Data that user will provide in the Settings Class.
 */
public class Interface extends JFrame{
	//SaveSettings saveSettings = new SaveSettings();
	
	//this window will prompt if user is Sender or Receiver
	class BeginWindow extends JFrame{
		private JPanel buttonPannel;
		private JButton sendButton;
		private JButton receiverButton;
		private JLabel textView;
		BeginWindow(){
			super("Who are you?");
			setLayout(new GridLayout(3,1));
			textView = new JLabel();
			textView.setText("Select you mode:");
			textView.setFont(new Font("Serif",Font.BOLD,20));
			add(textView,FlowLayout.LEFT);
			
			sendButton = new JButton("SENDER");
			receiverButton = new JButton("RECEIVER");
			Icon icon = new ImageIcon(getClass().getResource("send.jpg"));
			sendButton.setIcon(icon);
			sendButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					SaveSettings.SAVE_MODE=1;
					dispose();
					SendInterface sInterface = new SendInterface();
					sInterface.setSize(450,400);
					sInterface.setLocationRelativeTo(null);
					sInterface.setVisible(true);
					sInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			});
			
			icon = new ImageIcon(getClass().getResource("receive.jpg"));
			receiverButton.setIcon(icon);
			receiverButton.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent event){
					SaveSettings.SAVE_MODE=2;
					try{
						FileInputClass retrive = new FileInputClass();
						retrive.setFileName("save.txt");
						retrive.takeInput(1);
						
						SaveSettings.SAVE_DATALINK_PROTOCOL = Integer.parseInt(retrive.takeInput(1));
						SaveSettings.SAVE_DATALINK_SCHEME = Integer.parseInt(retrive.takeInput(1));
						SaveSettings.SAVE_PHYSICALLINK = Integer.parseInt(retrive.takeInput(1));
						
						System.out.println("Protocol Number: "+SaveSettings.SAVE_DATALINK_PROTOCOL);
						System.out.println("Scheme Number: "+SaveSettings.SAVE_DATALINK_SCHEME);
						System.out.println("PhysicalLink Number: "+SaveSettings.SAVE_PHYSICALLINK);
						
						
					}catch(Exception excp){
						
					}
					dispose();
				}
			});
			
			
			buttonPannel = new JPanel();
			buttonPannel.add(sendButton);
			buttonPannel.add(receiverButton);
			buttonPannel.setLayout(new GridLayout(2,1));
			add(buttonPannel, BorderLayout.CENTER);
		}
	}
	class SendInterface extends JFrame{
		private JComboBox dataLinkScheme;
		private JComboBox dataLinkProtocol;
		private JComboBox physical;
		private JLabel textShow;
		private String strdataLinkScheme[] = {"Hamming Distance" , "CRC"};
		private String strdataLinkProtocol[] = {"Go back n","Selective Repeat"};
		private String strphysical[] ={"NRZ-L","NRZ-I","RZ","Manchester","Differential Manchester","MLT-3","4B/5B","8B/10B"};
		private JButton Load; 
		SendInterface(){
			super("Sender");
			setLayout(new FlowLayout());
			
			//Default SaveSettings
			SaveSettings.SAVE_DATALINK_PROTOCOL=1;
			SaveSettings.SAVE_DATALINK_SCHEME=1;
			SaveSettings.SAVE_PHYSICALLINK=1;
			
			textShow = new JLabel();
			textShow.setText("This is sender side. Select Datalink and Physical Layer:");
			textShow.setFont(new Font("Italic",Font.ITALIC,15));
			add(textShow);
			
			textShow = new JLabel();
			textShow.setText("Select Datalink Control Protocol");
			add(textShow);
			
			dataLinkProtocol = new JComboBox(strdataLinkProtocol);
			dataLinkProtocol.setMaximumRowCount(2);
			dataLinkProtocol.setSelectedIndex(1); //default selection
			dataLinkProtocol.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = dataLinkProtocol.getSelectedIndex();
					SaveSettings.SAVE_DATALINK_PROTOCOL = index;
				}
			});
			add(dataLinkProtocol);
			
			
						
			
			textShow = new JLabel();
			textShow.setText("Error Detection Scheme");
			add(textShow);
			
			dataLinkScheme = new JComboBox(strdataLinkScheme);
			dataLinkScheme.setMaximumRowCount(2);
			dataLinkScheme.setSelectedIndex(1); //this is default
			dataLinkScheme.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = dataLinkScheme.getSelectedIndex();
					SaveSettings.SAVE_DATALINK_SCHEME = index;
				}
			});
			add(dataLinkScheme);


			textShow = new JLabel();
			textShow.setText("Select Physical Layer");
			textShow.setHorizontalAlignment(SwingConstants.CENTER);;
			add(textShow);
			
			physical = new JComboBox(strphysical);
			physical.setMaximumRowCount(5);
			physical.setSelectedIndex(1); //default selection
			
			physical.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = physical.getSelectedIndex();
					SaveSettings.SAVE_PHYSICALLINK = index;
					System.out.println(SaveSettings.SAVE_PHYSICALLINK);
				}
			});
			add(new JScrollPane(physical));
			
			Load = new JButton("LOAD & EXIT");
			Load.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try{
						FileOutputClass loadFile = new FileOutputClass();
						loadFile.setFileName("save.txt");
						loadFile.writeFile(SaveSettings.SAVE_MODE.toString());
						loadFile.writeFile(SaveSettings.SAVE_DATALINK_PROTOCOL.toString());
						loadFile.writeFile(SaveSettings.SAVE_DATALINK_SCHEME.toString());
						loadFile.writeFile(SaveSettings.SAVE_PHYSICALLINK.toString());
						loadFile.closeFile();
					}catch(Exception exception){
						System.out.println(exception);
					}
					dispose();
				}
				
			});
			add(Load);
		}
	}
	public void showInterface(){
		BeginWindow begin = new BeginWindow();
		begin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		begin.setSize(450, 200);
		begin.setVisible(true);
		begin.setLocationRelativeTo(null);
	}
}