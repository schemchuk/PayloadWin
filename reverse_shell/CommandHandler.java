package reverse_shell;

import java.net.Socket;

public interface CommandHandler {
    void handle(String[] args, Socket socket) throws Exception;
}