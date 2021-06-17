package anomaly_detectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ZScoreAnomalyDetector implements TimeSeriesAnomalyDetector {
	
	ArrayList<Float> thArr;

	@Override
	public void learnNormal(TimeSeries ts) {
		thArr= new ArrayList<>();
		ts.getInfo().forEach(col->thArr.add(zScore(ts.getLineAsArray(ts.getInfo().indexOf(col)))));
	}

	public float zScore(float[] col) {
		float th=0;
		float z;
		for(Float f:col)
		{
			z=(float) (Math.abs(f-StatLib.avg(col))
					/Math.sqrt(StatLib.var(col)));
			if(th<z)
				th=z;
		}
		return th;
	}
	
	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<AnomalyReport> detected = new ArrayList<AnomalyReport>();
		ArrayList<Float> curr;
		ArrayList<Float> info;
		
		for ( int i=0; i<ts.getLength();i++) {

			curr = new ArrayList<>();
			info = ts.getInfo().get(i);
			
			for (int j=0; j<info.size(); j++)
			{
				float z = zScore(StatLib.toFloat(curr));
				if (z>thArr.get(i))
				{
					detected.add(new AnomalyReport(ts.getTitles().get(i),j+1));
				}//if
				curr.add(info.get(j));
			}//for
		}//for
		return detected;
	}
	@Override
	public Shape sendShape(String feature) {
		return null;
	}
}
