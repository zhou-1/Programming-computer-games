package Assignment2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ScoreBoard {

   int score = 9;  // initial life/score board for player

   Font font;

   public ScoreBoard(){
	   font = Font.font("SansSerif", FontWeight.BOLD, 24);
   }

   public void render(GraphicsContext gc){
	   gc.setFill(Color.BLACK);
	   gc.setFont(font);
	   gc.fillText("Life:"+score, 2*Assignment2.EDGE+110, Assignment2.EDGE-160);
   }

   public void bumpScore(){
	   if(score>0) //lowest life is 0
	   score--;
   }

}
