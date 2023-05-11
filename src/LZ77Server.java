import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LZ77Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9090);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            String data = (String) objectInputStream.readObject();

            List<String> compressedData = LZ77.compress(data);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(compressedData);

            objectInputStream.close();
            objectOutputStream.close();
            clientSocket.close();
        }
    }
}

class LZ77 {
    public static List<String> compress(String input) {
        List<String> output = new ArrayList<>();

        int inputLength = input.length();
        int lookAheadBuffer = 4;
        int searchBuffer = inputLength - lookAheadBuffer;
        int pointer = 0;

        while (pointer < inputLength) {
            int searchLimit = Math.max(pointer - searchBuffer, 0);
            int maxLength = 0;
            int maxMatchIndex = -1;

            for (int i = pointer - 1; i >= searchLimit; i--) {
                int matchLength = 0;

                while (pointer + matchLength < inputLength && input.charAt(i + matchLength) == input.charAt(pointer + matchLength)) {
                    matchLength++;
                }

                if (matchLength > maxLength) {
                    maxLength = matchLength;
                    maxMatchIndex = i;
                }
            }

            if (maxLength == 0) {
                output.add("0,0," + input.charAt(pointer));
                pointer++;
            } else {
                int offset = pointer - maxMatchIndex;
                output.add(offset + "," + maxLength + "," + input.charAt(pointer + maxLength));
                pointer += maxLength + 1;
            }
        }

        return output;
    }
}
