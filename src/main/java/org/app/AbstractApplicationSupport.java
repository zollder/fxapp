package org.app;

import javafx.application.Application;

import org.springframework.context.ConfigurableApplicationContext;

public abstract class AbstractApplicationSupport extends Application
{
	private static String[] savedArgs;
	protected ConfigurableApplicationContext context;

	@Override
	public void stop() throws Exception
	{
		super.stop();
		context.close();
	}

	/**
	 * Application launcher.
	 * @param clazz
	 * @param args
	 */
	protected static void launchApp(Class<? extends AbstractApplicationSupport> clazz, String[] args)
	{
		AbstractApplicationSupport.savedArgs = args;
		Application.launch(clazz, args);
	}

	public static String[] getSavedArgs()
	{
		return savedArgs;
	}

	public static void setSavedArgs(String[] savedArgs)
	{
		AbstractApplicationSupport.savedArgs = savedArgs;
	}
}