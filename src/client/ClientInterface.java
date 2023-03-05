package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientInterface extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root= FXMLLoader.load(getClass().getResource("InterfaceClient.fxml"));
		Scene scene=new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("SOCKETS");
		primaryStage.show();
		
	}
	public static void main(String[] args) {
		launch(args);
	}
	

}
