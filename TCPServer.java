// TCPServer.java
import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        int port = 1234;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running on port " + port + "...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                // HTML Content (very basic)
                String basePath = "C:/Users/Eyuel/working space/advanced-network-assignment/resources/"; // use absolute path here
                String html = "<html><body>";
                for (int i = 1; i <= 5; i++) {
                    html += "<p>Line " + i + ": <img src='file:///" + basePath + "img" + i + ".jpg' width='100' height='100'></p>";
                }
                html += "</body></html>";


                out.println(html);
                out.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
