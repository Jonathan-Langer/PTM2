package model;

public class AttributeSettings {
	String name;
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
	
	public AttributeSettings(String[] data) {
		if(data.length==3) {
			this.name = data[0];
			this.minValue = Float.parseFloat(data[1]);
			this.maxValue = Float.parseFloat(data[2]);
		}
	}
	
}
