package Main;


import Model.*;
import MusicPlayer.MusicPlayer;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {
    public static Scene scene;
    public static Stage stage;
    public static Stack<String> tree = new Stack<>();
    private static Socket socket;
    private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;

    public static void main(String[] args) throws IOException {
        try {
            socket = new Socket("localhost", 8888);
        } catch (IOException e) {
            System.out.println("Socket creation failed.");
        }
        System.out.println("Connected to server successfully.");
        dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        dataOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        launch(args);
//        Scanner scanner = new Scanner(System.in);
//        MainPage mainPage = new MainPage();
//        MainPage.setScanner(scanner);
//        mainPage.execute();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../Fxml/FirstPage.fxml"));
        primaryStage.setTitle("Hello World");
        Main.scene = new Scene(root, 1500, 800);
        primaryStage.setScene(scene);
        Main.stage = primaryStage;
        tree.push("FirstPage");
        primaryStage.show();
       // MusicPlayer.getInstance().playBGMusic();
    }

    public static void setRoot(String fxml, String newTitle ,boolean isForBack) throws IOException {
        stage.setTitle(newTitle);
        scene.setRoot(loadFXML(fxml));
        stage.setScene(scene);
        if (!isForBack) {
            tree.push(fxml);
        }
      //  MusicPlayer.getInstance().changeBGMusic();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource( "Fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }


    public static void doBack(String title) throws IOException {
        Main.tree.pop();
        Main.setRoot(Main.tree.peek(), title, true);
    }
    public static void doBack() throws IOException {
        Main.tree.pop();
        Main.setRoot(Main.tree.peek(), Main.tree.peek(), true);
    }
    public static String sendAndGetMessage(String message) throws IOException {
        sendMessageToServer(message);
        return getMessageFromServer();
    }
    public static void sendMessageToServer(String message) throws IOException {
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }

    public static String getMessageFromServer() throws IOException {
        return dataInputStream.readUTF();
    }

    public static void sendObjectToServer(Serializable toSend) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(toSend);
        objectOutputStream.flush();
    }
    public static Object getObjectFromServer() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        return objectInputStream.readObject();
    }
    public static Object sendAndGetObjectFromServer(String toSend) throws IOException, ClassNotFoundException {
       sendMessageToServer(toSend);
        return getObjectFromServer();
    }

    public static Socket getSocket() {
        return socket;
    }
    public static Object sendAndGetObjectSerializable(Object object) throws IOException, ClassNotFoundException {
        sendObjectToServer((Serializable) object);
        return getObjectFromServer();
    }
}