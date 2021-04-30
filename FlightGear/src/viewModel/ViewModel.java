package viewModel;

import java.util.Observable;
import java.util.Observer;

import javafx.beans.property.StringProperty;

public class ViewModel extends Observable implements Observer{

	
	public StringProperty txtFilePath;
	public StringProperty csvFilePath;
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
