package test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	
	public final String TH;
	public ArrayList<CorrelatedFeatures> finalFeatures;
	public SimpleAnomalyDetector() {
		finalFeatures = new ArrayList<CorrelatedFeatures>();
		TH="0.9";
	}
	public SimpleAnomalyDetector(String threshold)
	{
		finalFeatures = new ArrayList<CorrelatedFeatures>();
		TH = threshold;
	}
	
	@Override
	public void learnNormal(TimeSeries ts) {
		ArrayList<CorrelatedFeatures> features = new ArrayList<CorrelatedFeatures>();
		ArrayList<String> attributes= ts.getFeatures();
		
		for(int i=0;i<ts.numCol;i++)
		{
			for(int j=i+1;j<ts.numCol;j++)
			{
				float[] a = ts.getDataOfCol(i);
				float[] b = ts.getDataOfCol(j);
				float pearson = StatLib.pearson(a, b);
				if(Math.abs(pearson) >=Float.parseFloat(TH))
				{
				 	Point ps[] = ts.getArray(ts.getColOfFeature(attributes.get(i)),ts.getColOfFeature(attributes.get(j)));
				 	Line lin_reg = StatLib.linear_reg(ps);
				 	float threshold = finalTH(ps,lin_reg)*1.1f; 
				 	
					//features.add(new CorrelatedFeatures(ts.getFeature(i),ts.getFeature(j),pearson,StatLib.linear_reg(ts.getArray(i, j)),Float.parseFloat(TH)));
				 	CorrelatedFeatures c =new CorrelatedFeatures(ts.getFeature(i), ts.getFeature(j),pearson,lin_reg,threshold);
				 	this.finalFeatures.add(c);
				}
			}
		}
	}
	
	private float finalTH(Point ps[],Line rl){
		float max=0;
		for(int i=0;i<ps.length;i++){
			float d=Math.abs(ps[i].y - rl.f(ps[i].x));
			if(d>max)
				max=d;
		}
		return max;
	}


	@Override
	public List<AnomalyReport> detect(TimeSeries ts) { 
		
		List<AnomalyReport> detections = new ArrayList<AnomalyReport>();
		for(CorrelatedFeatures cf: finalFeatures)
		{
			float[] x = ts.getDataOfCol(ts.getColOfFeature(cf.feature1));
			float[] y = ts.getDataOfCol(ts.getColOfFeature(cf.feature2));
			for(int i=0;i<x.length;i++)
			{
				if(Math.abs(y[i]-cf.lin_reg.f(x[i]))>cf.threshold) 
				{
					String description = cf.feature1 + "-" + cf.feature2;
					detections.add(new AnomalyReport(description,(i+1)));
				}
			}
		}
		return detections;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return this.finalFeatures;
	}
}