package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;
import model.MyModel;

public class ViewModel extends Observable implements Observer{

	
	public StringProperty txtFilePath;
	public StringProperty csvFilePath;
	public DoubleProperty altitudeValue, speedbrakeValue,
	rollValue,pitchValue,yawValue;//values of table clocks
	public DoubleProperty aileronValue, elevatorsValue
	, throttleValue, rudderValue;//values of joystick
	public Model m;
	
	public ViewModel(Model m) {
		this.m=m;
		txtFilePath=new SimpleStringProperty();
		csvFilePath=new SimpleStringProperty();
		altitudeValue=new SimpleDoubleProperty();
		speedbrakeValue=new SimpleDoubleProperty();
		rudderValue=new SimpleDoubleProperty();
		rollValue=new SimpleDoubleProperty();
		pitchValue=new SimpleDoubleProperty();
		yawValue=new SimpleDoubleProperty();
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		throttleValue=new SimpleDoubleProperty();
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
