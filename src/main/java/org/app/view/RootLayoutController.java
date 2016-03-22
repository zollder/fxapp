package org.app.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.app.config.SpringApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * RootLayoutController implementation.
 * Implements behavior of top menu components.
 */
@Component
public class RootLayoutController
{
	@Autowired
	@Qualifier("rootLayoutView")
	private BorderPane rootLayoutView;

	@Autowired
	@Qualifier("personOverview")
	private SpringApplicationConfig.View personOverviewView;


	/* The constructor is called before the initialize() method. */
	public RootLayoutController() {}

	/**
	 * Initializes and starts root layout.
	 * Serves as the main application entry point
	 * Is called from the main thread with #start().
	 */
	public void showRootLayout(Stage primaryStage)
	{
		rootLayoutView.setCenter(personOverviewView.getView());
		Scene rootScene = new Scene(rootLayoutView);

		// configure primary stage
		primaryStage.setTitle("AddressApp");
		primaryStage.setScene(rootScene);
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	/** Top menu close button handler. */
	@FXML
	private void close()
	{
		Platform.exit();
	}

	/** Top menu about button handler. */
	@FXML
	private void about()
	{
		//TODO: add missing handler
	}
}