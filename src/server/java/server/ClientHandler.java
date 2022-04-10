package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private Server server;
    private PrintWriter outMessage;
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 65500;
    private Socket clientSocket = null;
    private static int clientsCount = 0;

    public ClientHandler(Socket socket, Server server) {
        try {
            clientsCount++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                server.sendMessageToAllClients("Новый участник вошел в чат");
                server.sendMessageToAllClients("Клиентов в чате " + clientsCount);
                break;
            }

            while (true) {
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if (clientMessage.equalsIgnoreCase("sessionEnd")) {
                        break;
                    }
                    System.out.println(clientMessage);

                    server.sendMessageToAllClients(clientMessage);

                    Thread.sleep(100);
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            this.close();
        }
    }

    public void sendMessage(String message) {
        try {
            outMessage.println(message);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void close() {
        server.removeClient(this);
        clientsCount--;
        server.sendMessageToAllClients("Клиентов в чате " + clientsCount);
    }
}
