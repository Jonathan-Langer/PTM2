package view;

import java.io.File;

import javafx.stage.FileChooser;

public class WindowController {
	
	String csvFileName;
	
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
=======
import java.util.ArrayList;
import org.lwjgl.input.Controller;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import java.net.URL;
import java.util.ResourceBundle;

import org.lwjgl.input.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class WindowController implements Initializable{
    @FXML GridPane _ROOT;
    @FXML ComboBox<String> _CONTROLLERS;
    @FXML VBox _AXISBOX;
    @FXML TilePane _BUTTONSGRID;
    @FXML ImageView _CAMERAVIEW;
    FXController selected = null;
    @Override public void initialize(URL arg0, ResourceBundle arg1) {
        try{
            Controllers.create();
            for(int i = 0; i < Controllers.getControllerCount(); i++){
                this._CONTROLLERS.getItems().addAll(Controllers.getController(i).getName());
            }
            new Thread(new Runnable(){
                @Override
                public void run() {
                    while(true){
                        _CAMERAVIEW.setImage(new Image("http://192.168.1.171:8080/shot.jpg"));
                    }
                }
            },"CameraThread").start();;
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML private void onControllerSelectionChanged(){
        this._AXISBOX.getChildren().clear();
        if(this.selected != null){
            this.selected.stopThread();
        }
        this.selected = new FXController(Controllers.getController(this._CONTROLLERS.getSelectionModel().getSelectedIndex()));
        new Thread(this.selected, "Controller thread.").start();
        for(int i = 0; i < this.selected.getController().getAxisCount(); i++){
            ProgressBar axisValue = new ProgressBar();
                axisValue.setPadding(new Insets(8));
                axisValue.progressProperty().bind(this.selected.getAxisProperty().get(i).divide(2));
            TitledPane axisName = new TitledPane();
                axisName.setText("AXIS: [ " + this.selected.getController().getAxisName(i) + " ]");
                axisName.setCollapsible(false);
                axisName.setContent(axisValue);
            this._AXISBOX.getChildren().add(axisName);
        }
        this._BUTTONSGRID.getChildren().clear();
        this._BUTTONSGRID.setPrefColumns(4);
        for(int i = 0; i < this.selected.getController().getButtonCount(); i++){
            ToggleButton buttonState = new ToggleButton("[O]");
                buttonState.setPadding(new Insets(8));
                buttonState.setDisable(true);
                buttonState.selectedProperty().bind(this.selected.getButtonProperty().get(i));
            TitledPane buttonName = new TitledPane();
                buttonName.setText("[ " + this.selected.getController().getButtonName(i) + " ]");
                buttonName.setCollapsible(false);
                buttonName.setContent(buttonState);
            this._BUTTONSGRID.getChildren().add(buttonName);
        }
    }
}
