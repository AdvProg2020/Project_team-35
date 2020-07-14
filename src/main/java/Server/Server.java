package Server;

import Controller.Exceptions.NotValidRequestIdException;
import Controller.ManagerBoss;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                    if (input.charAt(0) == 'M') {
                        if (input.startsWith("MRequests")) {
                            handleManagerRequestsRequests(input);
                        }
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

        private void handleManagerRequestsRequests(String input) {
            //should send response to client
            String requestText = input.substring(9);
            if (requestText.startsWith("acceptRequest")) {
                try {
                    acceptRequestWithId(requestText);
                    sendMessageToClient("Successful :)");
                } catch (InvalidRequestException | NotValidRequestIdException e) {
                    sendMessageToClient(e.getMessage());
                }
            }
            else if (requestText.startsWith("declineRequest")) {
                try {
                    declineRequestWithId(requestText);
                    sendMessageToClient("Successful :)");
                } catch (InvalidRequestException | NotValidRequestIdException e) {
                    sendMessageToClient(e.getMessage());
                }
            }
            else if (requestText.equalsIgnoreCase("getCheckedRequests")) {

            }
            else if (requestText.equalsIgnoreCase("getUncheckedRequests")) {

            }

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
    public static Matcher getMatcher(String input , String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    private static void acceptRequestWithId(String command) throws InvalidRequestException, NotValidRequestIdException {
        Matcher matcher = getMatcher(command, "^acceptRequest(\\d+)$");
        if (matcher.matches()) {
            ManagerBoss.acceptRequestWithId(Integer.parseInt(matcher.group(1)));
        }
        else {
            throw new InvalidRequestException("Invalid Request Format: " + command);
        }
    }
    private static void declineRequestWithId(String command) throws InvalidRequestException, NotValidRequestIdException {
        Matcher matcher = getMatcher(command, "^declineRequest(\\d+)$");
        if (matcher.matches()) {
            ManagerBoss.declineRequestWithId(Integer.parseInt(matcher.group(1)));
        }
        else {
            throw new InvalidRequestException("Invalid Request Format: " + command);
        }
    }
}
