package view;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

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
	StringProperty csvTrainFile=new SimpleStringProperty();
	ListOfAttributes attributes;
	ViewModel vm;

	public WindowController() {
		txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
		csvTrainFile.set(new File("resources/last_train.csv").getAbsolutePath());
		attributes=new ListOfAttributes(txtFilePath.get());
		attributesView=new AttributesViewDisplayer();
		joystickDisplayer=new JoystickDisplayer();
		playerDisplayer = new Player();
		tableClocks=new TableClocksDisplayer();
	}
	public void setViewModel(ViewModel vm) {
		this.vm=vm;
		
		vm.bindToProperty("settingsFile", this.txtFilePath,"V2VM");
		vm.bindToProperty("csvTrain", this.csvTrainFile,"V2VM");
		vm.bindToProperty("csvTest", playerDisplayer.csvTestFilePath,"V2VM");
		
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
		vm.bindToProperty("maxAltimeter", tableClocks.maxAltimeter, "VM2V");
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
	/*	
		attributesView.loadAttributesToListView(attributes);
		attributesView.addEventFilter(MouseEvent.ANY,
				(e)->attributesView.toDisplay.requestFocus());
		joystickDisplayer=new JoystickDisplayerController(attributes);
		playerDisplayer = new Player();*/
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
			if(!checkValidateSettingFile(chooser.getPath())) {
				Alert message=new Alert(Alert.AlertType.ERROR);
				message.setContentText("oops!"
						+ " \n this file format is not valid \n and the file was'nt saved in the system");
				message.show();
				txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
			}
			else {
				txtFilePath.set(chooser.getPath());
				File lastSetting=new File(new File("resources/last_setting.txt").getAbsolutePath());
				File currentAttributes=new File(txtFilePath.get());
				try {
					if(!lastSetting.exists())
						lastSetting.createNewFile();
					PrintWriter write=new PrintWriter(lastSetting);
					BufferedReader read=new BufferedReader(new FileReader(currentAttributes));
					String line=null;
					while((line=read.readLine())!=null) {
						write.println(line);
						write.flush();
					}
					Alert message=new Alert(Alert.AlertType.CONFIRMATION);
					message.setContentText("well done!"
							+ " \n your txt file was saved in the system");
					message.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			attributesView.controller.changeSetting(attributes);
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
		/*if(chooser!=null)
		{			
			if() 
			{
				Alert message=new Alert(Alert.AlertType.ERROR);
				message.setContentText("oops!"
						+ " \n this file format is not valid \n and the file was'nt saved in the system");
				message.show();
				txtFilePath.set(new File("resources/last_train.csv").getAbsolutePath());
			}
		}
		else 
		{
			csvTrainFile.set(chooser.getPath());
			File lastTrainFile=new File(new File("resources/last_train.csv").getAbsolutePath());
			File currentAttributes=new File(csvTrainFile.get());
			try {
				if(!lastTrainFile.exists())
					lastTrainFile.createNewFile();
				PrintWriter write=new PrintWriter(lastTrainFile);
				BufferedReader read=new BufferedReader(new FileReader(currentAttributes));
				String line=null;
				while((line=read.readLine())!=null) {
					write.println(line);
					write.flush();
				}
				Alert message=new Alert(Alert.AlertType.CONFIRMATION);
				message.setContentText("well done!"
						+ " \n your csv file was saved in the system");
				message.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		csvTrainFile.set(chooser.getPath());
		File lastTrainFile=new File(new File("resources/last_train.csv").getAbsolutePath());
		File currentAttributes=new File(csvTrainFile.get());
		try {
			if(!lastTrainFile.exists())
				lastTrainFile.createNewFile();
			PrintWriter write=new PrintWriter(lastTrainFile);
			BufferedReader read=new BufferedReader(new FileReader(currentAttributes));
			String line=null;
			while((line=read.readLine())!=null) {
				write.println(line);
				write.flush();
			}
			Alert message=new Alert(Alert.AlertType.CONFIRMATION);
			message.setContentText("well done!"
					+ " \n your csv file was saved in the system");
			message.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		attributes=new ListOfAttributes(txtFilePath.get());
		attributesView.controller.changeSetting(attributes);
		joystickDisplayer=new JoystickDisplayer();	
	}
	
	
	
	private boolean checkValidateSettingFile(String txtFile) {
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(5, false);
		cellsAreApeared.put(6, false);
		cellsAreApeared.put(20, false);
		cellsAreApeared.put(24, false);
		cellsAreApeared.put(25, false);
		cellsAreApeared.put(28, false);
		cellsAreApeared.put(29, false);
		cellsAreApeared.put(36, false);
		try {
			BufferedReader read=new BufferedReader(new FileReader(new File(txtFile)));
			String line=null;
			while((line=read.readLine())!=null) {
				String[] data=line.split(",");
				if(data.length==4) {
					if(!cellsAreApeared.containsKey(Integer.parseInt(data[1]))) {
						read.close();
						return false;
					}
				}
				else {
					if(data.length==2) {
						if(!(data[0].equals("ip")||data[0].equals("port")||data[0].equals("rate"))) {
							read.close();
							return false;
						}
					}
					else {
						read.close();
						return false;
					}
				}
			}
			read.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
