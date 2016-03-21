package org.app.view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.annotation.PostConstruct;

import org.app.config.SpringApplicationConfig;
import org.app.dto.PersonDto;
import org.app.model.Person;
import org.app.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * PersonOverviewController main layout controller implementation.
 */
@Component
public class PersonOverviewController
{
	@FXML private TableView<PersonDto> personTable;
	@FXML private TableColumn<PersonDto, String> firstNameColumn;
	@FXML private TableColumn<PersonDto, String> lastNameColumn;
	@FXML private Label firstNameLabel;
	@FXML private Label lastNameLabel;
	@FXML private Label streetLabel;
	@FXML private Label cityLabel;
	@FXML private Label postalCodeLabel;
	@FXML private Label birthdayLabel;

	@Autowired
	private PersonService personService;

	@Autowired
	@Qualifier("editDialog")
	private SpringApplicationConfig.View editDialogView;

	private ObservableList<PersonDto> data = FXCollections.observableArrayList();
	private Scene dialogScene;

	/* The constructor is called before the initialize() method. */
	public PersonOverviewController() {}

	/**
	 * Initializes the controller class.
	 * Is automatically called after the FXML file has been loaded.
	 */
	@FXML
	private void initialize()
	{
		// sets value factory for cells to be displayed in the person table
		firstNameColumn.setCellValueFactory(cell -> cell.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cell -> cell.getValue().lastNameProperty());

		// reset person details
		showPersonDetails(null);

		// add listener to selected item properties, executes lambda expression whenever the property is selected
		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Loads data from the database, converts it into appropriate format, and populated table with data.
	 * Is called after all dependencies are injected.
	 */
	@PostConstruct
	public void init()
	{
		// load entities from the DB and convert them into DTO
		List<Person> entities = personService.findAll();
		List<PersonDto> dtos = entities.stream().map(person -> new PersonDto(person)).collect(Collectors.toList());
		this.data.addAll(dtos);

		// populate table with data
		personTable.setItems(getData());
	}

	/**
	 * Handles new operation.
	 * Is called when a user clicks "new..." button.
	 * Opens a dialog to enter new person details.
	 * Returns created {@link PersonDto} object.
	 */
	@FXML
	private Optional<PersonDto> handleCreatePerson()
	{
		PersonDto newPerson = new PersonDto();
		if (showPersonEditDialog(newPerson))
			getData().add(newPerson);

		return Optional.ofNullable(newPerson);
	}

	/**
	 * Handles edit operation.
	 * Is called when a user clicks "edit..." button.
	 * Opens a dialog to modify existing person details.
	 * Returns modified {@link PersonDto} object.
	 */
	@FXML
	private Optional<PersonDto> handleEditPerson()
	{
		PersonDto selectedPerson = personTable.getSelectionModel().getSelectedItem();
		if (selectedPerson != null)
		{
			if (showPersonEditDialog(selectedPerson))
				showPersonDetails(selectedPerson);
		}
		else
		{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Edit error");
			alert.setHeaderText("No Person selected");
			alert.setContentText("Please select a person to edit.");
			alert.showAndWait();
		}
		return Optional.ofNullable(selectedPerson);
	}

	/**
	 * Handles delete operation.
	 * Returns deleted {@link PersonDto} object.
	 */
	@FXML
	private Optional<PersonDto> handleDeletePerson()
	{
		TableViewSelectionModel<PersonDto> item = personTable.getSelectionModel();
		if ((item != null) && personService.delete(item.getSelectedItem().getId())) {
			return Optional.ofNullable(personTable.getItems().remove(item.getSelectedIndex()));
		}

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Delete error");
		alert.setHeaderText("No Person selected");
		alert.setContentText("Please select a person to delete.");
		alert.showAndWait();
		return Optional.empty();
	}

	/**
	 * Populates person details fields {@link PersonDto} info.
	 * Clears person details fields if the person is null.
	 * @param person
	 */
	private void showPersonDetails(PersonDto person)
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
	public boolean showPersonEditDialog(PersonDto person)
	{
		// load FXML and create new dialog stage
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Person");
		dialogStage.initModality(Modality.WINDOW_MODAL);

		AnchorPane editDialog = (AnchorPane) editDialogView.getView();
		if (dialogScene == null)
			dialogScene = new Scene(editDialog);
		else
			dialogScene.setRoot(editDialog);

		dialogStage.setScene(dialogScene);

		// get dialog controller and set Person object
		PersonEditDialogController controller = (PersonEditDialogController) editDialogView.getController();
		controller.setDialogStage(dialogStage);
		controller.setPerson(person);

		dialogStage.showAndWait();

		// save/update a Person if ok button was clicked
		boolean okClicked = controller.isOkClicked();
		if (okClicked)
		{
			Person savedPerson = personService.saveOrUpdate(new Person(person.getId(), person.getFirstName(), person.getLastName(),
							person.getStreet(), person.getPostalCode(), person.getCity(), person.getBirthday()));
			person.setId(savedPerson.getId());
		}
		return okClicked;
	}

	/**
	 * Returns an observable list of {@link PersonDto}s.
	 * @param peson
	 */
	public ObservableList<PersonDto> getData()
	{
		return data;
	}
}