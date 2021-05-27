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
	public DoubleProperty currentTime;

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
		currentTime=new SimpleDoubleProperty();

		txtFilePath=new SimpleStringProperty("");
		csvTrainFilePath=new SimpleStringProperty("");
		csvTestFilePath = new SimpleStringProperty("");
		
		throttleValue = new SimpleDoubleProperty();
		rudderValue=new SimpleDoubleProperty();
		aileronValue=new SimpleDoubleProperty();
		elevatorsValue=new SimpleDoubleProperty();
		minThrottle=new SimpleDoubleProperty();
		maxThrottle=new SimpleDoubleProperty(1);
		minAileron=new SimpleDoubleProperty();
		maxAileron=new SimpleDoubleProperty();
		minRudder=new SimpleDoubleProperty();
		maxRudder=new SimpleDoubleProperty(2);
		minElevator=new SimpleDoubleProperty();
		maxElevator=new SimpleDoubleProperty();
		
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
		properties.put("maxAirspeed",this.maxAirspeed);
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
		if((!(properties.containsKey(name)))||(!(direction.equals("V2VM")||direction.equals("VM2V"))))
			return false;
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
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			String s=(String)arg;
			String[] arr=s.split(" ");
			double toUpdate=Double.parseDouble(arr[1]);
			if(arr[0].equals("currentTime:"))
				currentTime.set(toUpdate);
			if(arr[0].equals("aileronMin:"))
				minAileron.setValue(toUpdate);
			if(arr[0].equals("aileronMax:"))
				maxAileron.setValue(toUpdate);
			if(arr[0].equals("aileronVal:"))
				aileronValue.setValue(toUpdate);
			if(arr[0].equals("elevatorMin:"))
				minElevator.setValue(toUpdate);
			if(arr[0].equals("elevatorMax:"))
				maxElevator.setValue(toUpdate);
			if(arr[0].equals("elevatorVal:"))
				elevatorsValue.setValue(toUpdate);
			if(arr[0].equals("rudderMin:"))
				minRudder.setValue(toUpdate);
			if(arr[0].equals("rudderMax:"))
				maxRudder.setValue(toUpdate);
			if(arr[0].equals("rudderVal:"))
				rudderValue.setValue(toUpdate);
			if(arr[0].equals("throttleMin:"))
				minThrottle.setValue(toUpdate);
			if(arr[0].equals("throttleMax:"))
				maxThrottle.setValue(toUpdate);
			if(arr[0].equals("throttleVal:"))
				throttleValue.setValue(toUpdate);
			if(arr[0].equals("yawMin:"))
				minYaw.setValue(toUpdate);
			if(arr[0].equals("yawMax:"))
				maxYaw.setValue(toUpdate);
			if(arr[0].equals("yawVal:"))
				yawValue.setValue(toUpdate);
			if(arr[0].equals("airspeedMin:"))
				minAirspeed.setValue(toUpdate);
			if(arr[0].equals("airspeedMax:"))
				maxAirspeed.setValue(toUpdate);
			if(arr[0].equals("airspeedVal:"))
				airspeedValue.setValue(toUpdate);
			if(arr[0].equals("altimeterMin:"))
				minAltimeter.setValue(toUpdate);
			if(arr[0].equals("altimeterMax:"))
				maxAltimeter.setValue(toUpdate);
			if(arr[0].equals("altimeterVal:"))
				altimeterValue.setValue(toUpdate);
			if(arr[0].equals("rollMin:"))
				minRoll.setValue(toUpdate);
			if(arr[0].equals("rollMax:"))
				maxRoll.setValue(toUpdate);
			if(arr[0].equals("rollVal:"))
				rollValue.setValue(toUpdate);
			if(arr[0].equals("pitchMin:"))
				minPitch.setValue(toUpdate);
			if(arr[0].equals("pitchMax:"))
				maxPitch.setValue(toUpdate);
			if(arr[0].equals("pitchVal:"))
				pitchValue.setValue(toUpdate);
			if(arr[0].equals("headingMin:"))
				minHeading.setValue(toUpdate);
			if(arr[0].equals("headingMax:"))
				maxHeading.setValue(toUpdate);
			if(arr[0].equals("headingVal:"))
				headingValue.setValue(toUpdate);
		}
	}
	public boolean checkValidateSettingFile(String txtFile){
		return m.checkValidateSettingFile(txtFile);
	}
	public void saveLastSettingFile(){
		m.saveLastSettingFile(txtFilePath.getValue());
	}
	public void applyValuesMinMax(){ m.applyValuesMinMax();}
	
	public boolean setTrainTimeSeries(String csvTrainFile) {
		if(!m.setTrainTimeSeries(csvTrainFile))
			return false;
		return true;
	}
	
	public void saveLastCsvTrainFile(){
		m.saveLastCsvTrainFile(csvTrainFilePath.getValue());
	}
	
	public boolean setTestTimeSeries(String csvTestFile) {
		if(!m.setTestTimeSeries(csvTestFile))//shnik put setTrain but i think she is wrong
			return false;
		csvTestFilePath.setValue(csvTestFile);
		return true;
	}
	public void initValues(){
		m.setValues(0);
	}
}
