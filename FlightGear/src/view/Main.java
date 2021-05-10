package view;
	
import javafx.application.Application;
import javafx.stage.Stage;
import viewModel.ViewModel;
import view.WindowController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxl = new FXMLLoader();
			BorderPane root = fxl.load(getClass().getResource("Window.fxml").openStream());
			Scene scene = new Scene(root,1500,800);
			viewModel.ViewModel vm=new ViewModel();
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			//WindowController wc = fxl.getController();
			//wc.setViewModel(vm);
			//vm.addObserver(wc);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
