package com.example.demo.displays;

import com.example.demo.actors.UserPlane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The PlanesLeftDisplay class is responsible for displaying the number of remaining enemy planes
 * that the player needs to defeat in the game. This class updates the displayed number in real-time
 * as the player destroys enemy planes.
 *
 * It tracks the number of enemy planes left and updates the display based on the number of kills
 * made by the player. This information is shown on the screen in a visually prominent position.
 *
 * @author Talya
 */
public class PlanesLeftDisplay {

    private static final int UPDATE_INTERVAL_MS = 500; // Update interval in milliseconds
    private final VBox container;
    private final Text planesLeftText;
    private final Timeline updateTimeline;
    private final UserPlane user;
    private final int totalEnemies;

    /**
     * Constructs a PlanesLeftDisplay object to initialize the display for showing the number of
     * remaining enemy planes. This object updates the number of planes left based on the player's kills.
     *
     * @param root The root container (Group) to which this display will be added.
     * @param screenWidth The width of the screen to position the display appropriately.
     * @param yPosition The vertical position on the screen where the display will be placed.
     * @param user The UserPlane object to track the number of kills.
     * @param totalEnemies The total number of enemy planes to track.
     */
    public PlanesLeftDisplay(Group root, double screenWidth, double yPosition, UserPlane user, int totalEnemies) {
        this.user = user;
        this.totalEnemies = totalEnemies;

        container = new VBox();
        container.setLayoutX(screenWidth - 200);
        container.setLayoutY(yPosition);

        planesLeftText = new Text("Planes Left: " + totalEnemies);
        planesLeftText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");
        container.getChildren().add(planesLeftText);
        root.getChildren().add(container);

        updateTimeline = new Timeline(new KeyFrame(Duration.millis(UPDATE_INTERVAL_MS), event -> updatePlanesLeftText()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the update timeline, which will update the number of planes left at regular intervals.
     */
    public void start() {
        updateTimeline.play();
    }

    /**
     * Stops the update timeline, halting the periodic update of the planes left text.
     */
    public void stop() {
        updateTimeline.stop();
    }

    /**
     * Updates the displayed number of planes left by calculating the difference between the
     * total enemies and the number of kills made by the player.
     */
    private void updatePlanesLeftText() {
        int planesLeft = totalEnemies - user.getNumberOfKills();
        planesLeftText.setText("Planes Left: " + planesLeft);
    }
}
