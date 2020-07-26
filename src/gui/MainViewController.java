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

		// Método LoadView é responsável por invocar a tela passada por parametro
				// foi criado método injetado com expressão lambda para passar além do parametro uma função que será executada 
				// de modo genérico na funçao loadViewData.
				// Desta forma a lista de departamentos será populada após a abertura da tela.
				loadView("/gui/SellerList.fxml",(SellerListController controller) -> {
					
					controller.setSellerService(new SellerService());
					controller.updateTableView();
				});

	}

	@FXML
	public void onMenuItemDepartmentAction() {

		// Método LoadView é responsável por invocar a tela passada por parametro
		// foi criado método injetado com expressão lambda para passar além do parametro uma função que será executada 
		// de modo genérico na funçao loadViewData.
		// Desta forma a lista de departamentos será populada após a abertura da tela.
		loadView("/gui/DepartmentList.fxml",(DepartmentListController controller) -> {
			
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();			
		});

	}

	@FXML
	public void onMenuItemAboutAction() {

		System.out.println("onMenuItemAboutAction");

		//como a tela about não tem execução adicional de método
		// é passado como parametro uma funçao sem ação
		loadView("/gui/About.fxml", x -> {});

	}



	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub

	}

	
	// para permitir a injeção de metódo dentro do parametro é utilizada a função generica consumir
	// além do método consumer é preciso transformar o método em generico do tipo T
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
			
			
			// variavel do tipo T recebe controlador e inicia método injetado do tipo T
			T controller = loader.getController();
			initializingAction.accept(controller);

			
 
		}catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Loading view", e.getMessage(), AlertType.ERROR);
		}
	}


}







