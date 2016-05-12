/*package project;

import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.Queue;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

public class SenderModem {

    static Queue <String> Q;

    public SenderModem(){
        Q= new LinkedList<String>();
    }

    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);

            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);

                OutputStream out = serialPort.getOutputStream();

                (new Thread(new SerialWriter(out))).start();

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }

    void putPacket(String str){

        Q.add(str);
    }

    public static class SerialWriter implements Runnable
    {
        OutputStream out;
        String temp="";

        public SerialWriter ( OutputStream out )
        {
            this.out = out;

        }


        public void run ()
        {
            try
            {
                while(Q.isEmpty());

                temp= Q.remove();
                this.out.write(temp);

                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }
            }
            catch ( IOException e )
            {
                e.printStackTrace();
            }
        }
    }

}
*/