import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(1234);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 1234.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;

        // Read the keys (a and b) from the client
        int a = Integer.parseInt(in.readLine());
        int b = Integer.parseInt(in.readLine());


        while ((inputLine = in.readLine()) != null) {
            // Apply the affine cipher encryption to the input
            String encrypted = affineEncrypt(inputLine, a, b);
            out.println(encrypted);
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }

    // Affine Cipher encryption function
    public static String affineEncrypt(String input, int a, int b) {
        StringBuilder output = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                // Apply the affine transformation
                char encrypted = (char) (((a * (c - 'A')  + b) % 26)+'A');
                output.append(encrypted);
            } else {
                output.append(c);
            }
        }
        return output.toString();
    }
}
