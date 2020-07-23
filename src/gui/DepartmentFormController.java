package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Department;
import model.services.DepartmentService;


public class DepartmentFormController implements Initializable {


	private Department entity;

	private DepartmentService service;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label labelErrorName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;


	//Action event vai pegar o evento da janela e permite controlar ela. usado no comando de fechar a janela
	@FXML 
	public void onBtSaveAction(ActionEvent event) {

		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}

		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
			
		}catch (DbException e) {
			Alerts.showAlert("Error Saving Objecto", null, e.getMessage(), AlertType.ERROR);
		}



	}





	@FXML 
	public void onBtCancelAction(ActionEvent event) {

		Utils.currentStage(event).close();
	}




	@Override
	public void initialize(URL uri, ResourceBundle rb) {

		InitializeNodes();

	}

	private void InitializeNodes() {

		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);

	}


	//seta objeto department no atributo entity ( objeto vem do DepartmentListController )
	public void setDepartment(Department entity) {
		this.entity = entity;
	}


	// atualiza os campos TXT com os valores do objeto
	public void updateFormData() {

		if ( entity == null) {
			throw new IllegalStateException("Entity was null");
		}

		txtId.setText(String.valueOf(entity.getId()));

		txtName.setText(entity.getName());

	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	private Department getFormData() {

		Department obj = new Department();

		obj.setId(Utils.tryParsetoInt(txtId.getText()));
		obj.setName(txtName.getText());

		return obj;		
	}


}
