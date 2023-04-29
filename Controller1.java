import com.sun.javafx.collections.ElementObservableListDecorator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class Controller1 {


    @FXML
    public TextField messageBar;


    ObservableList<String> selectedUsers;
    @FXML
    public void SendMessage(ActionEvent actionEvent) {
        PrivateMessage text = new PrivateMessage();
        text.writeMessage(messageBar.getText());
        messageBar.clear();
        System.out.println("Sending message: " + text);
        System.out.println("Number of clients: " + Server.clients.size());
        for (String selectedUser : selectedUsers) {
            System.out.println("point 1 " + text);
            for (Server.ClientThread t : Server.clients) {
                System.out.println("point 22 " );
                if (selectedUser.equals("client #" + t.count)) {
                    try {
                        t.updateClients(text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
