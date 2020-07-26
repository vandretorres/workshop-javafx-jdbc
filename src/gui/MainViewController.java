package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;

public class MainViewController implements Initializable{

	@FXML
	private MenuItem menuItemSeller;
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;


	@FXML
	public void onMenuItemSellerAction() {

		// M�todo LoadView � respons�vel por invocar a tela passada por parametro
				// foi criado m�todo injetado com express�o lambda para passar al�m do parametro uma fun��o que ser� executada 
				// de modo gen�rico na fun�ao loadViewData.
				// Desta forma a lista de departamentos ser� populada ap�s a abertura da tela.
				loadView("/gui/SellerList.fxml",(SellerListController controller) -> {
					
					controller.setSellerService(new SellerService());
					controller.updateTableView();
				});

	}

	@FXML
	public void onMenuItemDepartmentAction() {

		// M�todo LoadView � respons�vel por invocar a tela passada por parametro
		// foi criado m�todo injetado com express�o lambda para passar al�m do parametro uma fun��o que ser� executada 
		// de modo gen�rico na fun�ao loadViewData.
		// Desta forma a lista de departamentos ser� populada ap�s a abertura da tela.
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller) -> {
			
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();			
		});

	}

	@FXML
	public void onMenuItemAboutAction() {

		System.out.println("onMenuItemAboutAction");

		//como a tela about n�o tem execu��o adicional de m�todo
		// � passado como parametro uma fun�ao sem a��o
		loadView("/gui/About.fxml", x -> {});

	}



	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	
	// para permitir a inje��o de met�do dentro do parametro � utilizada a fun��o generica consumir
	// al�m do m�todo consumer � preciso transformar o m�todo em generico do tipo T
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {


		try {


			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVBox = loader.load();

			Scene mainScene = Main.getMainScene();

			VBox mainVBox = (VBox) ((ScrollPane) mainScene.getRoot()).getContent();

			Node mainMenu = mainVBox.getChildren().get(0);

			mainVBox.getChildren().clear();

			mainVBox.getChildren().add(mainMenu);

			mainVBox.getChildren().addAll(newVBox.getChildren());
			
			
			// variavel do tipo T recebe controlador e inicia m�todo injetado do tipo T
			T controller = loader.getController();
			initializingAction.accept(controller);

			
 
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Loading view", e.getMessage(), AlertType.ERROR);
		}
	}


}







