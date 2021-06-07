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
	String aileronName, elevatorName, rudderName, throttleName, altimeterName, airspeedName, headingName, rollName, pitchName, yawName;
	int port = -1;
	String ip = "";
	int rate = -1;
	Socket fg = null;
	double speed;
	PrintWriter writeToFlightGear = null;
	ActiveObject task;

	public MyModel() {
		this.txtLast = new File("resources/last_setting.txt").getAbsolutePath();
		checkValidateSettingFile(new File("resources/last_setting.txt").getAbsolutePath());
		atrList = new ListOfAttributes(txtLast);
		collsForView = new HashMap<>();
		task=new ActiveObject(5);
		//t.start();
	}
	@Override
	public int getRate(){return rate;}
	public double getAileronVal() {
		return aileronVal;
	}

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

	public void setAileronName(String aileronName) {
		this.aileronName = aileronName;
		setChanged();
		notifyObservers("aileronName: " + aileronName);
	}

	public void setElevatorName(String elevatorName) {
		this.elevatorName = elevatorName;
		setChanged();
		notifyObservers("elevatorsName: " + elevatorName);
	}

	public void setRudderName(String rudderName) {
		this.rudderName = rudderName;
		setChanged();
		notifyObservers("rudderName: " + rudderName);
	}

	public void setThrottleName(String throttleName) {
		this.throttleName = throttleName;
		setChanged();
		notifyObservers("throttleName: " + throttleName);
	}

	public void setAltimeterName(String altimeterName) {
		this.altimeterName = altimeterName;
		setChanged();
		notifyObservers("altimeterName: " + altimeterName);
	}

	public void setAirspeedName(String airspeedName) {
		this.airspeedName = airspeedName;
		setChanged();
		notifyObservers("airspeedName: " + airspeedName);
	}

	public void setHeadingName(String headingName) {
		this.headingName = headingName;
		setChanged();
		notifyObservers("headingName: " + headingName);
	}

	public void setRollName(String rollName) {
		this.rollName = rollName;
		setChanged();
		notifyObservers("rollName: " + rollName);
	}

	public void setPitchName(String pitchName) {
		this.pitchName = pitchName;
		setChanged();
		notifyObservers("pitchName: " + pitchName);
	}

	public void setYawName(String yawName) {
		this.yawName = yawName;
		setChanged();
		notifyObservers("yawName: " + yawName);
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
		atrApeared.put("aileron", -1);
		atrApeared.put("elevator", -1);
		atrApeared.put("rudder", -1);
		atrApeared.put("throttle", -1);
		atrApeared.put("altimeter", -1);
		atrApeared.put("airspeed", -1);
		atrApeared.put("heading", -1);
		atrApeared.put("roll", -1);
		atrApeared.put("pitch", -1);
		atrApeared.put("yaw", -1);
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
								if (atrApeared.get("port").equals(true)) {
									read.close();
									return null;
								} else
									atrL.port = Integer.parseInt(data[1]);
							}
							if (data[0].equals("rate")) {
								if (atrApeared.get("rate").equals(true)) {
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
		if (atrL == null)
			return false;
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
			for (String key : atrList.getAttributesNames()) {
				AttributeSettings a = atrList.getList().get(key);
				if (a.getColInCSV() == 0)
					setAileronName(key);
				if (a.getColInCSV() == 1)
					setElevatorName(key);
				if (a.getColInCSV() == 2)
					setRudderName(key);
				if (a.getColInCSV() == 6)
					setThrottleName(key);
				if (a.getColInCSV() == 20)
					setYawName(key);
				if (a.getColInCSV() == 24)
					setAirspeedName(key);
				if (a.getColInCSV() == 25)
					setAltimeterName(key);
				if (a.getColInCSV() == 28)
					setRollName(key);
				if (a.getColInCSV() == 29)
					setPitchName(key);
				if (a.getColInCSV() == 36)
					setHeadingName(key);
				collsForView.put(key, a.getColInCSV());
			}
		}
	}

	public TimeSeries checkValidationCSV(String csv) {
		TimeSeries ts = new TimeSeries(csv);
		if (ts.isEmpty())
			return null;
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
			return true;
		}
		return false;
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


	@Override
	public void play(int startTime) {
			task.execute(() -> {
				if (fg == null || writeToFlightGear == null)
					start();
				int i=startTime;
				while(true){
					if(i<test.getLineAsList(0).size()){
						if(writeToFlightGear!=null){
							String line = "";
							for (int j = 0; j < test.getTitles().size(); j++)
								if (j != 0)
									line = line + "," + test.getLineAsList(j).get(i);
								else
									line = line + test.getLineAsList(j).get(i);
							writeToFlightGear.println(line);
							writeToFlightGear.flush();
						}
						setValues(i);
						i++;
						setCurrentTime(currentTime+1);
						try {
							Thread.sleep((long)(1000.0/(speed*rate)));
						} catch (InterruptedException e) {
							System.out.println("didn't succeed to connect to the flight gear simulator");
						}
					}
					else
						break;
				}
			});
			task.shutDown();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {

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
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("done:)))");
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
			if (timeStep < 0 || test.getLength() >= timeStep) {
				double aileron = testForView.getLine("aileron").get(timeStep);
				double elevator = testForView.getLine("elevator").get(timeStep);
				double throttle = testForView.getLine("throttle").get(timeStep);
				double rudder = testForView.getLine("rudder").get(timeStep);
				double altimeter = testForView.getLine("altimeter").get(timeStep);
				double airspeed = testForView.getLine("airspeed").get(timeStep);
				double heading = testForView.getLine("heading").get(timeStep);
				double roll = testForView.getLine("roll").get(timeStep);
				double pitch = testForView.getLine("pitch").get(timeStep);
				double yaw = testForView.getLine("yaw").get(timeStep);
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
		CorrelatedFeatures mostRelevant = getCorrelatedFeatures(parameter);
		if (mostRelevant == null)
			return "no correlated feature";
		if (mostRelevant.feature1.equals(parameter))
			return mostRelevant.feature2;
		return mostRelevant.feature1;
	}

	private CorrelatedFeatures getCorrelatedFeatures(String parameter) {
		if (!trainForView.getTitles().contains(parameter))
			return null;
		SimpleAnomalyDetector d = new SimpleAnomalyDetector(Float.parseFloat("0.5"));
		d.learnNormal(trainForView);
		List<CorrelatedFeatures> a = d.getNormalModel();
		CorrelatedFeatures mostRelevant = null;
		for (CorrelatedFeatures c : a) {
			if (c.feature1.equals(parameter))
				if (mostRelevant == null)
					mostRelevant = c;
				else if (mostRelevant.corrlation < c.corrlation)
					mostRelevant = c;
			if (c.feature2.equals(parameter))
				if (mostRelevant == null)
					mostRelevant = c;
				else if (mostRelevant.corrlation < c.corrlation)
					mostRelevant = c;
		}
		return mostRelevant;
	}
	@Override
	public HashMap<Point,Color> sendPointOf1Parameter(int endTime,String feature){
		HashMap<Point, Color> result = new HashMap<>();
		if (endTime < 0)
			return result;
		if (!trainForView.getTitles().contains(feature))
			return result;
		if (testForView == null)
			return result;
		for (int i = 0; i <= endTime; i++) {
			Point p = new Point(i, testForView.getLine(feature).get(i));
			result.put(p, Color.rgb(58, 58, 191));
		}
		return result;
	}
	@Override
	public HashMap<Point, Color> sendPointOf2Parameter(int endTime, String feature) {
		HashMap<Point, Color> result = new HashMap<>();
		if (endTime < 0)
			return result;
		if (trainForView.getTitles().contains(feature))
			return result;
		if (testForView == null)
			return result;
		CorrelatedFeatures relevant = getCorrelatedFeatures(feature);
		if (relevant == null)
			return sendPointOf1Parameter(endTime,feature);
		for(int i=0;i<=endTime;i++){
			Point p=new Point(testForView.getLine(relevant.feature1).get(i),testForView.getLine(relevant.feature2).get(i));
			result.put(p,Color.rgb(58, 58, 191));
		}
		return result;
	}
}