import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TaskB {

    public static void main(String[] args) throws IOException {
        BufferedReader r = Files.newBufferedReader(Paths.get("input.txt"));
        int longest = 0, current = 0;
        String line = r.readLine();
        int len = Integer.valueOf(line);
        for (int i=0; i<len; i++) {
            line = r.readLine();
            int num = Integer.valueOf(line);
            if (num == 1) {
                current++;
            } else if (current > longest) {
                longest = current;
                current = 0;
            } else {
                current = 0;
            }
        }
        r.close();
        System.out.println(current > longest ? current : longest);
    }
}
