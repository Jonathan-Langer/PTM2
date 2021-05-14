package view.attributesView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import model.ListOfAttributes;

public class AttributesViewController implements Initializable {
	ObservableList<String> list = FXCollections.observableArrayList();
	ListOfAttributes attributes;
	
	@FXML
	ListView<String> listAttributes=new ListView<String>();
	
	@FXML
	LineChart<?, ?> selectedPrameter,correlatedPrameter,detections;
	
	public AttributesViewController(ListOfAttributes attributes) {
		this.attributes=attributes;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listAttributes.getItems().removeAll(listAttributes.getItems());
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		list.sort((s1,s2)->s1.compareTo(s2));
		listAttributes.getItems().addAll(list);
	}
}
