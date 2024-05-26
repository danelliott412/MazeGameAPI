


/**
 *
 * @author Daniel Elliott
 * This example builds game "Buoy Breaker" by inheriting MazeGame_API
 * obstacles = rocks
 * sprite = boat
 * target = buoy
 * refer to Elliott_API_MazeGame.docx for full API description
 */

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.paint.Color;


public class MazeGame_Example extends API_MazeGame{

    private GraphicsContext gc;


    @Override
    public void start(Stage stage) throws IOException {
        
        API_MazeGame mazeGame = new API_MazeGame();
        
                //TODO: set image of desired sprite (will be resized to 30px x 30px)
        mazeGame.SetSpriteImage("/BOAT.png", 0);
        
        //TODO: set image of your Obstacle (will be resized to 30px x 30px)
        mazeGame.SetObstacleImage("/Rock.png");
        
        //TODO: set image of your Target (will be resized to 30px x 30px)
        mazeGame.SetTargetImage("/Buoy.png");
        
        //TODO: set color of bacgground and game board default: Color.GRAY,Color.LIGHTBLUE
        mazeGame.SetColorScheme(Color.GRAY,Color.LIGHTBLUE);
        
        //TODO: set speed of character movement dfault: 1
        mazeGame.SetSpeed(1);
        
        //TODO: set number of obstacles to spawn after each target hit default: 5 
        mazeGame.SetObstacleSpawnCount(50);

        

        Group root = new Group();
        //Canvas must be sized to 1200px by 750px
        Canvas canvas = new Canvas(1200,750);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        //TODO: rename stage title
        stage.setTitle("Buoy Breaker");

        stage.setScene(scene);
        
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        gc = canvas.getGraphicsContext2D();
        
        //Adds key listener to scene
        mazeGame.KeyInput(scene);
        
        // Creates timeline for game animation creation
        Timeline timeline = mazeGame.StartAnimation(gc);
        timeline.play();


    }


    public static void main(String[] args) {
        launch(args);

    }
}

