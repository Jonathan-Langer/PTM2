package view.attributesView.coordinateSystem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
	double height;
	double width;
	double maxValue;
	double minValue;
	List<Circle> points=new ArrayList<>();
	List<Line> lines=new ArrayList<>();
	List<Circle> circles=new ArrayList<>();
	
	public CoordinateSystemController() {
		maxValue=100;
		minValue=-100;
		board=new AnchorPane();
	}
	public CoordinateSystemController(double max,double min) {
		maxValue=max;
		minValue=min;
		board=new AnchorPane();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		height=board.getPrefHeight();
		width=board.getPrefWidth();		
	}
	public void addPoint(double x,double y,Paint color) {
		double displayX=(x/(maxValue-minValue))*width+width/2;
		double displayY=height-(y/(maxValue-minValue))*height-height/2;
		Circle toDisplay=new Circle();
		toDisplay.setRadius(5);
		toDisplay.setCenterX(displayX);
		toDisplay.setCenterY(displayY);
		toDisplay.setFill(color);
		points.add(toDisplay);
		board.getChildren().removeAll(points);
		board.getChildren().addAll(points);
	}
	public void addLine(double m,double b,Paint color) {
		double valueForMinX=m*minValue+b;
		double valueForMaxX=m*maxValue+b;
		Line toDisplay=new Line();
		toDisplay.setStroke(color);
		toDisplay.setStrokeWidth(1.0);
		toDisplay.setStartX((minValue/(maxValue-minValue))*width+width/2);
		toDisplay.setEndX((maxValue/(maxValue-minValue))*width+width/2);
		toDisplay.setStartY((height-(valueForMinX/(maxValue-minValue))*height-height/2));
		toDisplay.setEndY((height-(valueForMaxX/(maxValue-minValue))*height-height/2));
		lines.add(toDisplay);
		board.getChildren().removeAll(lines);
		board.getChildren().addAll(lines);
	}
	public void addCircle(double centerX,double centerY,double radius,Paint color) {
		Circle toDisplay=new Circle();
		toDisplay.setCenterX((centerX/(maxValue-minValue))*width+width/2);
		toDisplay.setCenterY(height-(centerY/(maxValue-minValue))*height-height/2);
		toDisplay.setStroke(Color.GREENYELLOW);
		double radiusDisplay=((centerX+radius)/(maxValue-minValue))*width+width/2-(((centerX)/(maxValue-minValue))*width+width/2);
		toDisplay.setRadius(radiusDisplay);
		toDisplay.setFill(Color.rgb(255, 255, 255, 0));
		toDisplay.setStroke(color);
		toDisplay.setStrokeWidth(2.0);
		circles.add(toDisplay);
		board.getChildren().removeAll(circles);
		board.getChildren().addAll(circles);
	}
	public void clear() {
		board.getChildren().removeAll(points);
		board.getChildren().removeAll(lines);
		board.getChildren().removeAll(circles);
	}
}
