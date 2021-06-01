package view.attributesView.coordinateSystem;

import java.io.IOException;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import view.attributesView.AttributesViewController;

public class CoordinateSystemDisplayer extends Pane {
	public CoordinateSystemController controller;
	public StringProperty title;
	public CoordinateSystemDisplayer() {
		super();
		FXMLLoader loader=new FXMLLoader();
		try {
			Pane toDisplay=loader.load(getClass().getResource("CoordinateSystem.fxml").openStream());
			controller=loader.getController();
			this.getChildren().add(toDisplay);
			title=controller.title.textProperty();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
