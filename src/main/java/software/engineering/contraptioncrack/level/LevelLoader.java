package software.engineering.contraptioncrack.level;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import software.engineering.contraptioncrack.board.GameBoard;
import software.engineering.contraptioncrack.game.Player;
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
    private DropdownMenu dropdownMenu;
    private HashMap<String, Color> colors;

    public LevelLoader (Group root, Scene scene, ArrayList<GameObject> gameObjects) {

        this.root = root;
        this.scene = scene;
        this.gameObjects = gameObjects;
        this.colors = populateColors();
        this.playerCoords = new int[2];

    }

    private HashMap<String, Color> populateColors() {
        HashMap<String, Color> colorsMap = new HashMap<>();
        colorsMap.put("BLUE", Color.BLUE);
        colorsMap.put("CYAN", Color.CYAN);
        colorsMap.put("DARKGREY", Color.DARKGREY);
        colorsMap.put("GREEN", Color.GREEN);
        colorsMap.put("GREY", Color.GREY);
        colorsMap.put("LIGHTBLUE", Color.LIGHTBLUE);
        colorsMap.put("ORANGE", Color.ORANGE);
        colorsMap.put("PURPLE", Color.PURPLE);
        colorsMap.put("RED", Color.RED);
        colorsMap.put("YELLOW", Color.YELLOW);
        colorsMap.put("BLACK", Color.BLACK);
        return colorsMap;
    }

    public void loadLevel(String level) throws FileNotFoundException {

        root.getChildren().clear();
        Scanner readFile = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/level/levelinfo.txt"));

        // If a new game is starting
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

        // we want to load the gameboard based on our file. Passing the scanner instance will maintain
        // the current cursor index in the game file that is being read
        GameBoard gb = loadGameBoard(readFile);

        currentLevel = level;

        // This is the game menu that provides options
        dropdownMenu = DropdownMenu.getInstance();
        dropdownMenu.setVisible(false);
        root.getChildren().add(dropdownMenu);


        player = Player.getInstance();
        player.initialize(playerCoords[0], playerCoords[1]);
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
