package org.app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.app.model.Person;
import org.app.view.PersonEditDialogController;
import org.app.view.PersonOverviewController;

public class MainApp extends Application
{
	private Stage primaryStage;
	private BorderPane rootLayout;

	private ObservableList<Person> data = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage)
	{
		this.data.addAll(generateData());
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");

		initRootLayout();
		showPersonOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout()
	{
		try
		{
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showPersonOverview()
	{
		try
		{
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Opens a dialog to edit details of the specified person.
	 * @param peson
	 */
	public boolean showPersonEditDialog(Person person)
	{
		try
		{
			// load FXML and create new dialog stage
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane editDialog = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene dialogScene = new Scene(editDialog);
			dialogStage.setScene(dialogScene);

			// get dialog controller and set Person object
			PersonEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setPerson(person);

			dialogStage.showAndWait();
			return controller.isOkClicked();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	public ObservableList<Person> getData()
	{
		return data;
	}

	public List<Person> generateData()
	{
		return Arrays.asList(
				new Person("Hans", "Muster"),
				new Person("Ruth", "Mueller"),
				new Person("Heinz", "Kurz"),
				new Person("Cornelia", "Meier"),
				new Person("Werner", "Meyer"),
				new Person("Lydia", "Kunz"),
				new Person("Anna", "Best"),
				new Person("Stefan", "Meier"),
				new Person("Martin", "Mueller"));
	}
}