package model;


import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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
	int port=-1;
	String ip="";
	int rate=-1;

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
				ip=atrList.ip;
				port= atrList.port;
				rate=atrList.rate;
			}
		}
	}

	
	public TimeSeries checkValidationCSV(String csv) {
		TimeSeries ts=new TimeSeries(csv);
		if(ts.isEmpty())
			return null;
		int atrLen=0;
		ArrayList<Float> thisLine;
		
		Map<Integer, AttributeSettings> atrCols=
				atrList.getList().values().stream().
				collect(Collectors.toMap
						(atr->atr.colInCSV,atr->atr));
		
		for(int i=0; i<ts.getSize(); i++) {
			thisLine=ts.getLine(ts.getTitles().get(i));
			if(thisLine.size()!=ts.getSize()) //if all cols are in same length
				return null;
			if(atrCols.containsKey(i)) {//this col must be in csv
				atrLen++;
				for(float f : thisLine) { //if all values are between the min and max that was defined in the setting file
					if(f>atrCols.get(i).getMaxValue()||f<atrCols.get(i).getMinValue())
						return null;
				}
			}
		}
		if(atrLen!=atrList.getLength()) // if all the settings appear in the setting file
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
		try {
			Socket fg=new Socket("localhost",5400);
			PrintWriter writeToFlightGear=new PrintWriter(fg.getOutputStream());
			for(int i=startTime;i<startTime+rate;i++){
				String line="";
				for(int j=0;j<test.getTitles().size();j++)
					if(j!=0)
						line=line+","+test.getLineAsArray(j)[i];
					else
						line=line+test.getLineAsArray(j)[i];
				writeToFlightGear.println(line);
				writeToFlightGear.flush();
				Thread.sleep(100);//replace the number 100 with (long)(1000.0/(float)rate)
				fg.close();
				writeToFlightGear.close();
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
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
