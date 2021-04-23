package model;

import java.util.HashMap;
import java.util.Observable;

public class ListOfAttributes extends Observable{
	HashMap<String,AttributeSettings> attributesConnection;
	//the connection between the attribute name to its' setting
	
	public ListOfAttributes() {
		attributesConnection=new HashMap<>();
	}
	public void addAttribute(String attributeName, AttributeSettings attributeSettings) {
		attributesConnection.put(attributeName, attributeSettings);
		setChanged();
		notifyObservers();
	}
}
