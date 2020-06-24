package Main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Stack;

public class Main extends Application {
    public static Scene scene;
    public static Stage stage;
    public static Stack<String> tree = new Stack<>();

    public static void main(String[] args) {
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
    }

    public static void setRoot(String fxml, String newTitle) throws IOException {
        stage.setTitle(newTitle);
        scene.setRoot(loadFXML(fxml));
        tree.push(fxml);
        stage.setScene(scene);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource( "Fxml//" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void doBack() throws IOException {
        Main.tree.pop();
        Main.setRoot(Main.tree.peek(), Main.tree.peek());
    }
}