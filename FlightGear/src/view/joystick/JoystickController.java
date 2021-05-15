package view.joystick;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;

public class JoystickController implements Initializable {
	
	@FXML
	Slider rudder;
	
	@FXML
	Slider throttle;
	
	public JoystickController() {
		rudder = new Slider();
		throttle = new Slider();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		rudder.setMin(-1);
		rudder.setMax(1);
		rudder.setValue(0);
	}
	public void setRudderValue(double newVal) {
		if(rudder==null)
			rudder=new Slider();
		rudder.setValue(newVal);
	}
}
