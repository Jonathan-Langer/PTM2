package view.joystick;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class JoystickDisplayer extends AnchorPane {
	public DoubleProperty aileronValue=new SimpleDoubleProperty();
	public DoubleProperty elevatorsValue=new SimpleDoubleProperty();
	public DoubleProperty rudderValue=new SimpleDoubleProperty();
	public DoubleProperty throttleValue=new SimpleDoubleProperty();
	public JoystickController controller;
	
	public JoystickDisplayer() {
		super();
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("Joystick.fxml").openStream());
			//JoystickController controller=new JoystickController();
			controller=new JoystickController();
			controller.rudder.valueProperty().bind(rudderValue);
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
