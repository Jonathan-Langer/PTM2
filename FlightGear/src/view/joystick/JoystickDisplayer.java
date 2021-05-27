package view.joystick;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import model.ListOfAttributes;

public class JoystickDisplayer extends AnchorPane {
	public DoubleProperty aileronValue,elevatorsValue,rudderValue,throttleValue;
	public DoubleProperty minAileron,maxAileron,minElevator,maxElevator,minThrottle,maxThrottle,
	minRudder,maxRudder;
	public JoystickController controller;

	public JoystickDisplayer() {
		super();
		throttleValue = new SimpleDoubleProperty();
		rudderValue=new SimpleDoubleProperty();
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		minThrottle=new SimpleDoubleProperty();
		maxThrottle=new SimpleDoubleProperty();
		minAileron=new SimpleDoubleProperty();
		maxAileron=new SimpleDoubleProperty();
		minRudder=new SimpleDoubleProperty();
		maxRudder=new SimpleDoubleProperty();
		minElevator=new SimpleDoubleProperty();
		maxElevator=new SimpleDoubleProperty();
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("Joystick.fxml").openStream());
			controller=loader.getController();
			this.getChildren().add(toDisplay);
			throttleValue=controller.throttle.valueProperty();
			minThrottle=controller.throttle.minProperty();
			maxThrottle=controller.throttle.maxProperty();
			rudderValue=controller.rudder.valueProperty();
			minRudder=controller.rudder.minProperty();
			maxRudder=controller.rudder.maxProperty();
			aileronValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=aileronValue.getValue();
					controller.aileronVal.setText(Double.toString(x));
				}
			});
			minAileron=controller.minAileron;
			maxAileron=controller.maxAileron;
			elevatorsValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=elevatorsValue.getValue();
					controller.elevatorVal.setText(Double.toString(x));
				}
			});
			minElevator=controller.minElevator;
			maxElevator=controller.maxElevator;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
