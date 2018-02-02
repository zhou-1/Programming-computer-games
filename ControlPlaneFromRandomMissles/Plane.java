package Assignment2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Plane {
	   int x, y; // top-left corner

	   boolean upKey = false, downKey = false, leftKey = false, rightKey = false;

	   final static int WIDTH = 30;
	   final static int HEIGHT = 20;
	   final static int SPEED = 5;

	   // Set x1 as the middle of the plane
	   public Plane(int x1){
	      x = x1 - WIDTH/2;
	      y = Assignment2.EDGE;
	   }

	   //the plane can move in everywhere in the screen
	   public void move(){
		   if (leftKey && getLeft()>0)
		       x -= SPEED;
		   if (rightKey && getRight()<Assignment2.WIDTH)
		       x += SPEED;
		   if (upKey && getTop()>0)
		       y -= SPEED;
		   if (downKey && getBottom()<Assignment2.HEIGHT)
		       y += SPEED;
	   }
	   
	   // start the ball in mid-screen with a somewhat
	   // random direction
	   public void reset() {
		   	x = Assignment2.WIDTH/2;
		    y = Assignment2.HEIGHT/4;
		    
		}

	   public void render(GraphicsContext gc){
		   gc.setFill(Color.RED);
		   gc.fillRect(x, y, WIDTH, HEIGHT);
	   }

	   public void setUpKey(Boolean val){
		   upKey = val;
	   }

	   public void setDownKey(Boolean val){
		   downKey = val;
	   }
	   
	   public void setLeftKey(Boolean val){
		   leftKey = val;
	   }

	   public void setRightKey(Boolean val){
		   rightKey = val;
	   }

	   // return the x-coord of middle of plane
	   public int getLeft(){
		   return x;
	   }

	   public int getRight(){
		   return x+WIDTH;
	   }
	   
	   public int getTop(){
		   return y;
	   }

	   public int getBottom(){
		   return y+HEIGHT;
	   }
	}
