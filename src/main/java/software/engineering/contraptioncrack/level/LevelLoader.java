package software.engineering.contraptioncrack.level;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import software.engineering.contraptioncrack.game.Player;
import software.engineering.contraptioncrack.board.GameBoard;
import software.engineering.contraptioncrack.gameobjects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LevelLoader {

    private Player player;
    private Group root;
    private Scene scene;
    private AnimationTimer timer;
    private ArrayList<GameObject> gameObjects;
    private String currentLevel;
    private int[] playerCoords;
    private VBox dropdownMenu;
    private HashMap<String, Color> colors;

    public LevelLoader (Player player, Group root, Scene scene, AnimationTimer timer,
                        ArrayList<GameObject> gameObjects, int[] playerCoords, VBox dropdownMenu) {
        this.player = player;
        this.root = root;
        this.scene = scene;
        this.timer = timer;
        this.gameObjects = gameObjects;
        this.playerCoords = playerCoords;
        this.dropdownMenu = dropdownMenu;

        colors = new HashMap<>();
        colors.put("BLUE", Color.BLUE);
        colors.put("CYAN", Color.CYAN);
        colors.put("DARKGREY", Color.DARKGREY);
        colors.put("GREEN", Color.GREEN);
        colors.put("GREY", Color.GREY);
        colors.put("LIGHTBLUE", Color.LIGHTBLUE);
        colors.put("ORANGE", Color.ORANGE);
        colors.put("PURPLE", Color.PURPLE);
        colors.put("RED", Color.RED);
        colors.put("YELLOW", Color.YELLOW);
        colors.put("BLACK", Color.BLACK);
    }

    public void loadLevel(String level) throws FileNotFoundException {
        root.getChildren().clear();
        Scanner readFile = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/level/levelinfo.txt"));
        if (level.equals("level-1")) {
            Text title = new Text(200, 200, "Welcome to Contraption Crack!");
            title.setFont(new Font(30));
            Button btn = new Button("New Game");
            btn.setLayoutY(450);
            btn.setLayoutX(450);
            Button load = new Button("Load");
            load.setLayoutX(450);
            load.setLayoutY(500);
            btn.setOnAction(actionEvent -> {
                scene.setFill(Color.BLACK);
                try {
                    loadLevel("Level1");
                    timer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            root.getChildren().add(title);
            root.getChildren().add(btn);
            root.getChildren().add(load);
            return;
        } else {
            while (readFile.hasNextLine()) {
                if (readFile.nextLine().contains(level)) {
                    break;
                }
            }
        }

//        if (level.equals("Level4")) reachedLevel4 = true;
        if (level.equals("level-1")) {
            Text title = new Text(200, 200, "Welcome to Contraption Crack!");
            title.setFont(new Font(30));
            Button btn = new Button("New Game");
            btn.setLayoutY(450);
            btn.setLayoutX(450);
            Button load = new Button("Load");
            load.setLayoutX(450);
            load.setLayoutY(500);
            btn.setOnAction(actionEvent -> {
                scene.setFill(Color.BLACK);
                try {
                    loadLevel("Level1");
                    timer.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            root.getChildren().add(title);
            root.getChildren().add(btn);
            root.getChildren().add(load);
            return;
        }
        GameBoard gb = loadGameBoard(readFile);

        currentLevel = level;
        dropdownMenu = new VBox();
        dropdownMenu.setLayoutX(0);
        dropdownMenu.setLayoutY(0);
        dropdownMenu.setPrefSize(160, 80);
        Button save = new Button("Save");
        save.setPrefSize(160, 20);
        //        save.setOnAction(actionEvent -> saveState());
        Button load = new Button("Load");
        load.setPrefSize(160, 20);
        //        load.setOnAction(actionEvent -> {
//            try {
//                loadState();
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
        Button restartArea = new Button("Restart Area");
        restartArea.setPrefSize(160, 20);
        //        restartArea.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    if(reachedLevel4){
//                        loadLevel("Level4");
//                    }else {
//                        loadLevel("Level1");
//                    }
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        Button restartLevel = new Button("Restart Level");
        restartLevel.setPrefSize(160, 20);
//        restartLevel.setOnAction(actionEvent ->  {
//                try {
//                    loadLevel("Level1");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//        });
        dropdownMenu.getChildren().addAll(save, load, restartArea, restartLevel);

        root.getChildren().add(dropdownMenu);
        dropdownMenu.setVisible(false);

        player = new Player(playerCoords[0], playerCoords[1]);
        root.getChildren().add(gb);

        for (GameObject go : gameObjects) {
            if (go instanceof Jukebox) {
                root.getChildren().add(go);
                go.tick();
            } else if (go instanceof ExitTile) {
                ExitTile tempExit = (ExitTile) go;
                root.getChildren().add(tempExit.getExitTile());
            } else if (go instanceof Screwdriver) {
                Screwdriver tempScrew = (Screwdriver) go;
                root.getChildren().add(tempScrew.getScrewdriver());
            } else if (go instanceof Spring) {
                Spring tempSpring = (Spring) go;
                root.getChildren().add(tempSpring.getSpring());
            } else {
                root.getChildren().add(go);
            }
        }
        root.getChildren().add(player);

    }

    public void saveLevel(){}

    public GameBoard loadGameBoard(Scanner readFile) {
        int boardWidthInPixels = readFile.nextInt();
        int boardLengthInPixels = readFile.nextInt();
        int tileWidthAndHeight = readFile.nextInt();

        int boardWidthInTiles = readFile.nextInt();
        int boardLengthInTiles = readFile.nextInt();
        int[][] tileData = new int[boardLengthInTiles][boardWidthInTiles];

        for(int i = 0; i < boardLengthInTiles; i++) {
            for(int j = 0; j < boardWidthInTiles; j++) {
                tileData[i][j] = readFile.nextInt();
            }
        }

        int amountOfObjects = readFile.nextInt();
        root.getChildren().clear();
        gameObjects.clear();

        ArrayList<Spike> spikeList = new ArrayList<>();
        for(int i = 0; i < amountOfObjects; i++){
            String objectType = readFile.next();
            int x = readFile.nextInt();
            int y = readFile.nextInt();
            int direction;
            int group;
            String colorString;
            String shape;
            Color color;
            Boolean timed;
            try {
                switch (objectType) {
                    case "EXT":
                        direction = readFile.nextInt();
                        int levelTo = readFile.nextInt();
                        gameObjects.add(new ExitTile(x, y, direction, levelTo));
                        break;
                    case "JUK":
                        gameObjects.add(new Jukebox(x, y, Color.CYAN));
                        break;
                    case "BTN":
                        colorString = readFile.next();
                        color = colors.get(colorString);
                        group = readFile.nextInt();
                        shape = readFile.next();
                        timed = readFile.nextBoolean();
                        gameObjects.add(new GameButton(x, y, color, group, shape, timed, spikeList));
                        break;
                    case "SPI":
                        colorString = readFile.next();
                        color = colors.get(colorString);
                        String orientationString = readFile.next();
                        Boolean isActive = Boolean.parseBoolean(readFile.next());
                        group = readFile.nextInt();
                        Spike s;
                        Boolean orientation = orientationString.equals("horizontal");
                        s = new Spike(x, y, color, orientation, isActive, group);
                        gameObjects.add(s);
                        spikeList.add(s);
                        break;
                    case "WALL":
                        colorString = readFile.next();
                        color = colors.get(colorString);
                        int length = readFile.nextInt();
                        int width = readFile.nextInt();
                        String timedWall = readFile.next();
                        int delay = readFile.nextInt();
                        if (timedWall.equals("timed"))
                            gameObjects.add(new Wall(x, y, color, length, width, true, delay));
                        else
                            gameObjects.add(new Wall(x, y, color, length, width, false, delay));
                        break;
                    case "SPG":
                        direction = readFile.nextInt();
                        Boolean hold = readFile.nextBoolean();
                        gameObjects.add(new Spring(x, y, direction, hold));
                        break;
                    case "SCW":
                        gameObjects.add(new Screwdriver(x, y));
                        break;
                    case "BAR":
                        length = readFile.nextInt();
                        width = readFile.nextInt();
                        gameObjects.add(new Barrier(x, y, length, width));
                        break;
                    default:
                }
            } catch (FileNotFoundException fnfe) {
                System.out.println("Unable to find file");
            }
        }
        playerCoords[0] = readFile.nextInt();
        playerCoords[1] = readFile.nextInt();
        readFile.close();

        return new GameBoard(boardWidthInPixels, boardLengthInPixels, tileWidthAndHeight, tileData);

    }

}
