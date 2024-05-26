


import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Daniel Elliott
 * This class is designed to allow the user to create their own maze style game
 * the user will edit the sprite, obstacle and target images as well as set the
 * play speed, color scheme, and obstacle spawn count
 * Docs = Elliott_API_MazeGame.docx
 */
public class API_MazeGame extends Application {
    
    //To be overridden by demo class
    @Override
    public void start(Stage primaryStage) throws Exception {      
}
    
    private final int RIGHT = 0;
    private final int LEFT = 1;
    private final int UP = 2;
    private final int DOWN = 3;
    private int currentDirection;
    private int speed = 20;
    private int ObstacleSpawnCount = 5;

    private boolean TargetHit = true;
    private boolean GameOver = false;
    private int TargetBlock = 0;
    private int PrevTargetBlock = 190;

    private int rotation;
    private int rotationCorrection = 0;
    private double EndScreenOpacity = 0;

    private int x = 600;
    private int y = 375;
    private int bX = 400;
    private int bY = 250;
    private ArrayList<Integer> BlocksUsed = new ArrayList();

    private  Image ImageObstacle;
    private  Image ImageSprite;
    private  Image ImageTarget;
    
    private Color BackgroundColor = Color.GRAY;
    private Color GameBoardColor = Color.LIGHTBLUE;
   
    /**
     * Sets image of sprite
     * @param URL absolute file path, relative file path, or URL of image
     * NOTE: Images will be resized to 30 x 30 pixels
     * @param RotationCorrection corrects rotation of sprite image, 
     * enter int in terms of degrees i.e. 90, 180, 270
     */
    public void SetSpriteImage(String URL,int RotationCorrection){
        ImageSprite = new Image(URL);
        rotationCorrection = RotationCorrection;
    }
    
    
    /**
     * Sets image of Obstacle
     * @param URL absolute file path, relative file path, or URL of image
     * NOTE: Images will be resized to 30 x 30 pixels
     */
    public void SetObstacleImage(String URL){
        ImageObstacle = new Image(URL);
    }
    
    
    /**
     * Sets image of Target
     * @param URL absolute file path, relative file path, or URL of image
     * NOTE: Images will be resized to 30 x 30 pixels
     */
    public void SetTargetImage(String URL){
        ImageTarget = new Image(URL);
    }
    
    /**
     * Sets number of obstacles to spawn after each target hit
     * @param count number of obstacles to spawn
     */
    public void SetObstacleSpawnCount(int count){
        ObstacleSpawnCount = count;
    }
    
    
    /**
     * Sets the Color of both the background and "Game Board"
     * @param NewBackgroundColor Sets background color
     * @param NewGameBoardColor Sets game board color
     */
    public void SetColorScheme(Color NewBackgroundColor,
            Color NewGameBoardColor){
        BackgroundColor = NewBackgroundColor;
        GameBoardColor = NewGameBoardColor;
    }
    
    /**
     * SetSpeed sets game speed from 0(Slowest)-3(Fastest)
     * @param gameSpeed takes integer for sprite's speed of movement: 0(Slowest)-3(Fastest)
     */
    public void SetSpeed(int gameSpeed){
        
        switch (gameSpeed) {
            case 0:
                speed = 25;
                break;
            case 1:
                speed = 20;
                break;
            case 2:
                speed = 10;
                break;
            case 3:
                speed = 5;
                break;
            default:
                speed = 20;
                break;
        }
    }
    
    /**
     * Checks for successful collision with Target: creates target hit box 
     */
    private void CheckTarget(){
        if(bX-7 < x+15 && bX+37 > x+10 && bY -7 < y+25 && bY +37 > y+10){
            System.out.println("Target hit!");
            TargetHit = true;
        }
    }

    /**
     * Checks for collision with obstacle, border: creates obstacle hit box
     */
    private void CheckBoundaries(){
        int[] blockCoordinates;

        if(300 > x || 900 < x + 30 || 75 > y || 675 < y+30) {
            System.out.println("Game Over");
            GameOver = true;
        } else{

            for(int i = 0; i < BlocksUsed.size(); i++) {
                blockCoordinates = BlockCoordinates(BlocksUsed.get(i));
                int BlockX = blockCoordinates[0];
                int BlockY = blockCoordinates[1];
                if(BlockX < x + 25 && BlockX + 25 > x
                        && BlockY < y + 25 && BlockY + 25 > y) {
                    System.out.println("Game Over");
                    GameOver = true;
                }
            }
        }
    }
    /**
     * draws sprite on game board
     * @param gc GraphicsContext
     */
    private void DrawSprite(GraphicsContext gc){

        //Create Image view for simple Image rotation
        ImageView iv = new ImageView(ImageSprite);
        iv.setRotate(rotation + rotationCorrection);
        //Set params to transparent so sprite background is transparent
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        Image RotatedImageBoat = iv.snapshot(params, null);
        gc.drawImage( RotatedImageBoat, x, y,30,30);
    }
/**
 * Draws target in empty block after confirming chosen block was not taken
 * @param gc GraphicsContext
 */
    private void DrawTarget(GraphicsContext gc){
        //If buoy is hit, draw new buoy
        if(TargetHit) {
            spawnObstacle(0);
            do {
                TargetBlock = (int) (Math.random() * 400);
            } while (BlockTaken(TargetBlock));
            TargetHit = false;
        }

        int[] blockCoordinates;
        PrevTargetBlock = TargetBlock;

        blockCoordinates = BlockCoordinates(TargetBlock);
        bX  = blockCoordinates[0];
        bY = blockCoordinates[1];

        gc.drawImage( ImageTarget,bX, bY,30,30);
    }
    
