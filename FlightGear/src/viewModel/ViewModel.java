package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import model.Model;
import model.MyModel;

public class ViewModel extends Observable implements Observer{

	
	StringProperty txtFilePath;
	StringProperty csvTrainFilePath;
	StringProperty csvTestFilePath;
	
	public Map<String, Property> properties = new HashMap<>();

	//--------values for gauge table----------
	DoubleProperty altimeterValue, airspeedValue, headingValue,
	rollValue,pitchValue,yawValue;
	DoubleProperty minAltimeter,maxAltimeter,minAirspeed,maxAirspeed,minHeading,maxHeading,
	minRoll,maxRoll,minPitch,maxPitch,minYaw,maxYaw;

	//----------values for joystick-----------
	DoubleProperty aileronValue, elevatorsValue
	,throttleValue, rudderValue;
	DoubleProperty minAileron,maxAileron,minElevator,maxElevator,minThrottle,maxThrottle,
	minRudder,maxRudder;
	
	public Model m;
	
	public ViewModel(Model m) {
		this.m=m;
		txtFilePath=new SimpleStringProperty("");
		csvTrainFilePath=new SimpleStringProperty("");
		csvTestFilePath = new SimpleStringProperty("");
		
		throttleValue = new SimpleDoubleProperty();
		rudderValue=new SimpleDoubleProperty(1);
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		minThrottle=new SimpleDoubleProperty();
		maxThrottle=new SimpleDoubleProperty();
		minAileron=new SimpleDoubleProperty();
		maxAileron=new SimpleDoubleProperty();
		minRudder=new SimpleDoubleProperty();
		maxRudder=new SimpleDoubleProperty(2);
		minElevator=new SimpleDoubleProperty();
		maxElevator=new SimpleDoubleProperty();
		
		altimeterValue = new SimpleDoubleProperty(10);
		airspeedValue = new SimpleDoubleProperty();
		headingValue = new SimpleDoubleProperty();
		rollValue=new SimpleDoubleProperty();
		pitchValue=new SimpleDoubleProperty();
		yawValue=new SimpleDoubleProperty();
		minAltimeter = new SimpleDoubleProperty(10);
		maxAltimeter = new SimpleDoubleProperty(10);
		minAirspeed = new SimpleDoubleProperty();
		maxAirspeed = new SimpleDoubleProperty();
		minHeading = new SimpleDoubleProperty();
		maxHeading = new SimpleDoubleProperty(10);
		minRoll = new SimpleDoubleProperty();
		maxRoll = new SimpleDoubleProperty();
		minPitch = new SimpleDoubleProperty();
		maxPitch = new SimpleDoubleProperty();
		minYaw = new SimpleDoubleProperty();
		maxYaw = new SimpleDoubleProperty();
		
		properties.put("settingsFile", this.txtFilePath);
		properties.put("csvTrain", this.csvTrainFilePath);
		properties.put("csvTest", this.csvTestFilePath);
		
		properties.put("altimeter", this.altimeterValue);
		properties.put("airspeed", this.airspeedValue);
		properties.put("heading", this.headingValue);
		properties.put("roll", this.rollValue);
		properties.put("pitch", this.pitchValue);
		properties.put("yaw", this.yawValue);
		properties.put("minAltimeter",this.minAltimeter);
		properties.put("maxAltimeter",this.maxAltimeter);
		properties.put("minAirspeed",this.minAirspeed);
		properties.put("maxAirspeed",this.minAltimeter);
		properties.put("minHeading",this.minHeading);
		properties.put("maxHeading",this.maxHeading);
		properties.put("minRoll",this.minRoll);
		properties.put("maxRoll",this.maxRoll);
		properties.put("minPitch",this.minPitch);
		properties.put("maxPitch",this.maxPitch);
		properties.put("minYaw",this.minYaw);
		properties.put("maxYaw",this.maxYaw);
		
		properties.put("aileron", this.aileronValue);
		properties.put("elevator", this.elevatorsValue);
		properties.put("rudder", this.rudderValue);
		properties.put("throttle", this.throttleValue);
		properties.put("minAileron",this.minAileron);
		properties.put("maxAileron",this.maxAileron);
		properties.put("minElevator",this.minElevator);
		properties.put("maxElevator",this.maxElevator);
		properties.put("minRudder",this.minRudder);
		properties.put("maxRudder",this.maxRudder);
		properties.put("minThrottle",this.minThrottle);
		properties.put("maxThrottle",this.maxThrottle);
		
	}
	
	public boolean bindToProperty(String name, Property p,String direction) {
		//if the direction is VM2V: property_from_view.bind(property_form_view_model)
		//if the direction is V2VM: property_from_view_model.bind(property_from_view)
		if((!(properties.containsKey(name)))||(!(direction.equals("V2VM")||direction.equals("VM2V")))){
			System.out.println("false");
			return false;
		}
//		if(name.equals("settingsFile")){
//			StringProperty tmp=(StringProperty) p;
//			tmp.set(tmp.getValue());
//			tmp.addListener(new ChangeListener<String>() {
//				@Override
//				public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
//					String str=tmp.getValue();
//					txtFilePath.setValue(str);
//				}
//			});
//			tmp.set(tmp.getValue());
//			return true;
//		}
		Property prop = properties.get(name);
		properties.remove(name);

		if(direction.equals("V2VM")){	//the View change the ViewModel
			p.addListener(new ChangeListener() {
				@Override
				public void changed(ObservableValue observableValue, Object o, Object t1) {
					Object obj=p.getValue();
					prop.setValue(obj);
				}
			});
			p.setValue(p.getValue());
		}
		else{	//the ViewModel change the View
			p.bind(prop);
			prop.setValue(prop.getValue());
		}

		properties.put(name, prop);

		return true;
	}
	public void initializePropertyToView(){
		aileronValue.setValue(100);
		pitchValue.setValue(3);
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}

}
