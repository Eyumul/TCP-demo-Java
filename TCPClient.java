// TCPClient.java
import javax.swing.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Let JEditorPane handle the HTTP GET request and rendering
            JEditorPane editorPane = new JEditorPane();
            editorPane.setEditable(false);
            editorPane.setContentType("text/html");
            editorPane.setPage("http://localhost:1234/");  // loads full page with images

            JFrame frame = new JFrame("HTTP Page Viewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new JScrollPane(editorPane));
            frame.setSize(600, 700);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
