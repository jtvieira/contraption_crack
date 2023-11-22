package software.engineering.contraptioncrack.game;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import software.engineering.contraptioncrack.gameobjects.*;
import software.engineering.contraptioncrack.level.LevelLoader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    //Setting variables
    private final Player player;
    private final Group root;
    private final ArrayList<GameObject> gameObjects;
    private final LevelLoader levelLoader;
    private AnimationTimer gameLoop;
    private final DropdownMenu dropdownMenu;


    //movement variables
    private boolean wPressed;
    private boolean aPressed;
    private boolean sPressed;
    private boolean dPressed;
    private boolean escActive;

    private final int MOVEMENT_VARIABLE = 3;

    public GameController(Stage stage) {
        root = new Group();
        gameObjects = new ArrayList<>();
        Scene scene = initializeScene(root);
        player = Player.getInstance();
        levelLoader = new LevelLoader(root, scene, gameObjects);
        dropdownMenu = DropdownMenu.getInstance(levelLoader);
        try {
            levelLoader.loadLevel("level-1");
        } catch (FileNotFoundException fnfe) {
            System.out.println("File not found");
        }

        initGameLoop();
        stage.setTitle("Contraption Crack");
        stage.setScene(scene);
        stage.show();

    }

    public void startGame() {
        gameLoop.start();
    }
    private Scene initializeScene(Group root) {
        Scene initScene = new Scene(root, 900, 900);
        initScene.setFill(Color.WHITE);
        addMovementListeners(initScene);
        return initScene;
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

                double xPos = player.getLayoutX() + dx;
                double yPos = player.getLayoutY() + dy;

                if (!gameObjects.isEmpty()) {
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

    private void addMovementListeners(Scene scene) {
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
                    System.out.println(escActive);
                    if(!escActive){
                        escActive = true;
                        dropdownMenu.setVisible(true);
                        System.out.println("Escape Pushed");
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
    }
}
