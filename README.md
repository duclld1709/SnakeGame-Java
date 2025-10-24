# 🐍 Console Snake Game

A simple console-based **Java Snake Game** built without any external frameworks.
Just pure Java — compile and run directly from the command line or your favorite IDE.

---

## 📁 Project Structure

```
CONSOLED_SNAKE/
├── src/
│   └── snake/
│       ├── Direction.java
│       ├── Food.java
│       ├── GameFrame.java
│       ├── GamePanel.java
│       ├── InputTracker.java
│       ├── Main.java        ← contains the main() method
│       └── Snake.java
└── bin/
```

---

## 🚀 How to Run (Command Line)

### Step 1: Open the terminal in the project directory

```bash
cd CONSOLED_SNAKE
```

### Step 2: Compile the source files

```bash
javac -d bin src/snake/*.java
```

### Step 3: Run the program

```bash
java -cp bin snake.Main
```

---

## 🧠 Notes

* `-d bin` specifies the destination directory for compiled `.class` files.
* `-cp bin` sets the **classpath** to the folder containing compiled classes.
* Make sure your `Main.java` file includes the line:

  ```java
  package snake;
  ```

---

## 🕹️ Features

* Classic Snake gameplay using simple Java GUI components
* Keyboard input tracking for smooth control
* Lightweight — no external dependencies

---

## 🧰 Requirements

* Make sure **Java (JDK)** version **8 or higher** is installed on your system.
* **Current version used:** JDK 24
* Any IDE or command-line terminal that supports Java can be used to run the project.

---

