package view.joystick;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	public StringProperty throttleName,rudderName,aileronName,elevatorName;
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
			throttleName=controller.throttleTitle.textProperty();
			aileronName=controller.aileronTitle.textProperty();
			rudderName=controller.rudderTitle.textProperty();
			elevatorName=controller.elevatorTitle.textProperty();

			aileronValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					Platform.runLater(()->{
						double x=aileronValue.getValue();
						controller.aileronVal.setText(""
								+Double.parseDouble(new DecimalFormat("##.######").format(x)));
					});
				}
			});
			minAileron=controller.minAileron;
			maxAileron=controller.maxAileron;
			elevatorsValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					Platform.runLater(()->{
						double x=elevatorsValue.getValue();
						controller.elevatorVal.setText(""+x);
					});
				}
			});
			minElevator=controller.minElevator;
			maxElevator=controller.maxElevator;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
