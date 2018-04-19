import java.net.*;

import javafx.scene.paint.Color;

import java.awt.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameGroup extends Thread {

	GameClientThread arr[];
	final int SIZE=3;

	int grid[][];  //map of the board
	public static final int GWD=12; // width
	public static final int GHT=10; // and height of board
	Player red, blue, yellow;  //The three players

	Grab grab;

	GameGroup ( Socket s ) {
		arr = new GameClientThread[SIZE];
		addClient( s );
	}
	
	//GameClientThread GCT;

	public void addClient( Socket s ) {
		int x;

		for( x=0; x<SIZE; x++)
			if( arr[x] == null || !arr[x].isAlive() ) {
				arr[x] = new GameClientThread(s,this);
				arr[x].start();
				return ;
				}
	}

	public void run() {
		Point p;

		System.out.println("GameGroup begun");
		//Get a random starting board
		String board = fillGrid();

		//Position the two players - Note, we never use	the colors here
		p = emptySpot();
		blue = new Player(p.x, p.y, (int)(4*Math.random()), null);
		// We also need to mark each player's spot in the grid, so we'll
		// know it's not empty
		grid[p.x][p.y] = 3;
		
		p = emptySpot();
		red = new Player(p.x, p.y, (int)(4*Math.random()), null);
		grid[p.x][p.y] = 3;
		
		p = emptySpot();
		yellow = new Player(p.x, p.y, (int)(4*Math.random()), null);
		grid[p.x][p.y] = 3;

		//Send each player the config.
		output("start,"+board);
		//and player info (including which they are)
		output("blue,"+blue.x+","+blue.y+","+blue.dir);
		output("red,"+red.x+","+red.y+","+red.dir);
		output("yellow,"+yellow.x+","+yellow.y+","+yellow.dir);
		// We don't use output() here, because we need to send
		// different messages to each player
		arr[0].message("who,blue");
		arr[1].message("who,red");
		arr[2].message("who,yellow");
	}

	public String fillGrid()
	{
		// fill in the board at random and return
		// a String representing the board in row-major order
		// Coords are used like screen coords - 0,0 in top-left,
		// first coord is to right, second is down.
		int x,y,i;
		Point p;

		grid = new int[GWD][GHT];
		// Clear grid
		for (x = 0; x < GWD; x++)
		for (y = 0; y < GHT; y++)
			grid[x][y] = 0;
		// Place blocks
		for (i = 0; i < 40; i++)
		{
			p = emptySpot();
			grid[p.x][p.y] = 1;
		}
		// Place money
		for (i = 0; i < 8; i++)
		{
			p = emptySpot();
			grid[p.x][p.y] = 2;
		}
		//Now, make the string
		StringBuffer sb = new StringBuffer(GHT*GWD);
		for (y = 0; y < GHT; y++)
		 for (x = 0; x < GWD; x++)
			sb.append(grid[x][y]);
		return new String(sb);
	}

	public Point emptySpot()
	{
		int x, y;
		// Find an empty square in the grid
		do
		{
			x = (int)(GWD*Math.random());
			y = (int)(GHT*Math.random());
		} while (grid[x][y] != 0);
		return new Point(x,y);
	}
	
	/*
	GraphicsContext gc; // declare here to use in handlers
	public void render(GraphicsContext gc, int x, int y) {
		int px = Grab.CELLSIZE * x;
		int py = Grab.CELLSIZE * y;	
		
		//replace coin with white circle
		gc.setFill(Color.WHITE);		//red or blue
		gc.fillOval(px, py, Grab.CELLSIZE - 1, Grab.CELLSIZE - 1);
	}
	*/
	
	int i = 0;
	int j = 0;
	int k = 0;
	int L = 0;
	
	int o = 0;
	int u = 0;
	int t = 0;
	
	public synchronized void processMessage(String msg)
	{
		Player p;

		//System.out.println("pM got:"+msg);

		//Chop up the message, adjust the state, and tell the clients
		String[] words = msg.split(",");
		String cmd = words[0];

		// get the player name and find the correct
		// Player object
		// NOTE: This depends on all of the messages having the
		//   same "command,name" structure.
		String pname = words[1];
		if (pname.equals("blue"))
			p = blue;
		if (pname.equals("red"))
			p = red;
		else
			p = yellow;
		
		if (cmd.equals("turnleft"))
		{
			p.turnLeft();
			output(pname+","+p.x+","+p.y+","+p.dir);
		}
		else if (cmd.equals("turnright"))
		{
			p.turnRight();
			output(pname+","+p.x+","+p.y+","+p.dir);
		}
		else if (cmd.equals("step"))
		{
			int newx=-1, newy=-1;	//set to illegal subscripts in case the
									//logic below ever fails (at least we'll
									// get a message).

			//Compute new location
			switch(p.dir)
			{
				case Player.UP: newx = p.x; newy = p.y-1;
								if (newy < 0) return;
					break;
				case Player.RIGHT: newx = p.x+1; newy = p.y;
								if (newx >= GameGroup.GWD) return;
					break;
				case Player.DOWN: newx = p.x; newy = p.y+1;
								if (newy >= GameGroup.GHT) return;
					break;
				case Player.LEFT: newx = p.x-1; newy = p.y;
								if (newx < 0) return;
					break;
			}
			if (grid[newx][newy] != 0)		//whether there is empty slot ahead or not
				return;
			// Clear mark in grid first
			grid[p.x][p.y] = 0;
			p.x = newx; p.y = newy;
			// Then, mark the new spot
			grid[p.x][p.y] = 3;
			output(pname+","+p.x+","+p.y+","+p.dir);
		}
		else if (cmd.equals("grabcoin")) {
			output(pname+","+"attemp to grab!");
			int newx=-1, newy=-1;	//set to illegal subscripts in case the
									//logic below ever fails (at least we'll
									// get a message).
			
			//Compute new location
			switch(p.dir)
			{
				case Player.UP: newx = p.x; newy = p.y-1;
								if (newy < 0) return;
					break;
				case Player.RIGHT: newx = p.x+1; newy = p.y;
								if (newx >= GameGroup.GWD) return;
					break;
				case Player.DOWN: newx = p.x; newy = p.y+1;
								if (newy >= GameGroup.GHT) return;
					break;
				case Player.LEFT: newx = p.x-1; newy = p.y;
								if (newx < 0) return;
					break;
			}
			
			//whether there is a coin ahead or not
			if (grid[newx][newy] != 2) {
				output(pname + "," + "failed!");
				return;
			}
			else {
			//else if it is coin
			// Clear player mark in grid first; no need to do with graph							
			grid[p.x][p.y] = 0;
			int x,y;
			x = p.x; y=p.y;  //store the place for current circle
			p.x = newx; p.y = newy;		
			//Then clear coin mark in grid
			grid[p.x][p.y] = 0;
			
			//grab.use(); //update for render gc in grab class
			//String gg = fillGrid();
			
			//output(pname+","+"Succeed"+","+"gain one score!");
			output("grabcoin"+","+newx+","+newy+","+"succeed!");
			
			//Next add one score for each player if grabbed successfully
			if(pname.equals("blue")) {
				i += 1;		
				output(pname+","+"score: "+i);
			}
			if(pname.equals("red")) {
				j += 1;		
				output(pname+","+"score: "+j);
			}
			if(pname.equals("yellow")) {
				k += 1;		
				output(pname+","+"score: "+k);
			}
			
			//check for whether there us a winner right now
			if (i>j && i>k) {
				output("blue"+","+"current winner!");
			}
			if (j>i && j>k) {
				output("red"+","+"current winner!");
			}
			if (k>i && k>j) {
				output("yellow"+","+"current winner!");
			}
			else {
				output("currently there is no winner.");
			}
		
			//
			//String gg = fillGrid();
			
			//last, keep the circle at the same place
			p.x = x; p.y=y;
			
			//check whether coin is all gone or not
			L += 1;
			int coinss = 8;
			if (L==coinss) {
				output("game over, "+"all coins are picked up");
			}
			
			}			
		}
		else if (cmd.equals("blast")) {
			output(pname+","+"attemp to blast block!");
			int newx=-1, newy=-1;	//set to illegal subscripts in case the
									//logic below ever fails (at least we'll
									// get a message).
			
			//Compute new location
			switch(p.dir)
			{
				case Player.UP: newx = p.x; newy = p.y-1;
								if (newy < 0) return;
					break;
				case Player.RIGHT: newx = p.x+1; newy = p.y;
								if (newx >= GameGroup.GWD) return;
					break;
				case Player.DOWN: newx = p.x; newy = p.y+1;
								if (newy >= GameGroup.GHT) return;
					break;
				case Player.LEFT: newx = p.x-1; newy = p.y;
								if (newx < 0) return;
					break;
			}
			
			//whether there is a block ahead or not
			if (grid[newx][newy] != 1) {
				output(pname + "," + "failed!");
				return;
			}
			else {
				//else if it is block
				int max = 4; //only can blast 4 times
				
				if(pname.equals("blue")) {
					if (o<max) {
						// Clear player mark in grid first; no need to do with graph							
						grid[p.x][p.y] = 0;
						int x,y;
						x = p.x; y=p.y;  //store the place for current circle
						p.x = newx; p.y = newy;		
						//Then clear block mark in grid
						grid[p.x][p.y] = 0;
						output("blast"+","+newx+","+newy+","+"succeed!");
			
						//last, keep the circle at the same place
						p.x = x; p.y=y;
					
						//bump one more for o
						o += 1;
					}else {
						output(pname+","+"out of blast times");
					}
				}
				if(pname.equals("red")) {
					if (u<max) {
						// Clear player mark in grid first; no need to do with graph							
						grid[p.x][p.y] = 0;
						int x,y;
						x = p.x; y=p.y;  //store the place for current circle
						p.x = newx; p.y = newy;		
						//Then clear block mark in grid
						grid[p.x][p.y] = 0;
						output("blast"+","+newx+","+newy+","+"succeed!");
			
						//last, keep the circle at the same place
						p.x = x; p.y=y;
					
						//bump one more for o
						u += 1;
					}else {
						output(pname+","+"out of blast times");
					}
				}
				if(pname.equals("yellow")) {
					if (t<max) {
						// Clear player mark in grid first; no need to do with graph							
						grid[p.x][p.y] = 0;
						int x,y;
						x = p.x; y=p.y;  //store the place for current circle
						p.x = newx; p.y = newy;		
						//Then clear block mark in grid
						grid[p.x][p.y] = 0;
						output("blast"+","+newx+","+newy+","+"succeed!");
			
						//last, keep the circle at the same place
						p.x = x; p.y=y;
					
						//bump one more for o
						t += 1;
					}else {
						output(pname+","+"out of blast times");
					}
				}
			}	
		}
		
	}

	public void output(String str) {
	// Send a message to each client
		int x;

		for(x=0;x<SIZE;x++)
			if(arr[x] != null)
				arr[x].message(str);
	}

	public boolean full() {
	// Check if we have all our players
		int x;

		for(x=0;x<SIZE;x++)
			if( arr[x] == null )
				return false;
		return true;
	}
}
