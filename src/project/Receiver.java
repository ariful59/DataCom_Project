package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Receiver {

    //File fp2 = new File("Lab1_out.txt");
    FileWriter writer = null;
    BufferedReader br = null;
    Random rand = new Random();

    public Receiver() {

        try {
            br = new BufferedReader(new FileReader("temp.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            writer = new FileWriter(new File("out.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        int count = 0;
        char ch;
        String tmp = "";


        while (true) {

            int x = 0;
            try {
                x = br.read();
            } catch (IOException ex) {
            }

            if (x == -1) {
                break;
            }

            ch = (char) x;

            count++;

            tmp = tmp + ch;
            //  System.out.println(tmp);
            if (count == 1400) {

                System.out.println(count);
                count = 0;

                //    System.out.println(tmp);



                CRC C = new CRC();
                tmp = C.decode(tmp);

                if (tmp.equals("")) {
                    try {
                        writer.write("???????????????");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    continue;
                }

                //    System.out.println("before binary: "+tmp);

                BinaryConverter B = new BinaryConverter();
                tmp = B.fromBinary(tmp);

                //     System.out.println("after binary: "+tmp);

                divide(tmp);
                tmp = "";
            }



        }

        if (count != 0) {
            boolean flag = true;

            if (flag) {

                CRC C = new CRC();
                tmp = C.decode(tmp);

                BinaryConverter B = new BinaryConverter();
                tmp = B.fromBinary(tmp);

                divide(tmp);
            }

            tmp = "";
        }

        try {
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    String layer7(String str) {

        String Y;
        int len = str.length();

        Y = str.substring(3, len);

        return Y;
    }

    String layer6(String str) {

        String Y;
        int len = str.length();

        Y = str.substring(3, len);

        return layer7(Y);
    }

    String layer5(String str) {

        String Y;
        int len = str.length();

        Y = str.substring(3, len);

        return layer6(Y);

    }

    String layer4(String str) {

        String Y;
        int len = str.length();

        Y = str.substring(3, len);

        return layer5(Y);
    }

    String layer3(String str) {

        String Y;
        int len = str.length();

        Y = str.substring(3, len);

        return layer4(Y);
    }

    String layer2(String str) {

        String Y;
        int len = str.length();

        System.out.println(len + " length");

        Y = str.substring(3, len - 3);

        return layer3(Y);
    }

    String layer1(String str) {

        String Y;
        int len = str.length();

        //   Y = str.substring(4, len);
        Y = str;

        return layer2(Y);
    }

    private void divide(String str) {

        str = layer1(str);

        try {
            writer.write(str);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
