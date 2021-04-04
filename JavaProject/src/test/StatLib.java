package test;

import java.util.ArrayList;

public class StatLib {

	

	// simple average
	public static float avg(float[] x){
		float sum=0;
		for(int i = 0; i < x.length; i++)
		{
			sum += x[i];
		}
		float average= sum/x.length;
		return average;
	}

	// returns the variance of X and Y
	public static float var(float[] x){
		float sum=0;
		float average = avg(x);
		for(int i = 0; i < x.length; i++)
		{
			sum += Math.pow(x[i] - average, 2);
		}
		float variance=sum / x.length;
		return variance;
	}

	// returns the covariance of X and Y
	public static float cov(float[] x, float[] y){
		float sum=0;
		float averageX = avg(x);
		float averageY = avg(y);
		for(int i = 0; i < x.length; i++)
		{
			sum += (x[i] - averageX)*(y[i] - averageY);
		}
		float cov = sum / y.length;
		return cov;
	}


	// returns the Pearson correlation coefficient of X and Y
	public static float pearson(float[] x, float[] y){
		float cov = cov(x,y);
		float Sx = (float) Math.sqrt(var(x));
		float Sy = (float)Math.sqrt(var(y));
		float pearson = cov / (Sx*Sy);
		return pearson;
	}

	// performs a linear regression and returns the line equation
	public static Line linear_reg(Point[] points){
		float [] x = new float[points.length];
		float [] y = new float[points.length];

		//ArrayList<Float> x = new ArrayList<Float>();
		//ArrayList<Float> y = new ArrayList<Float>();
		for(int i=0; i < points.length; i++)
		{
			x[i] = points[i].x;
			y[i] = points[i].y;
		}
		float alpha = cov(x,y)/var(x);
		float b = (avg(y)) - (alpha*(avg(x))); 
		Line liniearReg = new Line(alpha , b);
		return liniearReg;
	}

	// returns the deviation between point p and the line equation of the points
	public static float dev(Point p,Point[] points){
		Line linearReg = linear_reg(points);
		float expectedVal = linearReg.f(p.x);
		float deviation = Math.abs(expectedVal - p.y);
		return deviation;
	}

	// returns the deviation between point p and the line
	public static float dev(Point p,Line l){
		float expectedVal = l.f(p.x);
		float deviation = Math.abs(expectedVal - p.y);
		return deviation;
	}
	
}
