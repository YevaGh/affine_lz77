import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class LZ77Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 9090);
        String data = "abracadabrad";
        System.out.println("Original Data: " + data);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(data);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        List<String> compressedData = (List<String>) objectInputStream.readObject();
        String decompressedData = LZ77D.decompress(compressedData);
        System.out.println("compressed Data: " + compressedData);
        System.out.println("Decompressed Data: " + decompressedData);
        objectOutputStream.close();
        objectInputStream.close();
        socket.close();
    }
}

class LZ77D {
    public static String decompress(List<String> input) {
        StringBuilder output = new StringBuilder();

        for (String token : input) {
            String[] parts = token.split(",");
            int offset = Integer.parseInt(parts[0]);
            int length = Integer.parseInt(parts[1]);
            char nextChar = parts[2].charAt(0);

            if (length == 0) {
                output.append(nextChar);
            } else {
                for (int i = 0; i < length; i++) {
                    int index = output.length() - offset;
                    output.append(output.charAt(index));
                }
                output.append(nextChar);
}
        }
        return output.toString();
        }
        }
