package model;


import java.io.*;
import java.util.Observable;
import java.util.*;
import java.util.function.Consumer;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;
import javafx.scene.control.Alert;

public class MyModel extends Observable implements Model {

	double currentTime;
	TimeSeries train,test;
	TimeSeriesAnomalyDetector detector;
	ListOfAttributes atrList;
	String txtLast;
	double aileronVal,elevatorVal,rudderVal,throttleVal,altimeterVal,airspeedVal,headingVal,rollVal,pitchVal,yawVal;

	public double getAileronVal() {
		return aileronVal;
	}

	public void setAileronVal(double aileronVal) {
		this.aileronVal = aileronVal;
		setChanged();
		notifyObservers("aileronVal: "+aileronVal);
	}

	public double getElevatorVal() {
		return elevatorVal;
	}

	public void setElevatorVal(double elevatorVal) {
		this.elevatorVal = elevatorVal;
		setChanged();
		notifyObservers("elevatorVal: "+elevatorVal);
	}

	public double getRudderVal() {
		return rudderVal;
	}

	public void setRudderVal(double rudderVal) {
		this.rudderVal = rudderVal;
		setChanged();
		notifyObservers("rudderVal: "+rudderVal);
	}

	public double getThrottleVal() {
		return throttleVal;
	}

	public void setThrottleVal(double throttleVal) {
		this.throttleVal = throttleVal;
		setChanged();
		notifyObservers("throttleVal: "+throttleVal);
	}

	public double getAltimeterVal() {
		return altimeterVal;
	}

	public void setAltimeterVal(double altimeterVal) {
		this.altimeterVal = altimeterVal;
		setChanged();
		notifyObservers("altimeterVal: "+altimeterVal);
	}

	public double getAirspeedVal() {
		return airspeedVal;
	}

	public void setAirspeedVal(double airspeedVal) {
		this.airspeedVal = airspeedVal;
		setChanged();
		notifyObservers("airspeedVal: "+airspeedVal);
	}

	public double getHeadingVal() {
		return headingVal;
	}

	public void setHeadingVal(double headingVal) {
		this.headingVal = headingVal;
		setChanged();
		notifyObservers("headingVal: "+headingVal);
	}

	public double getRollVal() {
		return rollVal;
	}

	public void setRollVal(double rollVal) {
		this.rollVal = rollVal;
		setChanged();
		notifyObservers("rollVal: "+rollVal);
	}

	public double getPitchVal() {
		return pitchVal;
	}

	public void setPitchVal(double pitchVal) {
		this.pitchVal = pitchVal;
		setChanged();
		notifyObservers("pitchVal: "+pitchVal);
	}

	public double getYawVal() {
		return yawVal;
	}

	public void setYawVal(double yawVal) {
		this.yawVal = yawVal;
		setChanged();
		notifyObservers("yawVal: "+yawVal);
	}

	public MyModel() {
		this.txtLast = new File("resources/last_setting.txt").getAbsolutePath();
		atrList=new ListOfAttributes(txtLast);
	}

