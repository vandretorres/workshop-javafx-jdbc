package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;

public class DepartmentListController implements Initializable {



	private DepartmentService service;

	@FXML
	private TableView<Department> tableViewDepartment;

	@FXML
	private TableColumn<Department, Integer> tableColumnId;

	@FXML
	private TableColumn<Department, String> tableColumnName;

	@FXML
	private Button btNew;

	private ObservableList<Department> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		// Retorna stage do evento  
		Stage parentStage = Utils.currentStage(event);
		
		createDialogForm("/gui/DepartmentForm.fxml",parentStage );	}


	// metodo usado para desacoplar chamada do objeto DepartmentSErvice com o atributo sevice.
	public void setDepartmentService(DepartmentService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {

		//Iniciar comportamento das colunas da tabela
		// metodo setCellValueFactory liga as colunas  com os atributos da classe Department
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		// comandos abaixo fazem com que a tabela seja dimensionada com o tamanho a stage principal
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
	}



	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// atribui resultado da lista de Departamentos com a lista FX ObservableList
		//Observable list é entrada pradrão para populara tabelas no FX
		List<Department> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewDepartment.setItems(obsList);
	}


	
	//Evento para capturar doubleClick das linhas da tabela
	// ideia é permitir atualização dos dados apartir do clique duplo
    @FXML
	public void onTableViewDoubleClick() {

		tableViewDepartment.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
		    @Override
		    public void handle(MouseEvent event)
		    {
		        if(event.getClickCount()>1)
		        {
		            System.out.println("double clicked!");
		        }
		    }
		});		
	}
    
    
    
    
    // método criado para chamar tela do tipo Dialog que será apresentado por cima da tela pai.
    //Parametro absolute Name informa qual FXML será aberto
    //Parametro parentStage retorna o Stage da tela principal para que o dialog seja aberto por cima.
     
    private void createDialogForm(String absoluteName, Stage parentStage) {
    	
    	try {
    		
    		// como para carregar aview DepartmentForm.fxml
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// Carrega view do tipo Pane ( superclasse da AnchorPane)
    		Pane pane = loader.load();
			
    		//instancia novo Stage para carregar a nova View			
			Stage dialogStage = new Stage();
			
			dialogStage.setTitle("Enter Department Data");
			
			// Cria nova Cena para carregar Pane e inclui nova cena no novo Stage
			// Pane
			//	+ Scene
			//		+ Stage
			dialogStage.setScene(new  Scene(pane));
			
			
			//desabilita opção de redimencionamento da tela
			dialogStage.setResizable(false);
			
			//informa qual é a stage parent
			dialogStage.initOwner(parentStage);
			
			// proibe que usuário cliente na stage principal enquanto dialog estiver aberta
			dialogStage.initModality(Modality.WINDOW_MODAL);
			
			// Shows this stage and waits for it to be hidden (closed) before returning to the caller.
			dialogStage.showAndWait(); 
			
    	}catch (IOException e) {
    		Alerts.showAlert("IO Exception", "Error Loading View", e.getMessage(), AlertType.ERROR);
			// TODO: handle exception
		}
    	
    }
	
}