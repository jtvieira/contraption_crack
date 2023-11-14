package software.engineering.contraptioncrack.level;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import software.engineering.contraptioncrack.Player;
import software.engineering.contraptioncrack.board.GameBoard;
import software.engineering.contraptioncrack.gameobjects.ExitTile;
import software.engineering.contraptioncrack.gameobjects.Jukebox;
import software.engineering.contraptioncrack.gameobjects.Screwdriver;
import software.engineering.contraptioncrack.gameobjects.Spring;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class LevelLoader {

    private Player player;
    private Group root;
    private Scene scene;

    public LevelLoader (Player player, Group root, Scene scene) {
        this.player = player;
        this.root = root;
        this.scene = scene;

    }

    public void loadLevel() throws FileNotFoundException {
        root.getChildren().clear();
        Scanner readFile = new Scanner(new File("src/main/java/software/engineering/contraptioncrack/level/levelinfo.txt"));
        if (!level.equals("level-1")) {
            while (readFile.hasNextLine()) {
                if (readFile.nextLine().contains(level)) {
                    break;
                }
            }
        }

        if (level.equals("Level4")) reachedLevel4 = true;
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

        player = new Player(playerX, playerY, mvmtBound);
        root.getChildren().add(gb);

        for (int i = 0; i < gameObjects.size(); i++) {
            if (gameObjects.get(i) instanceof Jukebox) {
                root.getChildren().add(gameObjects.get(i));
                gameObjects.get(i).tick();
            } else if (gameObjects.get(i) instanceof ExitTile) {
                ExitTile tempExit = (ExitTile) gameObjects.get(i);
                root.getChildren().add(tempExit.getExitTile());
            } else if (gameObjects.get(i) instanceof Screwdriver) {
                Screwdriver tempScrew = (Screwdriver) gameObjects.get(i);
                root.getChildren().add(tempScrew.getScrewdriver());
            } else if (gameObjects.get(i) instanceof Spring) {
                Spring tempSpring = (Spring) gameObjects.get(i);
                root.getChildren().add(tempSpring.getSpring());
            } else {
                root.getChildren().add(gameObjects.get(i));
            }
        }
        root.getChildren().add(player);

    }

}