	Thread t;
	Process p;
	@Override
	public boolean checkValidateSettingFile(String txtFile) {
		if(txtFile==null)
			return false;
		if(!txtFile.endsWith(".txt"))
			return false;
		File f=new File(txtFile);
		if(!f.exists())
			return false;
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		HashMap<String, Boolean> specialSettingsApeared = new HashMap<>();

		//for the ip, port and rate
		specialSettingsApeared.put("ip", false);
		specialSettingsApeared.put("port", false);
		specialSettingsApeared.put("rate", false);

		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(6, false);
		cellsAreApeared.put(20, false);
		cellsAreApeared.put(24, false);
		cellsAreApeared.put(25, false);
		cellsAreApeared.put(28, false);
		cellsAreApeared.put(29, false);
		cellsAreApeared.put(36, false);
		try {
			BufferedReader read=new BufferedReader(new FileReader(new File(txtFile)));
			String line=null;
			while((line=read.readLine())!=null) {
				String[] data = line.split(",");
				if (data.length == 4) {
					if (!cellsAreApeared.containsKey(Integer.parseInt(data[1]))) {
						read.close();
						return false;
					}
					if (Double.parseDouble(data[2]) > Double.parseDouble(data[3])) {
						read.close();
						return false;
					}
					if (cellsAreApeared.get(Integer.parseInt(data[1]))) {
						read.close();
						return false;
					} else
						cellsAreApeared.put(Integer.parseInt(data[1]), true);
				} else {
					if (data.length == 2) {
						if (!(data[0].equals("ip") || data[0].equals("port") || data[0].equals("rate"))) {
							read.close();
							return false;
						} else {
							if (data[0].equals("ip")) {
								if (specialSettingsApeared.get("ip").equals(true)) {
									read.close();
									return false;
								}
								else
									specialSettingsApeared.put("ip", true);
							}
							if (data[0].equals("port")) {
								if (specialSettingsApeared.get("port").equals(true)) {
									read.close();
									return false;
								}
								else
									specialSettingsApeared.put("port", true);
							}
							if (data[0].equals("rate")) {
								if (specialSettingsApeared.get("rate").equals(true)) {
									read.close();
									return false;
								}
								else
									specialSettingsApeared.put("rate", true);
							}
						}
					}
				}
			}
			for (Integer c: cellsAreApeared.keySet()) {
				if(!cellsAreApeared.get(c))
					return false;
			}
			for(String s : specialSettingsApeared.keySet()) {
				if(!specialSettingsApeared.get(s))
					return false;
			}
			line=read.readLine();
			read.close();
			return line == null;
		} catch (IOException e) {
			return false;
		}
 
	}

	@Override
	public void saveLastSettingFile(String currentTxtFile) {
		copyFile(currentTxtFile, new File("resources/last_setting.txt").getAbsolutePath());
		atrList=new ListOfAttributes(txtLast);
	}

