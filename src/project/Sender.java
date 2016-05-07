package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Sender {

    File fp = new File("temp.txt");
    FileWriter writer = null;
    BufferedReader br = null;
    // StringBuilder tmp = new StringBuilder("");
    Boolean finish1 = true, finish2 = false, finish3 = false, finish4 = false,finish5 = false,finish6 = false;
    Boolean finished = false;
    String tmp = "";
    String extra="";

    public Sender() {
        
        tmp = "";
        try {
            br = new BufferedReader(new FileReader("in.txt"));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            writer = new FileWriter(fp);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str1 = "", str2 = "", str3 = "", str4 = "", str;

        int counter = 0;

        while (!finished) {
            finish1=true;
            str="";
            
            str = readFromFile(0);
            
            counter++;
            
            CRC C = new CRC();
            str = C.encode(str);

            try {
                writer.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String layer7(String str) {

        String X = "A-H";
        X = X + str;

        return layer6(X);
    }

    String layer6(String str) {

        String X = "P-H";
        X = X + str;

        return layer5(X);
    }

    String layer5(String str) {

        String X = "S-H";
        X = X + str;

        return layer4(X);
    }

    String layer4(String str) {

        String X = "T-H";
        X = X + str;

        return layer3(X);
    }

    String layer3(String str) {

        String X = "N-H";
        X = X + str;

        return layer2(X);
    }

    String layer2(String str) {

        String X = "D-H";
        String Y = "D-T";

        X = X + str + Y;
        return layer1(X);
    }

    String layer1(String str) {

       // String X = "PH-H";

       // X = X + str;

        BinaryConverter B = new BinaryConverter();
      //  X = B.toBinary(X);
        str= B.toBinary(str);

       // return X;
        return str;
    }

    String readFromFile(int fileNo) {

        int count = 0;
        char ch;
        boolean finish = false;
        String tmp="";

        while (count < 150) {

            int x = 0;
            try {
                x= br.read();
            } catch (IOException ex) {
            }

            if (x == -1) {
                finish = true;

                finished=true;

                break;
            }

            if (x < 0 || x > 128) {
                continue;
            }

            ch = (char) x;

            count++;

            tmp += ch;            
        }

        if (count < 150 || finish == true) {
            //String str= tmp.toString();
            //  System.out.println(tmp);

            for (int i = count; i < 150; i++) {
                tmp += " ";
            }

            //  layer7(tmp);
        }

        tmp = layer7(tmp);
       
        return tmp;
    }

    
}
