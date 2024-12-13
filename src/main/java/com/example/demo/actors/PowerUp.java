package com.example.demo.actors;

import com.example.demo.LevelParent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * Represents a power-up object in the game that moves towards the player and activates
 * once it collides with the player's plane. It also has the ability to fire a projectile.
 *
 * The PowerUp class provides functionality to move towards the player, activate upon collision,
 * and update its image and size when activated.
 *
 * @author Talya
 */
    public class PowerUp extends ImageView {

        // Constants for image paths, initial position, and movement speed
        private static final String IMAGE_NAME = "/com/example/demo/images/powerup.png";
        private static final String USER_PLANE_IMAGE = "/com/example/demo/images/userplane1.png";
        private static final double INITIAL_X_POSITION = 800.0; // Initial position of the power-up
        private static final double INITIAL_Y_POSITION = 300.0;
        private static final double OFFSET_X = 0.0; // Adjust offset as needed
        private static final double OFFSET_Y = 50.0; // Adjust offset as needed
        private static final double SPEED = 8.0;

        // Instance variables

        private final LevelParent level;
        private final Timeline timeline;

        /**
         * Constructs a PowerUp that moves towards the player.
         *
         * @param level The level in which the power-up exists, used to access the user's plane.
         */
        public PowerUp(LevelParent level) {
            super(new Image(PowerUp.class.getResource(IMAGE_NAME).toExternalForm()));
            this.level = level;
            setX(INITIAL_X_POSITION);
            setY(INITIAL_Y_POSITION);
            setFitWidth(80); // Set the desired width
            setFitHeight(80); // Set the desired height

            // Timeline to move the power-up towards the player
            timeline = new Timeline(new KeyFrame(Duration.millis(50), e -> moveTowardsPlayer()));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        /**
         * Moves the power-up towards the player's plane.
         * It calculates the direction to the player and moves in that direction.
         * If the power-up collides with the player's plane, it activates the power-up.
         */
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

        /**
         * Makes the power-up follow the user's plane at a specified offset.
         *
         * @param userX The x-coordinate of the user's plane.
         * @param userY The y-coordinate of the user's plane.
         */
        public void followUser(double userX, double userY) {
            setX(userX + OFFSET_X);
            setY(userY + OFFSET_Y);
        }

        /**
         * Fires a projectile from the power-up's location, adding it to the level's root and user projectiles.
         * The projectile is created as an instance of {@link UserProjectile}.
         */
        public void fireProjectile() {
            //ActiveActorDestructible projectile = new UserProjectile(getX() + PROJECTILE_X_POSITION, getY() + PROJECTILE_Y_POSITION_OFFSET);
            ActiveActorDestructible projectile = new UserProjectile(getX() + 110, getY());
            level.getRoot().getChildren().add(projectile);
            level.getUserProjectiles().add(projectile);
        }

        /**
         * Activates the power-up by changing its image to represent the user's plane,
         * resizing it, and adding it as a follower plane to the user's plane.
         */
        public void activate() {
            setImage(new Image(PowerUp.class.getResource(USER_PLANE_IMAGE).toExternalForm()));
            setFitWidth(200); // Set new width for the user plane image
            setFitHeight(50); // Set new height for the user plane image
            // Add this power-up as a follower plane to the user's plane
            level.getUser().addFollowerPlane(this);
            }
    }
