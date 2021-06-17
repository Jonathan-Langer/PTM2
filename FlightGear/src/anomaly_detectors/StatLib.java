package anomaly_detectors;

import java.util.List;

public class StatLib {
	
	public static float[] toFloat(List<Float> arr) {
		float[] retArr = new float[arr.size()];
		for (int j=0; j<retArr.length;j++)
			retArr[j]=arr.get(j);
		return retArr;
	}

	// simple average
	public static float avg(float[] x) {
		float sum1 = 0;
		float sum2 = 0;
		int newLength = x.length >> 1;
		for(int i = 0; i < newLength; i++) {
			sum1 += x[i];
			sum2 += x[i+newLength];
		}
		return (sum1 + sum2)/x.length;
	}

	// returns the variance of X and Y
	public static float var(float[] x) {
		float avg1, avg2; // avg1 = average square, avg2 = average of squares
		avg1 = avg(x);
		avg1 = avg1 * avg1;
		float[] xSqr = new float[x.length];
		for (int i = 0; i < x.length; i++) {
			xSqr[i] = x[i] * x[i];
		}
		avg2 = avg(xSqr);
		return avg2 - avg1;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y) {
		// cov(X,Y)=E(X*Y)-E(x)*E(y)
		float avgX = avg(x), avgY = avg(y);
		float[] xy = new float[y.length];// Assuming x & y are the same length
		for (int i = 0; i < xy.length; i++) {
			xy[i] = (x[i] - avgX) * (y[i] - avgY);
		}
		return avg(xy);
	}

	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y) {
		if(x == y) {
			return 1;
		}
		if(x.length == y.length) {
			float sumXY1 = 0, sumXY2 = 0,  sumX1 = 0, sumX2 = 0, sumY1 = 0, sumY2 = 0;
			float avgOfX = avg(x);
			float avgOfY = avg(y);
			int newLength = x.length >> 1;
			for(int i = 0; i < newLength ; i++) {
				double calcX1 = x[i] - avgOfX;
				double calcY1 = y[i] - avgOfY;
				double calcX2 = x[i + newLength]-avgOfX;
				double calcY2 = y[i + newLength] - avgOfY;

				sumXY1 += (calcX1) * (calcY1);
				sumXY2 += (calcX2) * (calcY2);

				sumX1 += (calcX1) * (calcX1);
				sumX2 += (calcX2) * (calcX2);

				sumY1 += (calcY1) * (calcY1);
				sumY2 += (calcY2) * (calcY2);
			}
			return (float)((sumXY1 + sumXY2)/Math.sqrt((sumX1 + sumX2) * (sumY1 + sumY2)));
		}
		return 0;
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points) {
		float[] x = new float[points.length];
		float[] y = new float[points.length];
		for (int i = 0; i < points.length; i++) {
			x[i] = points[i].x;
			y[i] = points[i].y;
		}
		float a = cov(x, y) / var(x);
		float b = avg(y) - a * avg(x);
		Line l = new Line(a, b);
		return l;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p, Point[] points) {
		return dev(p, linear_reg(points));
	}

	// returns the deviation between point p and the line
	public static float dev(Point p, Line l) {
		return Math.abs(l.f(p.x) - p.y);
	}

}
