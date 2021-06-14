package view;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.*;

import javafx.animation.Animation;
import javafx.application.Platform;
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
	HashMap<StringProperty,String> namesAttribute=new HashMap<>();
	StringProperty txtFilePath=new SimpleStringProperty();
	StringProperty csvTrainFilePath=new SimpleStringProperty();
	ViewModel vm;
	StringProperty aileronName,elevatorName,throttleName,rudderName,altimeterName,airspeedName,headingName,rollName,pitchName,yawName;
	public WindowController() {
		txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
		csvTrainFilePath.set(new File("resources/last_train.csv").getAbsolutePath());
		attributesView=new AttributesViewDisplayer();
		joystickDisplayer=new JoystickDisplayer();
		playerDisplayer = new Player();
		tableClocks=new TableClocksDisplayer();
		aileronName=new SimpleStringProperty("");
		elevatorName=new SimpleStringProperty("");
		rudderName=new SimpleStringProperty("");
		throttleName=new SimpleStringProperty("");
		altimeterName=new SimpleStringProperty("");
		airspeedName=new SimpleStringProperty("");
		headingName=new SimpleStringProperty("");
		rollName=new SimpleStringProperty("");
		pitchName=new SimpleStringProperty("");
		yawName=new SimpleStringProperty("");
		namesAttribute.put(aileronName,aileronName.getValue());
		namesAttribute.put(elevatorName,elevatorName.getValue());
		namesAttribute.put(rudderName,rudderName.getValue());
		namesAttribute.put(throttleName,throttleName.getValue());
		namesAttribute.put(altimeterName,altimeterName.getValue());
		namesAttribute.put(airspeedName,airspeedName.getValue());
		namesAttribute.put(headingName,headingName.getValue());
		namesAttribute.put(rollName,rollName.getValue());
		namesAttribute.put(pitchName,pitchName.getValue());
		namesAttribute.put(yawName,yawName.getValue());
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
		vm.bindToProperty("aileronName",aileronName,"VM2V");
		vm.bindToProperty("elevatorName",elevatorName,"VM2V");
		vm.bindToProperty("rudderName",rudderName,"VM2V");
		vm.bindToProperty("throttleName",throttleName,"VM2V");
		vm.bindToProperty("altimeterName",altimeterName,"VM2V");
		vm.bindToProperty("airspeedName",airspeedName,"VM2V");
		vm.bindToProperty("headingName",headingName,"VM2V");
		vm.bindToProperty("rollName",rollName,"VM2V");
		vm.bindToProperty("pitchName",pitchName,"VM2V");
		vm.bindToProperty("yawName",yawName,"VM2V");
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
		vm.applyNames();
		playerDisplayer.setRate(vm.getRate());
		attributesView.controller.applySetting(namesAttribute.values());
		//vm.checkValidateSettingFile(txtFilePath.getValue());
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
					playerDisplayer.csvTestFilePath.set(null);
				}
				else{
					vm.initValues();
					playerDisplayer.setLength(vm.getLength());
					Alert message=new Alert(Alert.AlertType.CONFIRMATION);
					message.setContentText("well done!"
							+ " \n the test file has been saved in the system");
					message.show();
				}
			}
		});
		playerDisplayer.speedPlayer.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				vm.setSpeedOfFlight(Double.parseDouble(playerDisplayer.speedPlayer.getValue()));
			}
		});
		playerDisplayer.currentTime.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				Platform.runLater(()->{
					String s=attributesView.selectedParameter.getValue();
					if(!s.isEmpty()){
						s=s.substring(0,1).toUpperCase()+s.substring(1,s.length());
						//attributesView.controller.selectedPrameter.controller.clear();
						attributesView.controller.selectedPrameter.controller.changeSetting(0,vm.getLength(),
								Double.parseDouble(vm.properties.get("min"+s).getValue().toString()),
								Double.parseDouble(vm.properties.get("max"+s).getValue().toString()));
						attributesView.controller.selectedPrameter.controller.addSetPoints(
								vm.sendPointOf1Parameter((int)playerDisplayer.currentTime.get()
										,attributesView.selectedParameter.getValue()),Color.BLUE);
					}
				});
			}
		});
		playerDisplayer.controller.playIcon.fillProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1) {
				if(playerDisplayer.controller.playIcon.getFill()!= Color.BLACK){
					if(playerDisplayer.csvTestFilePath.get()!=null)
						vm.play(Double.parseDouble(playerDisplayer.speedPlayer.getValue()));
					else{
						/*Alert message=new Alert(Alert.AlertType.ERROR);
						message.setContentText("oops!"
								+ " \n you didnt choose any csv test file, please choose one and try again");
						message.show();
						playerDisplayer.controller.playIcon.setFill(Color.BLACK);*/
					}
				}
			}
		});
		playerDisplayer.controller.stopIcon.fillProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1) {
				if(playerDisplayer.controller.stopIcon.getFill()!=Color.BLACK)
					vm.stop();
			}
		});
		playerDisplayer.controller.pauseIcon.fillProperty().addListener(new ChangeListener<Paint>() {
			@Override
			public void changed(ObservableValue<? extends Paint> observableValue, Paint paint, Paint t1) {
				if(playerDisplayer.controller.pauseIcon.getFill()!=Color.BLACK)
					vm.pause();
			}
		});
		playerDisplayer.controller.setEventHandlerForForward(()->vm.forward());
		playerDisplayer.controller.setEventHandlerForRewind(()->vm.rewind());
		playerDisplayer.currentTime.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
				vm.setCurrentTime((int)playerDisplayer.currentTime.get());
				vm.setValues((int)playerDisplayer.currentTime.get());
			}
		});
		aileronName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				joystickDisplayer.aileronName.setValue(aileronName.getValue());
				namesAttribute.put(aileronName,aileronName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		elevatorName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				joystickDisplayer.elevatorName.setValue(elevatorName.getValue());
				namesAttribute.put(elevatorName,elevatorName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		rudderName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				joystickDisplayer.rudderName.setValue(rudderName.getValue());
				namesAttribute.put(rudderName,rudderName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		throttleName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				joystickDisplayer.throttleName.setValue(throttleName.getValue());
				namesAttribute.put(throttleName,throttleName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		altimeterName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.altimeterName.setValue(altimeterName.getValue());
				namesAttribute.put(altimeterName,altimeterName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		airspeedName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.airspeedName.setValue(airspeedName.getValue());
				namesAttribute.put(airspeedName,airspeedName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		headingName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.headingName.setValue(headingName.getValue());
				namesAttribute.put(headingName,headingName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		rollName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.rollName.setValue(rollName.getValue());
				namesAttribute.put(rollName,rollName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		pitchName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.pitchName.setValue(pitchName.getValue());
				namesAttribute.put(pitchName,pitchName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		yawName.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				tableClocks.yawName.setValue(yawName.getValue());
				namesAttribute.put(yawName,yawName.getValue());
				//attributesView.controller.applySetting(namesAttribute.values());
			}
		});
		attributesView.selectedParameter.addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
				if(vm!=null){
					String str=vm.getMostCorrelated(attributesView.selectedParameter.getValue());
					if(str!=null)
						attributesView.correlatedPrameter.setValue(str);
				}
			}
		});
	}

	public void loadClassFile(){
		FileChooser fc=new FileChooser();
		fc.setTitle("open anomaly detector class file");
		fc.setInitialDirectory(new File("./bin"));
		fc.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter
				("class file", "*.class")
				);
		File chooser=fc.showOpenDialog(null);
		if(chooser!=null) {
			if(vm.setAnomalyDetector(chooser.getParent(),chooser.getName())) {
				//file is valid
				Alert message=new Alert(Alert.AlertType.CONFIRMATION);
				message.setContentText("well done!"
						+ " \n the anomaly detector class file has been saved in the system");
				message.show();
			}
			else {
				Alert message=new Alert(Alert.AlertType.ERROR);
				message.setContentText("oops!"
						+ " \n please choose a valid class file that implements the interface TimeSeriesAnomalyDetector");
				message.show();
			}
		}
		else {
			Alert message=new Alert(Alert.AlertType.ERROR);
			message.setContentText("oops!"
					+ " \n please choose a class file");
			message.show();
		}
		//TimeSeriesAnomalyDetector
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
				vm.applyValuesMinMax();
				playerDisplayer.setRate(vm.getRate());
				vm.applyNames();
			}
			attributesView.controller.applySetting(namesAttribute.values());
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
				playerDisplayer.setLength(vm.getLength());
				Alert message=new Alert(Alert.AlertType.CONFIRMATION);
				message.setContentText("well done!"
						+ " \n your csv file was saved in the system");
				message.show();
				csvTrainFilePath.set(chooser.getPath());
			}
		}
		joystickDisplayer=new JoystickDisplayer();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
