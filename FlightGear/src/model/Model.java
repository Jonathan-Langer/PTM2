package model;

import java.net.URLClassLoader;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;

public interface Model {
	
	public boolean setTrainTimeSeries(String csvTrainFile);
	public boolean setTestTimeSeries(String csvTestFile);
	public void setCurrentTime(int currentTime);
	public void saveLastCsvTrainFile(String csvTrainFile);
	public int getLength();
	public void play(int startTime, int rate);
	public void pause();
	public void stop();
	
	public boolean setAnomalyDetector(String path,String name);
	public void start(); //run in the background

	public boolean checkValidateSettingFile(String txtFile);
	public void saveLastSettingFile(String currentTxtFile);
	public void applyValuesMinMax();
	public void applyNames();
	public void setValues(int timeStep);
}
