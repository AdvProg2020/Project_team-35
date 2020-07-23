package ViewControllers;

import Main.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable {
    public ListView list;
    public TextField text;

    public void send(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        update();
        String response = Main.sendAndGetMessage("sendMessageInAuctionChatRoom,"+text.getText());
        System.out.println(response);
        update();
    }

    public void back(MouseEvent mouseEvent) throws IOException {
        Main.setRoot("AuctionPage","auction page",false);
    }

    public void refresh(MouseEvent mouseEvent) throws IOException, ClassNotFoundException {
        update();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            update();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void update() throws IOException, ClassNotFoundException {
        ArrayList<String> listOfMessages = (ArrayList<String>) Main.sendAndGetObjectFromServer("GetListOfMessagesInAuctionChatRoom");

        for (String message : listOfMessages) {
            list.getItems().add(message);
        }
    }
}
