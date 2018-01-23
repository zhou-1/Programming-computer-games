import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Assignment 1
 * Write a JavaFX application which displays a picture
 * 
 * @author Zhou Shen
 * @version Jan 2018
 */
public class Assignment1 extends Application{
	public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    //no need to change here
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Assignment 1" );
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        Canvas canvas = new Canvas( 600, 500 ); //600 pixels wide by 500 pixels high
        root.getChildren().add( canvas );
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        render(gc);
        
        theStage.show();
    }
    
    void render(GraphicsContext gc)
    {
    	// Clear background
    	gc.setFill( Color.YELLOW );
        gc.fillRect( 0, 0, 600, 500);
        
        //Draw rectangle 
        gc.setFill(Color.BLUE);
        gc.fillRect(300, 300, 40, 40);
        gc.setStroke(Color.FUCHSIA);
        gc.setLineWidth(5.0);
        gc.strokeRect(300, 300, 40, 40);
        
        //Draw green ovals with for loops
        gc.setFill(Color.GREEN);
        int x = 80;
        for (int i = 0; i<=5; i++) {
        	gc.fillOval(50+i*x, 200, 50, 30);
        }
        
        //Draw circle with different colors
        gc.setFill(Color.CORAL);
        gc.fillOval(100, 400, 40, 40);
        gc.setFill(Color.CADETBLUE);
        gc.fillOval(150, 400, 40, 40);
        gc.setFill(Color.CRIMSON);
        gc.fillOval(200, 400, 40, 40);
        gc.setFill(Color.DARKGRAY);
        gc.fillOval(250, 400, 40, 40);
        gc.setFill(Color.DARKORANGE);
        gc.fillOval(300, 400, 40, 40);
        gc.setFill(Color.DEEPSKYBLUE);
        gc.fillOval(350, 400, 40, 40);
        
        //draw different kinds of polygon
        gc.setFill(Color.RED);
        gc.fillPolygon(new double[]{200, 240, 200},new double[]{110, 110, 140}, 3);//triangle 
        
        gc.setFill(Color.PURPLE);
        double xpoints[] = {25, 145, 25, 145, 25};  //xPoints - array containing the x coordinates of the polygon's points or null.
        double ypoints[] = {25, 25, 145, 145, 25};  //yPoints - array containing the y coordinates of the polygon's points or null.
        int npoints = 5;   //nPoints - the number of points that make the polygon.
        gc.fillPolygon(xpoints, ypoints, npoints);
        
    }
}
