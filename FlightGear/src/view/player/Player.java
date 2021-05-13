package view.player;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Player extends AnchorPane {
	
	public StringProperty options=new SimpleStringProperty();
	public StringProperty csvFilePath=new SimpleStringProperty(); 
	
	public Player() {
		/*
		 * super(); try { FXMLLoader loader=new FXMLLoader(); AnchorPane play =
		 * loader.load(getClass().getResource("Player.fxml").openStream());
		 * PlayerDisplayerController playerControl = loader.getController(); csvFilePath
		 * = playerControl.csvFilePath;
		 * 
		 * this.getChildren().add(play); System.out.println(""); } catch (IOException e)
		 * { e.printStackTrace(); }
		 */
		super();
		try {
			/*FXMLLoader loader=new FXMLLoader(getClass().getResource("Player.fxml"));
			controller=new PlayerDisplayerController();
			loader.setController(controller);
			Node n=loader.load();
			this.getChildren().add(n);*/
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane chuck = fxl.load(getClass().getResource("Player.fxml").openStream());
			
			PlayerDisplayerController pds = new PlayerDisplayerController();
			
			options.set(pds.options.getValue());
			csvFilePath = pds.csvFilePath;
			this.getChildren().add(chuck);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}