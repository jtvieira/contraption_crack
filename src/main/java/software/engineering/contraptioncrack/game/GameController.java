package software.engineering.contraptioncrack.game;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import software.engineering.contraptioncrack.gameobjects.*;
import software.engineering.contraptioncrack.level.LevelLoader;

import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    //Setting variables
    private Player player;
    private Group root;
    private Scene scene;
    private ArrayList<GameObject> gameObjects;
    private LevelLoader levelLoader;
    private AnimationTimer gameLoop;
    private VBox dropDownMenu;


    //movement variables
    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;

    private final int MOVEMENT_VARIABLE = 3;

    public GameController() {
        root = new Group();
        gameObjects = new ArrayList<>();
        scene = new Scene(root, 900, 900);
        scene.setFill(Color.WHITE);
        player = new Player();
        levelLoader = new LevelLoader(player, root, scene, gameLoop, gameObjects, new int[]{1,1}, dropDownMenu);

    }

    private void initGameLoop() {
        gameLoop = new AnimationTimer() {
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

                if (gameObjects.size() > 0) {
                    for (GameObject go : gameObjects) {
                        if (collision(player.getBounds((int) xPos, (int) yPos), go.getBounds())) {
                            if (go instanceof ExitTile) {
                                try {
                                    ExitTile tempExit = (ExitTile) go;
                                    if (tempExit.getLevel().equals("Level0"))
                                        break;
                                    levelLoader.loadLevel(tempExit.getLevel());
                                    break;
                                } catch (IOException e) {
                                    System.out.println("Unable to read from file");
                                    throw new RuntimeException(e);
                                }
                            } else if (go instanceof GameButton || go instanceof Screwdriver) {
                                go.activate();
                            } else if (go instanceof Spring) {
                                Spring tempSpring = (Spring) go;
                                if (tempSpring.getHold()) {
                                    if (tempSpring.getActive()) {
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
                                    default -> {
                                    }
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
    }

    private Boolean collision(Rectangle player, Rectangle object){
        return player.intersects(object.getBoundsInParent());
    }
}
