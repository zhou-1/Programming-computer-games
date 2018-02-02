package Assignment2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * a small game about plane and missle 
 * user control the plane with keyboard
 * in order to elude from missles
 * 
 * @author Zhou Shen
 * @version feb 2018
 */

public class Assignment2 extends Application {
	final String appName = "plane with random missles";
	final int FPS = 30; // frames per second
	final static int WIDTH = 600;
	final static int HEIGHT = 500;
	final static int EDGE = 200;

	GraphicsContext gc; // declare here to use in handlers

	Missle[] missles = new Missle[3]; //will be 3 random missles at a time
	Plane plane;  //only need one plane in the game
	ScoreBoard score;
	// Mouse data
	boolean mdown = false;
	int mx, my;
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		for (int i = 0; i < missles.length; i++)
			missles[i] = new Missle();
		plane = new Plane(300);
		score = new ScoreBoard();
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
			e -> {
				KeyCode c = e.getCode();
				switch (c) {
					case W: plane.setUpKey(true);
								break;
					case S: plane.setDownKey(true);
								break;
					case A: plane.setLeftKey(true);  
								break;
					case D: plane.setRightKey(true); 
								break;
					default:
								break;
				}
			}
		);
		
		scene.setOnKeyReleased(
				e -> {
					KeyCode c = e.getCode();
					switch (c) {
						case W: plane.setUpKey(false);
									break;
						case S: plane.setDownKey(false);
									break;
						case A: plane.setLeftKey(false);
									break;
						case D: plane.setRightKey(false);
									break;
						default:
									break;
					}
				}
			);
		
		//for mouse click
		scene.setOnMousePressed( e -> {
			mdown = true;
			mx = (int)e.getX();
			my = (int)e.getY();
			render(gc); // update the screen
		});
		scene.setOnMouseReleased( e -> {
			mdown = false;
			render(gc); // update the screen
		});
	}

	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		plane.move();
		for (Missle b: missles)
			b.move();
		// Run through pairs of boxes checking for collisions
		// (i > j to avoid duplicate pairs)
		for (int i = 1; i < missles.length; i++)
			for (int j = 0; j < i; j++)
				if (missles[i].overlaps(missles[j]))
				{
					missles[i].reverse();
					missles[j].reverse();
				}
		checkScore();
	}
	
	//once the plane meet the missle, life or score minus 1 
	void checkScore() {
		boolean scored = false;
		//check for all of three missles
		//check the side of missles and side of plane
		if (plane.getLeft() < 100 || plane.getTop() < 100 || plane.getRight() > 500 || plane.getBottom() > 450) {
			score.bumpScore();
			scored = true;
		}
		
		if (scored)
			plane.reset();
	}
	

	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// fill background
		gc.setFill(Color.CYAN);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		// draw game objects
		plane.render(gc);
		for (Missle b: missles)
			b.render(gc);
		score.render(gc);
		
		// Draw different color of background (if mouse is pressed)
		if (mdown){
			// fill background
			gc.setFill(Color.AQUAMARINE);
			gc.fillRect(0, 0, WIDTH, HEIGHT);
		}
	}

	/*
	 * Begin boiler-plate code...
	 * [Animation and events with initialization]
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

		// Initial setup
		initialize();
		setHandlers(theScene);
		
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
