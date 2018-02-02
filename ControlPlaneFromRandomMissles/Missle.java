package Assignment2;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Missle {
	// Just need one random generator shared
		// by all the boxes (so make it static)
		static Random rng = new Random();
		
		int x, y;   // top-left corner
		int w, h;   // width and height
		int vx, vy; // velocity vector
		
		final static int RADIUS = 15;

		public Missle()
		{
			// Choose random movement
			vx = 2 + rng.nextInt(7);
			if (rng.nextDouble() < 0.5)
				vx = -vx;
			vy = 2 + rng.nextInt(7);
			if (rng.nextDouble() < 0.5)
				vy = -vy;
			// Choose random position on the Canvas
			x = 5 + rng.nextInt(Assignment2.WIDTH - w - 10);
			y = 5 + rng.nextInt(Assignment2.HEIGHT - h - 10);
		}
		
		public void move()
		{
			x += vx; y += vy;
			// Check for walls
			if (x-RADIUS < 0)
				vx = -vx;
			if (x+RADIUS > Assignment2.WIDTH)
				vx = -vx;
			
			//add up for checking y walls
			if (y-RADIUS < 0)
				vy = -vy;
			if (y+RADIUS > Assignment2.HEIGHT)
				vy = -vy;
		}
		
		public boolean overlaps(Missle b)
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
	
		public void render(GraphicsContext gc)
		{
			gc.setFill(Color.BLUE);
			gc.fillOval(x-RADIUS, y-RADIUS, 2*RADIUS, 2*RADIUS);
		}
}
