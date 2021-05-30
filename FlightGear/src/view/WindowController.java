package view;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import view.attributesView.AttributesViewDisplayer;
import view.joystick.JoystickDisplayer;
import view.player.*;
import view.tableClocks.TableClocksController;
import view.tableClocks.TableClocksDisplayer;
import anomaly_detectors.TimeSeries;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.AttributeSettings;
import model.ListOfAttributes;
import viewModel.ViewModel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.SelectionMode;
import javafx.scene.*;
//import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.MyModel;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class WindowController implements Initializable,Observer{
	
	StringProperty txtFilePath=new SimpleStringProperty();
	StringProperty csvTrainFilePath=new SimpleStringProperty();
	ListOfAttributes attributes;
	ViewModel vm;

	public WindowController() {
		txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
		csvTrainFilePath.set(new File("resources/last_train.csv").getAbsolutePath());
		attributes=new ListOfAttributes(txtFilePath.get());
		attributesView=new AttributesViewDisplayer();
		joystickDisplayer=new JoystickDisplayer();
		playerDisplayer = new Player();
		tableClocks=new TableClocksDisplayer();
	}
	public void setViewModel(ViewModel vm) {
		this.vm=vm;
		
		vm.bindToProperty("settingsFile", this.txtFilePath,"V2VM");
		String s=txtFilePath.getValue();
		txtFilePath.set("a");
		txtFilePath.set(s);
		vm.bindToProperty("csvTrain", this.csvTrainFilePath,"V2VM");
		s=csvTrainFilePath.getValue();
		csvTrainFilePath.set("a");
		csvTrainFilePath.set(s);
		vm.bindToProperty("csvTest", playerDisplayer.csvTestFilePath,"V2VM");
		/*s=playerDisplayer.csvTestFilePath.getValue();
		playerDisplayer.csvTestFilePath.set("a");
		playerDisplayer.csvTestFilePath.set(s);*/

		vm.bindToProperty("aileron", joystickDisplayer.aileronValue,"VM2V");
		vm.bindToProperty("elevator", joystickDisplayer.elevatorsValue,"VM2V");
		vm.bindToProperty("rudder", joystickDisplayer.rudderValue,"VM2V");
		vm.bindToProperty("throttle", joystickDisplayer.throttleValue,"VM2V");
		vm.bindToProperty("minAileron",joystickDisplayer.minAileron,"VM2V");
		vm.bindToProperty("maxAileron",joystickDisplayer.maxAileron,"VM2V");
		vm.bindToProperty("minElevator",joystickDisplayer.minElevator,"VM2V");
		vm.bindToProperty("maxElevator", joystickDisplayer.maxElevator, "VM2V");
		vm.bindToProperty("minRudder", joystickDisplayer.minRudder, "VM2V");
		vm.bindToProperty("maxRudder", joystickDisplayer.maxRudder, "VM2V");
		vm.bindToProperty("minThrottle", joystickDisplayer.minThrottle, "VM2V");
		vm.bindToProperty("maxThrottle", joystickDisplayer.maxThrottle, "VM2V");

		vm.bindToProperty("altimeter", tableClocks.altimeterValue,"VM2V");
		vm.bindToProperty("airspeed", tableClocks.airspeedValue,"VM2V");
		vm.bindToProperty("heading", tableClocks.headingValue,"VM2V");
		vm.bindToProperty("roll", tableClocks.rollValue,"VM2V");
		vm.bindToProperty("pitch", tableClocks.pitchValue,"VM2V");
		vm.bindToProperty("yaw", tableClocks.yawValue,"VM2V");
		vm.bindToProperty("minAltimeter",tableClocks.minAltimeter,"VM2V");
		vm.bindToProperty("maxAltimeter", tableClocks.maxAltimeter, "VM2V");    //make exception
		vm.bindToProperty("minAirspeed", tableClocks.minAirspeed, "VM2V");
		vm.bindToProperty("maxAirspeed", tableClocks.maxAirspeed, "VM2V");
		vm.bindToProperty("minHeading", tableClocks.minHeading, "VM2V");
		vm.bindToProperty("maxHeading", tableClocks.maxHeading, "VM2V");
		vm.bindToProperty("minRoll",tableClocks.minRoll,"VM2V");
		vm.bindToProperty("maxRoll",tableClocks.maxRoll,"VM2V");
		vm.bindToProperty("minPitch",tableClocks.minPitch,"VM2V");
		vm.bindToProperty("maxPitch",tableClocks.maxPitch,"VM2V");
		vm.bindToProperty("minYaw",tableClocks.minYaw,"VM2V");
		vm.bindToProperty("maxYaw",tableClocks.maxYaw,"VM2V");
		vm.currentTime.bindBidirectional(playerDisplayer.currentTime);
		vm.applyValuesMinMax();
		
		vm.setTrainTimeSeries(csvTrainFilePath.get());
	}
	//---------------FXML Objects--------------
	@FXML
	MenuItem editSetting;
		
	@FXML
	AttributesViewDisplayer attributesView;
		
	@FXML
	JoystickDisplayer joystickDisplayer;
		
	@FXML
	TableClocksDisplayer tableClocks;
		
	@FXML
	Player playerDisplayer;
	
	
		
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerDisplayer.csvTestFilePath.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if(!vm.setTestTimeSeries(playerDisplayer.csvTestFilePath.getValue())) {
					Alert message=new Alert(Alert.AlertType.ERROR);
					message.setContentText("oops!"
							+ " \n this file format is not valid");
					message.show();
				}
				else{
					vm.initValues();
					Alert message=new Alert(Alert.AlertType.CONFIRMATION);
					message.setContentText("well done!"
							+ " \n the test file has been saved in the system");
					message.show();
				}
			}
		});
		playerDisplayer.controller.playIcon.fillProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1) {
				if(playerDisplayer.controller.playIcon.getFill()!= Color.BLACK){
					vm.play();
				}
			}
		});
	}
	
	
	public void loadTxtFile() {
		FileChooser fc=new FileChooser();
		fc.setTitle("open txt setting file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter
				("txt file", "*.txt")
				);
		File chooser=fc.showOpenDialog(null);
		if(chooser!=null) {
			if(!vm.checkValidateSettingFile(chooser.getPath())) {
				Alert message=new Alert(Alert.AlertType.ERROR);
				message.setContentText("oops!"
						+ " \n this file format is not valid \n and the file was'nt saved in the system");
				message.show();
				txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
			}
			else {
				txtFilePath.set(chooser.getPath());
				vm.saveLastSettingFile();
				Alert message=new Alert(Alert.AlertType.CONFIRMATION);
				message.setContentText("well done!"
						+ " \n your txt file was saved in the system");
				message.show();
			}
			attributes=new ListOfAttributes(txtFilePath.getValue());
			attributesView.controller.changeSetting(attributes);
			vm.applyValuesMinMax();
			//joystickDisplayer.controller.
		}
	}
	
	
	public void loadCSVFile() {
		FileChooser fc=new FileChooser();
		fc.setTitle("open csv file");
		fc.setInitialDirectory(new File("./resources"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter
				("csv file", "*.csv")
				);
		File chooser=fc.showOpenDialog(null);
		
		if(chooser!= null)
		{			
			if(!vm.setTrainTimeSeries(chooser.getPath()))
			{
				Alert message=new Alert(Alert.AlertType.ERROR);
				message.setContentText("oops!"
						+ " \n this file format is not valid \n and the file was'nt saved in the system");
				message.show();
			}
			else {
				vm.saveLastCsvTrainFile();
				Alert message=new Alert(Alert.AlertType.CONFIRMATION);
				message.setContentText("well done!"
						+ " \n your csv file was saved in the system");
				message.show();
				csvTrainFilePath.set(chooser.getPath());
			}
		}

		attributes=new ListOfAttributes(txtFilePath.get());
		attributesView.controller.changeSetting(attributes);
		joystickDisplayer=new JoystickDisplayer();	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
