
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

/*
 * Implement controls described in assignment
 * creator : Zhou Shen
 * Modified by Dr.mike slattery - mar 2018
 */
public class Roomba {
	Timeboard time;

	double x,y,dir_angle; //position, rotation

	boolean leftKey = false, rightKey = false;

	final static int WIDTH = 50;
	final static int HEIGHT = 50;
	final static int SPEED = 4;

	double vx;
	double vy;

	// Set x1 as the middle of the roomba
	public Roomba(int x1){
		x = x1 - WIDTH/2;
	    y = Assignment3.EDGE;
	    dir_angle = Math.PI;

	}

	//the plane can move in anywhere in the screen
	public void move(){
		//find direction of angle first
		//float angle = 25;
		//float dir_angle = (float) Math.toRadians(angle);

		//move in one direction
		// We use trig to compute values for  vx and vy
		//   based on angle and speed
		vx = SPEED * Math.cos(dir_angle);
		vy = SPEED * Math.sin(dir_angle);
		//System.out.println(vx+","+vy);

		x += vx; y += vy;

		// Check for walls
		// We don't bounce off walls - they just stop us
		if (x < 0)
			x = 0;
		if (x+WIDTH > Assignment3.WIDTH)
			x = Assignment3.WIDTH - WIDTH;

		//add up for checking y walls
		if (y < 0)
			y = 0;
		if (y+HEIGHT > Assignment3.HEIGHT)
			y = Assignment3.HEIGHT - HEIGHT;

		//once click left or right key
		// these just affect angle
		if (leftKey)
			dir_angle -= 0.2;
		if (rightKey)
			dir_angle += 0.2;
	}

	// start the ball in mid-screen with a somewhat
	// random direction
	public void reset() {
		x = Assignment3.WIDTH/2;
	    y = Assignment3.HEIGHT/4;

		time.stop();
	}


	public void render(GraphicsContext gc){
		//create the roomba
		gc.setFill(Color.YELLOW);
		gc.fillOval(x, y, WIDTH, HEIGHT);
		//create an eye for the roomba
		// We use dir_angle to position the "eye"
		// First, get the center of the roomba:
		double cx = x + WIDTH/2;
		double cy = y + HEIGHT/2;
		// Then get the center of the eye:
		double ecx = cx + (WIDTH/2 - 10)*Math.cos(dir_angle);
		double ecy = cy + (HEIGHT/2 - 10)*Math.sin(dir_angle);
		//Draw eye
		gc.setFill(Color.RED);
		gc.fillOval(ecx - 10, ecy - 10, 20, 20);
	}

	 public void setLeftKey(Boolean val){
		leftKey = val;
	 }

	 public void setRightKey(Boolean val){
		rightKey = val;
	 }

	// return the x-coord of middle of plane
	 public int getLeft(){
		 return (int)x;
	 }

	 public int getRight(){
		 return (int)x+WIDTH;
	 }


}
