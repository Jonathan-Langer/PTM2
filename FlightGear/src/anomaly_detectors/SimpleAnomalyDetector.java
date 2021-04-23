package anomaly_detectors;

import java.util.List;
import java.util.ArrayList;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
		
	public static float th = (float) 0.9;
	
	public SimpleAnomalyDetector() {
		super();
		this.normalModel = null;
	}
	public SimpleAnomalyDetector(float th) {
		this.th=th;
	}
	List<CorrelatedFeatures> normalModel;

	@Override
	public void learnNormal(TimeSeries ts) {
		normalModel = new ArrayList<>();
		float pear, max, maxAbsPear;
		int maxPlace;
		Line l;
		float [] valX, valY;
		Point[] points;
		for (int i=0; i<ts.getSize();i++) {
			maxAbsPear=0;
			maxPlace=-1;
			valX= ts.getLineAsArray(i);
			for (int j=i+1; j<ts.getSize();j++) {
				valY= ts.getLineAsArray(j);
				pear =StatLib.pearson(valX,valY);	
				if (Math.abs(pear)>=th && Math.abs(pear)>maxAbsPear)		
				{
					maxAbsPear=Math.abs(pear);
					maxPlace=j;
				}
			}//for j
			if(maxPlace!=-1) {
			valY= ts.getLineAsArray(maxPlace);
			pear =StatLib.pearson(valX,valY);
			points = new Point[valX.length];
			for(int k=0;k<points.length;k++)
				points[k]= new Point(valX[k],valY[k]);
			l =StatLib.linear_reg(points);
			max=0;
			for (Point p : points)
				max = Math.max(max, StatLib.dev(p, l));
			normalModel.add(new CorrelatedFeatures(ts.getTitles().get(i),
					ts.getTitles().get(maxPlace), pear, l, max));
			}//if
		}//for i
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<AnomalyReport> detected = new ArrayList<AnomalyReport>();
		Point p;
		
		for ( int i=0; i<ts.getLength();i++) {
			for (CorrelatedFeatures f : normalModel)
			{
				p=new Point(ts.getLine(f.feature1).get(i),
						ts.getLine(f.feature2).get(i));
				if (StatLib.dev(p,f.lin_reg)>(f.threshold+0.4))
				{
					detected.add(new AnomalyReport(f.feature1+"-"+f.feature2,i+1));
				}
			}
		}
		return detected;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return normalModel;
	}
}