    /**
     * Spawns 5 obstacles at a time using recursion, checks to see if
     * block number has been taken
     * @param n counter for recursion call
     */
    private void spawnObstacle(int n) {
        if (n < ObstacleSpawnCount) {
            int block;
            do {
                block = (int) (Math.random() * 400);
            } while(BlockTaken(block));
            BlocksUsed.add(block);

            spawnObstacle(n + 1);
        }
    }
    
    
    /**
     * Draws obstacles after utilizing "BlockCoordinates()" method
     * @param gc GraphicsContext
     */
    private void DrawObstacles(GraphicsContext gc){
        int[] blockCoordinates;
        for(int i = 0; i < BlocksUsed.size(); i++) {
            blockCoordinates = BlockCoordinates(BlocksUsed.get(i));
            int BlockX  = blockCoordinates[0];
            int BlockY = blockCoordinates[1];

            gc.drawImage( ImageObstacle,BlockX, BlockY,30,30);

        }
    }

    /**
     * Creates background of game window
     * @param gc GraphicsContext
     */
    private void DrawBackground(GraphicsContext gc){
        //Background fill
        gc.setFill(BackgroundColor);
        gc.fillRect(0, 0, 1200, 750);

        //Border
        gc.setFill(Color.rgb(220,220,220));
        gc.fillRect(288, 63, 624, 624);
        gc.setFill(Color.rgb(200,200,200));
        gc.fillRect(294, 69, 612, 612);

        //Main Game Background
        gc.setFill(GameBoardColor);
        gc.fillRect(300, 75, 600, 600);
    }
    
    /**
     * Creates end "fade to black" when player loses
     * @param gc 
     */
    
    private void EndScreen(GraphicsContext gc){

        Color black = new Color(0,0,0,EndScreenOpacity);

        gc.setFill(black);
        gc.fillRect(0, 0, 1200, 750);
        if(GameOver){
            if(EndScreenOpacity < .99) {
                EndScreenOpacity+= .01;
            }else{
                gc.setFill(Color.WHITE);
                gc.setFont(Font.font("Arial", FontWeight.BOLD, 150));
                gc.fillText("GAME OVER",150,400);
            }
        }
    }
    
    /**
     * Determines if randomly chosen Block has been used already in the list of
     * BlocksUsed, Also determines if chosen block was within certain distance of 
     * previous target to prevent spawn over player.
     * @param block The tile number of the game board
     * @return 
     */
    private boolean BlockTaken(int block){
        for(int i = 0; i < BlocksUsed.size(); i++){
            if(block == BlocksUsed.get(i)){
                return true;
            }
        }
        if(block < PrevTargetBlock + 3 && block > PrevTargetBlock - 2 ||
                block < PrevTargetBlock + 43 && block > PrevTargetBlock + 38 ||
                block < PrevTargetBlock + 23 && block > PrevTargetBlock + 18 ||
                block < PrevTargetBlock - 18 && block > PrevTargetBlock - 23 ||
                block < PrevTargetBlock - 38 && block > PrevTargetBlock - 43){
            return true;
        }
        return false;
    }
        private int[] BlockCoordinates(int block){

        int yBlock = block / 20;
        int[] blockCoordinates = new int[2];
        //Y coordinate value of block used
        blockCoordinates[1] = yBlock * 30 + 75 ;
        //X coordinate value of block used
        blockCoordinates[0] = (block - (yBlock * 20)) * 30 + 300;

        return blockCoordinates;
    }
        
        /**
         * Adds key listener to scene
         * @param scene 
         */
        public void KeyInput(Scene scene){
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode code = event.getCode();
                if (code == KeyCode.RIGHT || code == KeyCode.D) {
                    if (currentDirection != LEFT) {
                        currentDirection = RIGHT;
                    }
                } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                    if (currentDirection != RIGHT) {
                        currentDirection = LEFT;
                    }
                } else if (code == KeyCode.UP || code == KeyCode.W) {
                    if (currentDirection != DOWN) {
                        currentDirection = UP;
                    }
                } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                    if (currentDirection != UP) {
                        currentDirection = DOWN;
                    }
                }
            }
        });
    }
        /**
         * sets up Timeline for user to make it easier to set up game
         * @param gc GraphicsContext
         * @return timeline for animation
         */
        public Timeline StartAnimation(GraphicsContext gc){
            //"Heart beat" of application - sets timing ot gameplay animation and calls animation run method.
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(speed), e -> run(gc)));
            timeline.setCycleCount(Animation.INDEFINITE);

            return timeline;
        }
        /**
         * Method that loops to check parameters, draw animation
         * @param gc 
         */
            private void run(GraphicsContext gc) {
        DrawBackground(gc);
        CheckTarget();

        if(!GameOver) {
            DrawTarget(gc);
            DrawSprite(gc);
            CheckBoundaries();
        }

        switch (currentDirection) {
            case RIGHT:
                x++;
                rotation = 90;
                break;
            case LEFT:
                x--;
                rotation = 270;
                break;
            case UP:
                y--;
                rotation = 0;
                break;
            case DOWN:
                y++;
                rotation = 180;
                break;
        }

        DrawObstacles(gc);
        if(GameOver){
            EndScreen(gc);
        }
    }
}

        
                  
    
    


