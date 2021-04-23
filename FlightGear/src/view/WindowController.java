package view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import anomaly_detectors.TimeSeries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
//import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class WindowController extends Observable implements Initializable{
	
	String csvFileName;
	@FXML
	private ComboBox<String> options;
	
	//---------------FXML Objects--------------
		@FXML
		MenuItem editSetting;
		//@FXML
		//JoystickDisplayer joystickDisplayer;
		
		@FXML
		Button connect, textfile;
		
		@FXML
		RadioButton manual, autopilot;
		
		@FXML
		Label statlabel, airspeed, altitude;
		
		@FXML
		Slider throttle, rudder, flaps;

		@FXML
		Circle joystick, joystickBorder;
	
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
			csvFileName=chooser.getPath();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list=FXCollections.observableArrayList
				("0.25","0.5","0.75","1","1.25","1.5","1.75","2");
		options.setItems(list);
		if(options.getValue()==null)
			options.setValue("1");
	}
	
	public void editSetting() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditSetting.fxml"));
		Parent root1;
		try {
			root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));  
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void joystickOnMouseDrag(MouseEvent event) {
		/*if(this.manual.isSelected()) {
			if (event.getX() <= 100 && event.getX() >= -100)
				if (event.getY() <= 100 && event.getY() >= -100) {
					joystick.setCenterX(event.getX());
					joystick.setCenterY(event.getY());
					statlabel.setText("(Alieron = " +event.getX()/100 + " Elevator = " + event.getY()/100 + ")");
					//this.elevatorVal.set(event.getY()/-100);
					//this.alieronVal.set(event.getX()/100);
					//this.vm.elevatorChanged();
					//this.vm.aileronChanged();
			}
		}
		//else this.statlabel.setText(MCL);*/
	}
	
	public void joystickOnMouseRelease(MouseEvent event) {
		//joystick.setCenterX(0);
		//joystick.setCenterY(0);
	}
}
