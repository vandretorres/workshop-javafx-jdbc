package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//new ws
public class Main extends Application {

	private static Scene mainScene;
	private static Stage mainStage;

	@Override
	public void start(Stage primaryStage) {
		
		mainStage = primaryStage;

		loadLogin(mainStage);
		//loadMainScene(primaryStage);

	}

	public static Scene getMainScene() {

		return mainScene;
	}



	public void loadMainScene(Stage primaryStage) {

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


	public void loadLogin(Stage primaryStage) {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Login.fxml"));
			AnchorPane anchorPane = loader.load();

			
			mainScene = new Scene(anchorPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			primaryStage.show();


		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	

	public static void main(String[] args) {
		launch(args);
	} 



}
