package view;

import java.util.ArrayList;
import org.lwjgl.input.Controller;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;

public class FXController implements Runnable {
	
	  private boolean threadAlive = true;
	    private Controller controller;
	    private ArrayList<SimpleFloatProperty> axis = new ArrayList<>();
	    private ArrayList<SimpleBooleanProperty> buttons = new ArrayList<>();

	    public FXController(Controller controller){
	        this.controller = controller;
	        for(int i = 0; i < this.controller.getAxisCount(); i++){
	            this.axis.add(new SimpleFloatProperty(0));
	        }
	        for(int i = 0; i < this.controller.getButtonCount(); i++){
	            this.buttons.add(new SimpleBooleanProperty(false));
	        }
	    }

	    public ArrayList<SimpleFloatProperty> getAxisProperty(){
	        return this.axis;
	    }

	    public ArrayList<SimpleBooleanProperty> getButtonProperty(){
	        return this.buttons;
	    }

	    public Controller getController(){
	        return this.controller;
	    }

	    public void stopThread(){
	        this.threadAlive = false;
	    }

	    public boolean isThreadAlive(){
	        return this.threadAlive;
	    }

	    @Override
	    public void run() {
	        while(threadAlive){
	            for(int i = 0; i < this.controller.getAxisCount(); i++){
	                this.axis.get(i).set(this.controller.getAxisValue(i)+1);
	            }
	            for(int i = 0; i < this.controller.getButtonCount(); i++){
	                this.buttons.get(i).set(this.controller.isButtonPressed(i));
	            }
	            this.controller.poll();
	        }
	    }
}
