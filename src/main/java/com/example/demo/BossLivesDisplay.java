package com.example.demo;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class BossLivesDisplay {

    private static final int UPDATE_INTERVAL_MS = 500; // Update interval in milliseconds
    private final VBox container;
    private final Text bossLivesText;
    private final Timeline updateTimeline;
    private final Boss boss; // Assuming Boss is a class you have

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

    public void start() {
        updateTimeline.play();
    }

    public void stop() {
        updateTimeline.stop();
    }

    private void updateBossLivesText() {
        bossLivesText.setText("Boss Lives Left: " + boss.getHealth());
    }
}
