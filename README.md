# Java-Swing-Chat-Application

# Chat Application

## Overview

This project is a simple chat application implemented in Java using Swing for the graphical user interface (GUI). It consists of two main components:

- **UserX**: The client-side application that allows users to send messages to a server and display received messages.
- **UserY**: The server-side application that listens for incoming connections from clients, receives messages, and displays them.

The chat application supports basic chat functionalities with a graphical interface featuring custom components and gradient backgrounds.

## Features

- **Client-Side (UserX)**:
  - Connects to the server at `127.0.0.1` on port `6001`
  - Allows users to send messages
  - Displays messages received from the server

- **Server-Side (UserY)**:
  - Listens for incoming connections on port `6001`
  - Receives messages from clients
  - Displays received messages

## Technologies Used

- Java
- Swing (for GUI)
- GradientPaint (for custom backgrounds)
- Java Networking (for client-server communication)

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed on your system
- Basic understanding of Java and networking concepts

### Running the Application

1. **Start the Server (UserY)**:
   - Compile and run the `UserY` class. This will start the server and wait for client connections.
   ```sh
   javac UserY.java
   java UserY
