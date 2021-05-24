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
			controller=loader.getController();
			//controller.rudder.valueProperty().bind(rudderValue);
			this.getChildren().add(toDisplay);
			controller.rudder.valueProperty().bind(rudderValue);
			controller.throttle.valueProperty().bind(throttleValue);
			aileronValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=aileronValue.getValue();
					controller.aileronVal.setText(Double.toString(x));
				}
			});
			elevatorsValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=elevatorsValue.getValue();
					controller.elevatorVal.setText(Double.toString(x));
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
