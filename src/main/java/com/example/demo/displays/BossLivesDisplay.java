package com.example.demo.displays;


import com.example.demo.actors.Boss;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The BossLivesDisplay class is responsible for displaying the number of lives left for the boss
 * in the game. It periodically updates the display to reflect changes in the boss's health.
 *
 * This class creates a text display that shows the current number of lives the boss has left,
 * and it is updated at regular intervals based on the boss's health.
 *
 * @author Talya
 */
public class BossLivesDisplay {

    private static final int UPDATE_INTERVAL_MS = 500;
    private final VBox container;
    private final Text bossLivesText;
    private final Timeline updateTimeline;
    private final Boss boss;

    /**
     * Constructs a BossLivesDisplay object, initializing the display and setting up the update timeline.
     *
     * @param root The root Group of the scene to which this display will be added.
     * @param screenWidth The width of the screen to position the display correctly.
     * @param yPosition The vertical position of the display on the screen.
     * @param boss The boss whose health is being tracked and displayed.
     */
    public BossLivesDisplay(Group root, double screenWidth, double yPosition, Boss boss) {
        this.boss = boss;

        container = new VBox();
        container.setLayoutX(screenWidth - 200);
        container.setLayoutY(yPosition);

        bossLivesText = new Text("Boss Lives Left: " + boss.getHealth());
        bossLivesText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: white;");
        container.getChildren().add(bossLivesText);
        root.getChildren().add(container);

        updateTimeline = new Timeline(new KeyFrame(Duration.millis(UPDATE_INTERVAL_MS), event -> updateBossLivesText()));
        updateTimeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Starts the timeline to update the boss lives display periodically.
     */
    public void start() {
        updateTimeline.play();
    }

    /**
     * Stops the timeline, halting the periodic updates of the boss lives display.
     */
    public void stop() {
        updateTimeline.stop();
    }

    /**
     * Updates the text displayed on the screen with the current number of boss lives left.
     */
    private void updateBossLivesText() {
        bossLivesText.setText("Boss Lives Left: " + boss.getHealth());
    }
}
