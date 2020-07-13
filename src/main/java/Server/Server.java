package Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket;
        while (true) {
            socket = serverSocket.accept();
            System.out.println("new client connected to server");
            new handle(new DataInputStream(new BufferedInputStream(socket.getInputStream()))
                    , new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))).start();
        }
    }


    static class handle extends Thread {
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;

        public handle(DataInputStream dataInputStream, DataOutputStream dataOutputStream) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
        }

        @Override
        public void run() {
            String input;
            while (true) {
                try {
                    input = dataInputStream.readUTF();
                    if (input.startsWith("M")) {
                        handleManagerRequests(input);
                    }
                    else if (input.startsWith("S")) {
                        handleSellerRequests(input);
                    }
                    else if (input.startsWith("C")) {
                        handleCustomerRequests(input);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void handleManagerRequests(String input) {
            //should send response to client
        }

        private void handleCustomerRequests(String input) {
            //should send response to client
        }
        private void handleSellerRequests(String input) {
            //should send response to client
        }



        private void sendMessageToClient(String message) {
            try {
                dataOutputStream.writeUTF(message);
                dataOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
