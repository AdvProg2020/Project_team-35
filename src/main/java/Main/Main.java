package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Scene scene;
    public static Stage stage;

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
        Main.scene = new Scene(root, 2000, 1000);
        primaryStage.setScene(scene);
        Main.stage = primaryStage;
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        stage.setScene(scene);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getClassLoader().getResource( "Fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
