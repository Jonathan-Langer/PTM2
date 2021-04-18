package view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;

public class WindowController implements Initializable{
	
	String csvFileName;
	@FXML
	private ComboBox<String> options;
	
	public WindowController() {
		//options=new ComboBox<>();
		//options.setValue("1");
	}
	
	public void openCSVFile() {
		FileChooser fc=new FileChooser();
		fc.setTitle("open csv file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("csv file", "*.csv")
				);
		File chooser=fc.showOpenDialog(null);
		if(chooser!=null)
			csvFileName=chooser.getName();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list=FXCollections.observableArrayList
				("0.25","0.5","0.75","1","1.25","1.5","1.75","2");
		options.setItems(list);
		options.setValue("1");
	}
}
