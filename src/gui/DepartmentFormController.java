package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.services.DepartmentService;


public class DepartmentFormController implements Initializable {


	private Department entity;

	private DepartmentService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
			notifyDataChangeListener();
			Utils.currentStage(event).close();
			
		}catch (DbException e) {
			Alerts.showAlert("Error Saving Objecto", null, e.getMessage(), AlertType.ERROR);
		}



	}

	
	//metodo que emite o evento para outras classes
	private void notifyDataChangeListener() {
		for (DataChangeListener listener : dataChangeListeners) {			
			listener.onDataChange();			
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
	
	
    // Injeta objeto DepartmentService no Atributo Service .. chamado pelo DeparmentListController
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	// Cria um objeto Deparment com os dados informados pelo usuário
	private Department getFormData() {

		Department obj = new Department();

		obj.setId(Utils.tryParsetoInt(txtId.getText()));
		obj.setName(txtName.getText());

		return obj;		
	}
	
	// Este método irá inscrever os listeners na lista
	//Qualquer classe que queira sre notificado da altreação da tabela Department deverá impelmentar a interface
	//DatachangeListener
	public void subscribeDataChangeListener(DataChangeListener listener) {
		
		dataChangeListeners.add(listener);
		
	}

	
}
