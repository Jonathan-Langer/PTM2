package anomaly_detectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
import java.util.Random;

public class HybridAnomalyDetector implements TimeSeriesAnomalyDetector {
	private class WelzlAlgorithm{
		private Random rand = new Random();
		
		 public Circle miniDisk(final List<Point> points) {
				return bMinidisk(points, new ArrayList<Point>());
		    }
			
		    private Circle bMinidisk(final List<Point> points, final List<Point> boundary) {
		    	Circle minimumCircle = null;
				
				if (boundary.size() == 3) {
					minimumCircle = new Circle(boundary.get(0), boundary.get(1), boundary.get(2));
				}
				else if (points.isEmpty() && boundary.size() == 2) {
					minimumCircle = new Circle(boundary.get(0), boundary.get(1));
				}
				else if (points.size() == 1 && boundary.isEmpty()) {
					minimumCircle = new Circle(points.get(0).x, points.get(0).y, (float)0);
				}
				else if (points.size() == 1 && boundary.size() == 1) {
					minimumCircle = new Circle(points.get(0), boundary.get(0));
				}
				else {
					Point p = points.remove(rand.nextInt(points.size()));
					minimumCircle = bMinidisk(points, boundary);
					
					if (minimumCircle != null && !minimumCircle.containsPoint(p)) {
						boundary.add(p);
						minimumCircle = bMinidisk(points, boundary);
						boundary.remove(p);
						points.add(p);
					}
				}
						
				return minimumCircle;
		    }
	}
	private List<Point> getListPoint(List<Float> x,List<Float> y){
		List<Point> result=new ArrayList<>();
		for(int i=0;i<x.size();i++)
			result.add(new Point(x.get(i),y.get(i)));
		return result;
	}
	
	TimeSeriesAnomalyDetector algorithm;
	HashMap<String,HashSet<CorrelatedFeatures>> featuresToAlgorithm;
	HashMap<CorrelatedFeatures,Circle> welzlCircleModel;//storing circles for each 
	//pair of correlated features
	SimpleAnomalyDetector regressionDetector;
	ZScoreAnomalyDetector zScoreDetector;
	
	@Override
	public void learnNormal(TimeSeries ts) {
		regressionDetector=new SimpleAnomalyDetector((float)0);
		regressionDetector.learnNormal(ts);
		List<CorrelatedFeatures> mostCorrelated=regressionDetector.getNormalModel();
		WelzlAlgorithm algorithm=new WelzlAlgorithm();
		/* ----------sorting the features by the correlation value */
		featuresToAlgorithm=new HashMap<>();
		featuresToAlgorithm.put("ZScore", new HashSet<>());
		featuresToAlgorithm.put("Regression", new HashSet<>());
		featuresToAlgorithm.put("Welzl", new HashSet<>());
		for(CorrelatedFeatures c:mostCorrelated) {
			if(Math.abs(c.corrlation)>=0.95)
				featuresToAlgorithm.get("Regression").add(c);
			else {
				if(Math.abs(c.corrlation)<0.5)
					featuresToAlgorithm.get("ZScore").add(c);
				else
					featuresToAlgorithm.get("Welzl").add(c);
			}
		}
		for(CorrelatedFeatures c:featuresToAlgorithm.get("Welzl")) {
			welzlCircleModel.put(c,algorithm.miniDisk(
					getListPoint(ts.getLine(c.feature1), ts.getLine(c.feature2))));
		}
		TimeSeries trainZScoreAlgorithm=new TimeSeries();
		for(CorrelatedFeatures c:featuresToAlgorithm.get("ZScore")) {
			trainZScoreAlgorithm.addCol(c.feature1,ts.getLine(c.feature1));
			trainZScoreAlgorithm.addCol(c.feature2,ts.getLine(c.feature2));
		}
		zScoreDetector.learnNormal(trainZScoreAlgorithm);
		regressionDetector=new SimpleAnomalyDetector((float) 0.95);
		regressionDetector.learnNormal(ts);
	}

	@Override
	public List<AnomalyReport> detect(TimeSeries ts) {
		List<AnomalyReport> detected=new ArrayList<>();
		detected.addAll(regressionDetector.detect(ts));
		TimeSeries testZScoreAlgorithm=new TimeSeries();
		// ------build new TimeSeries-----
		for(CorrelatedFeatures c:featuresToAlgorithm.get("ZScore")) {
			testZScoreAlgorithm.addCol(c.feature1,ts.getLine(c.feature1));
			testZScoreAlgorithm.addCol(c.feature2,ts.getLine(c.feature2));
		}
		detected.addAll(zScoreDetector.detect(testZScoreAlgorithm));
		for(CorrelatedFeatures c:featuresToAlgorithm.get("Welzl")) {
			int count=1;
			List<Point> dataFeature=getListPoint(ts.getLine(c.feature1), ts.getLine(c.feature2));
			for(Point p:dataFeature) {
				if(!welzlCircleModel.get(c).containsPoint(p))
					detected.add(new AnomalyReport(c.feature1+"-"+c.feature2, count));
				++count;
			}
		}
		return detected;
	}

}
