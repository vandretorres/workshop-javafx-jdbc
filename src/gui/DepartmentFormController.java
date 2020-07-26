package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

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
import model.exceptions.ValidationException;
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
			
		}catch (ValidationException e) {
			setErrorMessages(e.getErrors());
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

	
	
	// Cria um objeto Deparment com os dados informados pelo usu�rio
	private Department getFormData() {

		//Cria objeto ValidationException lan�ando mensagem gen�rica para classe superior RunTimeException
		ValidationException exception = new ValidationException("Validation error");		
		
		Department obj = new Department();
		obj.setId(Utils.tryParsetoInt(txtId.getText()));			
		
		
		/*
		 * Comandos abaixo ir�o validar se campo Name do form Department est� vazio
		 * Caso esteja vazio o m�todo addError ir� adicionar a chave com o nome do campo e a descri��o do erro
		 * 
		 * Em seguida se a Lista Map dot ipo Exception possui registro para poder lan�ar a exce��o parao  m�todo que fez a chamda do getFormData ( onButtonSaveActiion)
		 * 
		 */
		if ( txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		obj.setName(txtName.getText());
		
		if ( exception.getErrors().size() > 0) {
			throw exception;
		}		
		return obj;	
	}
	
	
	// Este m�todo ir� inscrever os listeners na lista
	//Qualquer classe que queira sre notificado da altrea��o da tabela Department dever� impelmentar a interface
	//DatachangeListener
	public void subscribeDataChangeListener(DataChangeListener listener) {
		
		dataChangeListeners.add(listener);
		
	}
	
	
	/*
	 * M�todo lan�a a exce��o na label labelErrorName
	 * comando Set ir� armazenar as chaves da lista Map	 
	 */
	private void setErrorMessages(Map<String , String> errors) {
		
		Set<String> fields = errors.keySet();
		
		if(fields.contains("name")){
			
			labelErrorName.setText(errors.get("name"));
		}
		
		
	}

	
}
