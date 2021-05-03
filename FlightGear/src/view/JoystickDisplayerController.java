package view;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.ListOfAttributes;

public class JoystickDisplayerController extends AnchorPane {
	Joystick joystick;
	public JoystickDisplayerController() {
		super();
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Joystick.fxml"));
			joystick=new Joystick();
			loader.setController(joystick);
			Node n=loader.load();
			this.getChildren().removeAll(this.getChildren());
			this.getChildren().add(n);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public JoystickDisplayerController(ListOfAttributes attributes) {
		super();
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Joystick.fxml"));
			joystick=new Joystick(attributes);
			loader.setController(joystick);
			Node n=loader.load();
			this.getChildren().removeAll(this.getChildren());
			this.getChildren().add(n);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
