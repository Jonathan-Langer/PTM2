package view.player;

import java.io.IOException;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class Player extends AnchorPane {
	
	public StringProperty speedPlayer;
	public StringProperty csvTestFilePath;
	public DoubleProperty currentTime;
	public PlayerDisplayerController controller;
	int length;
	boolean sliderMoved=true;

	public void setLength(int l){length=l;}

	public Player() {
		super();
		try {
			speedPlayer=new SimpleStringProperty();
			FXMLLoader fxl = new FXMLLoader();
			AnchorPane toDisplay = fxl.load(getClass().getResource("Player.fxml").openStream());
			controller = fxl.getController();
			controller.options.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
					speedPlayer.setValue(controller.options.valueProperty().getValue());
				}
			});

			speedPlayer.set(controller.options.getValue());
			csvTestFilePath=controller.csvTestFilePath;
			//currentTime=controller.timeLine.valueProperty();
			currentTime=new SimpleDoubleProperty();
			currentTime.addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					double minValue=controller.timeLine.getMin();
					double maxValue=controller.timeLine.getMax();
					sliderMoved=false;
					controller.timeLine.valueProperty().setValue(minValue+((maxValue-minValue)*currentTime.getValue()/length));
				}
			});
			controller.timeLine.valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
					if(sliderMoved) {
						double minValue = controller.timeLine.getMin();
						double maxValue = controller.timeLine.getMax();

						currentTime.setValue(
								(Math.round((Math.round(controller.timeLine.valueProperty().getValue()) - minValue)
										* length
										/ (maxValue - minValue))));
					}
					else
						sliderMoved=true;
				}
			});
			this.getChildren().add(toDisplay);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}