import java.io.*;
import java.util.Arrays;

public class TaskC {

    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream("input.txt");
        FileReader fr = new FileReader(is);

        int len = fr.readNextNumber();
        //System.out.println("Len is "+len);
        Integer last = null;
        for (int i=0; i<len; i++) {
            int num = fr.readNextNumber();
            if (last == null || !last.equals(num)) {
                last = num;
//                System.out.println(num);
            }
        }
        is.close();
    }

    static class FileReader {
        private static final char[] NUMBERS = {'0','1','2','3','4','5','6','7','8','9'};
        private static final int BUFFER_SIZE = 100;
        final char[] buffer = new char[BUFFER_SIZE];
        int bufferPointer = -1; // -1 invalid
        final FileInputStream stream;

        FileReader(FileInputStream is) { stream = is; }

        int readNextNumber() throws IOException {
            boolean negative = false;
            Arrays.fill(buffer, (char) 0);
            int rbyte, i = buffer.length-1, res = 0;
            while ((rbyte = stream.read()) != -1 && i >= 0) {
                if (rbyte >= NUMBERS[0] && rbyte <= NUMBERS[9]) {
                    buffer[i] = (char) rbyte;
                    i--;
                } else if (rbyte == '-') {
                    negative = true;
                } else  {
                    break;
                }
            }
            i=1;
            for (char c : buffer) {
                if (c == 0) continue;
                res += i * getNumericValue(c);
                i *= 10;
            }
            return negative ? -res : res;
        }

        private int getNumericValue(char numChar) {
            for (int i=0; i<NUMBERS.length; i++) if (NUMBERS[i] == numChar) return i;
            return -1;
        }
    }
}
