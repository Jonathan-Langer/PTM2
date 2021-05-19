package view.player;

import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import model.ListOfAttributes;

public class PlayerDisplayerController  implements Initializable {
		
	public StringProperty csvTestFilePath=new SimpleStringProperty();
	
	@FXML
	public ComboBox<String> options = new ComboBox<>();
	
	public PlayerDisplayerController() {
		options = new ComboBox<>();
		csvTestFilePath = new SimpleStringProperty();
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			ObservableList<String> list=FXCollections.observableArrayList
					("0.25","0.5","0.75","1","1.25","1.5","1.75","2");
			options.setItems(list);
			if(options.getValue()==null)
				options.setValue("1");
		
	}
	public void openCSVFile() {
		FileChooser fc=new FileChooser();
		fc.setTitle("open csv file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter
				("csv file", "*.csv")
				);
		File chooser=fc.showOpenDialog(null);
		if(chooser!=null)
			csvTestFilePath.set(chooser.getPath());
	}
}