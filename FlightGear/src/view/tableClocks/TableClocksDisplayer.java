package view.tableClocks;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.ListOfAttributes;

public class TableClocksDisplayer extends Pane {
	public DoubleProperty altimeterValue, airspeedValue, headingValue,
	rollValue,pitchValue,yawValue;
	public TableClocksController controller;
	
	public TableClocksDisplayer() {
		FXMLLoader loader=new FXMLLoader();
		altimeterValue=new SimpleDoubleProperty();
		airspeedValue=new SimpleDoubleProperty();
		headingValue=new SimpleDoubleProperty();
		rollValue=new SimpleDoubleProperty();
		pitchValue=new SimpleDoubleProperty();
		yawValue=new SimpleDoubleProperty();
		try {
			Pane toDisplay=loader.load(getClass().getResource("TableClocks.fxml").openStream());
			controller=loader.getController();
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
