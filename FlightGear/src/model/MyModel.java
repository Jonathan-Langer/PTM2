package model;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import anomaly_detectors.*;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import viewModel.ViewModel;

public class MyModel extends Observable implements Model {

	int currentTime = 0;
	TimeSeries train, test;
	TimeSeries trainForView, testForView;
	TimeSeriesAnomalyDetector detector;
	ListOfAttributes atrList;
	HashMap<String, Integer> collsForView;
	String txtLast;
	double aileronVal, elevatorVal, rudderVal, throttleVal, altimeterVal, airspeedVal, headingVal, rollVal, pitchVal, yawVal;
	final String aileronName="aileron", elevatorName="elevator", rudderName="rudder", throttleName="throttle", altimeterName="altimeter", airspeedName="airspeed", headingName="heading", rollName="roll", pitchName="pitch", yawName="yaw";
	int port = -1;
	String ip = "";
	int rate = -1;
	Socket fg = null;
	double speed;
	PrintWriter writeToFlightGear = null;
	ActiveObject task;
	HashMap<String,List<Point>> pointsToDisplay;
	String selectedFeature,correlatedFeature;

	public MyModel() {
		this.txtLast = new File("resources/last_setting.txt").getAbsolutePath();
		checkValidateSettingFile(new File("resources/last_setting.txt").getAbsolutePath());
		atrList = new ListOfAttributes(txtLast);
		collsForView = new HashMap<>();
		task=new ActiveObject(1);
	}
	@Override
	public int getRate(){return rate;}
	public double getAileronVal() {
		return aileronVal;
	}

	@Override
	public void setCurrentTimeWithoutNotify(int currentTime){this.currentTime=currentTime;}

	public void setRate(int rate){
		this.rate=rate;
		setChanged();
		notifyObservers("rate: "+rate);
	}

	public void setAileronVal(double aileronVal) {
		this.aileronVal = aileronVal;
		setChanged();
		notifyObservers("aileronVal: " + aileronVal);
	}

	public double getElevatorVal() {
		return elevatorVal;
	}

	public void setElevatorVal(double elevatorVal) {
		this.elevatorVal = elevatorVal;
		setChanged();
		notifyObservers("elevatorVal: " + elevatorVal);
	}

	public double getRudderVal() {
		return rudderVal;
	}

	public void setRudderVal(double rudderVal) {
		this.rudderVal = rudderVal;
		setChanged();
		notifyObservers("rudderVal: " + rudderVal);
	}

	public double getThrottleVal() {
		return throttleVal;
	}

	public void setThrottleVal(double throttleVal) {
		this.throttleVal = throttleVal;
		setChanged();
		notifyObservers("throttleVal: " + throttleVal);
	}

	public double getAltimeterVal() {
		return altimeterVal;
	}

	public void setAltimeterVal(double altimeterVal) {
		this.altimeterVal = altimeterVal;
		setChanged();
		notifyObservers("altimeterVal: " + altimeterVal);
	}

	public double getAirspeedVal() {
		return airspeedVal;
	}

	public void setAirspeedVal(double airspeedVal) {
		this.airspeedVal = airspeedVal;
		setChanged();
		notifyObservers("airspeedVal: " + airspeedVal);
	}

	public double getHeadingVal() {
		return headingVal;
	}

	public void setHeadingVal(double headingVal) {
		this.headingVal = headingVal;
		setChanged();
		notifyObservers("headingVal: " + headingVal);
	}

	public double getRollVal() {
		return rollVal;
	}

	public void setRollVal(double rollVal) {
		this.rollVal = rollVal;
		setChanged();
		notifyObservers("rollVal: " + rollVal);
	}

	public double getPitchVal() {
		return pitchVal;
	}

	public void setPitchVal(double pitchVal) {
		this.pitchVal = pitchVal;
		setChanged();
		notifyObservers("pitchVal: " + pitchVal);
	}

	public double getYawVal() {
		return yawVal;
	}

	public void setYawVal(double yawVal) {
		this.yawVal = yawVal;
		setChanged();
		notifyObservers("yawVal: " + yawVal);
	}

