package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import model.ListOfAttributes;

public class AttributesViewDisplayer extends ListView<String> {
	ObservableList<String> list = FXCollections.observableArrayList();
	/*
	 ListOfAttributes attributes;
	public AttributesViewDisplayer(ListOfAttributes attributes) {
		this.attributes=attributes;
	}
	 */
	public void loadAttributesToListView(ListOfAttributes attributes) {
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		this.getItems().addAll(list);
	}
}
