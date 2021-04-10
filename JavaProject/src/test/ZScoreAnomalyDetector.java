package test;

import java.util.ArrayList;
import java.util.List;

public class ZScoreAnomalyDetector implements TimeSeriesAnomalyDetector {
	
	ArrayList<Float> thArr;
	
	@Override
	public void learnNormal(TimeSeries ts) {
		thArr= new ArrayList<>();
		
	}

	public float zScore() {
		return 0;
	}
	
	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		// TODO Auto-generated method stub
		return null;
	}

}
