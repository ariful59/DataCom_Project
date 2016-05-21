/*package project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;


import javax.swing.JOptionPane;

class Framing
{

    public long tim;
    public int f_number;
    public String data;

    Framing(int a, long b, String c)
    {
        f_number = a;
        tim = b;
        data = c;
    }
}

public class GBNSenderNull
{

    DataInputStream dIn;
    DataOutputStream dOut;
    Socket socket;
    Queue<Framing> Q;
    //Queue <String> saveingInp;
    long frame_time;
    int frame_number;
    Thread t;
    FrameTime timing;
    boolean EOF;

    GBNSenderNull() throws UnknownHostException, IOException, UnsupportedCommOperationException, PortInUseException, NoSuchPortException
    {
        //String SADD = JOptionPane.showInputDialog("Enter IP Address(keep null for local)\n");
        //if(SADD.length() == 0 || SADD == null)SADD = "127.0.0.1";
        //SADD = "192.168.43.46";
        System.out.println("HERE");
        //socket = new Socket(SaveSettings.IPADDRESS, 8080);

        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM1");
        if (portIdentifier.isCurrentlyOwned())
        {
            System.out.println("Error: Port is currently in use");
        } else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort)
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

                dOut = new DataOutputStream(serialPort.getOutputStream());
                dIn = new DataInputStream(serialPort.getInputStream());

            } else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }

        System.out.println("connect port");

        Q = new LinkedList<Framing>();
        //saveingInp = new LinkedList<String>();
        frame_number = 0;
        EOF = false;
        t = new ACK();
        t.start();
        timing = new FrameTime();
        timing.start();
    }

    class ACK extends Thread
    {

        public void run()
        {
            System.out.println("thread running");
            int ack_received = 0;
            while (true)
            {
                try
                {
                    ack_received = dIn.readInt();
                    System.out.println("ACK : " + ack_received + " Frame Size :" + Q.size());
                    if (ack_received < 0)
                    {
                        synchronized (Q)
                        {
                            while (true)
                            {
                                //synchronized(Q){
                                if (Q.isEmpty())
                                {
                                    break;
                                }
                                //}
                                Q.remove();
                                //Thread.yield();
                            }
                        }
                        return;
                    }
                } catch (IOException e)
                {
                    continue;
                }
                synchronized (Q)
                {
                	int sz = Q.size();
					int loc = 0,take = -1;
					for(int i=0;i<sz;i++){
						Framing copy = Q.remove();
						if(take == -1 && copy.f_number==ack_received)take = loc;
						loc++;
						Q.add(copy);
					}
					if(take==-1)Q.clear();
					else{
						for(int i=0;i<=take;i++)Q.remove();
					}
                }
                try
                {
                    Thread.sleep(100);
                } catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public void send(String str) throws IOException, InterruptedException
    {
        if (str.equals("*"))
        {
            EOF = true;
        }
        if (EOF)
        {

            while (!Q.isEmpty())
            {
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
        while (!flag)
        {
            synchronized (Q)
            {
                if (Q.size() < 4)
                {
                    flag = true;
                }
            }
            Thread.sleep(50);
            System.out.println("..");
            //Thread.yield();
        }
        System.out.println("frame_number : " + frame_number);
       
        dOut.writeInt(frame_number%8);
        dOut.writeInt((int) str.length());
        dOut.writeBytes(str);

        synchronized (Q)
        {
            Q.add(new Framing(frame_number%8, System.currentTimeMillis() % 10000, str));
        }
        frame_number++;
        frame_number %= 8;
    }

    public void CLOSE() throws IOException, InterruptedException
    {
        Thread.sleep(100);
        t.join();
        timing.join();
        dOut.close();
        dIn.close();
//        socket.close();

    }

    class FrameTime extends Thread
    {

        public boolean thread_cutoff;

        FrameTime()
        {
            thread_cutoff = false;
        }

        public void run()
        {
            while (!thread_cutoff)
            {
                synchronized (Q)
                {
                    if (!Q.isEmpty())
                    {
                        Framing f = Q.peek();
                        long cur_time = System.currentTimeMillis() % 10000;
                        if (cur_time - f.tim > 500)
                        {
                            int SZ = Q.size();
                            for (int i = 0; i < SZ; i++)
                            {
                                f = Q.remove();
                                try
                                {
                                    
                                    
                                    dOut.writeInt(f.f_number);
                                    dOut.writeInt((int) f.data.length());
                                    dOut.writeBytes(f.data);
                                } catch (IOException e)
                                {
                                    System.out.println(e);
                                }
                                f.tim = (System.currentTimeMillis() % 10000);
                                Q.add(f);
                            }
                        }
                    }
                }
            }
        }
    }
 }*/