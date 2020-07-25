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
    private static boolean isSupporterAlreadyActive = true;

    private Receiver receiver;

    public void sendClick(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("MRequestsClientChat:" + sendArea.getText());
        receiveArea.setText(receiveArea.getText() + "\n    " + sendArea.getText());
//        String response = Main.getMessageFromServer();
//        actionInfo.setText(response);
    }

    public void disconnectClick(MouseEvent mouseEvent) throws IOException {
        if (isSupporterAlreadyActive) {
            Main.sendMessageToServer("MRequestsCustomerDisconnect");
        }
        //should close the thread
        //String response = Main.getMessageFromServer();
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
                    if (message.equalsIgnoreCase("endThread")) {
                        receiveArea.setDisable(true);
                        sendArea.setDisable(true);
                        isSupporterAlreadyActive = false;
                        break;
                    }
                    receiveArea.setText(receiveArea.getText() + '\n' + message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("customer thread ended");

        }
    }
}
