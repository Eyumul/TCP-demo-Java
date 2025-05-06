// ManualTCPClient.java
import javax.swing.*;
import java.io.*;
import java.net.*;

public class ManualTCPClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;
        String requestPath = "/";

        try (Socket socket = new Socket(host, port)) {
            // Send HTTP GET request manually
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("GET " + requestPath + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Connection: close");
            out.println(); // blank line to end headers

            // Read the HTTP response
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            // Display response in GUI
            JTextArea textArea = new JTextArea(response.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);

            JFrame frame = new JFrame("Manual HTTP GET Response");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(scrollPane);
            frame.setSize(700, 500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
