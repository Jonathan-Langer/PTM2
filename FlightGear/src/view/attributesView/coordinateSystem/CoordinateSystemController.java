package view.attributesView.coordinateSystem;

import java.net.URL;
import java.util.*;

import anomaly_detectors.Point;
import com.sun.javafx.scene.paint.GradientUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CoordinateSystemController implements Initializable {
	@FXML
	Line x,y;
	@FXML
	AnchorPane board;
	@FXML
	Label title;
	double height;
	double width;
	double maxValueX,maxValueY;
	double minValueX,minValueY;
	List<Circle> points=new ArrayList<>();
	List<Point> pointList=new ArrayList<>();
	List<Line> lines=new ArrayList<>();
	List<Circle> circles=new ArrayList<>();

	public CoordinateSystemController() {
		maxValueX=10000;
		minValueX=-10000;
		maxValueY=10000;
		minValueY=-10000;
		board=new AnchorPane();
	}
	public void changeSetting(double minX,double maxX,double minY,double maxY) {
		if(maxX>minX&&maxY>minY){
			maxValueX=maxX;
			minValueX=minX;
			maxValueY=maxY;
			minValueY=minY;
			applyCoordinate();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		height=board.getPrefHeight();
		width=board.getPrefWidth();
		applyCoordinate();
	}
	public void applyCoordinate(){
		y.setStartX(width*(0-minValueX)/(maxValueX-minValueX));
		y.setEndX(width*(0-minValueX)/(maxValueX-minValueX));
		x.setStartY(height-height*(0-minValueY)/(maxValueY-minValueY));
		x.setEndY(height-height*(0-minValueY)/(maxValueY-minValueY));
	}
	public void addPoint(Point p,Paint color) {
		if(!pointList.contains(p)){
			double x0=this.y.getEndX();
			double y0=this.x.getEndY();
			double displayX=x0+p.x/(maxValueX-minValueX)*width;
			double displayY=y0-p.y/(maxValueY-minValueY)*height;
			Circle toDisplay=new Circle();
			toDisplay.setRadius(1);
			toDisplay.setCenterX(displayX);
			toDisplay.setCenterY(displayY);
			toDisplay.setFill(color);
			points.add(toDisplay);
			pointList.add(p);
			//board.getChildren().removeAll(points);
			board.getChildren().add(toDisplay);
		}
	}
	public void addLine(anomaly_detectors.Line l,Paint color) {
		double valueForMinX=l.a*minValueX+l.b;
		double valueForMaxX=l.a*maxValueX+l.b;
		Line toDisplay=new Line();
		toDisplay.setStroke(Color.GREENYELLOW);
		toDisplay.setStrokeWidth(1.0);
		double x0=this.y.getEndX();
		double y0=this.x.getEndY();
		toDisplay.setStartX(x0+minValueX/(maxValueX-minValueX)*width);
		toDisplay.setStartY(y0-valueForMinX/(maxValueY-minValueY)*height);
		toDisplay.setEndX(x0+maxValueX/(maxValueX-minValueX)*width);
		toDisplay.setEndY(y0-valueForMaxX/(maxValueY-minValueY)*height);
		board.getChildren().add(toDisplay);
	}
	public void addCircle(anomaly_detectors.Circle c, Paint color) {
		double x0=this.y.getEndX();
		double y0=this.x.getEndY();
		Circle toDisplay=new Circle();
		toDisplay.setCenterX(x0+c.center.x/(maxValueX-minValueX)*width);
		toDisplay.setCenterY(y0-c.center.y/(maxValueY-minValueY)*height);
		toDisplay.setStroke(Color.GREENYELLOW);
		double radiusDisplay=(x0+(c.center.x+c.radius)/(maxValueX-minValueX)*width-(x0+c.center.x/(maxValueX-minValueX)*width));
		toDisplay.setRadius(radiusDisplay);
		toDisplay.setFill(Color.rgb(255, 255, 255, 0));
		toDisplay.setStrokeWidth(2.0);
		board.getChildren().add(toDisplay);
	}
	public void addSetPoints(Collection<Point> listPoints, Paint color){
		List<Point> tmp = new ArrayList<>(List.copyOf(listPoints));
		tmp.removeAll(pointList);
		tmp.forEach((p)->addPoint(p,color));
	}
	public void removePoint(int numOfPointToRemove){ // remove the last "numOfPointsToRemove" points
		if(numOfPointToRemove>pointList.size()){
			int x=pointList.size();
			pointList.removeAll(pointList);
			for(int i=0;i<x;i++)
				if(board.getChildren().get(board.getChildren().size()-1) instanceof Circle){
					Circle c=(Circle)board.getChildren().get(board.getChildren().size()-1);
					if(!c.getFill().equals(Color.rgb(255, 255, 255, 0)))
						board.getChildren().remove(board.getChildren().size()-1);
					else
						i--;
				}
				else
					i--;
		}
		else{
			for(int i=0;i<numOfPointToRemove;i++)
				pointList.remove(pointList.size()-1);
			for(int i=0;i<numOfPointToRemove;i++)
				if(board.getChildren().get(board.getChildren().size()-1) instanceof Circle){
					Circle c=(Circle)board.getChildren().get(board.getChildren().size()-1);
					if(!c.getFill().equals(Color.rgb(255, 255, 255, 0)))
						board.getChildren().remove(board.getChildren().size()-1);
					else
						i--;
				}
				else
					i--;
		}
	}
	public void clear() {
		board.getChildren().removeAll(points);
		board.getChildren().removeAll(lines);
		board.getChildren().removeAll(circles);
		points.removeAll(points);
		pointList.removeAll(pointList);
		lines.removeAll(lines);
		circles.removeAll(circles);
	}
}
