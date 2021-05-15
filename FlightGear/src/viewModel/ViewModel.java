package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.*;
import model.Model;
import model.MyModel;

public class ViewModel extends Observable implements Observer{

	
	StringProperty txtFilePath;
	StringProperty csvTrainFilePath;
	StringProperty csvTestFilePath;
	
	Map<String, Property> properties = new HashMap<>();
	
	DoubleProperty altimeterValue, airspeedValue, headingValue,
	rollValue,pitchValue,yawValue;//values of table clocks
	
	DoubleProperty aileronValue, elevatorsValue
	,throttleValue, rudderValue;//values of joystick
	
	public Model m;
	
	public ViewModel(Model m) {
		this.m=m;
		txtFilePath=new SimpleStringProperty();
		csvTrainFilePath=new SimpleStringProperty();
		csvTestFilePath = new SimpleStringProperty();
		
		throttleValue = new SimpleDoubleProperty();
		rudderValue=new SimpleDoubleProperty();
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		
		altimeterValue = new SimpleDoubleProperty();
		airspeedValue = new SimpleDoubleProperty();
		headingValue = new SimpleDoubleProperty();
		rollValue=new SimpleDoubleProperty();
		pitchValue=new SimpleDoubleProperty();
		yawValue=new SimpleDoubleProperty();
		
		properties.put("settings", this.txtFilePath);
		properties.put("csvTrain", this.csvTrainFilePath);
		properties.put("csvTest", this.csvTestFilePath);
		
		properties.put("altimeter", this.altimeterValue);
		properties.put("airspeed", this.airspeedValue);
		properties.put("heading", this.headingValue);
		properties.put("roll", this.rollValue);
		properties.put("pitch", this.pitchValue);
		properties.put("yaw", this.yawValue);
		
		properties.put("aileron", this.aileronValue);
		properties.put("elevators", this.elevatorsValue);
		properties.put("rudder", this.rudderValue);
		properties.put("throttle", this.throttleValue);
		
	}
	
	public boolean bindToProperty(String name, Property p) {
		if(!(properties.containsKey(name))) 
			return false;
		
		Property prop = properties.get(name);
		properties.remove(name);
		prop.bind(p);
		properties.put(name, prop);
		return true;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
