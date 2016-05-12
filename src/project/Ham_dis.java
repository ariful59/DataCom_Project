package project;

import java.util.Random;

public class Ham_dis {

    String ret, tmp;
    final String seq[] = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
    final String enc[] = {"0000000", "0001101", "0010111", "0011010", "0100011", "0101110", "0110100", "0111001", "1000110", "1001011", "1010001", "1011100", "1100101", "1101000", "1110010", "1111111"};
    Random rand;
    boolean errorFlag;
    int putErrorCount, gotErrorCount, correctedCount;

    public Ham_dis() {
        ret = "";
        tmp = "";
        rand = new Random();
        putErrorCount = 0;
        gotErrorCount = 0;
        correctedCount = 0;
    }

    public String encode(String str) {

        for (int i = 0; i < str.length(); i += 4) {
            tmp = "";
            int got = -1;

            for (int j = i; j < i + 4; j++) {
                tmp += str.charAt(j);
            }

            for (int j = 0; j < 16; j++) {
                if (seq[j].equals(tmp)) {
                    got = j;
                    break;
                }
            }

            ret += enc[got];
        }

        return ret;
    }

    public String decode(String str) {
        ret = "";
        //boolean prevErr = false;
        for (int i = 0; i < str.length(); i += 7) {
            tmp = "";
            int got = -1;
            
            for (int j = i; j < i + 7; j++) {
                tmp += str.charAt(j);
            }

            errorFlag = isPutError(tmp);

            if (errorFlag) {
                tmp = putError(tmp);
                putErrorCount++;
            }


            for (int j = 0; j < 16; j++) {
                if (enc[j].equals(tmp)) {
                    got = j;
                    break;
                }
            }

            if (got == -1) {
                gotErrorCount++;

                tmp = correction(tmp);


                ret += tmp;
                //prevErr = true;
            } else {
                ret += seq[got];
            }
        }

        return ret;
    }

    private boolean isPutError(String tmp) {

        int x = rand.nextInt(100);

        if (x < 5) {
            return true;
        }
        return false;

    }

    private String putError(String tmp) {

        String out = "";
        int x,y,z=10;

        y = rand.nextInt(2) + 1;

        x = rand.nextInt(7);

        if(y==2){
            z = rand.nextInt(7);
        }

        //System.out.println(x);
        for (int i = 0; i < 7; i++) {

            if (x == i || z==i) {
                char ch = tmp.charAt(i);

                if (ch == '0') {
                    out += "1";
                } else {
                    out += "0";
                }
            } else {
                out += tmp.charAt(i);
            }

          //  tmp = out;
        }
        return out;
    }

    public void printError() {

        System.out.println(putErrorCount + " " + gotErrorCount + " " + correctedCount);

    }

    private String correction(String tmp) {
        String tmp2 = "";
        int i;

        for (i = 0; i < 16; i++) {
            if (distance(tmp, enc[i]) == 1) {
                tmp2 = seq[i];
                correctedCount++;
                break;
            }
        }

        if (i == 16) {
            System.out.println("here");
            tmp2 = tmp.substring(0, 4);
        }

        return tmp2;
    }

    private int distance(String tmp, String str) {
        int change = 0;
        for (int i = 0; i < 7; i++) {
            if (tmp.charAt(i) != str.charAt(i)) {
                change++;
            }
        }

        
        return change;
    }
}
