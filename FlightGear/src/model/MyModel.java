package model;


import java.io.*;
import java.util.Observable;
import java.util.*;
import java.util.function.Consumer;

import anomaly_detectors.TimeSeries;
import anomaly_detectors.TimeSeriesAnomalyDetector;

public class MyModel extends Observable implements Model {

	TimeSeries train,test;
	TimeSeriesAnomalyDetector detector;
	ListOfAttributes atrList;
	
	Thread t;
	Process p;
	
	public boolean checkValidateSettingFile(String txtFile) {
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(5, false);
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
	
	public TimeSeries checkValidation(String csv) {
		TimeSeries ts=new TimeSeries(csv);
		int atrLen=0;
		for(String s : ts.getTitles()) {
			if(ts.getLine(s).size()!=ts.getSize()) //if all cols are in same length
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
		}
		if(atrLen!=atrList.getLength()) // if all the names in the csv apeearing int he setting file
			return null;
		
		return ts;
	}
	
	
	@Override
	public void setTrainTimeSeries(String csvTrainFile) {
		TimeSeries ts= checkValidation(csvTrainFile);
		if(ts!=null)
			train=ts;
	}
	
	@Override
	public void setTestTimeSeries(String csvTestFile) {
		TimeSeries ts= checkValidation(csvTestFile);
		if(ts!=null)
			test=ts;
		
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
