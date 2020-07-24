package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SupporterPage implements Initializable {
    public TextArea receiveArea;
    public TextArea sendArea;
    public TextField destUsername;
    public Label actionInfo;

    private Receiver receiver;

    public void sendClick(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("MRequestsSupporterChat:" + destUsername.getText() + "`" + sendArea.getText());
        receiveArea.setText(receiveArea.getText() + "\n   " + sendArea.getText());
//        String response = Main.getMessageFromServer();
//        actionInfo.setText(response);
//        if (response.equalsIgnoreCase("Successful")) {
//            receiveArea.setText(receiveArea.getText() + "\n      " + sendArea.getText());
//        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        receiver = new Receiver();
        receiver.start();
    }

    public void logoutClick(MouseEvent mouseEvent) throws IOException {
        MusicPlayer.getInstance().playButtonMusic();
        String response =  Main.sendAndGetMessage("logout");
        Main.doBack();
    }

    class Receiver extends Thread {
        Socket socket = Main.getSocket();
        DataInputStream dataInputStream;



        @Override
        public void run() {
            try {
                dataInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (true) {
                try {
                    String message = dataInputStream.readUTF();
                    receiveArea.setText(receiveArea.getText() + '\n' + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
