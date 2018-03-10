import java.util.Random;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/*
 * Implement screen wrap
 * Modified by mike slattery - mar 2018
 */
public class Dust {
	// Just need one random generator shared
	// by all the boxes (so make it static)
	static Random rng = new Random();

	int x, y;   // top-left corner
	int w, h;   // width and height
	int vx, vy; // velocity vector

	final static int RADIUS = 15;

	public Dust()
		{
			// Choose random and slow movement
			vx = 1 + rng.nextInt(3);
			if (rng.nextDouble() < 0.5)
				vx = -vx;
			vy = 1 + rng.nextInt(3);
			if (rng.nextDouble() < 0.5)
				vy = -vy;
			// Choose random position on the Canvas
			x = 5 + rng.nextInt(Assignment3.WIDTH - w - 10);
			y = 5 + rng.nextInt(Assignment3.HEIGHT - h - 10);
		}

		public void move()
		{
			x += vx; y += vy;
			// Check for walls
			// When we hit walls, we want to wrap to other
			//  side of screen
			if (x-RADIUS < 0)
				x = Assignment3.WIDTH - RADIUS;
			if (x+RADIUS > Assignment3.WIDTH)
				x = RADIUS;

			//add up for checking y walls
			if (y-RADIUS < 0)
				y = Assignment3.HEIGHT - RADIUS;
			if (y+RADIUS > Assignment3.HEIGHT)
				y = RADIUS;
		}

		public boolean overlaps(Dust b)
		{
			return false;
		}

		public int getX() {
			return x;
		}

		public void reverse()
		{
			vx = -vx;
			vy = -vy;
		}

		public void disappear() {
			//once meet the roomba
			//that dust will disappear


		}

		public void render(GraphicsContext gc)
		{
			gc.setFill(Color.GRAY);
			gc.fillOval(x-RADIUS, y-RADIUS, 2*RADIUS, 2*RADIUS);
		}

		//check the rest dusts
		public int checkRest(){
			//need to do more
			//once all done

			return (int)x;
		}
}
