package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

/**
 * The Controller class manages the flow of the game by navigating between different game levels.
 * It listens for updates from the game levels and transitions between them when required.
 *
 * The class utilizes Java reflection to dynamically load and initialize game levels.
 *
 * @author Talya
 */
public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.levels.LevelOne";
	private final Stage stage;


	/**
	 * Constructs a Controller with the specified Stage.
	 *
	 * @param stage The primary stage of the game, used to display the scenes.
	 */
	public Controller(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Starts the game by showing the primary stage and loading the first level.
	 *
	 * @throws ClassNotFoundException If the class for the level cannot be found.
	 * @throws NoSuchMethodException If the constructor for the level cannot be found.
	 * @throws SecurityException If a security manager denies access to the level class.
	 * @throws InstantiationException If the level class cannot be instantiated.
	 * @throws IllegalAccessException If the level constructor is inaccessible.
	 * @throws IllegalArgumentException If the arguments to the constructor are incorrect.
	 * @throws InvocationTargetException If the level constructor throws an exception.
	 */
	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}


	/**
	 * Transitions to the specified level by dynamically loading the class using reflection.
	 *
	 * @param className The fully qualified name of the class representing the level to load.
	 * @throws ClassNotFoundException If the specified class cannot be found.
	 * @throws NoSuchMethodException If the constructor for the level cannot be found.
	 * @throws SecurityException If a security manager denies access to the level class.
	 * @throws InstantiationException If the level class cannot be instantiated.
	 * @throws IllegalAccessException If the level constructor is inaccessible.
	 * @throws IllegalArgumentException If the arguments to the constructor are incorrect.
	 * @throws InvocationTargetException If the level constructor throws an exception.
	 */
	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Stage.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();

			stage.setScene(scene);
			myLevel.startGame();

	}

	/**
	 * This method is called when an observable object updates the controller. It transitions to the next level
	 * based on the information provided in the update.
	 *
	 * @param arg0 The observable object that triggered the update.
	 * @param arg1 The object containing the information for the next level (class name).
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		try {
			goToLevel((String) arg1);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

}
