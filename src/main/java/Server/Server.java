package Server;

import Controller.*;
import Controller.Exceptions.*;
import Model.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    private static HashMap<Socket, Account> onlineAccounts = new HashMap<>();
    private static DataInputStream dataInputStreamBank;
    private static DataOutputStream dataOutputStreamBank;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket;

        connectToBankServer();
        while (true) {
            socket = serverSocket.accept();
            onlineAccounts.put(socket, null);
            System.out.println("new client connected to server");
            new handle(new DataInputStream(new BufferedInputStream(socket.getInputStream()))
                    , new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())), socket,dataOutputStreamBank,dataInputStreamBank).start();
        }
    }
    public static void connectToBankServer() throws IOException {
        try {
            Socket socket = new Socket("localHost", 8080);
            dataOutputStreamBank  = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            dataInputStreamBank = new DataInputStream(socket.getInputStream());
            System.out.println("connected to BankApI");
        } catch (IOException e) {
            throw new IOException("Exception while initiating connection:");
        }
    }



    static class handle extends Thread {
        private static DataInputStream dataInputStreamBank;
        private static DataOutputStream dataOutputStreamBank;
        private DataInputStream dataInputStream;
        private DataOutputStream dataOutputStream;
        private Socket socket;

        public handle(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket,DataOutputStream dataOutputStreamBank1,DataInputStream dataInputStreamBank1) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.socket = socket;
            dataInputStreamBank = dataInputStreamBank1;
            dataOutputStreamBank  = dataOutputStreamBank1;
        }
