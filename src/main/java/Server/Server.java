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
    private static HashMap<Socket, Auction> onlineAuction = new HashMap<>();
    private static HashMap<Account, Supporter> activeChats = new HashMap<>();
    private static ArrayList<Supporter> onlineSupporters = new ArrayList<>();
    private static HashMap<Socket, Product> onlineProducts = new HashMap<>();
    private static HashMap<Socket, Product> productsPageOnlineProduct = new HashMap<>();
    private static HashMap<Socket, ArrayList<Product>> productsWhichAreSold = new HashMap<>();
    private static boolean isBeforeACart;
    private static DataInputStream dataInputStreamBank;
    private static DataOutputStream dataOutputStreamBank;
    private static String bankAccountID;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket socket;
//        new Manager("a", "a", "a", "a@a.a", "0", "a");
//        new Supporter("s", "a", "a", "a@a.a", "0", "s");
//        new Customer("c", "a", "a", "a@a.a", "0", "c");


        connectToBankServer();
        while (true) {

            socket = serverSocket.accept();
            onlineAccounts.put(socket, null);
            isBeforeACart = false;
            onlineAuction.put(socket, null);
            onlineProducts.put(socket, null);
            productsPageOnlineProduct.put(socket, null);
            productsWhichAreSold.put(socket, null);

            System.out.println("new client connected to server");
            new handle(new DataInputStream(new BufferedInputStream(socket.getInputStream()))
                    , new DataOutputStream(new BufferedOutputStream(socket.getOutputStream())), socket, dataOutputStreamBank, dataInputStreamBank).start();
        }
    }

    public static void connectToBankServer() throws IOException {
        try {
            Socket socket = new Socket("localHost", 8080);
            dataOutputStreamBank = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
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

        public handle(DataInputStream dataInputStream, DataOutputStream dataOutputStream, Socket socket, DataOutputStream dataOutputStreamBank1, DataInputStream dataInputStreamBank1) {
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.socket = socket;
            dataInputStreamBank = dataInputStreamBank1;
            dataOutputStreamBank = dataOutputStreamBank1;
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
                        } else if (input.startsWith("MCategory")) {
                            handleManagerRequestsCategory(input);
                        } else if (input.startsWith("MNewManager")) {
                            handleManagerRequestsNewManager(input);
                        }

                    } else if (input.startsWith("C")) {
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
                    } else if (input.startsWith("logoutSSSSS")) {
                        logoutS(input);
                    } else if (input.startsWith("logout")) {
                        logout(input);
                    } else if (input.startsWith("makeAuction")) {
                        createAuction(input);
                    } else if (input.startsWith("AddProduct")) {
                        addProduct(input);
                    } else if (input.startsWith("ShowAttributes")) {
                        showAttributes(input);
                    } else if (input.startsWith("updateCompany")) {
                        updateCompanyOfSeller(input);
                    } else if (input.startsWith("sellerEditPersonalInfo")) {
                        editSellerProfile(input);
                    } else if (input.startsWith("GetProducts")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(Category.getAllCategories());
                        for (Category category : Category.getAllCategories()) {
                            System.out.println(category.getCategoryName());
                        }
                        objectOutputStream.flush();
                    } else if (input.equalsIgnoreCase("showAuctions")) {
                        String response = Auction.showAllAuctionsInfo();
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    } else if (input.startsWith("API")) {
                        String response = sendAndGetMessageFromBankAPI(input.substring(input.indexOf(",") + 1));
                        Account account = onlineAccounts.get(socket);
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    } else if (input.startsWith("purchase")) {
                        purchase(input);
                    } else if (input.startsWith("getToken")) {
                        getTokenFromBankAndGiveItToClient(input);
                    } else if (input.startsWith("transactionResult")) {
                        String result = getBalanceOfBankAccount(input.substring(input.indexOf(",") + 1));
                        dataOutputStream.writeUTF(result);
                        dataOutputStream.flush();
                    } else if (input.startsWith("transfer")) {
                        sendRequestAndGetBillID(input);
                    } else if (input.startsWith("ThisIsOUrBill")) {
                        Account account = onlineAccounts.get(socket);
                        String billID = input.substring(input.indexOf(",") + 1);
                        String response = pay(billID);
                        Bill bill = Bill.getBill(billID);


                        if (response.equalsIgnoreCase("done successfully")) {
                            if (bill != null) {
                                Customer customer = (Customer) account;
                                System.out.println(customer.getPocket());
                                customer.setPocket(customer.getPocket()+bill.getAmount());
                                System.out.println(customer.getPocket());
                            }
                            account.getBills().remove(billID);
                        }
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                    } else if (input.startsWith("addMeToAuction")) {
                        addCustomerToAuction(input);
                    } else if (input.startsWith("enterToAuction")) {
                        enterToAnAuction(input);
                    } else if (input.startsWith("addMoneyToAuction")) {
                        addAmountOfMoneyInAuction(input);
                    } else if (input.startsWith("GetListOfMessagesInAuctionChatRoom")) {
                        getListOfAuctionChatRoomMessages();
                    } else if (input.startsWith("sendMessageInAuctionChatRoom")) {
                        sendMessageFromClientToAuction(input);
                    } else if (input.startsWith("addFile")) {
                        addFile(input);
                    } else if (input.startsWith("getShopAccountID")) {
                        dataOutputStream.writeUTF(bankAccountID);
                        dataOutputStream.flush();
                    } else if (input.startsWith("GetOnlineProductOfProductsPage")) {
                        int id = Integer.parseInt(input.substring(input.indexOf(",") + 1));
                        Product product = Product.getProductWithId(id);
                        productsPageOnlineProduct.put(socket, product);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject("S");
                        objectOutputStream.flush();
                    } else if (input.startsWith("GetProductForProductPage")) {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(productsPageOnlineProduct.get(socket));
                        objectOutputStream.flush();
                    } else if (input.startsWith("compare")) {
                        String id = input.substring(input.indexOf("-") + 1);
                        String productID = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
                        Product product = Product.getProductWithId(Integer.parseInt(productID));
                        StringBuilder text = ProductBoss.compare(id, product);
                        dataOutputStream.writeUTF(String.valueOf(text));
                        dataOutputStream.flush();
                    } else if (input.startsWith("AddToCart")) {
                        int productID = Integer.parseInt(input.substring(input.indexOf(",") + 1), input.indexOf("-"));
                        int numberOfAdding = Integer.parseInt(input.substring(input.indexOf("-") + 1));
                        Customer customer = (Customer) onlineAccounts.get(socket);
                        Product product = Product.getProductWithId(productID);
                        customer.getListOFProductsAtCart().put(product, numberOfAdding);
                        dataOutputStream.writeUTF("s");
                        dataOutputStream.flush();
                    } else if (input.startsWith("doPayment")) {
                        doPayment();
                    } else if (input.equalsIgnoreCase("makeEmptyCustomerCart")) {
                        productsWhichAreSold.put(socket, null);
                        dataOutputStream.writeUTF("S");
                        dataOutputStream.flush();
                    } else if (input.startsWith("giveMeSeller")) {
                        int productID = Integer.parseInt(input.substring(input.indexOf(",") + 1));
                        Product product = Product.getProductWithId(productID);
                        Seller seller = product.getSeller();
                        for (Account value : onlineAccounts.values()) {
                            if (value.equals(seller)) {

                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        private void logoutS(String input) throws IOException {
            Account account = onlineAccounts.get(socket);
            if (account instanceof Supporter) {
                onlineSupporters.remove(account);
                removeSupporterActiveChat((Supporter) account);
                sendMessageToClient("endThread");
                System.out.println("command send");
            }
            AccountBoss.logout(account);
            onlineAccounts.put(socket, null);
        }

        private void removeSupporterActiveChat(Supporter supporter) throws IOException {
            ArrayList<Account> actives = getActiveAccountChatsWithSupporter(supporter);
            for (Account active : actives) {
                Socket socket = getSocketWithAccount(active);
                DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                stream.writeUTF("endThread1");
                stream.flush();
                activeChats.remove(active);
            }
        }

        private ArrayList<Account> getActiveAccountChatsWithSupporter(Supporter supporter) {
            ArrayList<Account> list = new ArrayList<>();
            for (Account account : activeChats.keySet()) {
                if (activeChats.get(account).equals(supporter)) {
                    list.add(account);
                }
            }
            return list;
        }

        private void doPayment() throws IOException {
            Customer customer = (Customer) onlineAccounts.get(socket);
            boolean response = false;
            try {
                response = CustomerBoss.doPayment(customer);

                dataOutputStream.writeUTF(String.valueOf(response));

            } catch (NoMoneyInCustomerPocket noMoneyInCustomerPocket) {
                dataOutputStream.writeUTF(noMoneyInCustomerPocket.getMessage());

            } finally {
                dataOutputStream.flush();
            }

        }

        private void addFile(String input) throws IOException {
            String address = input.substring(input.indexOf(",") + 1);
            Product product = onlineProducts.get(socket);
            File file = new File(address);
            product.setFile(file);
            dataOutputStream.writeUTF("S");
            dataOutputStream.flush();
        }

        private void sendMessageFromClientToAuction(String input) throws IOException {
            String text = input.substring(input.indexOf(",") + 1);
            String username = onlineAccounts.get(socket).getUsername();
            String completeText = username + "  :  " + text;
            Auction auction = onlineAuction.get(socket);
            auction.getListOfMessages().add(completeText);
            dataOutputStream.writeUTF("S");
            dataOutputStream.flush();
        }

        private void getListOfAuctionChatRoomMessages() throws IOException {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Auction auction = onlineAuction.get(socket);
            objectOutputStream.writeObject(auction.getListOfMessages());
            objectOutputStream.flush();
        }

        private void addAmountOfMoneyInAuction(String input) throws IOException {
            double extra = Double.parseDouble(input.substring(input.indexOf(",") + 1));
            Auction auction = onlineAuction.get(socket);
            try {
                auction.addAmountOfOfferedMoney((Customer) onlineAccounts.get(socket), extra);
                dataOutputStream.writeUTF("S");
            } catch (NotEnoughMoney notEnoughMoney) {
                dataOutputStream.writeUTF(notEnoughMoney.getMessage());
            } finally {
                dataOutputStream.flush();
            }
        }

        private void enterToAnAuction(String input) throws IOException {
            int auctionID = Integer.parseInt(input.substring(input.indexOf(",") + 1));
            Customer customer = (Customer) onlineAccounts.get(socket);
            if (ProductBoss.isThisCustomerInThisAuction(auctionID, customer)) {
                dataOutputStream.writeUTF("S");
                Auction auction = Auction.getAuctionByID(auctionID);
                onlineAuction.put(socket, auction);
            } else {
                dataOutputStream.writeUTF("this is not available");
            }
            dataOutputStream.flush();
        }

        private void addCustomerToAuction(String input) throws IOException {
            int auctionID = Integer.parseInt(input.substring(input.indexOf(",") + 1, input.indexOf("-")));
            double basePrice = Double.parseDouble(input.substring(input.indexOf("-") + 1));
            try {
                ProductBoss.addACustomerToAuction(auctionID, (Customer) onlineAccounts.get(socket), basePrice);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (NotEnoughMoney | NullAuction | YouAreInThisAuction notEnoughMoney) {
                dataOutputStream.writeUTF(notEnoughMoney.getMessage());
                dataOutputStream.flush();
            }
        }

        private void sendRequestAndGetBillID(String input) throws IOException {
            String token = "";
            String money = "";
            String sourceID = "";
            String destID = "";
            String description = "";
            String receiptType = "";
            Matcher matcher = getMatcher(input, "\\{" + "(\\w+),(\\w+|-\\d+|\\w+\\s*\\w*)" + "\\}");
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                if (key.equalsIgnoreCase("token")) {

                    token = value;
                } else if (key.equalsIgnoreCase("receiptType")) {

                    receiptType = value;
                } else if (key.equalsIgnoreCase("money")) {

                    money = value;
                } else if (key.equalsIgnoreCase("sourceID")) {

                    sourceID = value;
                } else if (key.equalsIgnoreCase("destID")) {

                    destID = value;
                } else if (key.equalsIgnoreCase("description")) {
                    description = value;
                }
            }
            String billID = createBill(token, receiptType, money, sourceID, destID, description);
            Account account = (Account) onlineAccounts.get(socket);
            account.getBills().add(billID);
            if (destID.equalsIgnoreCase(bankAccountID)) {
                Bill bill = new Bill(billID, Double.parseDouble(money));
            }
            dataOutputStream.writeUTF(billID);
            dataOutputStream.flush();
        }

        private void getTokenFromBankAndGiveItToClient(String input) throws IOException {
            String username = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String password = input.substring(input.indexOf("-") + 1);
            String response = getTokenFromBank(username, password);
            if (!response.equalsIgnoreCase("invalid username or password")) {
                Account account = Account.getAccountWithUsername(username);
                account.setToken(response);
            }

            dataOutputStream.writeUTF(response);
            dataOutputStream.flush();
        }

        private String getTokenFromBank(String username, String password) throws IOException {
            String request = "get_token " + username + " " + password;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }

        private String createBill(String token, String receiptType, String money, String sourceId, String destId, String description) throws IOException {
            String request = "create_receipt " + token + " " + receiptType + " " + money + " " + sourceId + " " + destId + " " + description;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return (dataInputStreamBank.readUTF());
        }

        private String getHistoryOfBills(String token, String type) throws IOException {
            String request = "get_transaction " + token + " " + type;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }

        private String pay(String receiptID) throws IOException {
            String request = "pay " + receiptID;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }

        private String getBalanceOfBankAccount(String token) throws IOException {
            String request = "get_balance " + token;
            dataOutputStreamBank.writeUTF(request);
            dataOutputStreamBank.flush();
            return dataInputStreamBank.readUTF();
        }

        private void exitFromBank() throws IOException {
            dataOutputStreamBank.writeUTF("exit");
            dataOutputStreamBank.flush();
        }

        private void editSellerProfile(String input) throws IOException {
            String parameter = input.substring(input.indexOf(",") + 1, input.indexOf("+"));
            String value = input.substring(input.indexOf("+") + 1);
            Account account = onlineAccounts.get(socket);
            try {
                AccountBoss.startEditPersonalField(parameter, value, account);
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();
            } catch (NotValidFieldException | InvalidNumber e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }
        }

        private void updateCompanyOfSeller(String input) {
            String companyName = input.substring(input.indexOf(",") + 1);
            Seller seller = (Seller) onlineAccounts.get(socket);
            seller.setCompanyName(companyName);
        }

        private void showAttributes(String input) throws IOException {
            String category = input.substring(input.indexOf(",") + 1);
            try {
                ArrayList<String> specials = SellerBoss.getWithNameOfCategoryItsSpecials(category);
                String response = "";
                for (String special : specials) {
                    response += special + "\n";
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
            String price = "";
            String inventory = "";
            String company = "";
            String description = "";
            String category = "";
            Matcher matcher = getMatcher(input, "\\{" + "(\\w*),(\\w*)" + "\\}");
            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);

                if (key.equalsIgnoreCase("name")) {
                    name = value;
                } else if (key.equalsIgnoreCase("price")) {
                    price = value;
                } else if (key.equalsIgnoreCase("inventory")) {
                    inventory = value;
                } else if (key.equalsIgnoreCase("company")) {
                    company = value;
                } else if (key.equalsIgnoreCase("description")) {
                    description = value;
                } else if (key.equalsIgnoreCase("category")) {
                    category = value;
                }

            }
            Matcher matcher1 = getMatcher(input, "\\[" + "(\\w*),(\\w*)" + "\\]");
            HashMap<String, String> attributes = new HashMap<>();
            while (matcher1.find()) {
                String key1 = matcher1.group(1);
                String value1 = matcher1.group(2);
                attributes.put(key1, value1);
            }
            Seller seller = (Seller) onlineAccounts.get(socket);
            Product product = SellerBoss.addRequestProduct(name, price, inventory, attributes, company, category, seller, description);
            onlineProducts.put(socket, product);
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
            } catch (ProductIsFinished | DateException | ThisIsNotYours | WeHaveAuctionWithThisProduct productIsFinished) {
                dataOutputStream.writeUTF(productIsFinished.getMessage());
                dataOutputStream.flush();
            }

        }

        private void logout(String input) throws IOException {
            Account account = onlineAccounts.get(socket);
            AccountBoss.logout(account);
            if (account instanceof Supporter) {
                onlineSupporters.remove(account);
            }
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
            Account account1 = onlineAccounts.get(socket);
            if (account1 != null) {
                dataOutputStream.writeUTF("first logout");
                dataOutputStream.flush();
                return;
            }
            int i = 0;
            if (onlineAccounts.size() != 0 && account1 != null) {
                for (Account value : onlineAccounts.values()) {
                    if (value.getUsername().equalsIgnoreCase(account1.getUsername()))
                        i++;
                }
            }
            if (i > 1) {
                dataOutputStream.writeUTF("first logout");
                dataOutputStream.flush();
                return;
            }
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
                } else if (account instanceof Supporter) {
                    sendMessageToClient("goToSupportPage");
                    onlineSupporters.add((Supporter) account);
                } else {
                    dataOutputStream.writeUTF("goToMainMenu");
                    dataOutputStream.flush();
                }
            } catch (ExistenceOfUserWithUsername | PasswordValidity existenceOfUserWithUsername) {
                dataOutputStream.writeUTF(existenceOfUserWithUsername.getMessage());
                dataOutputStream.flush();

            }
        }

        private void register(String input) throws IOException {
            Matcher matcher = getMatcher(input, "\\[" + "(\\w*|email address|phone number|company name),(\\w*|(\\w+)@(\\w+).(\\w+))" + "\\]");
            String type = input.substring(input.indexOf(",") + 1, input.indexOf("-"));
            String username = input.substring(input.indexOf("-") + 1, input.indexOf("+"));
            String firstName = "";
            String lastName = "";
            String password = "";
            HashMap<String, String> allPersonalInfo = new HashMap<>();
            while (matcher.find()) {
                if (matcher.group(1).equalsIgnoreCase("name")) {
                    firstName = matcher.group(2);
                } else if (matcher.group(1).equalsIgnoreCase("family")) {
                    lastName = matcher.group(2);
                } else if (matcher.group(1).equalsIgnoreCase("password")) {
                    password = matcher.group(2);
                }
                allPersonalInfo.put(matcher.group(1), matcher.group(2));
            }
            try {

                AccountBoss.firstStepOfRegistering(type, username);
                AccountBoss.makeAccount(allPersonalInfo);
                if (type.equalsIgnoreCase("seller") || type.equalsIgnoreCase("customer") || (type.equalsIgnoreCase("manager") && Manager.getAllManagers().size() == 1)) {
                    dataOutputStreamBank.writeUTF("create_account " + firstName + " " + lastName + " " + username + " " + password + " " + password);
                    dataOutputStreamBank.flush();
                    String numberOfAccount = dataInputStreamBank.readUTF();
                    if (type.equalsIgnoreCase("seller")) {
                        Seller seller = (Seller) Account.getAccountWithUsername(username);
                        seller.setNumberOfBankAccount(numberOfAccount);
                    } else if (type.equalsIgnoreCase("customer")) {
                        Customer customer = (Customer) Account.getAccountWithUsername(username);
                        customer.setNumberOfBankAccount(numberOfAccount);
                    } else if (type.equalsIgnoreCase("manager")) {
                        Manager manager = (Manager) Account.getAccountWithUsername(username);
                        manager.setNumberOfBankAccount(numberOfAccount);
                        if (Manager.getAllManagers().size() == 1) {
                            bankAccountID = numberOfAccount;
                        }
                    }
                }
                dataOutputStream.writeUTF("S");
                dataOutputStream.flush();

            } catch (MoreThanOneManagerException | RepeatedUserName | RequestProblemNotExistManager e) {
                dataOutputStream.writeUTF(e.getMessage());
                dataOutputStream.flush();
            }


        }

        private void handleManagerRequestsRequests(String input) throws IOException, ClassNotFoundException {
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
            } else if (requestText.equalsIgnoreCase("GetOnlineSupporters")) {
                sendObjectToClient(onlineSupporters);
            } else if (requestText.startsWith("CheckSupporterUserName-")) {
                String username = requestText.substring(requestText.indexOf('-') + 1);
                if (Account.isThereAccountWithUserName(username)) {
                    sendMessageToClient("Error");
                } else {
                    sendMessageToClient("Ok");
                }
            } else if (requestText.equalsIgnoreCase("RegisterSupporter")) {
                HashMap<String, String> data = (HashMap<String, String>) readObjectFromClient();
                AccountBoss.makeAccount(data);
            } else if (requestText.startsWith("ClientChat:")) {
                String message = requestText.substring(requestText.indexOf(':') + 1);
                Account sender = onlineAccounts.get(socket);
                if (activeChats.containsKey(sender)) {
                    Supporter supporter = activeChats.get(sender);
                    Socket supporterSocket = getSocketWithSupporter(supporter);
                    DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(supporterSocket.getOutputStream()));
                    stream.writeUTF(sender.getUsername() + " : " + message);
                    stream.flush();
//                    sendMessageToClient("Successful :)");
                } else {
//                    sendMessageToClient("Error :( Not Connected");
                }
            } else if (requestText.startsWith("SupporterChat:")) {
                String destinationUsername = requestText.substring(requestText.indexOf(':') + 1, requestText.indexOf('`'));
                String message = requestText.substring(requestText.indexOf('`') + 1);
                if (activeChats.containsKey(Account.getAccountWithUsername(destinationUsername))) {
                    Socket destSocket = getSocketWithAccount(Account.getAccountWithUsername(destinationUsername));
                    DataOutputStream stream = new DataOutputStream(new BufferedOutputStream(destSocket.getOutputStream()));
                    stream.writeUTF(message);
                    stream.flush();
//                    sendMessageToClient("Successful");
                } else {
//                    sendMessageToClient("Username is Not active.");
                }
            } else if (requestText.startsWith("StartChatWith:")) {
                String supporterUsername = requestText.substring(requestText.indexOf(':') + 1);
                if (Supporter.isThereSupporterWithUsername(supporterUsername)) {
                    Supporter supporter = Supporter.getSupporterWithUsername(supporterUsername);
                    if (onlineSupporters.contains(supporter)) {
                        activeChats.put(onlineAccounts.get(socket), supporter);
                        sendMessageToClient("Successful :)");
                    } else {
                        sendMessageToClient("Supporter is not online :(");
                    }
                } else {
                    sendMessageToClient("Supporter is not available now :(");
                }
            } else if (requestText.equalsIgnoreCase("CustomerDisconnect")) {
                activeChats.remove(onlineAccounts.get(socket));
                sendMessageToClient("endThread");
            }
        }

        private Socket getSocketWithSupporter(Supporter supporter) {
            for (Socket socket1 : onlineAccounts.keySet()) {
                if (onlineAccounts.get(socket1).equals(supporter)) {
                    return socket1;
                }
            }
            return null;
        }

        private Socket getSocketWithAccount(Account account) {
            for (Socket socket1 : onlineAccounts.keySet()) {
                if (onlineAccounts.get(socket1).equals(account)) {
                    return socket1;
                }
            }
            return null;
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


    public static void purchase(String input) throws InvalidRequestException {
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
        } else throw new InvalidRequestException("Invalid Request Format");
    }
}
