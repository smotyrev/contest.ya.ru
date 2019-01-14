import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaskD {
    private static final boolean DEBUG = false;
//    private static final char A = '(', B = ')';
    private static final char A = '0', B = '1';
    static int highlighted = 0;

    public static void main(String... args) throws IOException {
        BufferedReader br = Files.newBufferedReader(Paths.get("input.txt"));
        int N = Integer.valueOf(br.readLine());
//        if (N > 4) {System.out.println("error"); System.exit(0);}
        byte[] data = new byte[N*2];
        int offset = 0;
        while (offset < Math.max(N-2, 1)) {
            for (int i=0; i < data.length; i++) {
                if (i < offset*2) {
                    data[i] = (byte) (i % 2 == 0 ? A : B);
                } else {
                    data[i] = (byte) (i < N+offset ? A : B);
                }
            }
            if (offset == 0) {
                print(data);
            }
            for (int i=N-offset-(offset==0?1:2); i > 0; i--) {
                if (DEBUG) {
                    System.out.println(" "+offset+" / " + i);
                }
                moveOpeningToEnd(data.clone(), offset*2, i, data.length-1);
            }
            offset++;
        }
    }

    private static void moveOpeningToEnd(byte[] data, int offset, int idx, int limit) {
        if (data[offset+idx] == B) {
            return;
        }
        if (DEBUG) {
            System.out.print("moveOpeningToEnd ");
            for (int i=0; i < offset; i++) System.out.print(' ');
            System.out.print('↥');
            for (int i=1; i < idx; i++) System.out.print(' ');
            System.out.print('↗');
            for (int i=1; i < limit-offset-idx; i++) System.out.print(' ');
            System.out.print('↧');
            System.out.println();
            System.out.println("moveOpeningToEnd "+new String(data)+" / "+offset+" / "+idx+" / "+limit);
        }

        byte[] subData = new byte[0];

        // move opened
        boolean firstClosed = false;
        int openCnt = 0;
        for (int i=idx+offset; i<limit-1; i++) {
            byte next = data[i+1];
            if (next == A) {
                openCnt++;
                continue;
            } else if (!firstClosed) {
                firstClosed = true;
                byte curr = data[idx+offset];
                data[i+1] = curr;
                data[idx+offset] = next;
                if (openCnt > 0) {
                    subData = data.clone();
                }
            } else {
                byte curr = data[i];
                data[i+1] = curr;
                data[i] = next;
            }
            if (DEBUG) {
                highlighted = i+1;
            }
            print(data);
        }

        // move inner
        if (subData.length > 0) {
            int n = (limit - offset) / 2 + 1;
            int idxClose = n+offset+1;
            if (DEBUG) {
                System.out.println("openCnt = "+openCnt+" idxClose = "+idxClose);
                for (int i=0;i<idxClose;i++) System.out.print(' ');
                System.out.println('↖');
                System.out.println(new String(subData)+'⟱');
            }
            moveClosed(subData.clone(), idxClose, limit);
        }
    }

    private static void moveClosed(byte[] data, int idxClose, int limit) {
        int leftClosed = limit - idxClose;
        for (;;) {
            if (data[idxClose-1] == A) {
                data[idxClose-1] = B;
                data[idxClose] = A;
                idxClose--;
            } else {
                break;
            }
        }
        int leftOpen = 0;
        for (int i=idxClose; i>=0; i--) {
            if (data[i] == A) {
                leftOpen++;
            } else {
                leftOpen--;
            }
        }
        if (DEBUG) {
            System.out.println("idxClose = "+ idxClose+" leftClosed = "+leftClosed+" leftOpen = " + leftOpen);
        }
        if (leftOpen < 0) {
            return;
        }
        if (leftClosed > 1) {
            print(data);
        }
        for (int i=(limit-idxClose)/2-1; i>0; i--) {
            if (--leftClosed == 0) {
                return;
            }
            moveOpeningToEnd(data.clone(), idxClose+1, i, limit);
        }
    }

    private static void print(byte[] data) {
        if (DEBUG) {
            for (int i=0; i<data.length; i++) {
                if (highlighted == i+1) {
                    System.out.print('↘');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
        String combo = new String(data);
//        System.out.println(combo);
        System.out.println("'" + combo + "' = " + Integer.parseInt(combo, 2));
    }
}
