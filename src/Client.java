import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

       try {
            echoSocket = new Socket("localhost", 1234);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: localhost.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: localhost.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        // Read the keys (a and b) from the user
        System.out.print("Enter key a: ");
        int a = Integer.parseInt(stdIn.readLine());
        System.out.println();
        System.out.print("Enter key b: ");
        int b = Integer.parseInt(stdIn.readLine());
        out.println(a);
        out.println(b);

        // Read input from the user and send it to the server
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Server response: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
