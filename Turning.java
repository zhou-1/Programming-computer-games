package examples;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Turning animation using a
 * Finite State Machine (FSM)
 * 
 * @author mike slattery
 * @version jan 2016
 */
public class Turning extends Application {
	final String appName = "Turning Demo";
	final int FPS = 25; // frames per second
	final int WIDTH = 600;
	final int HEIGHT = 500;

	// Car's coordinates
	int cx = 220;
	int cy = 220;
	
	final int RIGHT = 0; // FSM states ...
	final int LEFT = 1;
	final int UP = 2;
	final int DOWN = 3;
	int state = RIGHT;   // car's direction

	/**
	 *  Update variables for one time step
	 */
	void update() {
		switch (state) {
		case RIGHT:
			cx += 2;
			if (cx == 420)
				state = UP;
			break;
		case UP:
			cy -= 2;
			if (cy == 20)
				state = LEFT;
			break;
		case LEFT:
			cx -= 2;
			if (cx == 220)
				state = DOWN;
			break;
		case DOWN:
			cy += 2;
			if (cy == 220)
				state = RIGHT;
			break;
		}
	}

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// Clear background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw buildings
		gc.setFill(Color.GREY);
		gc.fillRect(70, 70, 140, 140);
		gc.fillRect(270, 70, 140, 140);
		gc.fillRect(70, 270, 140, 140);
		gc.fillRect(270, 270, 140, 140);

		// Draw car
		gc.setFill(Color.RED);
		gc.fillRect(cx, cy, 40, 40);
	}

	/*
	 * Begin boiler-plate code...
	 * [Animation]
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
		theStage.setTitle(appName);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		GraphicsContext gc = canvas.getGraphicsContext2D();

		// Setup and start animation loop (Timeline)
		KeyFrame kf = new KeyFrame(Duration.millis(1000 / FPS),
				e -> {
					// update position
					update();
					// draw frame
					render(gc);
				}
			);
		Timeline mainLoop = new Timeline(kf);
		mainLoop.setCycleCount(Animation.INDEFINITE);
		mainLoop.play();

		theStage.show();
	}
	/*
	 * ... End boiler-plate code
	 */
}