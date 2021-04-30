package view;

import javafx.geometry.Orientation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.effect.Effect;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import model.AttributeSettings;
import model.ListOfAttributes;

public class JoystickDisplayer extends AnchorPane {
	RadioButton manual;
	Label airspeed, altitude;
	Slider throttle, rudder, flaps;
	Circle joystick, joystickBorder;
	ListOfAttributes attributes;
	
	public JoystickDisplayer() {
		super();
		manual=new RadioButton();
		airspeed=new Label();
		altitude=new Label();
		throttle=new Slider();
		rudder=new Slider();
		flaps=new Slider();
		joystick=new Circle();
		joystickBorder=new Circle();
	}
	public void setAttributes(ListOfAttributes attributes) {
		this.attributes=attributes;
	}
	public void draw() {
		if(attributes!=null) {
			// ----------manual--------
			manual.setText("Manual Controls");
			manual.setLayoutX(8.0);
			manual.setLayoutY(4.0);
			manual.setMnemonicParsing(false);
			// --------rudder----------
			rudder.setLayoutX(53);
			rudder.setLayoutY(287);
			for(AttributeSettings a:attributes.getList().values()) {
				if(a.getColInCSV()==2) {
					rudder.setMax(a.getMaxValue());
					rudder.setMin(a.getMinValue());
					break;
				}
			}
			rudder.setPrefHeight(16);
			rudder.setPrefWidth(200);
			rudder.setShowTickLabels(true);
			// ------------throttle--------
			throttle.setLayoutX(20);
			throttle.setLayoutY(62);
			for(AttributeSettings a:attributes.getList().values()) {
				if(a.getColInCSV()==6) {
					throttle.setMax(a.getMaxValue());
					throttle.setMin(a.getMinValue());
					break;
				}
			}
			throttle.setOrientation(Orientation.VERTICAL);
			throttle.setPrefHeight(190);
			throttle.setPrefWidth(16);
			throttle.setShowTickLabels(true);
			// ----------joystickBorder-------
			joystickBorder.setLayoutX(153);
			joystickBorder.setLayoutY(161);
			joystickBorder.setRadius(100);
			joystickBorder.setStroke(Color.BLACK);
			joystickBorder.setStrokeType(StrokeType.INSIDE);
			// -----------joystick---------
			joystick.setLayoutX(153);
			joystick.setLayoutY(161);
			joystick.setFocusTraversable(true);
			joystick.setRadius(46);
			joystick.setStroke(Color.rgb(206, 221, 67));
			joystick.setStrokeLineCap(StrokeLineCap.ROUND);
			joystick.setStrokeWidth(3.0);
			// -----------flaps-----------
			flaps.setLayoutX(37);
			flaps.setLayoutY(28);
			flaps.setBlockIncrement(0.3333334);
			for(AttributeSettings a:attributes.getList().values()) {
				if(a.getColInCSV()==3) {
					throttle.setMax(a.getMaxValue());
					throttle.setMin(a.getMinValue());
					break;
				}
			}
			flaps.setPrefHeight(16);
			flaps.setPrefWidth(56);
			// -----------airspeed------
			airspeed.setLayoutX(271);
			airspeed.setLayoutY(14);
			airspeed.setPrefHeight(17);
			airspeed.setPrefWidth(60);
			airspeed.setText("speed: 0");
			airspeed.setFont(Font.font(11));
			// ---------altitude-------
			altitude.setLayoutX(271);
			altitude.setLayoutY(35);
			altitude.setText("altitude: 0");
			altitude.setFont(Font.font(11));
			
			getChildren().add(joystick);
		}
	}
}
