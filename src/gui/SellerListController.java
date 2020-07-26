package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Seller;
import model.services.SellerService;

public class SellerListController implements Initializable , DataChangeListener {



	private SellerService service;

	@FXML
	private TableView<Seller> tableViewSeller;

	@FXML
	private TableColumn<Seller, Integer> tableColumnId;

	@FXML
	private TableColumn<Seller, String> tableColumnName;
	
	
	@FXML
	private TableColumn<Seller, String> tableColumnEmail;
	
	@FXML
	private TableColumn<Seller, Date> tableColumnBirthdate;
	
	@FXML
	private TableColumn<Seller, Double> tableColumnBaseSalary;
	
	
	@FXML 
	private TableColumn<Seller, Seller> tableColumnEDIT; 

	@FXML
	private TableColumn<Seller, Seller> tableColumnREMOVE; 

	@FXML
	private Button btNew;

	private ObservableList<Seller> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		// Retorna stage do evento  
		Stage parentStage = Utils.currentStage(event);

		//cria objeto Seller para injetar na classe SellerForm
		Seller obj = new Seller();		
		createDialogForm(obj, "/gui/SellerForm.fxml",parentStage );	

	}


	// metodo usado para desacoplar chamada do objeto SellerSErvice com o atributo sevice.
	public void setSellerService(SellerService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {

		//Iniciar comportamento das colunas da tabela
		// metodo setCellValueFactory liga as colunas  com os atributos da classe Seller
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		tableColumnEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		tableColumnBirthdate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		// Formata coluna FXML para tipo data
		Utils.formatTableColumnDate(tableColumnBirthdate, "dd/MM/yyyy");
		tableColumnBaseSalary.setCellValueFactory(new PropertyValueFactory<>("baseSalary"));
		// Formata coluna FXML para tipo Double		
		Utils.formatTableColumnDouble(tableColumnBaseSalary, 2);

		// comandos abaixo fazem com que a tabela seja dimensionada com o tamanho a stage principal
		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewSeller.prefHeightProperty().bind(stage.heightProperty());
	}



	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Service was null");
		}

		// atribui resultado da lista de Departamentos com a lista FX ObservableList
		//Observable list é entrada pradrão para populara tabelas no FX
		List<Seller> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewSeller.setItems(obsList);

		initEditButtons();
		initRemoveButtons();
	}



	//Evento para capturar doubleClick das linhas da tabela
	// ideia é permitir atualização dos dados apartir do clique duplo
	@FXML
	public void onTableViewDoubleClick() {

		tableViewSeller.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent event)
			{
				if(event.getClickCount()>1)
				{
					System.out.println("double click");
				}
			}
		});		
	}




	// método criado para chamar tela do tipo Dialog que será apresentado por cima da tela pai.
	//Parametro absolute Name informa qual FXML será aberto
	//Parametro parentStage retorna o Stage da tela principal para que o dialog seja aberto por cima.

	private void createDialogForm(Seller obj, String absoluteName, Stage parentStage) {

		try {

			// como para carregar aview SellerForm.fxml
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			// Carrega view do tipo Pane ( superclasse da AnchorPane)
			Pane pane = loader.load();

			//recuperar o controlador do form ( SellerFormController )
			SellerFormController controller  = loader.getController();
			//Injeta objeto Seller no formulário
			controller.setSeller(obj);

			//Injeta objeto DeparmentSErvice no método setDeparmentService que será utilizado para persistir os dados
			controller.setSellerService(new SellerService());

			//classe está se inscrevendo para receber o evento da classe DeparmentFormController
			// toda vez o evento for acionado o metodo onDataChange será executado
			controller.subscribeDataChangeListener(this);

			//atualiza os campos os valores do objeto
			controller.updateFormData();


			//instancia novo Stage para carregar a nova View			
			Stage dialogStage = new Stage();

			dialogStage.setTitle("Enter Seller Data");

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

	
	
	@Override
	public void onDataChange() {
		//toda vez que o evento for acionado ( listener DataChangeListener )
		// o metodo onDataChange será executado atualizando a tabela
		updateTableView();

	}



	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("edit");
			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(
						event -> createDialogForm(
								obj, "/gui/SellerForm.fxml",Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Seller, Seller>() {
			private final Button button = new Button("remove");
			@Override
			protected void updateItem(Seller obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}		
			
		});
	}

	
	private void removeEntity(Seller obj) {
		
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Do you want to remove depto " + obj.getName());
		
		if( result.get() == ButtonType.OK) {
			if(service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}catch (DbIntegrityException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
		
		
	}


}