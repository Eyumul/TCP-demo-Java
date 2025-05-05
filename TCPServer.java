// TCPServer.java
import java.io.*;
import java.net.*;
import java.nio.file.Files;

public class TCPServer {
    public static void main(String[] args) {
        int port = 1234;
        String basePath = "C:/Users/Eyuel/working_space/advanced-network-assignment/resources/";

        // Array of topic objects
        Topic[] topics = {
            new Topic("Data Center Network Architecture",
                "Data centers are the backbone of cloud services, providing storage, computation, and networking at scale. Their network architecture includes core, aggregation, and access layers designed to ensure high availability, scalability, and low latency. Common designs like leaf-spine topology are used to enable predictable performance and redundancy. Images for this topic often feature detailed network diagrams showing server racks, switches, routers, and cabling paths within large-scale data center infrastructures.",
                "img1.jpg"
            ),
            new Topic("5G Network Infrastructure",
                "5G is the fifth generation of wireless technology, enabling faster data rates, lower latency, and massive device connectivity. Its infrastructure includes small cells, macro towers, edge computing units, and centralized radio access networks (C-RAN). The use of millimeter waves and network slicing allows 5G to support diverse applications from autonomous cars to remote surgery. Visuals typically include antenna towers, edge devices, and layered architecture diagrams illustrating the different components of a 5G ecosystem.",
                "img2.jpg"
            ),
            new Topic("Optical Fiber Communication Systems",
                "Optical fiber systems transmit data using light pulses through thin strands of glass or plastic. They are essential for high-speed internet, long-distance communication, and backbone infrastructure. These systems offer low signal loss, high bandwidth, and immunity to electromagnetic interference. Visual aids often depict cross-sections of fiber optic cables, light refraction paths, and transmission equipment such as optical transmitters, receivers, and amplifiers.",
                "img3.jpg"
            ),
            new Topic("Internet of Things (IoT) Networking",
                "IoT networking connects everyday devices—like smart home appliances, wearables, and industrial sensors—to the internet, enabling them to collect, send, and receive data. It relies on various technologies like Wi-Fi, Bluetooth, Zigbee, and cellular networks to ensure seamless communication between devices and cloud platforms. Diagrams for IoT often show smart environments (homes, cities, factories) with interconnected devices, sensors, gateways, and cloud services.",
                "img4.jpg"
            ),
            new Topic("Cloud Networking and Virtual Private Clouds (VPCs)",
                "Cloud networking involves the management of network resources within cloud environments like AWS, Azure, or Google Cloud. A Virtual Private Cloud (VPC) allows users to create isolated network segments within a public cloud, complete with customizable subnets, firewalls, and routing rules. Cloud network diagrams are common visuals, usually showing components like subnets, route tables, NAT gateways, firewalls, and connections between cloud and on-premise systems.",
                "img5.jpg"
            )
        };
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("HTTP Server running on http://localhost:" + port + "/");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                OutputStream outStream = clientSocket.getOutputStream();
                PrintWriter out = new PrintWriter(outStream, true);

                // Read request line
                String requestLine = in.readLine();
                if (requestLine == null || !requestLine.startsWith("GET")) {
                    clientSocket.close();
                    continue;
                }

                // Extract the requested path
                String path = requestLine.split(" ")[1];
                System.out.println("Requested: " + path);

                if (path.equals("/") || path.equals("/index.html")) {
                    // Send HTML
                    // HTML structure with styles
                    String html = "<html><head><style>"
                    + "body { font-family: Arial, sans-serif; background-color: #dddddd; padding: 20px; }"
                    + ".topic { background: #ffffff; padding: 15px; margin-bottom: 20px;}"
                    + ".topic h2 { margin-top: 4px; color: #467ffa; }"
                    + ".topic p { color: #555555; line-height: 1.6; }"
                    + "</style></head><body>";

                    // Loop through topics to add them to HTML
                    for (int i = 0; i < topics.length; i++) {
                        Topic t = topics[i];
                        html += "<div class='topic'>"
                            + "<img width='140' height='100' src='/img" + (i + 1) + ".jpg'>"
                            + "<h2>" + (i+1) + ". " + t.title + "</h2>"
                            + "<p>" + t.description + "</p>"
                            + "</div>";
                    }

                    html += "</body></html>";

                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: text/html");
                    out.println("Content-Length: " + html.getBytes().length);
                    out.println("Connection: close");
                    out.println();
                    out.print(html);
                    out.flush();

                } else if (path.matches("/img[1-5]\\.jpg")) {
                    // Serve image
                    String imageName = path.substring(1); // remove leading /
                    File imageFile = new File(basePath + imageName);
                    if (imageFile.exists()) {
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());

                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-Type: image/jpeg");
                        out.println("Content-Length: " + imageData.length);
                        out.println("Connection: close");
                        out.println();
                        out.flush();

                        outStream.write(imageData);
                        outStream.flush();
                    } else {
                        send404(out);
                    }
                } else {
                    send404(out);
                }

                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void send404(PrintWriter out) {
        out.println("HTTP/1.1 404 Not Found");
        out.println("Content-Type: text/plain");
        out.println("Connection: close");
        out.println();
        out.println("404 Not Found");
        out.flush();
    }
}

// Topic class to hold title, description, and image
class Topic {
    String title;
    String description;
    String image;

    Topic(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }
}
