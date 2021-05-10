package view.tableClocks;

import java.net.URL;
import java.util.ResourceBundle;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.TickLabelLocation;
import eu.hansolo.medusa.TickLabelOrientation;
import eu.hansolo.medusa.TickMarkType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;

public class TableClocksController implements Initializable {
	@FXML
	StackPane altitude,speedbrake,rudder,roll,pitch,yaw;
	
	public TableClocksController() {
		altitude=new StackPane();
		speedbrake=new StackPane();
		rudder=new StackPane();
		roll=new StackPane();
		pitch=new StackPane();
		yaw=new StackPane();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Gauge altitude=GaugeBuilder.create()
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
		this.altitude.getChildren().add(altitude);
		Gauge speedbreak=GaugeBuilder.create()
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
		this.speedbrake.getChildren().add(speedbreak);
		Gauge rudder=GaugeBuilder.create()
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
                .title("rudder")
                .unit("degrees")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.rudder.getChildren().add(rudder);
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
                .title("altitude")
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
                .title("altitude")
                .unit("km")
                .foregroundBaseColor(Gauge.DARK_COLOR)
                .build();
		this.pitch.getChildren().add(pitch);
	}
}
