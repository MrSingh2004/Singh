import java.io.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleWebGet {

    /* Return the current time in HH:MM:SS */
    private static String time() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(Calendar.getInstance().getTime()).toString();
    }

    /* Get and save a web object in a local file. The url of the web object is in urlName */
    public static void httpGet(String urlName) {
        try {
            /* Code here:
               Parse the url to get the host name and path name, and create a local file
               with the same file name as indicated in the url to save the object */
            URL url = new URL(urlName);
            String host = url.getHost();
            String path = url.getPath();
            String fileName = path.endsWith("/") || path.equals("") ? "index.html" : path.substring(path.lastIndexOf('/') + 1);

            /* Code here:
               Print the urlName and parsed host name and path name following the same
               format as wget command */
            System.out.println("URL: " + urlName);
            System.out.println("Host: " + host);
            System.out.println("Path: " + (path.equals("") ? "/" : path));

            /* Code here:
               get the ip address of the host name, handle UnknownHostException, print
               status message or error message accordingly following the same format as wget command */
            System.out.println("Resolving " + host + "...");
            InetAddress ip = InetAddress.getByName(host);
            System.out.println("Connecting to " + host + " (" + ip.getHostAddress() + ")...");

            /* Code here:
               Create a socket to connect to the host at port 80 and send the GET request */
            Socket socket = new Socket(host, 80);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.print("GET " + (path.equals("") ? "/" : path) + " HTTP/1.1\r\n");
            out.print("Host: " + host + "\r\n");
            out.print("Connection: close\r\n");
            out.print("\r\n");
            out.flush();

            /* Code here:
               Read the response headers. The first line is the status line.
               Print the status line. Then skip the remaining header lines */
            InputStream raw = socket.getInputStream();
            ByteArrayOutputStream headerBytes = new ByteArrayOutputStream();
            int prev = 0, curr;
            boolean headerEnd = false;
            while ((curr = raw.read()) != -1) {
                headerBytes.write(curr);
                if (prev == '\r' && curr == '\n') {
                    String headerChunk = headerBytes.toString("ISO-8859-1");
                    if (headerChunk.endsWith("\r\n\r\n")) {
                        headerEnd = true;
                        break;
                    }
                }
                prev = curr;
            }

            String headers = headerBytes.toString("ISO-8859-1");
            BufferedReader headerReader = new BufferedReader(new StringReader(headers));
            String statusLine = headerReader.readLine();
            System.out.println("HTTP request sent, awaiting response...");
            System.out.println(statusLine);

            String line;
            int contentLength = -1;
            while ((line = headerReader.readLine()) != null && !line.isEmpty()) {
                if (line.toLowerCase().startsWith("content-length:")) {
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
            }

            /* Code here:
               Read the HTTP response body and save it into the file.
               Measure the time to calculate and print the speed. */
            ByteArrayOutputStream body = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int count;

            long startTime = System.currentTimeMillis();
            while ((count = raw.read(buffer)) != -1) {
                body.write(buffer, 0, count);
            }
            long endTime = System.currentTimeMillis();

            FileOutputStream fileOut = new FileOutputStream(fileName);
            fileOut.write(body.toByteArray());
            fileOut.close();

            double timeSeconds = (endTime - startTime) / 1000.0;
            if (timeSeconds == 0) timeSeconds = 0.001; // avoid divide by zero
                double speed = body.size() / 1024.0 / timeSeconds;


            System.out.println("Length: " + contentLength + " [" + fileName + "]");
            System.out.println("Saving to: '" + fileName + "'");
            System.out.printf("%d bytes received in %.2f seconds (%.2f KB/s)\n", body.size(), timeSeconds, speed);

            socket.close();

      } catch (UnknownHostException e) {
            System.err.println("Unknown host");
       } catch (IOException e) {
            System.err.println("Error while reading or saving response.");
       }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java SimpleWebGet <URL>");
            System.exit(-1);
        }
        httpGet(args[0]);
    }
}
