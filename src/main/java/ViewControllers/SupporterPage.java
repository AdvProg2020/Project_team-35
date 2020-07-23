package ViewControllers;

import Main.Main;
import MusicPlayer.MusicPlayer;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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

    private Receiver receiver;

    public void sendClick(MouseEvent mouseEvent) {

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
