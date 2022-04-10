package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Server {
    private final Logger logger = Logger.getLogger(Server.class);
    // порт, прослушивающий сервер
    private static final int PORT = 65500;
    // лист, в который будут добавляться клиенты, подключившиеся к серверу
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public Server() {
        Socket clientSocket = null;
        ServerSocket serverSocket = null;
        try {
            logger.debug("Server is up");
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен");
            while (true) {
                clientSocket = serverSocket.accept();
                ClientHandler client = new ClientHandler(clientSocket, this);
                clients.add(client);
                new Thread(client).start();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Сервер остановлен");
                serverSocket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void sendMessageToAllClients(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

}