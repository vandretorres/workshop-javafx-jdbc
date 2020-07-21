package application;

import java.io.IOException;

import gui.util.Alerts;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//new ws
public class Main extends Application {
	
	private static Scene mainScene;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPlane = loader.load();
			
			// configura ScrollPane para seajustar ao tamanho de tela
			scrollPlane.setFitToHeight(true);
			scrollPlane.setFitToWidth(true);			
			mainScene = new Scene(scrollPlane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();

			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Scene getMainScene() {
		
		return mainScene;
	}
	
	
		
	

	public static void main(String[] args) {
		launch(args);
	} 
	
	
	
}
