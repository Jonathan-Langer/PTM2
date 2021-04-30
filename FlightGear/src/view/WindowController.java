package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
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
import model.AttributeSettings;
import model.ListOfAttributes;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
//import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class WindowController extends Observable implements Initializable,Observer{
	
	String csvFilePath;
	String txtFilePath;
	ListOfAttributes attributes;
	@FXML
	private ComboBox<String> options;
	
	public WindowController() {
		txtFilePath=new File("resources/last_setting.txt").getAbsolutePath();
		attributes=new ListOfAttributes(txtFilePath);
		attributesView=new AttributesViewDisplayer();
	}

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
		
		@FXML
		private AttributesViewDisplayer attributesView;
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
	
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
			csvFilePath=chooser.getPath();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list=FXCollections.observableArrayList
				("0.25","0.5","0.75","1","1.25","1.5","1.75","2");
		options.setItems(list);
		if(options.getValue()==null)
			options.setValue("1");
		attributesView.loadAttributesToListView(attributes);
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
				txtFilePath=new File("resources/last_setting.txt").getAbsolutePath();
			}
			else {
				txtFilePath=chooser.getPath();
				File lastSetting=new File(new File("resources/last_setting.txt").getAbsolutePath());
				File currentAttributes=new File(txtFilePath);
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
			attributes=new ListOfAttributes(txtFilePath);
			
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
	
	/*public void joystickOnMouseDrag(MouseEvent event) {
		if(this.manual.isSelected()) {
			if (event.getX() <= 100 && event.getX() >= -100)
				if (event.getY() <= 100 && event.getY() >= -100) {
					joystick.setCenterX(event.getX());
					joystick.setCenterY(event.getY());
					statlabel.setText("(Alieron = " +event.getX()/100 + " Elevator = " + event.getY()/100 + ")");
					this.elevatorVal.set(event.getY()/-100);
					this.alieronVal.set(event.getX()/100);
					this.vm.elevatorChanged();
					this.vm.aileronChanged();
			}
		}
		else this.statlabel.setText(MCL);
	}
	
	public void joystickOnMouseRelease(MouseEvent event) {
		joystick.setCenterX(0);
		joystick.setCenterY(0);
	}*/
	private void loadAttributesToListView() {
		list.removeAll(list);
		list.addAll(attributes.getAttributesNames());
		attributesView.getItems().addAll(list);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
}