	@Override
	public void applyValuesMinMax() {
		if(atrList!=null){
			for(String key:atrList.getAttributesNames()){
				AttributeSettings a=atrList.getList().get(key);
				setChanged();
				if(a.getColInCSV()==0){
					notifyObservers("aileronMin: "+a.getMinValue());
					setChanged();
					notifyObservers("aileronMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==1){
					notifyObservers("elevatorMin: "+a.getMinValue());
					setChanged();
					notifyObservers("elevatorMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==2){
					notifyObservers("rudderMin: "+a.getMinValue());
					setChanged();
					notifyObservers("rudderMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==6){
					notifyObservers("throttleMin: "+a.getMinValue());
					setChanged();
					notifyObservers("throttleMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==20) {
					notifyObservers("yawMin: "+a.getMinValue());
					setChanged();
					notifyObservers("yawMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==24){
					notifyObservers("airspeedMin: "+a.getMinValue());
					setChanged();
					notifyObservers("airspeedMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==25){
					notifyObservers("altimeterMin: "+a.getMinValue());
					setChanged();
					notifyObservers("altimeterMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==28){
					notifyObservers("rollMin: "+a.getMinValue());
					setChanged();
					notifyObservers("rollMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==29){
					notifyObservers("pitchMin: "+a.getMinValue());
					setChanged();
					notifyObservers("pitchMax: "+a.getMaxValue());
				}
				if(a.getColInCSV()==36){
					notifyObservers("headingMin: "+a.getMinValue());
					setChanged();
					notifyObservers("headingMax: "+a.getMaxValue());
				}

			}
		}
	}

	
	public TimeSeries checkValidationCSV(String csv) {
		if(csv==null)
			return null;
		if(!csv.endsWith(".csv"))
			return null;
		File file=new File(csv);
		if(!file.exists())
			return null;
		TimeSeries ts=new TimeSeries(csv);
		if(ts.isEmpty())
			return null;
		int atrLen=0;
		/*for(String s : ts.getTitles()) {
			if(ts.getLine(s).size()!=ts.getLine(ts.getTitles().get(0)).size()) //if all cols are in same length
				return null;
			if(atrList.contains(s)) { //check if the col in the csv matches to 
									//the col that is written in the settings file 
				if(ts.getTitles().indexOf(s)!=atrList.getList().get(s).getColInCSV())
					return null;
				atrLen++;
				for(float f : ts.getLine(s)) { //if all values are between the min and max that was defind in the setting file
					if(f>atrList.getList().get(s).getMaxValue()||f<atrList.getList().get(s).getMinValue())
						return null;
				}
			}
		}*/
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(6, false);
		cellsAreApeared.put(20, false);
		cellsAreApeared.put(24, false);
		cellsAreApeared.put(25, false);
		cellsAreApeared.put(28, false);
		cellsAreApeared.put(29, false);
		cellsAreApeared.put(36, false);
		for(String s:ts.getTitles()){
			if(ts.getLine(s).size()!=ts.getLine(ts.getTitles().get(0)).size()) //if all cols are in same length
				return null;
			if(atrList.contains(s)){
				AttributeSettings a=atrList.attributesConnection.get(s);
				int coll=a.colInCSV;
				if(!cellsAreApeared.get(coll)){
					float[] arr=ts.getLineAsArray(coll);
					for(float f : arr) { //if all values are between the min and max that was defind in the setting file
						if(f>a.getMaxValue()||f<a.getMinValue())
							return null;
					}
					atrLen++;
					cellsAreApeared.put(coll,true);
				}
			}
		}
		if(atrLen!=atrList.getLength()) // if all the names in the csv apeearing int he setting file
			return null;
		
		return ts;
	}

	
	@Override
	public boolean setTrainTimeSeries(String csvTrainFile) {
		TimeSeries ts= checkValidationCSV(csvTrainFile);
		if(ts!=null) {
			train=ts;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean setTestTimeSeries(String csvTestFile){
		TimeSeries ts= checkValidationCSV(csvTestFile);
		if(ts!=null) {
			test=ts;
			return true;
		}
		return false;
	}

	
	public void saveLastCsvTrainFile(String currentCsvTrainFile) {
		copyFile(currentCsvTrainFile, new File("resources/last_train.txt").getAbsolutePath());
	}
	
	public void copyFile(String originalPath, String copyPath) {
		File lastFile=new File(copyPath);
		File currentFile=new File(originalPath);
		try {
			if(!lastFile.exists())
				lastFile.createNewFile();
			PrintWriter write=new PrintWriter(lastFile);
			BufferedReader read=new BufferedReader(new FileReader(currentFile));
			String line=null;
			while((line=read.readLine())!=null) {
				write.println(line);
				write.flush();
			}
			write.close();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void play(int startTime, int rate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAnomalyDetector(TimeSeriesAnomalyDetector ad) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
	@Override
	public void setValues(int timeStep){
		if(test!=null){
			if (timeStep < 0 || test.getLineAsArray(0).length >= timeStep) {
				double aileron=test.getLineAsArray(0)[timeStep];
				double elevator=test.getLineAsArray(1)[timeStep];
				double throttle=test.getLineAsArray(6)[timeStep];
				double rudder=test.getLineAsArray(2)[timeStep];
				double altimeter=test.getLineAsArray(25)[timeStep];
				double airspeed=test.getLineAsArray(24)[timeStep];
				double heading=test.getLineAsArray(36)[timeStep];
				double roll=test.getLineAsArray(28)[timeStep];
				double pitch=test.getLineAsArray(29)[timeStep];
				double yaw=test.getLineAsArray(20)[timeStep];
				setAileronVal(aileron);
				setElevatorVal(elevator);
				setThrottleVal(throttle);
				setRudderVal(rudder);
				setAltimeterVal(altimeter);
				setAirspeedVal(airspeed);
				setHeadingVal(heading);
				setRollVal(roll);
				setPitchVal(pitch);
				setYawVal(yaw);
			}
		}
	}
	public void setCurrentTime(double currentTime){
		this.currentTime=currentTime;
		setChanged();
		notifyObservers("currentTime: "+currentTime);
	}
}
