

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

public class Controller implements Initializable {


    public Label ChatName;
    public Button SendToUser;

    @FXML
    public TextField messageBar;
    @FXML
    public Button messageButton;

    @FXML
    public static ListView chatView;

    Client clientConnection;


    static ObservableList<String> connectedClientsList;

    ObservableList<String> selectedUsers;


    @FXML
    private ListView<String> ClientList;



    public void updateChat(String message) {
        Platform.runLater(() -> chatView.getItems().add(message));
    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ClientList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        connectedClientsList = FXCollections.observableArrayList();
        ClientList.setItems(connectedClientsList);
        for (Server.ClientThread client : Server.clients) { // iterate over the clients array list
            connectedClientsList.add("Client #" + client.count + "\n"); // add each client to the observable list
        }
        clientConnection = new Client(data -> {
            Platform.runLater(() -> {
                if (data instanceof Server.ClientThread) { // check if the data is a ClientThread
                    Server.ClientThread client = (Server.ClientThread) data;
                    connectedClientsList.add("Client #" + client.count + "\n"); // add the client to the list
                } else {
                    ClientList.getItems().add(data.toString()); // add other data to the list
                }
            });
        });
        clientConnection.start();
    }




    @FXML
    public void ChooseUsers(ActionEvent actionEvent) {
        selectedUsers = ClientList.getSelectionModel().getSelectedItems();
        if (!selectedUsers.isEmpty()) {
            String selectedUsersString = String.join(", ", selectedUsers);
            ChatName.setText(selectedUsersString);
        } else {
            ChatName.setText("No users selected");
        }
    }


//    @FXML
//    public void SendMessage(ActionEvent actionEvent) {
//        PrivateMessage text = messageBar;
//        messageBar.clear();
//        System.out.println("Sending message: " + text);
//        System.out.println("Number of clients: " + Server.clients.size());
//        for (String selectedUser : selectedUsers) {
//            System.out.println("point 1 " + text);
//            for (Server.ClientThread t : Server.clients) {
//                System.out.println("point 22 " );
//                if (selectedUser.equals("client #" + t.count)) {
//                    try {
//                        t.updateClients(text);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }










}
