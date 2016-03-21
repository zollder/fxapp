package org.app;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.app.config.SpringApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;

@Lazy
@SpringBootApplication
public class MainApp extends AbstractApplicationSupport
{
	@Autowired
	@Qualifier("rootLayout")
	private BorderPane rootLayout;

	@Autowired
	@Qualifier("personOverview")
	private SpringApplicationConfig.View personOverviewView;

	protected static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		context = SpringApplication.run(getClass(), getSavedArgs());
		context.getAutowireCapableBeanFactory().autowireBean(this);

		rootLayout.setCenter(personOverviewView.getView());
		Scene scene = new Scene(rootLayout);

		// configure primary stage
		primaryStage.setTitle("AddressApp");
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launchApp(MainApp.class, args);
	}
}