package view.attributesView;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.ListOfAttributes;
import view.joystick.JoystickController;

public class AttributesViewDisplayer extends AnchorPane {
	public AttributesViewController controller;
	public AttributesViewDisplayer() {
		super();
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("AttributesView.fxml").openStream());
			controller=loader.getController();
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
