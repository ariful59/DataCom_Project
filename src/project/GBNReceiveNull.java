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
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;


public class GBNReceiveNull
{

    DataInputStream dIn;
    DataOutputStream dOut;
    Socket socket;
    ServerSocket server;
    Random rand;
    //FileOutputClass fout;
    //Receiver rec;
    boolean EOF;
    Queue<String> Q;
    Thread t;

    GBNReceiveNull() throws IOException, IOException, UnsupportedCommOperationException, PortInUseException, NoSuchPortException
    {
        EOF = false;
        Q = new LinkedList<String>();

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

        //server = new ServerSocket(8080);
        rand = new Random();
        //rec = new Receiver();
        t = new RECTHREAD();
        t.start();
    }

    class RECTHREAD extends Thread
    {

        public void run()
        {
            int R = 0;

            while (true)
            {
                int frame_number = 0;
                try
                {
                    frame_number = dIn.readInt();
                    System.out.println("frame NUMBER"+frame_number);
                } catch (EOFException e)
                {
                    continue;
                } catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (frame_number < 0)
                {
                    try
                    {
                        dOut.writeInt(-1);
                    } catch (IOException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    EOF = true;
                    /*try
                    {
                        Thread.sleep(1000);
                    } catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    */
                    

                    return;
                } else
                {
                    try
                    {
                        int SIZE = dIn.readInt();
                        String str = "";
                        for (int i = 0;i<SIZE ; i++)
                        {
                            str += (char) dIn.read();
                        }
                        if (frame_number == R)
                        {
                            Q.add(str);
                            R++;
                            R %= 5;
                        }
                        System.out.println(str);
                        //if (rand.nextInt(100) >= 5)
                        //{
                            //Thread.sleep(20);
                            dOut.writeInt(R);
                        //}
                    } catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }

        }
    }

    public boolean isEOF()
    {
        return (EOF && Q.isEmpty());
    }

    public String getString()
    {
        if (Q.isEmpty())
        {
            return "";
        }
        return Q.remove();
    }

    public void CLOSE() throws IOException, InterruptedException
    {
        Thread.sleep(100);
        //fout.closeFile();
        t.join();
        dIn.close();
        dOut.close();
       // server.close();
       // socket.close();
        Q.clear();
    }
}

