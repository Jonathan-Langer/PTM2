package view.joystick;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import model.ListOfAttributes;

public class JoystickController implements Initializable {
	
	ListOfAttributes attributes;
	
	@FXML
	Slider rudder;
	
	@FXML
	Slider throttle;
	
	public JoystickController() {
		File lastSetting=new File(new File("resources/last_setting.txt").getAbsolutePath());
		if(lastSetting.exists())
			this.attributes=new ListOfAttributes(lastSetting.getAbsolutePath());
		else
			this.attributes=new ListOfAttributes();
	}
	public JoystickController(ListOfAttributes attributes) {
		this.attributes=attributes;
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for(String name:this.attributes.getList().keySet()) {
			if(attributes.getList().get(name).getColInCSV()==2) {
				rudder.setMin(attributes.getList().get(name).getMinValue());
				rudder.setMax(attributes.getList().get(name).getMaxValue());
			}
			if(attributes.getList().get(name).getColInCSV()==6) {
				throttle.setMin(attributes.getList().get(name).getMinValue());
				throttle.setMax(attributes.getList().get(name).getMaxValue());
			}
		}
	}
}
