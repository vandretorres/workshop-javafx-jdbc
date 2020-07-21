package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class LoginController implements Initializable{

	
	@FXML
	private TextField textFieldUser;
	
	@FXML
	private TextField textFieldPassword;
	
	@FXML
	private Button buttonConfirm;
	
	
	@FXML
	public void onButtonConfirm() {
		
		String user =  textFieldUser.getText();
		
		String password = textFieldPassword.getText();
		
		if(user != null && password.equals("1234")) {
			
		
		
			Main mvc = new Main();
			
			Stage stage = (Stage) Main.getMainScene().getWindow();
			
			mvc.loadMainScene(stage);				
		}
		else {
			Alerts.showAlert("Error Authentication", null, "Login and password does not match!", AlertType.ERROR);
		}

	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

	



}







