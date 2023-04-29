

import java.util.HashMap;

import com.sun.org.apache.xerces.internal.dom.PSVIElementNSImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiServer extends Application{

	
	TextField s1,s2,s3,s4, c1;
	@FXML
	Button serverChoice,clientChoice,b1, b2;
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	HBox buttonBox;
	VBox clientBox;
	Scene startScene;
	BorderPane startPane;
	Server serverConnection;
	Client clientConnection;
	
	@FXML
	ListView<String> listItems, listItems2, listItems3;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Chatroom");
		
		this.serverChoice = new Button("Server");
		this.serverChoice.setStyle("-fx-pref-width: 300px");
		this.serverChoice.setStyle("-fx-pref-height: 300px");

		this.serverChoice.setOnAction(e->{ primaryStage.setScene(sceneMap.get("server"));
											primaryStage.setTitle("This is the Server");
				serverConnection = new Server(data -> {
					Platform.runLater(()->{
						listItems.getItems().add(data.toString());
					});

				});
											
		});
		
		
		this.clientChoice = new Button("Client");
		this.clientChoice.setStyle("-fx-pref-width: 300px");
		this.clientChoice.setStyle("-fx-pref-height: 300px");
		
		this.clientChoice.setOnAction(e-> {primaryStage.setScene(sceneMap.get("client"));
											primaryStage.setTitle("This is a client");
											clientConnection = new Client(data->{
												PrivateMessage d = (PrivateMessage)data;
							Platform.runLater(()->{
								listItems3.getItems().add(((PrivateMessage) data).privateMessage);
								listItems3.getItems().clear();
								listItems3.getItems().addAll(((PrivateMessage) data).cList);
											});
							});
							
											clientConnection.start();
		});
		
		this.buttonBox = new HBox(400, serverChoice, clientChoice);
		startPane = new BorderPane();
		startPane.setPadding(new Insets(70));
		startPane.setCenter(buttonBox);

		//Parent root = FXMLLoader.load(getClass().getResource("/Start.fxml"));
		
		startScene = new Scene(startPane, 800,800);
		
		listItems = new ListView<String>();
		listItems2 = new ListView<String>();
		listItems3 = new ListView<String>();
		listItems3.setPadding(new Insets(8,0,0,0));
		listItems3.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		c1 = new TextField();
		b1 = new Button("Send");
		b1.setOnAction(e->{
			PrivateMessage data = new PrivateMessage();
			ObservableList<String> list = listItems3.getSelectionModel().getSelectedItems();
			list.forEach(x -> {
				data.clients.add(x);
			});
			data.bool = true;
			data.privateMessage = c1.getText();
			clientConnection.send(data);
			c1.clear();
		});
		b2 = new Button("Message All");
		b2.setOnAction(e->{
			PrivateMessage data = new PrivateMessage();
			data.bool = false;
			data.privateMessage = c1.getText();
			clientConnection.send(data);
			c1.clear();
		});
		
		sceneMap = new HashMap<String, Scene>();
		
		sceneMap.put("server",  createServerGui());
		sceneMap.put("client",  createClientGui());
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
		
		 
		
		primaryStage.setScene(startScene);
		primaryStage.show();
		
	}
	
	public Scene createServerGui() {
		
		BorderPane pane = new BorderPane();
		pane.setPadding(new Insets(70));
		pane.setStyle("-fx-background-color: coral");
		
		pane.setCenter(listItems);
	
		return new Scene(pane, 500, 400);
		
		
	}
	
	public Scene createClientGui() {
		
		clientBox = new VBox(10, c1,b1, b2, listItems2, listItems3);
		clientBox.setStyle("-fx-background-color: blue");
		return new Scene(clientBox, 400, 300);
		
	}

}