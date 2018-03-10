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
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Assignment3 extends Application{
	final String appName = "Roomba";
	final int FPS = 30; // frames per second
	final static int WIDTH = 600;
	final static int HEIGHT = 500;
	final static int EDGE = 250;

	GraphicsContext gc; // declare here to use in handlers
	
	Dust[] dust = new Dust[12]; //will be 12 random dusts at a time
	Roomba roomba;
	Timeboard time;
	
	
	/**
	 * Set up initial data structures/values
	 */
	void initialize()
	{
		for (int i = 0; i < dust.length; i++)
			dust[i] = new Dust();
		roomba = new Roomba(300);
		time = new Timeboard();
		time.start(); //start the time
	}
	
	void setHandlers(Scene scene)
	{
		scene.setOnKeyPressed(
			e -> {
				KeyCode c = e.getCode();
				switch (c) {
					case LEFT: roomba.setLeftKey(true);
								break;
					case RIGHT: roomba.setRightKey(true);
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
						case LEFT: roomba.setLeftKey(false);
									break;
						case RIGHT: roomba.setRightKey(false);
									break;
						default:
									break;
					}
				}
			);
	}
	
	/**
	 *  Update variables for one time step
	 */
	public void update()
	{
		roomba.move();
		for (Dust b: dust)
			b.move();
		// Run through pairs of boxes checking for collisions
		// (i > j to avoid duplicate pairs)
		//need to do overlaps and reverse
		for (int i = 1; i < dust.length; i++)
			for (int j = 0; j < i; j++)
				if (dust[i].overlaps(dust[j]))
				{
					dust[i].reverse();
					dust[j].reverse();
				}
		checkTime();
	}
	
	//once the roomba collects all the dusts
	//the time will stop
	void checkTime() {
		boolean time1 = false;
		
		//check for all the dusts
		//if (dust.checkRest() == 0) {
			//time.stopTime();
			//time1 = true;
		//}
		
		if (time1)
			roomba.reset();
			time.stop();
	}
	
	
	/**
	 *  Draw the game world
	 */
	void render(GraphicsContext gc) {
		// fill background
		gc.setFill(Color.BLUE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		// draw game objects
		for (Dust b: dust)
			b.render(gc);
		
		roomba.render(gc);
		time.render(gc);
		
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
