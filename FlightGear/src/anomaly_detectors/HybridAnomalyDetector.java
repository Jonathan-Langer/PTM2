package anomaly_detectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.*;
import java.util.Random;

public class HybridAnomalyDetector implements TimeSeriesAnomalyDetector {
	private List<Point> getListPoint(List<Float> x,List<Float> y){
		List<Point> result=new ArrayList<>();
		for(int i=0;i<x.size();i++)
			result.add(new Point(x.get(i),y.get(i)));
		return result;
	}
	
	TimeSeriesAnomalyDetector algorithm;
	HashMap<String,HashSet<CorrelatedFeatures>> featuresToAlgorithm;
	HashMap<CorrelatedFeatures,Circle> welzlCircleModel=new HashMap<>();//storing circles for each
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

	@Override
	public Shape sendShape(String feature) {
		CorrelatedFeatures mostRelevant=null;
		for(CorrelatedFeatures cf:featuresToAlgorithm.get("ZScore")){
			if(cf.feature1.equals(feature)||cf.feature2.equals(feature)){
				return null;
			}
		}
		for(CorrelatedFeatures cf:featuresToAlgorithm.get("Regression")){
			if(mostRelevant==null)
				mostRelevant=cf;
			else{
				if(mostRelevant.corrlation<cf.corrlation)
					mostRelevant=cf;
			}
		}
		if(mostRelevant!=null)
			return mostRelevant.lin_reg;
		for(CorrelatedFeatures cf:featuresToAlgorithm.get("Welzl")){
			if(mostRelevant==null)
				mostRelevant=cf;
			else{
				if(mostRelevant.corrlation<cf.corrlation)
					mostRelevant=cf;
			}
		}
		return welzlCircleModel.get(mostRelevant);
	}
}
