package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PlanesLeftDisplay {

    private static final int UPDATE_INTERVAL_MS = 500; // Update interval in milliseconds
    private final VBox container;
    private final Text planesLeftText;
    private final Timeline updateTimeline;
    private final UserPlane user;
    private final int totalEnemies;

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

    public void start() {
        updateTimeline.play();
    }

    public void stop() {
        updateTimeline.stop();
    }

    private void updatePlanesLeftText() {
        int planesLeft = totalEnemies - user.getNumberOfKills();
        planesLeftText.setText("Planes Left: " + planesLeft);
    }
}
