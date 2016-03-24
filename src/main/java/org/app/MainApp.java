package org.app;

import java.io.IOException;

import javafx.stage.Stage;

import org.app.view.RootLayoutController;
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
	@Qualifier("rootLayoutController")
	private RootLayoutController rootLayoutController;

	protected static final Logger logger = LoggerFactory.getLogger(MainApp.class);

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		context = SpringApplication.run(getClass(), getSavedArgs());
		context.getAutowireCapableBeanFactory().autowireBean(this);

		rootLayoutController.showRootLayout(primaryStage);
	}

	public static void main(String[] args)
	{
		launchApp(MainApp.class, args);
	}
}