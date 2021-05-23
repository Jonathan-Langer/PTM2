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
	public Gauge gAltimeter,gAirspeed,gHeading,gRoll,gPitch,gYaw;
	
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		gAltimeter=GaugeBuilder.create()
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
		this.altimeter.getChildren().add(gAltimeter);
		gAirspeed=GaugeBuilder.create()
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
		this.airspeed.getChildren().add(gAirspeed);
		gHeading=GaugeBuilder.create()
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
		this.heading.getChildren().add(gHeading);
		gRoll=GaugeBuilder.create()
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
		this.roll.getChildren().add(gRoll);
		gYaw=GaugeBuilder.create()
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
		this.yaw.getChildren().add(gYaw);
		gPitch=GaugeBuilder.create()
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
		this.pitch.getChildren().add(gPitch);
		
		for(String name:this.attributes.getList().keySet()) {
			if(attributes.getList().get(name).getColInCSV()==24) {
				gAirspeed.setMinValue(attributes.getList().get(name).getMinValue());
				gAirspeed.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==25) {
				gAltimeter.setMinValue(attributes.getList().get(name).getMinValue());
				gAltimeter.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==20) {
				gYaw.setMinValue(attributes.getList().get(name).getMinValue());
				gYaw.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==28) {
				gRoll.setMinValue(attributes.getList().get(name).getMinValue());
				gRoll.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==29) {
				gPitch.setMinValue(attributes.getList().get(name).getMinValue());
				gPitch.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==36) {
				gHeading.setMinValue(attributes.getList().get(name).getMinValue());
				gHeading.setMaxValue(attributes.getList().get(name).getMaxValue());
			}
	}
}}
