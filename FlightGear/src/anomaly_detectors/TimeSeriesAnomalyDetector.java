package anomaly_detectors;

import java.util.HashMap;
import java.util.List;

public interface TimeSeriesAnomalyDetector {
	void learnNormal(TimeSeries ts);
	List<AnomalyReport> detect(TimeSeries ts);
	Shape sendShape(String feature);
	//HashMap<Integer,Point> getDetectedPoints();
}
