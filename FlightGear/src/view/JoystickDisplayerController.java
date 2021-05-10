package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import model.ListOfAttributes;

public class JoystickDisplayerController extends AnchorPane implements Initializable {
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
	
	public void editRudderMaxVal(double max) {
		try {
			this.getChildren().removeAll(this.getChildren());
			FXMLLoader loader=new FXMLLoader(getClass().getResource("Joystick.fxml"));
			joystick.rudder.setMax(max);
			loader.setController(joystick);
			Node n = loader.load();
			this.getChildren().add(n);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
				
	}
}
