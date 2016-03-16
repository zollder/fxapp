package org.app.view;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.app.MainApp;
import org.app.model.Person;

public class PersonOverviewController
{
	@FXML private TableView<Person> personTable;
	@FXML private TableColumn<Person, String> firstNameColumn;
	@FXML private TableColumn<Person, String> lastNameColumn;
	@FXML private Label firstNameLabel;
	@FXML private Label lastNameLabel;
	@FXML private Label streetLabel;
	@FXML private Label cityLabel;
	@FXML private Label postalCodeLabel;
	@FXML private Label birthdayLabel;

	private ObservableList<Person> data = FXCollections.observableArrayList();

	/* A hook to main */
	private MainApp mainApp;

	/* The constructor is called before the initialize() method. */
	public PersonOverviewController() {}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;

		// populate table with data
		personTable.setItems(getData());
	}

	/**
	 * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		this.data.addAll(generateData());

		// initialize person's table
		firstNameColumn.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cell -> cell.getValue().lastNameProperty());

		// reset person details
		showPersonDetails(null);

		// add listener to selected item properties, executes lambda expression whenever the property is selected
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Handles new operation.
	 * Is called when a user clicks "new..." button.
	 * Opens a dialog to enter new person details.
	 * Returns created {@link Person} object.
	 */
	@FXML
	private Optional<Person> handleCreatePerson()
	{
		Person newPerson = new Person();
		if (showPersonEditDialog(newPerson, mainApp.getPrimaryStage()))
			getData().add(newPerson);

		return Optional.ofNullable(newPerson);
	}

	/**
	 * Handles edit operation.
	 * Is called when a user clicks "edit..." button.
	 * Opens a dialog to modify existing person details.
	 * Returns modified {@link Person} object.
	 */
	@FXML
	private Optional<Person> handleEditPerson()
	{
		Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null)
		{
			if (showPersonEditDialog(selectedPerson, mainApp.getPrimaryStage()))
				showPersonDetails(selectedPerson);
			else
			{
				Alert alert = new Alert(AlertType.WARNING);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Edit error");
				alert.setHeaderText("No Person selected");
				alert.setContentText("Please select a person to edit.");
				alert.showAndWait();
			}
		}

		return Optional.ofNullable(selectedPerson);
	}

	/**
	 * Handles delete operation.
	 * Returns deleted {@link Person} object.
	 */
	@FXML
	private Optional<Person> handleDeletePerson()
	{
		int index = personTable.getSelectionModel().getSelectedIndex();
		if (index >= 0)
			return Optional.ofNullable(personTable.getItems().remove(index));

		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle("Delete error");
		alert.setHeaderText("No Person selected");
		alert.setContentText("Please select a person to delete.");
		alert.showAndWait();
		return Optional.empty();
	}

	/**
	 * Populates person details fields {@link Person} info.
	 * Clears person details fields if the person is null.
	 * @param person
	 */
	private void showPersonDetails(Person person)
	{
		if (person != null)
		{
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			streetLabel.setText(person.getStreet());
			cityLabel.setText(person.getCity());
			postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
			birthdayLabel.setText(person.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		}
		else
		{
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			streetLabel.setText("");
			cityLabel.setText("");
			postalCodeLabel.setText("");
			birthdayLabel.setText("");
		}
	}

	/**
	 * Opens a dialog to edit details of the specified person.
	 * @param peson
	 */
	public boolean showPersonEditDialog(Person person, Stage primaryStage)
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