//name
// family
// password

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
                        }
                        else if (input.startsWith("MCategory")) {
                            handleManagerRequestsCategory(input);
                        }
                        else if (input.startsWith("MNewManager")) {
                            handleManagerRequestsNewManager(input);
                        }

                    }  else if (input.startsWith("C")) {
                        handleCustomerRequests(input);
                    } else if (input.startsWith("R")) {
                        register(input);
                    } else if (input.startsWith("Login")) {
                        login(input);
                    } else if (input.startsWith("B")) {
                        beginning();
                    } else if (input.startsWith("GetOnlineAccount")) {
                        getOnlineAccount();
                    } else if (input.startsWith("AddOff")) {
                        addOff(input);
                    } else if (input.startsWith("logout")) {
                        logout(input);
                    } else if (input.startsWith("makeAuction")) {
                        createAuction(input);
                    } else if (input.startsWith("AddProduct")){
                        addProduct(input);
                    } else if (input.startsWith("ShowAttributes")){
                        showAttributes(input);
                    }else if (input.startsWith("updateCompany")){
                        updateCompanyOfSeller(input);
                    }else if (input.startsWith("sellerEditPersonalInfo")){
                        editSellerProfile(input);
                    }else if (input.startsWith("GetProducts")){

                    }else if (input.equalsIgnoreCase("showAuctions")){
                        String response = Auction.showAllAuctionsInfo();
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    }else if (input.startsWith("API")){
                        String response = sendAndGetMessageFromBankAPI(input.substring(input.indexOf(",")+1));
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    }
                    else if (input.startsWith("purchase")) {
                        purchase(input);
                    }else if (input.startsWith("getToken")){
                        getTokenFromBankAndGiveItToClient(input);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void getTokenFromBankAndGiveItToClient(String input) throws IOException {
            String username = input.substring(input.indexOf(",")+1,input.indexOf("-"));
            String password = input.substring(input.indexOf("-")+1);
            String response = getTokenFromBank(username,password);
            if (!response.equalsIgnoreCase("invalid username or password")){
                Account account = Account.getAccountWithUsername(username);
                account.setToken(response);
            }
            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
        }

        private String getTokenFromBank(String username,String password) throws IOException {
            String request = "get_token "+username+" "+password;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }
        private String createBill(String token , String receiptType , String money , String sourceId , String destId ,String description ) throws IOException {
            String request = "create_receipt "+token+" "+receiptType+" "+money+" "+sourceId+" "+destId+" "+description;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return (dataInputStreamBank.readUTF());
        }
        private String getHistoryOfBills(String token , String type) throws IOException {
            String request = "get_transaction "+token+" "+type;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }
        private String pay(String receiptID) throws IOException {
            String request = "pay "+receiptID;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }
        private String getBalanceOfBankAccount(String token) throws IOException {
            String request = "get_balance "+token;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }
        private void exitFromBank() throws IOException {
            dataOutputStreamBank.writeUTF("exit");
            dataOutputStreamBank.flush();
        }
        private void editSellerProfile(String input) throws IOException {
            String parameter = input.substring(input.indexOf(",")+1,input.indexOf("+"));
            String value = input.substring(input.indexOf("+")+1);
            Account account = onlineAccounts.get(socket);
            try {
                AccountBoss.startEditPersonalField(parameter, value,account);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (NotValidFieldException | InvalidNumber e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        }

        private void updateCompanyOfSeller(String input) {
            String companyName = input.substring(input.indexOf(",")+1);
            Seller seller = (Seller) onlineAccounts.get(socket);
            seller.setCompanyName(companyName);
        }

        private void showAttributes(String input) throws  IOException {
            String category = input.substring(input.indexOf(",")+1);
            try {
                ArrayList<String> specials =  SellerBoss.getWithNameOfCategoryItsSpecials(category);
                String response = "";
                for (String special : specials) {
                    response+=special+"\n";
                }
                dataOutputStream.writeUTF(response);
                dataOutputStream.flush();
            } catch (ThereIsNotCategoryWithNameException e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }



        }

        private void addProduct(String input) throws IOException {
            String name = "";
            String price ="";
            String inventory ="";
            String company = "";
            String description ="";
            String category = "";
            Matcher matcher = getMatcher(input,"\\{"+"(\\w*),(\\w*)"+"\\}");
            while (matcher.find()){
                String key =  matcher.group(1);
                String value = matcher.group(2);

                if (key.equalsIgnoreCase("name")){
                    name = value;
                }else if (key.equalsIgnoreCase("price")){
                    price = value;
                }else if (key.equalsIgnoreCase("inventory")){
                    inventory = value;
                }else if (key.equalsIgnoreCase("company")){
                    company = value;
                }else if (key.equalsIgnoreCase("description")){
                    description = value;
                }else if (key.equalsIgnoreCase("category")){
                    category = value;
                }

            }
            Matcher matcher1 = getMatcher(input,"\\["+"(\\w*),(\\w*)"+"\\]");
            HashMap<String , String> attributes = new HashMap<>();
            while (matcher1.find()){
                String key1 = matcher1.group(1);
                String value1 = matcher1.group(2);
                attributes.put(key1,value1);
            }
            Seller seller = (Seller) onlineAccounts.get(socket);
            SellerBoss.addRequestProduct(name,price,inventory,attributes,company,category,seller,description);
            dataOutputStream.writeUTF("S");
            dataOutputStream.flush();
        }

        private void createAuction(String input) throws ParseException, IOException {
            String username = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String startTime = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            String finalTime = input.substring(input.indexOf("+") + 1, input.indexOf("#"));
            int productId = Integer.parseInt(input.substring(input.indexOf("#") + 1));
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
            Date last = new SimpleDateFormat("yyyy-MM-dd").parse(finalTime);
            Seller seller = (Seller) Account.getAccountWithUsername(username);
            try {
                ProductBoss.makeAuction(seller, Product.getProductWithId(productId), start, last);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (ProductIsFinished | DateException | ThisIsNotYours productIsFinished) {
                dataOutputStream.writeUTF(productIsFinished.getMessage());
                dataOutputStream.flush();
            }

        }

        private void logout(String input) throws IOException {
            Account account = onlineAccounts.get(socket);
            AccountBoss.logout(account);
            onlineAccounts.put(socket, null);
            dataOutputStream.writeUTF("S");
            dataOutputStream.flush();
        }

        private void addOff(String input) throws IOException {
            String max = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String percent = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            String productsId = input.substring(input.indexOf("+") + 1, input.indexOf("!"));
            String startDate = input.substring(input.indexOf("!") + 1, input.indexOf("$"));
            String finalDate = input.substring(input.indexOf("$") + 1);

            /// there is a pot which i must check it later
            String[] array = productsId.split("\n");
            //this is the end of suspected piece
            ArrayList<String> ids = new ArrayList<String>(Arrays.asList(array));
            ArrayList<Integer> arrayOfProductsOfOffIds = new ArrayList<>();
            for (String id : ids) {
                arrayOfProductsOfOffIds.add(Integer.parseInt(id));
            }
            try {
                SellerBoss.addOff(arrayOfProductsOfOffIds, (Seller) Account.getOnlineAccount(), startDate, finalDate, percent, max);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (ParseException | ThisIsNotYours | TimeLimit | InvalidNumber | InputStringExceptNumber | NullProduct | JustOneOffForEveryProduct e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }

        }

        private void getOnlineAccount() throws IOException {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Account account = onlineAccounts.get(socket);

            objectOutputStream.writeObject(account);
            objectOutputStream.flush();
        }

        private void beginning() throws IOException {
            if (ManagerBoss.weHaveManagerOrNot()) {
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } else {
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
                Account account = Account.getAccountWithUsername(username);
                onlineAccounts.put(socket, account);
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
            } catch (ExistenceOfUserWithUsername | PasswordValidity | LoginWithoutLogout existenceOfUserWithUsername) {
                dataOutputStream.writeUTF(existenceOfUserWithUsername.getMessage());
                dataOutputStream.flush();

            }
        }

        private void register(String input) throws IOException {
            Matcher matcher = getMatcher(input, "\\[" + "(\\w*|email address|phone number|company name),(\\w*|(\\w+)@(\\w+).(\\w+))" + "\\]");
            String type = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String username = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            String firstName ="";
            String lastName = "";
            String password = "";
            HashMap<String, String> allPersonalInfo = new HashMap<>();
            while (matcher.find()) {
                if (matcher.group(1).equalsIgnoreCase("name")){
                    firstName = matcher.group(2);
                }else if (matcher.group(1).equalsIgnoreCase("family")){
                    lastName = matcher.group(2);
                }else if (matcher.group(1).equalsIgnoreCase("password")){
                    password = matcher.group(2);
                }
                System.out.println(matcher.group(1) + "       " + matcher.group(2));
                allPersonalInfo.put(matcher.group(1), matcher.group(2));
            }
            try {
                System.out.println(username);
                AccountBoss.firstStepOfRegistering(type, username);
                AccountBoss.makeAccount(allPersonalInfo);
                if (type.equalsIgnoreCase("seller") || type.equalsIgnoreCase("customer") || (type.equalsIgnoreCase("manager") && Manager.getAllManagers().size()==1)){
                    dataOutputStreamBank.writeUTF("create_account "+firstName+" "+lastName+" "+username+" "+password+" "+password);
                    dataOutputStreamBank.flush();
                    String numberOfAccount = dataInputStreamBank.readUTF();
                    if (type.equalsIgnoreCase("seller")){
                        Seller seller = (Seller) Account.getAccountWithUsername(username);
                        seller.setNumberOfBankAccount(numberOfAccount);
                    }else if (type.equalsIgnoreCase("customer")){
                        Customer customer = (Customer)Account.getAccountWithUsername(username);
                        customer.setNumberOfBankAccount(numberOfAccount);
                    }else if (type.equalsIgnoreCase("manager")){
                        Manager manager = (Manager) Account.getAccountWithUsername(username);
                        manager.setNumberOfBankAccount(numberOfAccount);
                    }
                }
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
        public void handleManagerRequestsCategory(String input) throws IOException, ClassNotFoundException {
            String request = input.substring(9);
            if (request.startsWith("Create")) {
                String categoryName = request.substring(6);
                ArrayList<String> attributes = (ArrayList<String>) readObjectFromClient();
                try {
                    ManagerBoss.addNewCategory(categoryName, attributes);
                    sendMessageToClient("Successful :)");
                } catch (RepeatedCategoryNameException e) {
                    sendMessageToClient("Error! Repeated Name :(");
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
        private void sendRequestToBankAPI(String request) throws IOException {
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
        }
        private String getResponseFromBankAPI() throws IOException {
            return dataInputStreamBank.readUTF();
        }
        private String sendAndGetMessageFromBankAPI(String request) throws IOException {
            sendRequestToBankAPI(request);
            return getResponseFromBankAPI();
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


    public static void purchase (String input) throws InvalidRequestException {
        String address;
        String phoneNumber;
        Matcher matcher = getMatcher(input, "^purchase (.+), (\\d+)$");
        if (matcher.find()) {
            address = matcher.group(1);
            phoneNumber = matcher.group(2);
            try {
                CustomerBoss.doPayment((Customer) Account.getOnlineAccount());
            } catch (NoMoneyInCustomerPocket noMoneyInCustomerPocket) {
                noMoneyInCustomerPocket.printStackTrace();
            }
        }
        else throw new InvalidRequestException("Invalid Request Format");
    }
}
