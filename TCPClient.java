// TCPClient.java
import javax.swing.*;
import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 1234;

        try (Socket socket = new Socket(host, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder html = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                html.append(line);
            }

            // Display in Swing
            JEditorPane editorPane = new JEditorPane("text/html", html.toString());
            editorPane.setEditable(false);

            JFrame frame = new JFrame("Web Page Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new JScrollPane(editorPane));
            frame.setSize(600, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
