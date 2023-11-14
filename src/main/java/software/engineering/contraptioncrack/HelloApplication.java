package software.engineering.contraptioncrack;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import software.engineering.contraptioncrack.board.GameBoard;
import software.engineering.contraptioncrack.gameobjects.*;
import software.engineering.contraptioncrack.level.LevelLoader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloApplication extends Application {
    /** The scene that is being displayed */
    Group root;
    Scene scene;
    Player player;
    int playerX, playerY;
    int[] mvmtBound;
    private Boolean wPressed = false;
    private Boolean aPressed = false;
    private Boolean sPressed = false;
    private Boolean dPressed = false;
    private Boolean escActive = false;
    private final int movementVariable = 3;
    private LevelLoader levelLoader;
    ArrayList<GameObject> gameObjects;
    String currentLevel;
    VBox dropdownMenu;
    private Boolean reachedLevel4 = false;
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            int dx = 0, dy = 0;
            if (wPressed) dy -= movementVariable;
            if (aPressed) dx -= movementVariable;
            if (sPressed) dy += movementVariable;
            if (dPressed) dx += movementVariable;
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
                                loadLevel(tempExit.getLevel());
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
                                }else {
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
        root = new Group();
        gameObjects = new ArrayList<>();
        mvmtBound = new int[]{900,900};
        scene = new Scene(root, 900, 900);
        scene.setFill(Color.WHITE);

        levelLoader = new LevelLoader(player, root, scene, timer);
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

    public Boolean collision(Rectangle player, Rectangle object){
        return player.intersects(object.getBoundsInParent());
    }

    public GameBoard loadGameBoard(Scanner readFile) throws FileNotFoundException {
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
            switch (objectType){
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
                    color = readColor(colorString);
                    group = readFile.nextInt();
                    shape = readFile.next();
                    timed = readFile.nextBoolean();
                    gameObjects.add(new GameButton(x, y, color, group, shape, timed, spikeList));
                    break;
                case "SPI":
                    colorString = readFile.next();
                    color = readColor(colorString);
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
                    color = readColor(colorString);
                    int length = readFile.nextInt();
                    int width = readFile.nextInt();
                    String timedWall = readFile.next();
                    int delay = readFile.nextInt();
                    if(timedWall.equals("timed"))
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
                    gameObjects.add(new Barrier(x , y, length, width));
                    break;
                default:
            }
        }
        playerX = readFile.nextInt();
        playerY = readFile.nextInt();
        readFile.close();

        return new GameBoard(boardWidthInPixels, boardLengthInPixels, tileWidthAndHeight, tileData);
    }


    /**
     * This method
     */
    public void loadLevel(String level) throws IOException {
        root.getChildren().clear();
        Scanner readFile = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/level/levelinfo.txt"));
        if(!level.equals("level-1")) {
            while (readFile.hasNextLine()) {
                if (readFile.nextLine().contains(level)) {
                    break;
                }
            }
        }

        if(level.equals("Level4"))  reachedLevel4 = true;
        if(level.equals("level-1")) {
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

        player = new Player(playerX, playerY, mvmtBound);
        root.getChildren().add(gb);

        for(int i = 0; i < gameObjects.size(); i++){
            if(gameObjects.get(i) instanceof Jukebox){
                root.getChildren().add(gameObjects.get(i));
                gameObjects.get(i).tick();
            }else if(gameObjects.get(i) instanceof ExitTile){
                ExitTile tempExit = (ExitTile) gameObjects.get(i);
                root.getChildren().add(tempExit.getExitTile());
            }else  if(gameObjects.get(i) instanceof Screwdriver){
                Screwdriver tempScrew = (Screwdriver) gameObjects.get(i);
                root.getChildren().add(tempScrew.getScrewdriver());
            }else if(gameObjects.get(i) instanceof Spring){
                Spring tempSpring = (Spring) gameObjects.get(i);
                root.getChildren().add(tempSpring.getSpring());
            }else {
                root.getChildren().add(gameObjects.get(i));
            }
        }
        root.getChildren().add(player);

    }

    public void saveState(){
        PrintWriter writer = null;
        try{
            writer = new PrintWriter("src/main/java/software/engineering/contraptioncrack/saves/data.txt");
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(currentLevel);
        }catch (IOException e){
            e.printStackTrace();
            writer.close();
        }
    }

    public void loadState() throws FileNotFoundException {
        Scanner scan = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/saves/data.txt"));
        while (scan.hasNextLine()){
            try{
                String level = scan.next();
                loadLevel(level);
                scan.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Color readColor(String color){
        switch (color){
            case "BLUE":
                return Color.BLUE;
            case "CYAN":
                return Color.CYAN;
            case "DARKGREY":
                return Color.DARKGRAY;
            case "GREEN":
                return Color.GREEN;
            case "GREY":
                return Color.GREY;
            case "LIGHTBLUE":
                return Color.LIGHTSKYBLUE;
            case "ORANGE":
                return Color.ORANGE;
            case "PURPLE":
                return Color.PURPLE;
            case "RED":
                return Color.RED;
            case "YELLOW":
                return Color.YELLOW;
            default:
                return Color.BLACK;
        }
    }



    public static void main(String[] args) {
        launch();
    }
}