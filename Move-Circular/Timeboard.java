import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Timeboard {
   Dust dust;

   int time = 0;  // initial time board for player
   private long startTime = 0;
   private long stopTime = 0;
   private boolean running = false;
   
   Font font;
   public Timeboard(){
	   font = Font.font("SansSerif", FontWeight.BOLD, 24);
   }
   
   public void start() {
	   this.startTime = System.currentTimeMillis();
	   this.running = true;
   }
   
   //use it in the dust class
   //once dust all cleared, done
   public void stop() {
	   this.stopTime = System.currentTimeMillis();
	   this.running = false;
   }

   //elaspsed time in milliseconds
   public long getElapsedTime() {
	   long elapsed;
	   if (running) {
		   elapsed = (System.currentTimeMillis() - startTime);
	   } else {
		   elapsed = (stopTime - startTime);
	   }
	   return elapsed;
   }


   //elaspsed time in seconds
   public long getElapsedTimeSecs() {
	   long elapsed;
	   if (running) {
		   elapsed = ((System.currentTimeMillis() - startTime) / 1000);
	   } else {
		   elapsed = ((stopTime - startTime) / 1000);
	   }
	   return elapsed;
   }
   
   public void render(GraphicsContext gc){
	   gc.setFill(Color.GREENYELLOW);
	   gc.setFont(font);
	   gc.fillText("Start Time:"+time, 2*Assignment3.EDGE-70, Assignment3.EDGE-220);
	   gc.fillText("Time USED:"+getElapsedTimeSecs(), 2*Assignment3.EDGE-85, Assignment3.EDGE-180);
   }

}
