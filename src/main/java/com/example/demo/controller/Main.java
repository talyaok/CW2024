package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;
/* og code
	@Override
	public void start(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame();
	}*/
	@Override public void start(Stage stage)
	{ try {
		// Set application name for better macOS compatibility
		System.setProperty("apple.awt.application.name", TITLE);

		WelcomeScreen welcomeScreen = new WelcomeScreen();
		welcomeScreen.start(stage);
	} catch (Exception e) {
		e.printStackTrace(); }
	} // Method to launch the game
	public void launchGame(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("launching game...");
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame(); }

	public static void main(String[] args) {
		/* comment out for testing
		// Add JVM arguments for better compatibility
		System.setProperty("prism.order", "sw");
		System.setProperty("prism.verbose", "true");

		 */

		launch();
	}
}
