package ViewControllers;

import Main.Main;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class CustomerChatPage {
    public TextArea receiveArea;
    public TextArea sendArea;
    public Label actionInfo;

    public void sendClick(MouseEvent mouseEvent) throws IOException {
        Main.sendMessageToServer("MRequestsChat:" + sendArea.getText());
        String response = Main.getMessageFromServer();
        actionInfo.setText(response);
    }

    public void disconnectClick(MouseEvent mouseEvent) {

    }
}
