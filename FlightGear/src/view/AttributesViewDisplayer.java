package view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.ListOfAttributes;

public class AttributesViewDisplayer extends StackPane implements Initializable {
	ListView<String> toDisplay=new ListView<>();
	ObservableList<String> list = FXCollections.observableArrayList();
	
	public void loadAttributesToListView(ListOfAttributes attributes) {
		getChildren().remove(toDisplay);
		toDisplay.getItems().removeAll(toDisplay.getItems());
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		list.sort((s1,s2)->s1.compareTo(s2));
		toDisplay.getItems().addAll(list);
		getChildren().add(toDisplay);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		toDisplay.addEventHandler(MouseEvent.ANY, (e)->toDisplay.requestFocus());
	}
}
