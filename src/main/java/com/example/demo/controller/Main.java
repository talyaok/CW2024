package com.example.demo.controller;

import java.lang.reflect.InvocationTargetException;

import com.example.demo.menus.WelcomeScreen;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The Main class serves as the entry point for the Sky Battle game.
 * It extends the JavaFX Application class and handles the initialization of the game application.
 *
 * This class starts the WelcomeScreen and prepares the stage for the game to be launched.
 * It also defines the game's screen size, title, and other settings before transitioning to the game controller.
 *
 * @author Talya
 */
public class Main extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String TITLE = "Sky Battle";
	private Controller myController;

	/**
	 * The start method is called when the JavaFX application is launched. It initializes the WelcomeScreen
	 * and displays it to the user.
	 *
	 * @param stage The primary stage of the JavaFX application.
	 */
	@Override public void start(Stage stage)
	{ try {
		// Set application name for better macOS compatibility
		System.setProperty("apple.awt.application.name", TITLE);

		WelcomeScreen welcomeScreen = new WelcomeScreen();
		welcomeScreen.start(stage);
	} catch (Exception e) {
		e.printStackTrace(); }
	}

	/**
	 * Launches the game by setting up the stage, configuring the screen size, and initializing the Controller.
	 * This method transitions to the game's first level.
	 *
	 * @param stage The primary stage of the JavaFX application, used for displaying the game scenes.
	 * @throws ClassNotFoundException If the class for the level cannot be found.
	 * @throws NoSuchMethodException If the constructor for the level cannot be found.
	 * @throws SecurityException If a security manager denies access to the level class.
	 * @throws InstantiationException If the level class cannot be instantiated.
	 * @throws IllegalAccessException If the level constructor is inaccessible.
	 * @throws IllegalArgumentException If the arguments to the constructor are incorrect.
	 * @throws InvocationTargetException If the level constructor throws an exception.
	 */
	public void launchGame(Stage stage) throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("launching game...");
		stage.setTitle(TITLE);
		stage.setResizable(false);
		stage.setHeight(SCREEN_HEIGHT);
		stage.setWidth(SCREEN_WIDTH);
		myController = new Controller(stage);
		myController.launchGame(); }

	/**
	 * The main method is the entry point of the Java application.
	 * It launches the JavaFX application by calling the launch() method.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		launch();
	}
}
