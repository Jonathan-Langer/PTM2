package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import model.ListOfAttributes;

public class AttributesViewDisplayer extends ListView<String> {
	ObservableList<String> list = FXCollections.observableArrayList();
	
	public void loadAttributesToListView(ListOfAttributes attributes) {
		this.getItems().removeAll(list);
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		this.getItems().addAll(list);
	}
}
