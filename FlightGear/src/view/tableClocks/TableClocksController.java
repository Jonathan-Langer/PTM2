package view.tableClocks;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.TickLabelLocation;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.TickMarkType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import model.ListOfAttributes;

public class TableClocksController implements Initializable {
	@FXML
	StackPane altimeter,airspeed,heading,roll,pitch,yaw;
	
	ListOfAttributes attributes;
	
	public TableClocksController() {
		altimeter=new StackPane(); //height of the flight
		airspeed=new StackPane(); //speed of the flight
		heading=new StackPane(); //direction of the flight
		roll=new StackPane();
		pitch=new StackPane();
		yaw=new StackPane();
		File lastSetting=new File(new File("resources/last_setting.txt").getAbsolutePath());
		if(lastSetting.exists())
			this.attributes=new ListOfAttributes(lastSetting.getAbsolutePath());
		else
			this.attributes=new ListOfAttributes();
	}
	public TableClocksController(ListOfAttributes attributes) {
		altimeter=new StackPane(); //height of the flight
		airspeed=new StackPane(); //speed of the flight
		heading=new StackPane(); //direction of the flight
		roll=new StackPane();
		pitch=new StackPane();
		yaw=new StackPane();
		this.attributes=attributes;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Gauge altimeter=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("altitude")
                .unit("km")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.altimeter.getChildren().add(altimeter);
		Gauge airspeed=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("speed-break")
                .unit("km/h")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.airspeed.getChildren().add(airspeed);
		Gauge heading=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("heading")
                .unit("degrees")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.heading.getChildren().add(heading);
		Gauge roll=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("roll")
                .unit("km")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.roll.getChildren().add(roll);
		Gauge yaw=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("yaw")
                .unit("km")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.yaw.getChildren().add(yaw);
		Gauge pitch=GaugeBuilder.create()
                .minValue(0)
                .maxValue(10)
                .animated(true)
                .autoScale(true)
                .shadowsEnabled(true)
                .ledColor(Color.rgb(250, 50, 0))
                .gradientBarEnabled(true)
                .gradientBarStops(new Stop(0.0, Color.rgb(0, 0, 255, 0.7)),
                                  new Stop(0.5, Color.rgb(0, 200, 255, 0.7)),
                                  new Stop(1.0, Color.rgb(0, 255, 0, 0.7)))
                .majorTickMarkType(TickMarkType.TRAPEZOID)
                .majorTickMarksVisible(true)
                .mediumTickMarkType(TickMarkType.TRAPEZOID)
                .mediumTickMarksVisible(true)
                .minorTickMarkType(TickMarkType.LINE)
                .minorTickMarksVisible(false)
                .tickLabelsVisible(true)
                .tickLabelOrientation(TickLabelOrientation.HORIZONTAL)
                .tickLabelLocation(TickLabelLocation.INSIDE)
                .title("pitch")
                .unit("km")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.pitch.getChildren().add(pitch);
		
		for(String name:this.attributes.getList().keySet()) {
			if(attributes.getList().get(name).getColInCSV()==24) {
				airspeed.setMinValue(attributes.getList().get(name).getMinValue());
				airspeed.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==25) {
				altimeter.setMinValue(attributes.getList().get(name).getMinValue());
				altimeter.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==20) {
				yaw.setMinValue(attributes.getList().get(name).getMinValue());
				yaw.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==28) {
				roll.setMinValue(attributes.getList().get(name).getMinValue());
				roll.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==29) {
				pitch.setMinValue(attributes.getList().get(name).getMinValue());
				pitch.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==36) {
				heading.setMinValue(attributes.getList().get(name).getMinValue());
				heading.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
	}
}}
