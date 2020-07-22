package Bank;

import Server.Server;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class handles initiating connection to bankAPI ,sending requests to Bank server
 * and also responses from Bank server.
 */
public class BankAPI {
    public static final int PORT = 1;
    public static final String IP = "127.0.0.1";
    private static String request = "";
    private static DataOutputStream dataOutputStream;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static Boolean isThisAnswerWhichShouldSendToServer;

    /**
     * This method is used to add initiating socket and IN/OUT data stream .
     *
     * @throws IOException when IP/PORT hasn't been set up properly.
     */
    public static void ConnectToBankServer() throws IOException {
        try {
            Socket socket = new Socket(IP, PORT);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            System.out.println("connected To Bank Server");
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }

    /**
     * This method is used to start a Thread ,listening on IN data stream.
     */
    public static void StartListeningOnInput() {
        new Thread(() -> {
            while (true) {
                try {
                    String response = inputStream.readUTF();
                    System.out.println(response);
                    if (request.startsWith("create_account")){
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    }
                } catch (IOException e) {
                    System.out.println("disconnected");
                    System.exit(0);
                }
            }
        }).start();
    }

    /**
     * This method is used to send message with value
     *
     * @param msg to Bank server.
     * @throws IOException when OUT data stream been interrupted.
     */
    public static void SendMessage(String msg) throws IOException {
        try {
            outputStream.writeUTF(msg);

        } catch (IOException e) {
            throw new IOException("Exception while sending message:");
        }
    }

    /**
     * This method is used to illustrate an example of using methods of this class.
     */
    public static void main(String[] args) {
        try {
            ConnectToBankServer();
            StartListeningOnInput();
           // Scanner scanner = new Scanner(System.in);
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket socket = null;

            socket = serverSocket.accept();
            while (true) {
                dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                request = dataInputStream.readUTF();
                SendMessage(request);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }


}
