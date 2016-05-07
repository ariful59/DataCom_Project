package project;

public class BinaryConverter {

    int count;

    public BinaryConverter() {
        count = 0;
    }

    public String toBinary(String S) {

        char str[] = new char[1000000];
        String ans = "";

        int len = S.length();
        str = S.toCharArray();

        for (int i = 0; i < len; i++) {
            int temp = (int) str[i];

            String tmp = Integer.toBinaryString(temp);

            int remain = 8 - tmp.length();

            for (int j = 0; j < remain; j++) {
                tmp = "0" + tmp;
            }
            ans += tmp;
        }

        return ans;

    }

    public String fromBinary(String S) {
        char str[] = new char[1000000];
        String ans = "";
        int x = 0;
        int len = S.length();
        str = S.toCharArray();
        boolean flag = false;

        for (int i = 0; i < len; i += 8) {
            x = 0;
            flag = false;
            for (int j = 128, k = i; j >= 1; j /= 2, k++) {

                if (str[k] != '0') {
                    x += j;
                }

            }
            if (x >= 0 && x <= 128) {
                char y = (char) x;
                ans += y;
            } else {
                count++;
                ans += "$";
            }

        }
        return ans;

    }
}
