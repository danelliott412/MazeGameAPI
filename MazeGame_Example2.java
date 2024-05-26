


/**
 *
 * @author Daniel Elliott
 * This example builds game "Tire Tracer" by inheriting MazeGame_API
 * obstacles = tires
 * sprite = car
 * target = coin
 * refer to Elliott_API_MazeGame.docx.docx for full API description
 */

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.paint.Color;



public class MazeGame_Example2 extends API_MazeGame{

    private GraphicsContext gc;


    @Override
    public void start(Stage stage) throws IOException {
        
        API_MazeGame mazeGame = new API_MazeGame();
        //TODO: set image of desired sprite (will be resized to 30px x 30px)
        mazeGame.SetSpriteImage("https://www.clipartmax.com/png/middle/5-53790_related-posts-scratch-race-car-sprite.png",270);
        
        //TODO: set image of your Obstacle (will be resized to 30px x 30px)
        mazeGame.SetObstacleImage("http://pngimg.com/uploads/tire/tire_PNG64.png");
        
        //TODO: set image of your Target (will be resized to 30px x 30px)
        mazeGame.SetTargetImage("https://s3.amazonaws.com/ngccoin-production/us-coin-explorer/1633063-001o.jpg");
        
        //TODO: set color of bacgground and game board default: Color.GRAY,Color.LIGHTBLUE
        mazeGame.SetColorScheme(Color.DARKRED,Color.WHITE);
        
        //TODO: set speed of character movement dfault: 1
        mazeGame.SetSpeed(1);
        
        //TODO: set number of obstacles to spawn after each target hit default: 5 
        mazeGame.SetObstacleSpawnCount(12);
       

        Group root = new Group();
        //Canvas must be sized to 1200px by 750px
        Canvas canvas = new Canvas(1200,750);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        //TODO: rename stage title
        stage.setTitle("Tire Tracer");

        stage.setScene(scene);
        
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        gc = canvas.getGraphicsContext2D();
        
        //Adds key listener to scene
        mazeGame.KeyInput(scene);
        
        //Create timeline for game animation creation
        Timeline timeline = mazeGame.StartAnimation(gc);
        timeline.play();
    }


    public static void main(String[] args) {
        launch(args);

    }
}