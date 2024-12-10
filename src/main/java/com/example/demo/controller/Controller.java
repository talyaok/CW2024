package com.example.demo.controller;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import com.example.demo.ActiveActorDestructible;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import com.example.demo.LevelParent;

import javafx.animation.AnimationTimer;//t
import javafx.scene.input.KeyCode;//t
import javafx.scene.input.KeyEvent;//t
import com.example.demo.UserPlane; //t

public class Controller implements Observer {

	private static final String LEVEL_ONE_CLASS_NAME = "com.example.demo.LevelOne";
	private final Stage stage;

	//private UserPlane userPlane; //t
	//private Scene scene; //t


	public Controller(Stage stage) {
		this.stage = stage;
	}

	public void launchGame() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {

			stage.show();
			goToLevel(LEVEL_ONE_CLASS_NAME);
	}
/*
	public void goToNextLevel(String className) {
		try {
			goToLevel(className);
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText(e.getClass().toString());
			alert.show();
		}
	}

 */

	private void goToLevel(String className) throws ClassNotFoundException, NoSuchMethodException, SecurityException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Class<?> myClass = Class.forName(className);
			Constructor<?> constructor = myClass.getConstructor(double.class, double.class, Stage.class);
			LevelParent myLevel = (LevelParent) constructor.newInstance(stage.getHeight(), stage.getWidth(), stage);
			myLevel.addObserver(this);
			Scene scene = myLevel.initializeScene();

		//scene = myLevel.initializeScene();
			stage.setScene(scene);
			myLevel.startGame();

	}
	//t
	/*
	private void setupInputHandling() {
		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.UP) {
				userPlane.moveUp();
			} else if (event.getCode() == KeyCode.DOWN) {
				userPlane.moveDown();
			} else if (event.getCode() == KeyCode.SPACE) {
				ActiveActorDestructible projectile = userPlane.fireProjectile();
				// Add projectile to the game (you'll need a method to do this)
			}
		});

		scene.setOnKeyReleased(event -> {
			if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
				userPlane.stop();
			}
		});
	}

	private void startGameLoop() {
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				if (userPlane != null) {
					userPlane.updateActor(); // Update plane position
					// Update other game elements as needed
				}
			}
		}.start();
	}
//t
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
