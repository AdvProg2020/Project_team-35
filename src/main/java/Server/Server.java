package Server;

import Controller.AccountBoss;
import Controller.Exceptions.*;
import Controller.ManagerBoss;
import Controller.SellerBoss;
import Model.Account;
import Model.Customer;
import Model.Manager;
import Model.Seller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
                    , new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())), socket).start();
        }
    }


    static class handle extends Thread {
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private Socket socket;

        public handle(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.socket = socket;
        }

        @Override
        public void run() {
            String input;
            while (true) {
                try {
                    input = dataInputStream.readUTF();
                    System.out.println("from client:" + input);
                    if (input.charAt(0) == 'M') {
                        if (input.startsWith("MRequests")) {
                            handleManagerRequestsRequests(input);
                        } else if (input.startsWith("MNewManager")) {
                            handleManagerRequestsNewManager(input);
                        }
                    } else if (input.startsWith("S")) {
                        handleSellerRequests(input);
                    } else if (input.startsWith("C")) {
                        handleCustomerRequests(input);
                    } else if (input.startsWith("R")) {
                        register(input);
                    } else if (input.startsWith("L")) {
                        login(input);
                    }else if (input.startsWith("B")){
                        beginning();
                    }else if (input.equalsIgnoreCase("GetOnlineAccount")){
                        getOnlineAccount();
                    }else if (input.startsWith("AddOff")){
                        addOff(input);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void addOff(String input) throws  IOException {
            String max = input.substring(input.indexOf(",")+1,input.indexOf("-"));
            String percent = input.substring(input.indexOf("-")+1,input.indexOf("+"));
            String productsId = input.substring(input.indexOf("+")+1,input.indexOf("!"));
            String startDate = input.substring(input.indexOf("!")+1,input.indexOf("$"));
            String finalDate = input.substring(input.indexOf("$")+1);

            /// there is a pot which i must check it later
            String [] array =    productsId.split("\n");
            //this is the end of suspected piece
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(array));
            ArrayList<Integer> arrayOfProductsOfOffIds = new ArrayList<>();
            for (String id : ids) {
                arrayOfProductsOfOffIds.add(Integer.parseInt(id));
            }
            try {
                SellerBoss.addOff(arrayOfProductsOfOffIds,(Seller) Account.getOnlineAccount(),startDate,finalDate,percent,max);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber | NullProduct | JustOneOffForEveryProduct e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }

        }

        private void getOnlineAccount() throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Account account = Account.getOnlineAccount();
            System.out.println(account.getEmail());
            objectOutputStream.writeObject(account);
            objectOutputStream.flush();
        }

        private void beginning() throws IOException {
            if (ManagerBoss.weHaveManagerOrNot()){
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            }else {
                dataOutputStream.writeUTF("F");
                dataOutputStream.flush();
            }
        }

        private void login(String input) throws IOException {
            String username = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String password = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            try {

                AccountBoss.checkUsernameExistenceInLogin(username);
                AccountBoss.checkPasswordValidity(username, password);
                AccountBoss.startLogin(username, password);
                if (Account.getAccountWithUsername(username) instanceof Manager) {
                    dataOutputStream.writeUTF("goToManagerAccountPage");
                    dataOutputStream.flush();
                } else if (Account.getAccountWithUsername(username) instanceof Seller) {
                    dataOutputStream.writeUTF("goToSellerPage");
                    dataOutputStream.flush();
                } else if (Account.getAccountWithUsername(username) instanceof Customer) {
                    dataOutputStream.writeUTF("goToCustomerPage");
                    dataOutputStream.flush();
                } else {
                    dataOutputStream.writeUTF("goToMainMenu");
                    dataOutputStream.flush();
                }
            } catch ( ExistenceOfUserWithUsername | LoginWithoutLogout | PasswordValidity existenceOfUserWithUsername) {
                    dataOutputStream.writeUTF(existenceOfUserWithUsername.getMessage());
                    dataOutputStream.flush();

            }
        }

        private void register(String input) throws IOException {
            Matcher matcher = getMatcher(input, "\\[" + "(\\w*|email address|phone number),(\\w*|(\\w+)@(\\w+).(\\w+))" + "\\]");
            String type = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String username = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            HashMap<String, String> allPersonalInfo = new HashMap<>();
            while (matcher.find()) {
                System.out.println(matcher.group(1)+"       "+matcher.group(2));
                allPersonalInfo.put(matcher.group(1), matcher.group(2));
            }
            try {
                System.out.println(username);
                AccountBoss.firstStepOfRegistering(type, username);
                AccountBoss.makeAccount(allPersonalInfo);
                System.out.println(    Account.getAccountWithUsername(username).getUsername());
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (MoreThanOneManagerException | RepeatedUserName | RequestProblemNotExistManager e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }


        }

        private void handleManagerRequestsRequests(String input) throws IOException {
            //should send response to client
            String requestText = input.substring(9);
            if (requestText.startsWith("AcceptRequest")) {
                try {
                    startAcceptRequestWithId(requestText);
                    sendMessageToClient("Successful :)");
                } catch (InvalidRequestException | NotValidRequestIdException e) {
                    sendMessageToClient(e.getMessage());
                }
            } else if (requestText.startsWith("DeclineRequest")) {
                try {
                    startDeclineRequestWithId(requestText);
                    sendMessageToClient("Successful :)");
                } catch (InvalidRequestException | NotValidRequestIdException e) {
                    sendMessageToClient(e.getMessage());
                }
            } else if (requestText.equalsIgnoreCase("GetCheckedRequests")) {
                sendObjectToClient(Manager.getCheckedRequests());
            } else if (requestText.equalsIgnoreCase("GetUncheckedRequests")) {
                sendObjectToClient(Manager.getNewRequests());
            }

        }

        private void handleManagerRequestsNewManager(String input) throws IOException, ClassNotFoundException {
            String request = input.substring(11);
            if (request.startsWith("create")) {
                Matcher matcher = getMatcher(request, "^create(\\S+)$");
                if (matcher.matches()) {
                    try {
                        ManagerBoss.checkNewManagerUserName(matcher.group(1));
                    } catch (RepeatedUserName repeatedUserName) {
                        sendMessageToClient(repeatedUserName.getMessage());
                        return;
                    }
                    sendMessageToClient("Successful");
                    HashMap<String, String> data = (HashMap<String, String>) readObjectFromClient();
                    AccountBoss.makeAccount(data);
                    sendMessageToClient("Successful");
                } else {
                    sendMessageToClient("Invalid Username Format");
                }

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

        private Object readObjectFromClient() throws IOException, ClassNotFoundException {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            return objectInputStream.readObject();
        }
        private void sendObjectToClient(Serializable toSend) throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(toSend);
            objectOutputStream.flush();
        }
    }

    public static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

    private static void startAcceptRequestWithId(String command) throws InvalidRequestException, NotValidRequestIdException {
        Matcher matcher = getMatcher(command, "^AcceptRequest(\\d+)$");
        if (matcher.matches()) {
            System.out.println("rid: " + Integer.parseInt(matcher.group(1)));
            ManagerBoss.acceptRequestWithId(Integer.parseInt(matcher.group(1)));
        } else {
            throw new InvalidRequestException("Invalid Request Format: " + command);
        }
    }

    private static void startDeclineRequestWithId(String command) throws InvalidRequestException, NotValidRequestIdException {
        Matcher matcher = getMatcher(command, "^DeclineRequest(\\d+)$");
        if (matcher.matches()) {
            ManagerBoss.declineRequestWithId(Integer.parseInt(matcher.group(1)));
        } else {
            throw new InvalidRequestException("Invalid Request Format: " + command);
        }
    }

}
