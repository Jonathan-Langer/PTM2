package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.shape.Circle;
import model.AttributeSettings;
import model.ListOfAttributes;

public class Joystick implements Initializable{
	// -------------data members-------------
	@FXML
	RadioButton manual;
	@FXML
	Slider rudder;
	@FXML
	Slider throttle;
	@FXML
	Circle joystickBorder;
	@FXML
	Label rudderLabel;
	@FXML
	Label throttleLabel;
	@FXML
	Label aileronLabel;
	@FXML
	Label elevatorLabel;
	@FXML
	Circle joystick;
	ListOfAttributes attributes;
	
	public Joystick() {
		attributes=new ListOfAttributes(new File("resources/last_setting.txt").getAbsolutePath());
		throttle=new Slider();
		rudder=new Slider();
		for(AttributeSettings a:attributes.getList().values()) {
			if(a.getColInCSV()==2) {
				rudder.setMax(a.getMaxValue());
				rudder.setMin(a.getMinValue());
				continue;
			}
			if(a.getColInCSV()==6) {
				throttle.setMax(a.getMaxValue());
				throttle.setMin(a.getMinValue());
				continue;
			}
		}
	}
	public Joystick(ListOfAttributes attributes) {
		this.attributes=attributes;
	}
	
	public RadioButton getManual() {
		return manual;
	}

	public Slider getRudder() {
		return rudder;
	}

	public Slider getThrottle() {
		return throttle;
	}

	public Circle getJoystickBorder() {
		return joystickBorder;
	}

	public Label getRudderLabel() {
		return rudderLabel;
	}

	public Label getThrottleLabel() {
		return throttleLabel;
	}

	public Label getAileronLabel() {
		return aileronLabel;
	}

	public Label getElevatorLabel() {
		return elevatorLabel;
	}

	public Circle getJoystick() {
		return joystick;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
