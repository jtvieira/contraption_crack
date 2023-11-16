package software.engineering.contraptioncrack.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import software.engineering.contraptioncrack.gameobjects.*;
import software.engineering.contraptioncrack.level.LevelLoader;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    /** The scene that is being displayed */
    private Group root;
    private Scene scene;
    private Player player;
    private int[] playerCoords = new int[2];
    private int[] mvmtBound;
    private Boolean wPressed = false;
    private Boolean aPressed = false;
    private Boolean sPressed = false;
    private Boolean dPressed = false;
    private Boolean escActive = false;
    private final int MOVEMENT_VARIABLE = 3;
    private LevelLoader levelLoader;
    private ArrayList<GameObject> gameObjects;
    private String currentLevel;
    private VBox dropdownMenu;
    private Boolean reachedLevel4;
    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            int dx = 0, dy = 0;
            if (wPressed) dy -= MOVEMENT_VARIABLE;
            if (aPressed) dx -= MOVEMENT_VARIABLE;
            if (sPressed) dy += MOVEMENT_VARIABLE;
            if (dPressed) dx += MOVEMENT_VARIABLE;
            /**
             * Checks collision with game objects
             */
            double xPos = player.getLayoutX() + dx;
            double yPos = player.getLayoutY() + dy;

            if(gameObjects.size() > 0) {
                for(GameObject go : gameObjects) {
                    if(collision(player.getBounds((int) xPos, (int) yPos), go.getBounds())) {
                        if(go instanceof ExitTile) {
                            try {
                                ExitTile tempExit = (ExitTile) go;
                                if(tempExit.getLevel().equals("Level0"))
                                    break;
                                levelLoader.loadLevel(tempExit.getLevel());
                                break;
                            } catch (IOException e) {
                                System.out.println("Unable to read from file");
                                throw new RuntimeException(e);
                            }
                        } else if(go instanceof GameButton || go instanceof Screwdriver) {
                            go.activate();
                        } else if(go instanceof Spring) {
                            Spring tempSpring = (Spring) go;
                            if(tempSpring.getHold()){
                                if(tempSpring.getActive()){
                                    dx = 0;
                                    dy = 0;
                                    break;
                                } else {
                                    tempSpring.activate();
                                    root.getChildren().add(tempSpring.getSpring());
                                }
                            }
                            int direction = tempSpring.getDirection();
                            switch (direction) {
                                case 0 -> dx += 160;
                                case 90 -> dy += 160;
                                case 180 -> dx -= 160;
                                case 270 -> dy -= 160;
                                default -> {}
                            }
                        } else {
                            dx = 0;
                            dy = 0;
                        }
                    }
                }

            }
            player.movePlayer(dx, dy);
        }
    };

    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = new GameController();
        root = new Group();
        gameObjects = new ArrayList<>();
        scene = new Scene(root, 900, 900);
        scene.setFill(Color.WHITE);

        levelLoader = new LevelLoader(player, root, scene, timer, gameObjects, playerCoords, dropdownMenu);
        levelLoader.loadLevel("level-1");
        /**
         * Player Movement Controller
         */
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                    wPressed = true;
                    break;
                case A:
                    aPressed = true;
                    break;
                case S:
                    sPressed = true;
                    break;
                case D:
                    dPressed = true;
                    break;
                case ESCAPE:
                    if(!escActive){
                        escActive = true;
                        dropdownMenu.setVisible(true);
                    }else{
                        escActive = false;
                        dropdownMenu.setVisible(false);
                    }
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case W:
                    wPressed = false;
                    break;
                case A:
                    aPressed = false;
                    break;
                case S:
                    sPressed = false;
                    break;
                case D:
                    dPressed = false;
                    break;
            }
        });

        scene.getRoot().requestFocus();
        stage.setTitle("Contraption Crack");
        stage.setScene(scene);
        stage.show();
    }



//    public void saveState(){
//        PrintWriter writer = null;
//        try{
//            writer = new PrintWriter("src/main/java/software/engineering/contraptioncrack/saves/data.txt");
//            BufferedWriter bw = new BufferedWriter(writer);
//            bw.write(currentLevel);
//        }catch (IOException e){
//            e.printStackTrace();
//            writer.close();
//        }
//    }
//
//    public void loadState() throws FileNotFoundException {
//        Scanner scan = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/saves/data.txt"));
//        while (scan.hasNextLine()){
//            try{
//                String level = scan.next();
//                loadLevel(level);
//                scan.close();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }




    public static void main(String[] args) {
        launch();
    }
}