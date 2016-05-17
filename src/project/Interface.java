package project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.UnsupportedCommOperationException;

import javax.swing.*;

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
					dispose();
					receiverInterface rInterface = new receiverInterface();
					rInterface.setSize(450,400);
					rInterface.setLocationRelativeTo(null);
					rInterface.setVisible(true);
					rInterface.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		private JComboBox blockCoding;
		private JComboBox physical;
		
		private JLabel textShow;
		private String strdataLinkScheme[] = {"Hamming Distance" , "CRC"};
		private String strdataLinkProtocol[] = {"Go back n"};
		private String strBlockCoding[] = {"4B/5B"};
		private String strphysical[] ={"NRZ-L","NRZ-I","RZ","Manchester","Differential Manchester"};
		private JButton WireLess , Wired; 
		SendInterface(){
			super("Sender");
			setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
			
			//Default SaveSettings
			SaveSettings.SAVE_DATALINK_PROTOCOL=0;
			SaveSettings.SAVE_DATALINK_SCHEME=0;
			SaveSettings.SAVE_PHYSICALLINK=0;
			SaveSettings.SAVE_BLOCK_CODING=0;
			
			textShow = new JLabel();
			textShow.setText("This is sender side. Select Datalink and Physical Layer:");
			textShow.setFont(new Font("Italic",Font.ITALIC,15));
			add(textShow);
			
			textShow = new JLabel();
			textShow.setText("Select Datalink Control Protocol");
			add(textShow);
			
			dataLinkProtocol = new JComboBox(strdataLinkProtocol);
			dataLinkProtocol.setMaximumRowCount(1);
			dataLinkProtocol.setSelectedIndex(0); //default selection
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
			dataLinkScheme.setSelectedIndex(0); //this is default
			dataLinkScheme.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = dataLinkScheme.getSelectedIndex();
					SaveSettings.SAVE_DATALINK_SCHEME = index;
				}
			});
			add(dataLinkScheme);
			
			
			textShow = new JLabel();
			textShow.setText("Select Block Coding Scheme");
			add(textShow);
			
			blockCoding = new JComboBox(strBlockCoding);
			blockCoding.setMaximumRowCount(1);
			blockCoding.setSelectedIndex(0); //this is default
			blockCoding.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = blockCoding.getSelectedIndex();
					SaveSettings.SAVE_BLOCK_CODING= index;
				}
			});
			add(blockCoding);


			textShow = new JLabel();
			textShow.setText("Select Line Coding Scheme");
			textShow.setHorizontalAlignment(SwingConstants.CENTER);;
			add(textShow);
			
			physical = new JComboBox(strphysical);
			physical.setMaximumRowCount(5);
			physical.setSelectedIndex(0); //default selection
			
			physical.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = physical.getSelectedIndex();
					SaveSettings.SAVE_PHYSICALLINK = index;
					System.out.println(SaveSettings.SAVE_PHYSICALLINK);
				}
			});
			add(new JScrollPane(physical));
			
			WireLess = new JButton("LAN CONNECTION");
			WireLess.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Sender side:\nMode: "+SaveSettings.SAVE_MODE);
					System.out.println("Protocol Number: "+SaveSettings.SAVE_DATALINK_PROTOCOL);
					System.out.println("Scheme Number: "+SaveSettings.SAVE_DATALINK_SCHEME);
					System.out.println("PhysicalLink Number: "+SaveSettings.SAVE_PHYSICALLINK);
					dispose();
					SenderMain crc = new SenderMain();
					try{
						crc.doJob();
					}catch(IOException excep){
						System.out.println(excep);
					}
					
				}
				
			});
			add(WireLess);
			
			Wired = new JButton("NULL-MODEM CONNECTION");
			Wired.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
                                        SenderMainNull send = new SenderMainNull();
                                        try{
                        try
                        {
                            send.doJob();
                        } catch (UnknownHostException ex)
                        {
                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (UnsupportedCommOperationException ex)
                        {
                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (PortInUseException ex)
                        {
                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchPortException ex)
                        {
                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                        }
                                        }catch(IOException excep){
                                            System.out.println(excep);
                                        }

				}
				
			});
			add(Wired);
		}
	}
	
	class receiverInterface extends JFrame{
		
		private JComboBox dataLinkScheme;
		private JComboBox dataLinkProtocol;
		private JComboBox physical;
		private JComboBox blockCoding;
		
		private JLabel textShow;
		private String strdataLinkScheme[] = {"Hamming Distance" , "CRC"};
		private String strdataLinkProtocol[] = {"Go back n"};
		private String strBlockCoding[] = {"4B/5B"};
		private String strphysical[] ={"NRZ-L","NRZ-I","RZ","Manchester","Differential Manchester"};
		private JButton WireLess,Wired;
		
		receiverInterface(){
			
			super("Receiver");
			setLayout(new FlowLayout(FlowLayout.CENTER,10,30));
			
			//Default SaveSettings
			SaveSettings.SAVE_DATALINK_PROTOCOL=0;
			SaveSettings.SAVE_DATALINK_SCHEME=0;
			SaveSettings.SAVE_PHYSICALLINK=0;
			SaveSettings.SAVE_BLOCK_CODING=0;
			
			textShow = new JLabel();
			textShow.setText("This is receiver side. Select Datalink and Physical Layer:");
			textShow.setFont(new Font("Italic",Font.ITALIC,15));
			add(textShow);
			
			textShow = new JLabel();
			textShow.setText("Select Datalink Control Protocol");
			add(textShow);
			
			dataLinkProtocol = new JComboBox(strdataLinkProtocol);
			dataLinkProtocol.setMaximumRowCount(1);
			dataLinkProtocol.setSelectedIndex(0); //default selection
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
			dataLinkScheme.setSelectedIndex(0); //this is default
			dataLinkScheme.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = dataLinkScheme.getSelectedIndex();
					SaveSettings.SAVE_DATALINK_SCHEME = index;
				}
			});
			add(dataLinkScheme);
			
			textShow = new JLabel();
			textShow.setText("Select Block Coding Scheme");
			add(textShow);
			
			blockCoding = new JComboBox(strBlockCoding);
			blockCoding.setMaximumRowCount(0);
			blockCoding.setSelectedIndex(0); //this is default
			blockCoding.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = blockCoding.getSelectedIndex();
					SaveSettings.SAVE_BLOCK_CODING= index;
				}
			});
			add(blockCoding);

			textShow = new JLabel();
			textShow.setText("Select Line Coding Scheme");
			textShow.setHorizontalAlignment(SwingConstants.CENTER);;
			add(textShow);
			
			physical = new JComboBox(strphysical);
			physical.setMaximumRowCount(5);
			physical.setSelectedIndex(0); //default selection
			
			physical.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = physical.getSelectedIndex();
					SaveSettings.SAVE_PHYSICALLINK = index;
					System.out.println(SaveSettings.SAVE_PHYSICALLINK);
				}
			});
			add(new JScrollPane(physical));
			
			WireLess = new JButton("LAN CONNECTION");
			WireLess.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.out.println("Receiver side:\nMode: "+SaveSettings.SAVE_MODE);
					System.out.println("Protocol Number: "+SaveSettings.SAVE_DATALINK_PROTOCOL);
					System.out.println("Scheme Number: "+SaveSettings.SAVE_DATALINK_SCHEME);
					System.out.println("PhysicalLink Number: "+SaveSettings.SAVE_PHYSICALLINK);
					dispose();
					ReceiverMain crc = new ReceiverMain();
					try{
						crc.doJob();
					}catch(Exception excep){
						System.out.println(excep);
					}
					
				}
				
			});
			add(WireLess);
			
			Wired = new JButton("NULL-MODEM CONNECTION");
			Wired.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					ReceiverMainNull receive = new ReceiverMainNull();
                                        try
                                        {
                                            receive.doJob();
                                        } catch (IOException ex)
                                        {
                                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (UnsupportedCommOperationException ex)
                                        {
                                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (PortInUseException ex)
                                        {
                                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                                        } catch (NoSuchPortException ex)
                                        {
                                            Logger.getLogger(Interface.class.getName()).log(Level.SEVERE, null, ex);
                                        }

				}
				
			});
			add(Wired);
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
