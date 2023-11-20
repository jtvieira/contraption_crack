package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class DropdownMenu extends VBox {

    private static DropdownMenu menu;

    public static synchronized DropdownMenu getInstance() {
        if(menu == null)
            menu = new DropdownMenu();
        return menu;
    }

    private DropdownMenu() {
        menu.setLayoutX(0);
        menu.setLayoutY(0);
        menu.setPrefSize(160, 80);
        this.populateMenu();
    }

    private void populateMenu() {
        Button save = createSave();
        Button load = createLoad();
        Button restartArea = createRestartArea();
        Button restartLevel = createRestartLevel();

        menu.getChildren().addAll(save, load, restartArea, restartLevel);
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

}
