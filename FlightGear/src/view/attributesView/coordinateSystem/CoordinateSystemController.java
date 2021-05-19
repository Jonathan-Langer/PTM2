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
	public void addPoint(double x,double y) {
		double displayX=(x/(maxValue-minValue))*width+width/2;
		double displayY=height-(y/(maxValue-minValue))*height-height/2;
		Circle toDisplay=new Circle();
		toDisplay.setRadius(7);
		toDisplay.setCenterX(displayX);
		toDisplay.setCenterY(displayY);
		toDisplay.setFill(Color.rgb(51, 89, 210));
		points.add(toDisplay);
		board.getChildren().removeAll(points);
		board.getChildren().addAll(points);
	}
	public void addLine(double m,double b) {
		double valueForMinX=m*minValue+b;
		double valueForMaxX=m*maxValue+b;
		Line toDisplay=new Line();
		toDisplay.setStroke(Color.GREENYELLOW);
		toDisplay.setStrokeWidth(1.0);
		toDisplay.setStartX((minValue/(maxValue-minValue))*width+width/2);
		toDisplay.setEndX((maxValue/(maxValue-minValue))*width+width/2);
		toDisplay.setStartY((height-(valueForMinX/(maxValue-minValue))*height-height/2));
		toDisplay.setEndY((height-(valueForMaxX/(maxValue-minValue))*height-height/2));
		board.getChildren().add(toDisplay);
	}
	public void addCircle(double centerX,double centerY,double radius) {
		Circle toDisplay=new Circle();
		toDisplay.setCenterX((centerX/(maxValue-minValue))*width+width/2);
		toDisplay.setCenterY(height-(centerY/(maxValue-minValue))*height-height/2);
		toDisplay.setStroke(Color.GREENYELLOW);
		double radiusDisplay=((centerX+radius)/(maxValue-minValue))*width+width/2-(((centerX)/(maxValue-minValue))*width+width/2);
		toDisplay.setRadius(radiusDisplay);
		toDisplay.setFill(Color.rgb(255, 255, 255, 0));
		toDisplay.setStrokeWidth(2.0);
		board.getChildren().add(toDisplay);
	}
	public void addPointToWindow() {
		Scanner s=new Scanner(System.in);
		double x=s.nextDouble();
		double y=s.nextDouble();
		addPoint(x,y);
		System.out.println("the point: ("+x+","+y+") is shown");
	}
	public void addLineToWindow() {
		Scanner s=new Scanner(System.in);
		double m=s.nextDouble();
		double b=s.nextDouble();
		addLine(m,b);
		System.out.println("the line "+m+"x "+b+" is shown");
	}
	public void addCircleToWindow() {
		Scanner s=new Scanner(System.in);
		double x=s.nextDouble();
		double y=s.nextDouble();
		double r=s.nextDouble();
		addCircle(x,y,r);
		System.out.println("the circle (x- "+x+")^2 + (y-"+y+")^2 = "+r+"^2 is shown");
	}
}
