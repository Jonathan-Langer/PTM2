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
			altimeterValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=altimeterValue.getValue();
					controller.gAltimeter.setValue(x);
					System.out.println("altimeterVal: "+x);
				}
			});
			minAltimeter.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minAltimeter.getValue();
					controller.gAltimeter.setMinValue(x);
					System.out.println("minAltimeter: "+x);
				}
			});
			maxAltimeter.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxAltimeter.getValue();
					controller.gAltimeter.setMaxValue(x);
					System.out.println("maxAltimeter: "+x);
				}
			});
			airspeedValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=airspeedValue.getValue();
					controller.gAirspeed.setValue(x);
					System.out.println("airspeedVal: "+x);
				}
			});
			minAirspeed.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minAirspeed.getValue();
					controller.gAirspeed.setMinValue(x);
					System.out.println("minAirspeed: "+x);
				}
			});
			maxAirspeed.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxAirspeed.getValue();
					controller.gAirspeed.setMaxValue(x);
					System.out.println("maxAirspeed: "+x);
				}
			});
			headingValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=headingValue.getValue();
					controller.gHeading.setValue(x);
					System.out.println("headingVal: "+x);
				}
			});
			minHeading.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minHeading.getValue();
					controller.gHeading.setMinValue(x);
					System.out.println("minHeading: "+x);
				}
			});
			maxHeading.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxHeading.getValue();
					controller.gHeading.setMaxValue(x);
					System.out.println("maxHeading "+x);
				}
			});
			pitchValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=pitchValue.getValue();
					controller.gPitch.setValue(x);
					System.out.println("pitchVal: "+x);
				}
			});
			minPitch.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minPitch.getValue();
					controller.gPitch.setMinValue(x);
					System.out.println("minPitch: "+x);
				}
			});
			maxPitch.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxPitch.getValue();
					controller.gPitch.setMaxValue(x);
					System.out.println("maxPitch: "+x);
				}
			});
			rollValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=rollValue.getValue();
					controller.gRoll.setValue(x);
					System.out.println("rollVal: "+x);
				}
			});
			minRoll.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minRoll.getValue();
					controller.gRoll.setMinValue(x);
					System.out.println("minRoll: "+x);
				}
			});
			maxRoll.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxRoll.getValue();
					controller.gRoll.setMaxValue(x);
					System.out.println("maxRoll: "+x);
				}
			});
			yawValue.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=yawValue.getValue();
					controller.gYaw.setValue(x);
					System.out.println("yawVal: "+x);
				}
			});
			minYaw.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=minYaw.getValue();
					controller.gYaw.setMinValue(x);
					System.out.println("minYaw: "+x);
				}
			});
			maxYaw.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double x=maxYaw.getValue();
					controller.gYaw.setMaxValue(x);
					System.out.println("maxYaw: "+x);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
