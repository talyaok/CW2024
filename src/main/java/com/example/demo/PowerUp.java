package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

    public class PowerUp extends ImageView {

        private static final String IMAGE_NAME = "/com/example/demo/images/powerup.png";
        private static final String USER_PLANE_IMAGE = "/com/example/demo/images/userplane1.png";
        private static final double INITIAL_X_POSITION = 800.0; // Initial position of the power-up
        private static final double INITIAL_Y_POSITION = 300.0;
        private static final double OFFSET_X = 0.0; // Adjust offset as needed
        private static final double OFFSET_Y = 50.0; // Adjust offset as needed
        private static final int PROJECTILE_X_POSITION = 110; // Add this variable
        private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
        private static final double SPEED = 8.0;
        private final LevelParent level;
        private final Timeline timeline;

        public PowerUp(LevelParent level) {
            super(new Image(PowerUp.class.getResource(IMAGE_NAME).toExternalForm()));
            this.level = level;
            setX(INITIAL_X_POSITION);
            setY(INITIAL_Y_POSITION);
            setFitWidth(80); // Set the desired width
            setFitHeight(80); // Set the desired height

            timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveTowardsPlayer()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

        private void moveTowardsPlayer() {
            double playerX = level.getUser().getLayoutX() + level.getUser().getTranslateX();
            double playerY = level.getUser().getLayoutY() + level.getUser().getTranslateY();
            double deltaX = playerX - getX(); double deltaY = playerY - getY();
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            double moveX = SPEED * deltaX / distance;
            double moveY = SPEED * deltaY / distance;

            setX(getX() + moveX);
            setY(getY() + moveY);

            // Check for collision with player
            if (getBoundsInParent().intersects(level.getUser().getBoundsInParent())) {
                activate();
                timeline.stop();
            }
        }
        public void followUser(double userX, double userY) {
            setX(userX + OFFSET_X);
            setY(userY + OFFSET_Y);
        }
        public void fireProjectile() {
            //ActiveActorDestructible projectile = new UserProjectile(getX() + PROJECTILE_X_POSITION, getY() + PROJECTILE_Y_POSITION_OFFSET);
            ActiveActorDestructible projectile = new UserProjectile(getX() + 110, getY());
            level.getRoot().getChildren().add(projectile);
            level.getUserProjectiles().add(projectile);
        }
        public void activate() {
            setImage(new Image(PowerUp.class.getResource(USER_PLANE_IMAGE).toExternalForm()));
            setFitWidth(200); // Set new width for the user plane image
            setFitHeight(50); // Set new height for the user plane image
            // Add this power-up as a follower plane to the user's plane
            level.getUser().addFollowerPlane(this);
            // Remove power-up from the scene if needed
            // level.getRoot().getChildren().remove(this);
            }
    }