	private ListOfAttributes checkValidationSettingFile(String txtFile) {
		if (txtFile == null)
			return null;
		File f = new File(txtFile);
		if (!f.exists())
			return null;
		HashMap<String, Integer> atrApeared = new HashMap<>();
		ListOfAttributes atrL = new ListOfAttributes();

		//for the ip, port and rate
		atrApeared.put(aileronName, -1);
		atrApeared.put(elevatorName, -1);
		atrApeared.put(rudderName, -1);
		atrApeared.put(throttleName, -1);
		atrApeared.put(altimeterName, -1);
		atrApeared.put(airspeedName, -1);
		atrApeared.put(headingName, -1);
		atrApeared.put(rollName, -1);
		atrApeared.put(pitchName, -1);
		atrApeared.put(yawName, -1);
		atrApeared.put("ip", -1);
		atrApeared.put("port", -1);
		atrApeared.put("rate", -1);

		try {
			BufferedReader read = new BufferedReader(new FileReader(new File(txtFile)));
			String line = null;
			while ((line = read.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length == 4) {
					if (!atrApeared.containsKey(data[0])) { //only these attributes
						read.close();
						return null;
					}
					if (Double.parseDouble(data[2]) >= Double.parseDouble(data[3])) { //min is smaller than max
						read.close();
						return null;
					}
					if (atrApeared.get(data[0]) != -1) { //attribute is already in use
						read.close();
						return null;
					}
					if (atrApeared.containsValue(Integer.parseInt(data[1]))) { //col is already in use
						read.close();
						return null;
					}
					atrApeared.put(data[0], Integer.parseInt(data[1]));
					String[] arr = {data[1], data[2], data[3]};
					atrL.addAttribute(data[0], new AttributeSettings(arr));
				}//if 4
				else {
					if (data.length == 2) {
						if (!(data[0].equals("ip") || data[0].equals("port") || data[0].equals("rate"))) {
							read.close();
							return null;
						} else {
							if (data[0].equals("ip")) {
								if (atrApeared.get("ip") != -1) {
									read.close();
									return null;
								} else
									atrL.ip = data[1];
							}
							if (data[0].equals("port")) {
								if (atrApeared.get("port")!=-1) {
									read.close();
									return null;
								} else
									atrL.port = Integer.parseInt(data[1]);
							}
							if (data[0].equals("rate")) {
								if (atrApeared.get("rate")!=-1) {
									read.close();
									return null;
								} else
									atrL.rate = Integer.parseInt(data[1]);
							}
							atrApeared.put(data[0], -2);
						}
					}//if 2
					else {
						read.close();
						return null;
					}//else
				}//else
			}//while
			read.close();
			return atrL;
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public void saveLastSettingFile(String currentTxtFile) {
		copyFile(currentTxtFile, new File("resources/last_setting.txt").getAbsolutePath());
		atrList = new ListOfAttributes(txtLast);
	}

	@Override
	public boolean checkValidateSettingFile(String txtFile) {
		ListOfAttributes atrL = checkValidationSettingFile(txtFile);
		if (atrL == null){
			atrList=new ListOfAttributes();
			return false;
		}
		atrList = atrL;
		return true;
	}

	@Override
	public void applyValuesMinMax() {
		if (atrList != null) {
			for (String key : atrList.getAttributesNames()) {
				AttributeSettings a = atrList.getList().get(key);
				setChanged();
				notifyObservers(key + "Min: " + a.getMinValue());
				setChanged();
				notifyObservers(key + "Max: " + a.getMaxValue());
			}
			ip = atrList.ip;
			port = atrList.port;
			rate=atrList.rate;
		}
	}

	@Override
	public void setSpeedOfFlight(double speed){
		this.speed=speed;
	}

	@Override
	public void applyNames() {
		if (atrList != null) {
			if(!atrList.isEmpty()){
				try {
					collsForView.put(aileronName,atrList.getList().get(aileronName).colInCSV);
					collsForView.put(elevatorName,atrList.getList().get(elevatorName).colInCSV);
					collsForView.put(rudderName, atrList.getList().get(rudderName).colInCSV);
					collsForView.put(throttleName, atrList.getList().get(throttleName).colInCSV);
					collsForView.put(altimeterName,atrList.getList().get(altimeterName).colInCSV);
					collsForView.put(airspeedName,atrList.getList().get(airspeedName).colInCSV);
					collsForView.put(headingName,atrList.getList().get(headingName).colInCSV);
					collsForView.put(rollName,atrList.getList().get(rollName).colInCSV);
					collsForView.put(pitchName,atrList.getList().get(pitchName).colInCSV);
					collsForView.put(yawName,atrList.getList().get(yawName).colInCSV);
					setChanged();
					notifyObservers("aileronName: "+aileronName);
					setChanged();
					notifyObservers("elevatorsName: "+elevatorName);
					setChanged();
					notifyObservers("rudderName: "+rudderName);
					setChanged();
					notifyObservers("throttleName: "+throttleName);
					setChanged();
					notifyObservers("altimeterName: "+altimeterName);
					setChanged();
					notifyObservers("airspeedName: "+airspeedName);
					setChanged();
					notifyObservers("headingName: "+headingName);
					setChanged();
					notifyObservers("rollName: "+rollName);
					setChanged();
					notifyObservers("pitchName: "+pitchName);
					setChanged();
					notifyObservers("yawName: "+yawName);
					if(train!=null)
						trainForView=train.filterBySelectingColl(collsForView);
				}catch(Exception e){collsForView.clear();}
			}
		}
	}

	public TimeSeries checkValidationCSV(String csv) {
		TimeSeries ts = new TimeSeries(csv);
		if (ts.isEmpty())
			return null;
		if(atrList.isEmpty())
			return ts;
		int atrLen = 0;
		ArrayList<Float> thisLine;

		Map<Integer, AttributeSettings> atrCols =
				atrList.getList().values().stream().
						collect(Collectors.toMap
								(atr -> atr.colInCSV, atr -> atr));

		for (int i = 0; i < ts.getSize(); i++) {
			thisLine = ts.getLine(ts.getTitles().get(i));
			if (thisLine.size() != ts.getLength()) //if all cols are in same length
				return null;
			if (atrCols.containsKey(i)) {//this col must be in csv
				atrLen++;
				for (float f : thisLine) { //if all values are between the min and max that was defined in the setting file
					if (f > atrCols.get(i).getMaxValue() || f < atrCols.get(i).getMinValue())
						return null;
				}
			}
		}
		if (atrLen != atrList.getLength()) // if all the settings appear in the setting file
			return null;

		return ts;
	}

	@Override
	public boolean setTrainTimeSeries(String csvTrainFile) {
		TimeSeries ts = checkValidationCSV(csvTrainFile);
		if (ts != null) {
			train = ts;
			trainForView = ts.filterBySelectingColl(collsForView);
			if (detector != null)
				detector.learnNormal(train);
			return true;
		}
		return false;
	}

	@Override
	public boolean setTestTimeSeries(String csvTestFile) {
		TimeSeries ts = checkValidationCSV(csvTestFile);
		if (ts != null) {
			test = ts;
			testForView = ts.filterBySelectingColl(collsForView);
			pointsToDisplay=new HashMap<>();
			pointsToDisplay.put(aileronName,new ArrayList<>());
			pointsToDisplay.put(elevatorName,new ArrayList<>());
			pointsToDisplay.put(rudderName,new ArrayList<>());
			pointsToDisplay.put(throttleName,new ArrayList<>());
			pointsToDisplay.put(altimeterName,new ArrayList<>());
			pointsToDisplay.put(airspeedName,new ArrayList<>());
			pointsToDisplay.put(headingName,new ArrayList<>());
			pointsToDisplay.put(rollName,new ArrayList<>());
			pointsToDisplay.put(pitchName,new ArrayList<>());
			pointsToDisplay.put(yawName,new ArrayList<>());
			setupPoints();
			return true;
		}
		return false;
	}

	void setupPoints(){
		if(testForView!=null){
			for(int i=0;i<testForView.getLength();i++)
				for(String key:pointsToDisplay.keySet())
					pointsToDisplay.get(key).add(new Point((float)i,testForView.getLine(key).get(i)));
		}
	}

	public void saveLastCsvTrainFile(String currentCsvTrainFile) {
		copyFile(currentCsvTrainFile, new File("resources/last_train.txt").getAbsolutePath());
	}

	public void copyFile(String originalPath, String copyPath) {
		File lastFile = new File(copyPath);
		File currentFile = new File(originalPath);
		try {
			if (!lastFile.exists())
				lastFile.createNewFile();
			PrintWriter write = new PrintWriter(lastFile);
			BufferedReader read = new BufferedReader(new FileReader(currentFile));
			String line = null;
			while ((line = read.readLine()) != null) {
				write.println(line);
				write.flush();
			}
			write.close();
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	boolean wantToSuspend=false;
	@Override
	public void play() {
		wantToSuspend=false;
		task.execute(() -> {
			wantToSuspend=false;
			while(!wantToSuspend) {
				if (currentTime + 1 < test.getLineAsList(0).size()) {
					setCurrentTime(currentTime + 1);
					try {
						Thread.sleep((long) (1000.0 / (speed * rate)));
					} catch (InterruptedException e) {
						System.out.println("didn't succeed to connect to the flight gear simulator");
					}
				} else
					break;
			}
			});
	}

	@Override
	public void pause() {
		wantToSuspend=true;
	}

	@Override
	public void stop() {
		pause();
		setCurrentTime(0);
	}

	@Override
	public void forward() { setCurrentTime(Math.min(test.getLength()-1,currentTime+5*rate)); }

	@Override
	public void rewind(){
		setCurrentTime(Math.max(0,currentTime-5*rate));
	}

	@Override
	public boolean setAnomalyDetector(String path, String name) {
		String[] paths = getPaths(path, name);
		URLClassLoader urlClassLoader;
		try {
			urlClassLoader = URLClassLoader.newInstance(
					new URL[]{new URL("file://" + paths[0])});
			Class<?> c = urlClassLoader.loadClass(paths[1]);
			if (c == null)
				return false;
			detector = (TimeSeriesAnomalyDetector) c.newInstance();
			detector.learnNormal(trainForView);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		detector.learnNormal(trainForView);
		return true;
	}

	public String[] getPaths(String path, String name) {
		Character backs = 92;
		String pattern = "\\W";
		String backslash = (backs).toString();
		String bin = "bin" + backslash;
		String[] ret = path.split("bin");
		ret[0] += bin;
		String[] packagePath = ret[1].split(pattern);
		ret[1] = "";
		for (String s : packagePath)
			if (!s.isEmpty())
				ret[1] += s + ".";
		ret[1] += name;//this string ends with ".class"
		ret[1] = ret[1].split(".class")[0];
		return ret;
	}

	@Override
	public void start() {
		try {
			fg = new Socket("localhost", port);
			writeToFlightGear = new PrintWriter(fg.getOutputStream());
		} catch (IOException e) {
			System.out.println("the connection with simulator doesn't succeed");
		}
	}

	@Override
	public void setValues(int timeStep) {
		if (test != null) {
			if (timeStep >= 0 && test.getLength() > timeStep) {
				double aileron = testForView.getLine(aileronName).get(timeStep);
				double elevator = testForView.getLine(elevatorName).get(timeStep);
				double throttle = testForView.getLine(throttleName).get(timeStep);
				double rudder = testForView.getLine(rudderName).get(timeStep);
				double altimeter = testForView.getLine(altimeterName).get(timeStep);
				double airspeed = testForView.getLine(airspeedName).get(timeStep);
				double heading = testForView.getLine(headingName).get(timeStep);
				double roll = testForView.getLine(rollName).get(timeStep);
				double pitch = testForView.getLine(pitchName).get(timeStep);
				double yaw = testForView.getLine(yawName).get(timeStep);
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
				if (fg == null || writeToFlightGear == null)
					start();
				if(writeToFlightGear!=null){
					String line = "";
					for (int j = 0; j < test.getTitles().size(); j++)
						if (j != 0)
							line = line + "," + test.getLineAsList(j).get(timeStep);
						else
							line = line + test.getLineAsList(j).get(timeStep);
					writeToFlightGear.println(line);
					writeToFlightGear.flush();
				}
			}
		}
	}

	@Override
	public void setCurrentTime(int currentTime) {
		this.currentTime = currentTime;
		setChanged();
		notifyObservers("currentTime: " + currentTime);
	}

	public int getLength() {
		return this.test.getLength();
	}

	@Override
	public String getMostCorrelated(String parameter) {
		selectedFeature=parameter;
		CorrelatedFeatures mostRelevant = getCorrelatedFeatures(parameter);
		if (mostRelevant == null)
			return (correlatedFeature="no correlated feature");
		if (mostRelevant.feature1.equals(parameter))
			return (correlatedFeature=mostRelevant.feature2);
		return (correlatedFeature=mostRelevant.feature1);
	}

	public CorrelatedFeatures getCorrelatedFeatures(String parameter) {
		if (!trainForView.getTitles().contains(parameter))
			return null;
		SimpleAnomalyDetector d = new SimpleAnomalyDetector(Float.parseFloat("0.5"));
		d.learnNormal(trainForView);
		List<CorrelatedFeatures> a = d.getNormalModel();
		CorrelatedFeatures mostRelevant = null;
		for (CorrelatedFeatures c : a) {
			if (c.feature1.equals(parameter)||c.feature2.equals(parameter))
				if (mostRelevant == null)
					mostRelevant = c;
				else if (mostRelevant.corrlation < c.corrlation)
					mostRelevant = c;
			/*if (c.feature2.equals(parameter))
				if (mostRelevant == null)
					mostRelevant = c;
				else if (mostRelevant.corrlation < c.corrlation)
					mostRelevant = c;*/
		}
		return mostRelevant;
	}
	@Override
	public List<Point> sendPointOf1Parameter(int endTime,String feature){
		List<Point> result = new ArrayList<>();
		if (endTime < 0)
			return result;
		if (!pointsToDisplay.containsKey(feature))
			return result;
		if (testForView == null)
			return result;
		result=pointsToDisplay.get(feature).subList(0,endTime);
		return result;
	}
	@Override
	public HashMap<Point, Color> sendPointOf2Parameter(int endTime, String feature) {
		HashMap<Point,Color> result=new HashMap<>();
		if(testForView!=null){
			CorrelatedFeatures cf=getCorrelatedFeatures(feature);
			if(detector==null){
				for(int i=0;i<endTime;i++){
					if(cf==null)
						result.put(new Point(i,testForView.getLine(feature).get(i)),Color.BLUE);
					else
						result.put(new Point(testForView.getLine(cf.feature1).get(i),testForView.getLine(cf.feature2).get(i)),Color.BLUE);
				}
			}
			else{
				List<AnomalyReport> l=detector.detect(testForView);
				for(int i=0;i<endTime;i++){
					if(cf==null){
						String description=feature;
						if(l.contains(new AnomalyReport(description,i)))
							result.put(new Point(i,testForView.getLine(feature).get(i)),Color.RED);
						else
							result.put(new Point(i,testForView.getLine(feature).get(i)),Color.BLUE);
					}
					else{
						String description=cf.feature1+"-"+cf.feature2;
						if(l.contains(new AnomalyReport(description,i)))
							result.put(new Point(testForView.getLine(cf.feature1).get(i),testForView.getLine(cf.feature2).get(i))
									,Color.RED);
						else
							result.put(new Point(testForView.getLine(cf.feature1).get(i),testForView.getLine(cf.feature2).get(i))
									,Color.BLUE);
					}
				}
			}
		}
		return result;
	}
	@Override
	public Shape sendShapeDetector(String feature){
		if(detector!=null)
			return detector.sendShape(feature);
		return null;
	}
	@Override
	public void shutDown(){
		task.shutDown();
	}
}