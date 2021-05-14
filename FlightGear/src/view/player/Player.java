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
		super();
		try {
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