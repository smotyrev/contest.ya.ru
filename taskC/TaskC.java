import java.io.*;
import java.util.Arrays;

public class TaskC {

    public static void main(String[] args) throws IOException {
        FileInputStream is = new FileInputStream("input.txt");
        FileReader fr = new FileReader(is);

        BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream("output.txt")),
                10
        );

        int len = fr.readNextNumber();
//        System.out.println("Len is "+len);
        int last = 0;
        int limit = 500;
        StringBuilder sb = new StringBuilder();
        int num = 0;
        for (int i=0; i<len; i++) {
            num = fr.readNextNumber();
            if (i == 0) {
                last = num;
                sb.append(num);
            } else if (last != num) {
                last = num;
                sb.append('\n');
                sb.append(num);
            }
            if (i%limit == 0) {
                bw.write(sb.toString());
                sb.setLength(0);
            }
        }
//        System.out.println(num);
        bw.write(sb.toString());
        bw.close();
        is.close();
    }

    static class FileReader {
        private static final char[] NUMBERS = {'0','1','2','3','4','5','6','7','8','9'};
        private static final int BUFFER_SIZE = 30;
        final byte[] buffer = new byte[BUFFER_SIZE];
        final byte[] nbuffer = new byte[11];
        int bufferPointer = BUFFER_SIZE;
        final FileInputStream stream;

        FileReader(FileInputStream is) { stream = is; }

        int readNextNumber() throws IOException {
            Arrays.fill(nbuffer, (byte) 0);
            boolean negative = false;
            byte rbyte;
            int i = nbuffer.length - 1, res = 0;

            while ((rbyte = readByte()) != -1 && i >= 0) {
                if (rbyte >= NUMBERS[0] && rbyte <= NUMBERS[9]) {
                    nbuffer[i] = rbyte;
                    i--;
                } else if (rbyte == '-') {
                    negative = true;
                } else  {
                    break;
                }
            }
            i=1;
            for (byte b : nbuffer) {
                if (b == 0) continue;
                res += i * getNumericValue(b);
                i *= 10;
            }
            return negative ? -res : res;
        }

        private byte readByte() throws IOException {
            if (bufferPointer > BUFFER_SIZE - 1) {
                int bytesRead = stream.read(buffer);
                if (bytesRead == -1) {
                    return (byte) -1;
                } else if (bytesRead < BUFFER_SIZE) {
//                    System.out.println("BEFORE: "+new String(buffer));
                    for (int i = bytesRead; i < BUFFER_SIZE; i++) {
                        buffer[i] = 0;
                    }
//                    System.out.println("AFTER: "+new String(buffer));
                }
                bufferPointer = 0;
            }
            return buffer[bufferPointer++];
        }

        private int getNumericValue(byte numByte) {
            for (int i=0; i<NUMBERS.length; i++) if (NUMBERS[i] == numByte) return i;
            return -1;
        }
    }
}
