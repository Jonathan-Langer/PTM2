package model;

import java.util.Observable;

public class AttributeSettings extends Observable {
	Integer colInCSV;
	float minValue,maxValue;
	/*float latitude_deg;
	float longitude_deg;
	float altitude_ft;
	float alttitude;
	float speed;
	float aileron;
	float elevator;
	float rudder;
	float flaps;
	float thorttle;
	//float engine_pump;
	//float electric_pump;
	//float external_power;
	//float APU_Generator;
	float yaw_deg;
	float roll_deg;
	float pitch_deg;
	float heading_deg;
	int rate;
	//file name regular flight
	//file name algorithm detections
	int port;
	int IP;*/
	//need to add yaw in the settings
	
	public AttributeSettings(String[] data) {
		if(data.length==3) {
			this.colInCSV = Integer.parseInt(data[0]);
			this.minValue = Float.parseFloat(data[1]);
			this.maxValue = Float.parseFloat(data[2]);
		}
	}

	public Integer getColInCSV() {
		return colInCSV;
	}

	public void setColInCSV(Integer colInCSV) {
		this.colInCSV = colInCSV;
	}

	public float getMinValue() {
		return minValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}

	public float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(float maxValue) {
		this.maxValue = maxValue;
	}
}
