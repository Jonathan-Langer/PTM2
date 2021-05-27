package view.player;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Player extends AnchorPane {
	
	public StringProperty speedPlayer;
	public StringProperty csvTestFilePath;
	public DoubleProperty currentTime;
	public PlayerDisplayerController controller;
	
	public Player() {
		super();
		try {
			speedPlayer=new SimpleStringProperty();
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane toDisplay = fxl.load(getClass().getResource("Player.fxml").openStream());
			controller = fxl.getController();
			controller.options.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
					speedPlayer.setValue(controller.options.valueProperty().getValue());
				}
			});

			speedPlayer.set(controller.options.getValue());
			csvTestFilePath=controller.csvTestFilePath;
			currentTime=controller.timeLine.valueProperty();
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}