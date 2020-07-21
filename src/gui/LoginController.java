package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

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
		
		System.out.println(user + " - " + password);
		
		if (password.contentEquals("123")){
							
			System.out.println("Login ok");
		}

	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

	



}







