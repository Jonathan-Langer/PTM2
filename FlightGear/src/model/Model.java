package model;

import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;

import anomaly_detectors.CorrelatedFeatures;
import anomaly_detectors.Point;
import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;
import javafx.scene.paint.Color;

public interface Model {

	public void shutDown();
	public boolean setTrainTimeSeries(String csvTrainFile);
	public boolean setTestTimeSeries(String csvTestFile);
	public void setCurrentTime(int currentTime);
	public void saveLastCsvTrainFile(String csvTrainFile);
	public int getLength();
	public int getRate();
	public void setSpeedOfFlight(double speed);
	public void play();
	public void pause();
	public void stop();
	public void forward();
	public void rewind();
	public void setCurrentTimeWithoutNotify(int currentTime);
	
	public boolean setAnomalyDetector(String path,String name);
	public void start(); //run in the background

	public boolean checkValidateSettingFile(String txtFile);
	public void saveLastSettingFile(String currentTxtFile);
	public void applyValuesMinMax();
	public void applyNames();
	public void setValues(int timeStep);
	public String getMostCorrelated(String parameter);
	public List<Point> sendPointOf1Parameter(int endTime, String feature);
	public HashMap<Point, Color> sendPointOf2Parameter(int endTime, String feature);
	public CorrelatedFeatures getCorrelatedFeatures(String parameter);
}
