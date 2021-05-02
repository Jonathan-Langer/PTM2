package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.ListOfAttributes;

public class AttributesViewDisplayer extends StackPane {
	ListView<String> toDisplay=new ListView<>();
	ObservableList<String> list = FXCollections.observableArrayList();
	
	public AttributesViewDisplayer() {
		toDisplay.addEventHandler(MouseEvent.ANY, (e)->toDisplay.requestFocus());
	}
	public void loadAttributesToListView(ListOfAttributes attributes) {
		getChildren().remove(toDisplay);
		toDisplay.getItems().removeAll(toDisplay.getItems());
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		toDisplay.getItems().addAll(list);
		getChildren().add(toDisplay);
	}
}
