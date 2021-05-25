package view.player;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Player extends AnchorPane {
	
	public StringProperty options;
	public StringProperty csvTestFilePath;
	public PlayerDisplayerController controller;
	
	public Player() {
		super();
		try {
			options=new SimpleStringProperty();
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane toDisplay = fxl.load(getClass().getResource("Player.fxml").openStream());
			controller = fxl.getController();
			controller.options.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
					options.setValue(controller.options.valueProperty().getValue());
				}
			});
			options.set(controller.options.getValue());
			csvTestFilePath=controller.csvTestFilePath;
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}