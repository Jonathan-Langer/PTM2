package view.tableClocks;

import java.io.IOException;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.ListOfAttributes;

public class TableClocksDisplayer extends Pane {
	public DoubleProperty altimeterValue, airspeedValue, headingValue,
	rollValue,pitchValue,yawValue;
	public DoubleProperty minAltimeter,maxAltimeter,minAirspeed,maxAirspeed,minHeading,maxHeading,
	minRoll,maxRoll,minPitch,maxPitch,minYaw,maxYaw;

	public TableClocksController controller;
	
	public TableClocksDisplayer() {
		super();
		altimeterValue = new SimpleDoubleProperty();
		airspeedValue = new SimpleDoubleProperty();
		headingValue = new SimpleDoubleProperty();
		rollValue=new SimpleDoubleProperty();
		pitchValue=new SimpleDoubleProperty();
		yawValue=new SimpleDoubleProperty();
		minAltimeter = new SimpleDoubleProperty();
		maxAltimeter = new SimpleDoubleProperty();
		minAirspeed = new SimpleDoubleProperty();
		maxAirspeed = new SimpleDoubleProperty();
		minHeading = new SimpleDoubleProperty();
		maxHeading = new SimpleDoubleProperty();
		minRoll = new SimpleDoubleProperty();
		maxRoll = new SimpleDoubleProperty();
		minPitch = new SimpleDoubleProperty();
		maxPitch = new SimpleDoubleProperty();
		minYaw = new SimpleDoubleProperty();
		maxYaw = new SimpleDoubleProperty();
		FXMLLoader loader=new FXMLLoader();
		try {
			Pane toDisplay=loader.load(getClass().getResource("TableClocks.fxml").openStream());
			controller=loader.getController();
			this.getChildren().add(toDisplay);
			controller.gAltimeter.valueProperty().bind(altimeterValue);
			minAltimeter.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minAltimeter.getValue();
					controller.gAltimeter.setMinValue(x);
				}
			});
			maxAltimeter.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxAltimeter.getValue();
					controller.gAltimeter.setMaxValue(x);
				}
			});
			controller.gAirspeed.valueProperty().bind(airspeedValue);
			minAirspeed.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minAirspeed.getValue();
					controller.gAirspeed.setMinValue(x);
				}
			});
			maxAirspeed.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxAirspeed.getValue();
					controller.gAirspeed.setMaxValue(x);
				}
			});
			controller.gHeading.valueProperty().bind(headingValue);
			minHeading.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minHeading.getValue();
					controller.gHeading.setMinValue(x);
				}
			});
			maxHeading.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxHeading.getValue();
					controller.gHeading.setMaxValue(x);
				}
			});
			controller.gPitch.valueProperty().bind(pitchValue);
			minPitch.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minPitch.getValue();
					controller.gPitch.setMinValue(x);
				}
			});
			maxPitch.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxPitch.getValue();
					controller.gPitch.setMaxValue(x);
				}
			});
			controller.gRoll.valueProperty().bind(rollValue);
			minRoll.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minRoll.getValue();
					controller.gRoll.setMinValue(x);
				}
			});
			maxRoll.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxRoll.getValue();
					controller.gRoll.setMaxValue(x);
				}
			});
			controller.gYaw.valueProperty().bind(yawValue);
			minYaw.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minYaw.getValue();
					controller.gYaw.setMinValue(x);
				}
			});
			maxYaw.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxYaw.getValue();
					controller.gYaw.setMaxValue(x);
				}
			});
			minYaw.setValue(0);
			maxYaw.setValue(10);
			yawValue.setValue(6.5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
