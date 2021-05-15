package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

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
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Slider;
//import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class WindowController implements Initializable,Observer{
	
	StringProperty csvFilePath=new SimpleStringProperty();
	StringProperty txtFilePath=new SimpleStringProperty();
	ListOfAttributes attributes;
	ViewModel vm;

	public WindowController() {
		txtFilePath.set(new File("resources/last_setting.txt").getAbsolutePath());
		attributes=new ListOfAttributes(txtFilePath.get());
		attributesView=new AttributesViewDisplayer(attributes);
		joystickDisplayer=new JoystickDisplayer();
		playerDisplayer = new Player();
		tableClocks=new TableClocksDisplayer();
		joystickDisplayer=new JoystickDisplayer();
	}
	
	public void setViewModel(ViewModel vm) {
		this.vm=vm;
		vm.csvFilePath.bind(playerDisplayer.csvFilePath);
		vm.txtFilePath.bind(this.txtFilePath);
		vm.altitudeValue.bind(tableClocks.altitudeValue);
		vm.pitchValue.bind(tableClocks.pitchValue);
		vm.rollValue.bind(tableClocks.rollValue);
		vm.rudderValue.bind(tableClocks.rudderValue);
		vm.speedbrakeValue.bind(tableClocks.speedbrakeValue);
		vm.yawValue.bind(tableClocks.yawValue);
		vm.rudderValue.bind(joystickDisplayer.rudderValue);
		vm.throttleValue.bind(joystickDisplayer.throttleValue);
		vm.aileronValue.bind(joystickDisplayer.aileronValue);
		vm.elevatorsValue.bind(joystickDisplayer.elevatorsValue);
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
	/*
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
			csvFilePath.set(chooser.getPath());
	}*/
		
	
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
			attributes=new ListOfAttributes(txtFilePath.get());
			attributesView.loadAttributesToListView(attributes);
			joystickDisplayer=new JoystickDisplayer();
		}
	}
	
	private boolean checkValidateSettingFile(String txtFile) {
		HashMap<Integer, Boolean> cellsAreApeared=new HashMap<>();
		cellsAreApeared.put(0, false);
		cellsAreApeared.put(1, false);
		cellsAreApeared.put(2, false);
		cellsAreApeared.put(3, false);
		cellsAreApeared.put(5, false);
		cellsAreApeared.put(6, false);
		cellsAreApeared.put(14, false);
		cellsAreApeared.put(15, false);
		cellsAreApeared.put(16, false);
		cellsAreApeared.put(17, false);
		cellsAreApeared.put(18, false);
		cellsAreApeared.put(19, false);
		cellsAreApeared.put(39, false);
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
