package org.app.config;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import org.app.view.PersonOverviewController;
import org.app.view.RootLayoutController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

@Configuration
@PropertySource("classpath:/org/app/properties/application.properties")
public class SpringApplicationConfig
{
	private static final String PATH = "../view/";
	private static final String ROOT = "RootLayout.fxml";
	private static final String OVERVIEW = "PersonOverview.fxml";
	private static final String EDIT_DIALOG = "PersonEditDialog.fxml";

	/**
	 * Loads and returns root layout view.
	 * @throws IOException
	 */
	@Bean(name = "rootLayoutView")
	public BorderPane getRootLayoutView() throws IOException
	{
		return (BorderPane) loadView(PATH + ROOT).getView();
	}

	/**
	 * Loads and returns root layout controller.
	 * @throws IOException
	 */
	@Bean(name = "rootLayoutController")
	public RootLayoutController getRootLayoutController() throws IOException
	{
		return (RootLayoutController) loadView(PATH + ROOT).getController();
	}

	/**
	 * Loads and adds person view to the spring context, and wraps it with corresponding controller into {@link View}.
	 * @return View - person layout {@link View}
	 * @throws IOException
	 */
	@Bean(name="personOverview")
	public View getPersonOverviewView() throws IOException
	{
		return loadView(PATH + OVERVIEW);
	}

	/**
	 * Adds {@link PersonOverviewController} controller to spring context and makes all required injections.
	 * @return PersonOverviewController - main layout controller
	 * @throws IOException
	 */
	@Bean(name="personOverviewController")
	public PersonOverviewController getPersonOverviewController() throws IOException
	{
		return (PersonOverviewController) getPersonOverviewView().getController();
	}

	/**
	 * Loads and adds edit dialog view to the spring context, and wraps it with corresponding controller into {@link View}.
	 * @return View - edit dialog {@link View}
	 * @throws IOException
	 */
	@Bean(name="editDialog")
	@Scope("prototype")
	public View getPersonEditDialogView() throws IOException
	{
		return loadView(PATH + EDIT_DIALOG);
	}

	/**
	 * FXML view loader.
	 * Instantiates controller object, performs all FXML injections and calls controller initializer.
	 * Builds and returns a {@link View} with loaded view and controller
	 * @throws IOException
	 */
	protected View loadView(String viewUrl) throws IOException
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource(viewUrl));
		return new View(loader.load(), loader.getController());
	}

	/**
	 * View wrapper.
	 * Takes two parameters: controller (bean) and view used at the application entry point.
	 */
	public class View
	{
		private Parent view;
		private Object controller;

		public View(Parent view, Object controller)
		{
			this.setView(view);
			this.setController(controller);
		}

		public Parent getView()
		{
			return view;
		}

		public void setView(Parent view)
		{
			this.view = view;
		}

		public Object getController()
		{
			return controller;
		}

		public void setController(Object controller)
		{
			this.controller = controller;
		}
	}
}
