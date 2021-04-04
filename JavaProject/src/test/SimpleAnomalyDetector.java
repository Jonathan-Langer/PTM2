package test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.math.*;

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
		
		/*List<AnomalyReport> detections = new ArrayList<AnomalyReport>();
		float currentMaxDev;
		int timeStep;
		for(CorrelatedFeatures c: this.finalFeatures)
		{
			Point[] currentPoints = ts.getArray(ts.getColOfFeature(c.feature1),ts.getColOfFeature(c.feature2));
			currentMaxDev = -999;
			timeStep = 0;
			for(int j = 0; j < currentPoints.length; j++) 
			{
				if(currentMaxDev < StatLib.dev(currentPoints[j], c.lin_reg))
				{
					currentMaxDev = StatLib.dev(currentPoints[j], c.lin_reg);
					timeStep=j;
				}
			}
			timeStep++;
			if(c.threshold<currentMaxDev)
			{
				detections.add(new AnomalyReport(new String(c.feature1 + "-" + c.feature2),timeStep));
			}
		}
		return detections;*/
		
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
/*
package test;

import java.util.ArrayList;
import java.util.List;

public class SimpleAnomalyDetector implements TimeSeriesAnomalyDetector {
	
	ArrayList<CorrelatedFeatures> cf;
	public final Float TH;
	
	public SimpleAnomalyDetector() {
		cf=new ArrayList<>();
		TH=Float.parseFloat("0.9");
	}
	
	public SimpleAnomalyDetector(String threshold)
	{
		cf = new ArrayList<CorrelatedFeatures>();
		TH = Float.parseFloat(threshold);
	}
	

	@Override
	public void learnNormal(TimeSeries ts) {
		ArrayList<String> atts=ts.getAttributes();
		int len=ts.getRowSize();

		float vals[][]=new float[atts.size()][len];
		for(int i=0;i<atts.size();i++){
			for(int j=0;j<ts.getRowSize();j++){
				vals[i][j]=ts.getAttributeData(atts.get(i)).get(j);
			}
		}


		for(int i=0;i<atts.size();i++){
			for(int j=i+1;j<atts.size();j++){
				float p=StatLib.pearson(vals[i],vals[j]);
				if(Math.abs(p)>TH){

					Point ps[]=toPoints(ts.getAttributeData(atts.get(i)),ts.getAttributeData(atts.get(j)));
					Line lin_reg=StatLib.linear_reg(ps);
					float threshold=findThreshold(ps,lin_reg)*1.1f; // 10% increase

					CorrelatedFeatures c=new CorrelatedFeatures(atts.get(i), atts.get(j), p, lin_reg, threshold);

					cf.add(c);
				}
			}
		}
	}

	private Point[] toPoints(ArrayList<Float> x, ArrayList<Float> y) {
		Point[] ps=new Point[x.size()];
		for(int i=0;i<ps.length;i++)
			ps[i]=new Point(x.get(i),y.get(i));
		return ps;
	}
	
	private float findThreshold(Point ps[],Line rl){
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
		ArrayList<AnomalyReport> v=new ArrayList<>();
		
		for(CorrelatedFeatures c : cf) {
			ArrayList<Float> x=ts.getAttributeData(c.feature1);
			ArrayList<Float> y=ts.getAttributeData(c.feature2);
			for(int i=0;i<x.size();i++){
				if(Math.abs(y.get(i) - c.lin_reg.f(x.get(i)))>c.threshold){
					String d=c.feature1 + "-" + c.feature2;
					v.add(new AnomalyReport(d,(i+1)));
				}
			}			
		}
		return v;
	}
	
	public List<CorrelatedFeatures> getNormalModel(){
		return cf;
	}
}
*/