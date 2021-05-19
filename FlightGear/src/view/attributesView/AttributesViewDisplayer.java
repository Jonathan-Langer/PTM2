package view.attributesView;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import model.ListOfAttributes;
import view.joystick.JoystickController;

public class AttributesViewDisplayer extends AnchorPane {
	ListOfAttributes attributes;
	public AttributesViewController controller;
	public AttributesViewDisplayer() {
		super();
		attributes=new ListOfAttributes();
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("AttributesView.fxml").openStream());
			controller=new AttributesViewController(attributes);
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public AttributesViewDisplayer(ListOfAttributes attributes) {
		super();
		this.attributes=attributes;
		FXMLLoader loader=new FXMLLoader();
		try {
			AnchorPane toDisplay=loader.load(getClass().getResource("AttributesView.fxml").openStream());
			AttributesViewController controller=new AttributesViewController(attributes);
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void loadAttributesToListView(ListOfAttributes attributes) {
		controller.loadAttributesToListView(attributes);
	}
}
