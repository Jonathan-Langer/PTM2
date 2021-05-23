package view.attributesView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import anomaly_detectors.Point;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.ListOfAttributes;
import view.attributesView.coordinateSystem.CoordinateSystemController;
import view.attributesView.coordinateSystem.CoordinateSystemDisplayer;

public class AttributesViewController implements Initializable {
	ObservableList<String> list = FXCollections.observableArrayList();
	ListOfAttributes attributes;
	
	@FXML
	ListView<String> listAttributes=new ListView<String>();
	
	@FXML
	public CoordinateSystemDisplayer detections,selectedPrameter,correlatedPrameter;
	
	/*
	 * public AttributesViewController(ListOfAttributes attributes) {
	 * this.attributes=attributes; detections=new CoordinateSystemDisplayer();
	 * selectedPrameter=new CoordinateSystemDisplayer(); correlatedPrameter=new
	 * CoordinateSystemDisplayer(); }
	 */
	
	public AttributesViewController() {
		File lastSetting=new File(new File("resources/last_setting.txt").getAbsolutePath());
		if(lastSetting.exists())
			this.attributes=new ListOfAttributes(lastSetting.getAbsolutePath());
		else
			this.attributes=new ListOfAttributes();
		detections=new CoordinateSystemDisplayer();
		selectedPrameter=new CoordinateSystemDisplayer();
		correlatedPrameter=new CoordinateSystemDisplayer();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		/*listAttributes.getItems().removeAll(listAttributes.getItems());
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		list.sort((s1,s2)->s1.compareTo(s2));
		listAttributes.getItems().addAll(list);*/
		applySetting();
		// -----------set event handler for choosing an option---------
		//listAttributes.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->toDelete());
		//access to the selected item - 
		//listAttributes.getSelectionModel().getSelectedItem()
	}
	
	public void applySetting() {
		listAttributes.getItems().removeAll(listAttributes.getItems());
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		list.sort((s1,s2)->s1.compareTo(s2));
		listAttributes.getItems().addAll(list);
	}
	
	public void changeSetting(ListOfAttributes newAttrinbutes) {
		this.attributes=newAttrinbutes;
		applySetting();
	}
}
