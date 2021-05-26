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
		throttleValue = new SimpleDoubleProperty(0.9);
		rudderValue=new SimpleDoubleProperty(0.5);
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		minThrottle=new SimpleDoubleProperty(-1);
		maxThrottle=new SimpleDoubleProperty(1);
		minAileron=new SimpleDoubleProperty(-1);
		maxAileron=new SimpleDoubleProperty(1);
		minRudder=new SimpleDoubleProperty(-1);
		maxRudder=new SimpleDoubleProperty(1);
		minElevator=new SimpleDoubleProperty(-1);
		maxElevator=new SimpleDoubleProperty(1);
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("Joystick.fxml").openStream());
			//JoystickController controller=new JoystickController();
			controller=loader.getController();
			//controller.rudder.valueProperty().bind(rudderValue);
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
					controller.aileronVal.setText(""+x);
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
