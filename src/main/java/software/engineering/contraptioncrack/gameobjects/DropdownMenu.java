package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import software.engineering.contraptioncrack.level.LevelLoader;

import java.io.FileNotFoundException;

public class DropdownMenu extends VBox {

    private static DropdownMenu menu;
    private LevelLoader levelLoader;

    public static synchronized DropdownMenu getInstance(LevelLoader levelLoader) {
        if(menu == null)
            menu = new DropdownMenu(levelLoader);
        return menu;
    }

    public static synchronized DropdownMenu getInstance() {
        return menu == null ? new DropdownMenu() : menu;
    }

    private DropdownMenu() {}

    private DropdownMenu(LevelLoader levelLoader) {
        System.out.println("Creating new dropdown Menu");
        menu = getInstance();
        menu.setLayoutX(0);
        menu.setLayoutY(0);
        menu.setPrefSize(160, 80);
        this.populateMenu();
        this.levelLoader = levelLoader;
    }

    private void populateMenu() {
        Button save = createSave();
        Button load = createLoad();
        Button restartArea = createRestartArea();
        Button restartLevel = createRestartLevel();
        Button exitGame = createExitGame();
        menu.getChildren().addAll(save, load, restartArea, restartLevel, exitGame);
    }

    private Button createSave() {
        Button save = new Button("Save");
        save.setPrefSize(160, 20);
        //        save.setOnAction(actionEvent -> saveState());
        return save;
    }

    private Button createLoad() {
        Button load = new Button("Load");
        load.setPrefSize(160, 20);
        //        load.setOnAction(actionEvent -> {
//            try {
//                loadState();
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
        return load;
    }

    private Button createRestartArea() {
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
        return restartArea;
    }

    private Button createRestartLevel() {
        Button restartLevel = new Button("Restart Level");
        restartLevel.setPrefSize(160, 20);
//        restartLevel.setOnAction(actionEvent ->  {
//                try {
//                    loadLevel("Level1");
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//        });
        return restartLevel;
    }

    private Button createExitGame() {
        Button exitGame = new Button("Exit Game");
        exitGame.setPrefSize(160, 20);
        exitGame.setOnAction(actionEvent -> {
            try {
                levelLoader.loadLevel("level-1");
            } catch(FileNotFoundException fnfe){
                System.out.println("File not found");
            }
        });
        return exitGame;
    }

}
