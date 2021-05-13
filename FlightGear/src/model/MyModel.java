package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;

public class MyModel extends Observable implements Model {

	TimeSeries train,test;
	TimeSeriesAnomalyDetector detector;
	
	public boolean checkValidateSettingFile(String txtFile) {
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(3, false);
		cellsAreApeared.put(5, false);
		cellsAreApeared.put(6, false);
		cellsAreApeared.put(14, false);
		cellsAreApeared.put(15, false);
		cellsAreApeared.put(16, false);
		cellsAreApeared.put(17, false);
		cellsAreApeared.put(18, false);
		cellsAreApeared.put(19, false);
		cellsAreApeared.put(39, false);
		try {
			BufferedReader read=new BufferedReader(new FileReader(new File(txtFile)));
			String line=null;
			while((line=read.readLine())!=null) {
				String[] data=line.split(",");
				if(data.length==4) {
					if(!cellsAreApeared.containsKey(Integer.parseInt(data[1]))) {
						read.close();
						return false;
					}
				}
				else {
					if(data.length==2) {
						if(!(data[0].equals("ip")||data[0].equals("port")||data[0].equals("rate"))) {
							read.close();
							return false;
						}
					}
					else {
						read.close();
						return false;
					}
				}
			}
			read.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	public boolean checkValidateCSV(String csvFilePath) {
		try {
			BufferedReader read=new BufferedReader(new FileReader(csvFilePath));
			
		} catch (FileNotFoundException e) {
			return false;
		}
	}
	
	
	@Override
	public void setTimeSeries(TimeSeries ts) {
		
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

}
