


/**
 *
 * @author Daniel Elliott 
 * This file is designed to be used as a File > Save As to create your 
 * own version of a maze game by following the API documentation using an 
 * instance of the class (Not needed as we extend MazeGame_API, BUT easier to 
 * see what each method does, without referencing the API documentation PDF) 
 * API Documentation: Elliott_API_MazeGame.docx
 * TODO: Uncomment code in the Start method & 
 * use mazeGame. following the API to create your own maze game.
 * 
 */

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.paint.Color;



public class MazeGame_Template extends API_MazeGame{

    private GraphicsContext gc;


    @Override
    public void start(Stage stage) throws IOException {
        
        API_MazeGame mazeGame = new API_MazeGame();
        
        //TODO: set image of desired sprite (will be resized to 30px x 30px)
        //mazegame.SetSpriteImage();
        
        //TODO: set image of your Obstacle (will be resized to 30px x 30px)
        //mazegame.SetObstacleImage();
        
        //TODO: set image of your Target (will be resized to 30px x 30px)
        //mazegame.SetTargetImage();
        
        //TODO: set color of bacgground and game board default: Color.GRAY,Color.LIGHTBLUE
        //mazegame.SetColorScheme();
        
        //TODO: set speed of character movement dfault: 1
        //mazegame.SetSpeed();
        
        //TODO: set number of obstacles to spawn after each target hit default: 5 
        //mazegame.SetObstacleSpawnCount();
        

        Group root = new Group();
        //Canvas must be sized to 1200px by 750px
        Canvas canvas = new Canvas(1200,750);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);

        //TODO: rename stage title
        stage.setTitle("Maze Game");

        stage.setScene(scene);
        
        stage.setResizable(false);
        stage.sizeToScene();
        stage.show();
        gc = canvas.getGraphicsContext2D();
        
        //Adds key listener to scene
        mazeGame.KeyInput(scene);
        
        // Creates timeline for game animation creation
        Timeline timeline = mazeGame.StartAnimation(gc);
        // Starts animation
        timeline.play();

    }


    public static void main(String[] args) {
        launch(args);

    }
}
