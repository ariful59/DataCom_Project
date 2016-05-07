package project;

import java.util.Random;

public class CRC {

    String ret, tmp;
    final String divisor = "100000100110000010001110110110111";
    Random rand;
    boolean errorFlag;
    int putErrorCount, gotErrorCount, correctedCount;

    public CRC() {
        ret = "";
        tmp = "";
        rand = new Random();
        putErrorCount = 0;
        gotErrorCount = 0;
        correctedCount = 0;
    }

    public String encode(String str) {

        ret = str;

        return ret + crc_start(str + "00000000000000000000000000000000");
    }

    public String decode(String str) {

        int len = str.length();

        if (isPutError(str)) {
            System.out.println("error is put");
            str = putError(str);
        }

        if (crc_start(str).equals("00000000000000000000000000000000")) {

            return str.substring(0, len - 32);
        }

        System.out.println("error");
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
        int x, y, z = 10;

        y = rand.nextInt(100);

        if (y < 50) {

            System.out.println("hereh here");
            x = rand.nextInt(1200);

            System.out.println("x is :" + x);

            if (tmp.charAt(x) == '1') {
                out = "0";
            } else {
                out = "1";
            }

            return tmp.substring(0, x) + out + tmp.substring(x + 1);

        } else {

            z = rand.nextInt(20) + 1;

            for (int i = 0; i < z; i++) {
                x = rand.nextInt(1200);

                System.out.println("x is :" + x);

                if (tmp.charAt(x) == '1') {
                    out = "0";
                } else {
                    out = "1";
                }

                tmp = tmp.substring(0, x) + out + tmp.substring(x + 1);

            }
            return tmp;
        }



    }

    public void printError() {

        System.out.println(putErrorCount + " " + gotErrorCount + " " + correctedCount);

    }

    private String crc_start(String data) {

        String result = "";
        int len = data.length();
        result = div(data.substring(0, 33));

        for (int i = 33; i < len; i++) {
            result = div(result + data.charAt(i));
        }

        System.out.println(result);
        return result;
    }

    private char XOR(char c1, char c2) {
        return (c1 == c2) ? '0' : '1';
    }

    private String div(String s1) {
        String remainder = "";
        int len = s1.length();
        if (s1.charAt(0) == '0') {

            return s1.substring(1, len);
        }

        for (int i = 1; i < len; i++) {
            remainder += XOR(s1.charAt(i), divisor.charAt(i));
        }
        //System.out.println("div="+divisor);
        return remainder;
    }
}
