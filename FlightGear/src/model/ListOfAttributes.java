/*
 the txt file template is:
 user_attribute_name, real_attribute_name,min_val,max_val
 .....
 ip, the_ip_address
 port, the_number_of_port  
 rate, the_frequency_in_second
   */

package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

public class ListOfAttributes extends Observable{
	HashMap<String,AttributeSettings> attributesConnection;
	int port,rate;
	String ip;
	//the connection between the attribute name to its' setting
	
	public ListOfAttributes(String txtFile) {
		attributesConnection=new HashMap<>();
		try {
			BufferedReader read=new BufferedReader
					(new FileReader(new File(txtFile)));
			String line=null;
			while((line=read.readLine())!=null) {
				String[] data=line.split(",");
				if(data.length==4)
					attributesConnection.put
					(data[0],new AttributeSettings
							(new String[]{data[1],data[2],data[3]}));
				else {
					if(data.length==2) {
						Integer value= Integer.parseInt(data[1]);
						switch(data[0]) {
						case "ip":
							ip=data[1];
							break;
						case "port":
							port=value;
							break;
						case "rate":
							rate=value;
							break;
						}
					}
				}
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void addAttribute(String attributeName, AttributeSettings attributeSettings) {
		attributesConnection.put(attributeName, attributeSettings);
		setChanged();
		notifyObservers();
	}
}
