package model;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;

public interface Model {
	
	public void setTrainTimeSeries(String csvTrainFile) throws Exception;
	public void setTestTimeSeries(String csvTestFile) throws Exception;
	public void play(int startTime, int rate);
	public void pause();
	public void stop();
	
	public void setAnomalyDetector(TimeSeriesAnomalyDetector ad);
	public void start(); //run in the background
	
}
