package model;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;

public interface Model {
	
	public boolean setTrainTimeSeries(String csvTrainFile);
	public boolean setTestTimeSeries(String csvTestFile);
	public void saveLastCsvTrainFile(String csvTrainFile);
	public void play(int startTime, int rate);
	public void pause();
	public void stop();
	
	public void setAnomalyDetector(TimeSeriesAnomalyDetector ad);
	public void start(); //run in the background

	public boolean checkValidateSettingFile(String txtFile);
	public void saveLastSettingFile(String currentTxtFile);
	public void applyValuesMinMax();
	
}
