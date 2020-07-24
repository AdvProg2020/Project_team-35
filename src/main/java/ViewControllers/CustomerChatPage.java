package ViewControllers;

import Main.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerChatPage implements Initializable {
    public TextArea receiveArea;
    public TextArea sendArea;
    public Label actionInfo;

    private Receiver receiver;

    public void sendClick(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("MRequestsClientChat:" + sendArea.getText());
//        String response = Main.getMessageFromServer();
//        actionInfo.setText(response);
    }

    public void disconnectClick(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("MRequestsCustomerDisconnect");
        String response = Main.getMessageFromServer();
        Main.doBack();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        receiver = new Receiver();
        receiver.start();
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
