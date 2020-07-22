package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
	public void onBtNewAction() {
		System.out.println("onBtNewAction");
	}


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
	
}