# PayloadWin

⚔️ **Колекція користувацьких пейлоадів для Windows**, розроблених у рамках лабораторних досліджень з етичного хакингу, експлуатації, постексплуатації та обходу захисту.

> 🔐 Для навчальних і дослідницьких цілей у ізольованому середовищі.

---

## 🎯 Цілі

- Розробка кастомних пейлоадів (reverse shell, bind shell, beacon тощо)
- Тестування виявлення AV/EDR (Antivirus / Endpoint Detection & Response)
- Обхід AMSI, UAC, Defender
- Підготовка до сертифікацій (OSCP, CRTO, PNPT)
- Поглиблене розуміння внутрішніх механізмів атак

---

## 📁 Структура
PayloadWin/
├── reverse_shell/ # Класичні reverse shell на Java / C#
├── bind_shell/ # Локальні bind shell для вхідного підключення
├── staged_payloads/ # Пейлоади з кількох етапів (stager → loader)
├── obfuscated/ # Пейлоади з обфускацією (XOR, Base64, String Encryption)
├── evasion/ # Техніки обходу EDR/AV: AMSI Bypass, DLL Injection, UAC
├── powershell/ # PowerShell-скрипти та payload'и
├── shellcode_loaders/ # Завантажувачі шеллкодів (C/C++, Java, .NET)
├── persistence/ # Техніки закріплення в системі
├── beacon/ # Симуляція C2-зʼєднань (на кшталт Cobalt Strike)
└── README.md


---

## 🛠️ Використані технології

- Java, PowerShell, C#, Batch
- Netcat, Metasploit, msfvenom
- IntelliJ IDEA
- GitHub CLI (`gh`)
- Kali Linux, Windows 10 VM

---

## ⚠️ Застереження

> Всі приклади призначені **виключно для навчання в контрольованому середовищі** (віртуальні машини, приватна лабораторія).  
> **Заборонено** використання цього коду проти реальних систем без дозволу.  
> Автор не несе відповідальності за неправомірне використання.

---

## 📌 Автор

- **schemchuk** aka `woshe@kali`
- Linux-ентузіаст, дослідник безпеки, практик пентесту
- [GitHub профіль](https://github.com/schemchuk)


