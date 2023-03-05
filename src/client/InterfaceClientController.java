package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class InterfaceClientController {
	@FXML
	private TextField host;
	@FXML
	private TextField msg;
	ObservableList<Label> ListModel=FXCollections.observableArrayList();
	@FXML
	private VBox msgs;
	PrintWriter pw;
	
	@FXML
	public void ConncteToServer(ActionEvent e) {
		String hostName=host.getText();
		int port=6666;
		
		try {
			
			Socket sk=new Socket(hostName,port);
			InputStream is=sk.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			
			OutputStream os=sk.getOutputStream();
			pw=new PrintWriter(os,true);
			new Thread(()->{
				while(true){
						try {
						String rep=br.readLine();   
							Platform.runLater(()->{	
								Label label=new Label(rep);
								label.setStyle("-fx-font-weight: bold; -fx-text-fill:orange;  ");
								ListModel.add(label);
								msgs.getChildren().setAll(ListModel);
								   });
				     	} catch (IOException exp) {
					// TODO Auto-generated catch block
					exp.printStackTrace();
			        	}                          
			
				}
			}).start();
		
			
		} catch (IOException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		
	}
	
	@FXML
	public void sendMsg(ActionEvent e) {
		String textMsg=msg.getText();
		Label label;
		if(textMsg.contains("=>")) {
    		String[] msgParams=textMsg.split("=>");
    	String msgt=msgParams[1];
		label=new Label(msgt);
		}else {
			label=new Label(textMsg);
		}
		label.setStyle("-fx-font-weight: bold;");
		label.setTextAlignment(TextAlignment.RIGHT);
		ListModel.add(label);
		msgs.getChildren().setAll(ListModel);
		msg.setText("");
		pw.println(textMsg);
	}

	
	

}
