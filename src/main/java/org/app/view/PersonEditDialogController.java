package org.app.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.apache.commons.lang3.StringUtils;
import org.app.model.PersonDto;
import org.springframework.stereotype.Component;

/**
 * Implements a dialog to edit Person details.
 * @author zollder
 */
@Component
public class PersonEditDialogController
{
	@FXML private TextField firstNameField;
	@FXML private TextField lastNameField;
	@FXML private TextField streetField;
	@FXML private TextField cityField;
	@FXML private TextField postalCodeField;
	@FXML private TextField birthDayField;

	private Stage dialogStage;
	private PersonDto person;
	private boolean okClicked;

	/* The constructor is called before the initialize() method. */
	public PersonEditDialogController() {}
	/**
	 * Initializes the controller class.
	 * Is automatically called once FXML is loaded.
	 */
	@FXML
	private void initialize() {}

	/**
	 * OK button handler.
	 * Validates form fields and, if successful, updates person details.
	 */
	@FXML
	private void okButtonHandler()
	{
		if (validateFormFields())
		{
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setCity(cityField.getText());
			person.setBirthday(LocalDate.parse(birthDayField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));

			okClicked = true;
			dialogStage.close();
		}
	}

	/**
	 * Cancel button handler.
	 * Close the dialog stage when clicked.
	 */
	@FXML
	private void cancelButtonHandler()
	{
		dialogStage.close();
	}

	/**
	 * Sets edit dialog stage.
	 * @param stage
	 */
	public void setDialogStage(Stage stage)
	{
		this.dialogStage = stage;
	}

	/**
	 * Populates dialog fields with details of the person to edit.
	 * @param person
	 */
	public void setPerson(PersonDto person)
	{
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		cityField.setText(person.getCity());
		birthDayField.setText(person.getBirthday().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
		birthDayField.setPromptText("dd-mm-yyyy");
	}

	/**
	 * Populates dialog fields with person values.
	 * @param person
	 */
	public boolean isOkClicked()
	{
		return this.okClicked;
	}

	/**
	 * Validates dialog form fields.
	 * Returns true if all field values are valid.
	 * Shows and error dialog otherwise.
	 */
	private boolean validateFormFields()
	{
		StringBuffer message = new StringBuffer();
		if (StringUtils.isBlank(firstNameField.getText()))
			message.append("Invalid first name field value.\n");
		if (StringUtils.isBlank(lastNameField.getText()))
			message.append("Invalid last name field value.\n");
		if (StringUtils.isBlank(streetField.getText()))
			message.append("Invalid street value.\n");
		if (StringUtils.isBlank(cityField.getText()))
			message.append("Invalid city field value.\n");

		try {
			Integer.parseInt(postalCodeField.getText());
		} catch (NumberFormatException ex) {
			message.append("Invalid postal code value.\n");
		}

		if (StringUtils.isBlank(birthDayField.getText()))
			message.append("Missing birth date.\n");
		else {
			try {
				LocalDate.parse(birthDayField.getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			} catch (DateTimeParseException ex) {
				message.append("Wrong date format. Supported format: dd-mm-yyyy\n");
			}
		}

		if (StringUtils.isBlank(message.toString()))
			return true;

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Form field error(s)");
		alert.setHeaderText("Invalid form field value(s).");
		alert.setContentText(message.toString());
		alert.showAndWait();

		return false;
	}






}