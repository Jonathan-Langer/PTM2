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
	public StringProperty csvTestFilePath=new SimpleStringProperty(); 
	public PlayerDisplayerController controller;
	
	public Player() {
		super();
		try {
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane toDisplay = fxl.load(getClass().getResource("Player.fxml").openStream());
			controller = fxl.getController();
			options.set(controller.options.getValue());
			csvTestFilePath = controller.csvTestFilePath;
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}