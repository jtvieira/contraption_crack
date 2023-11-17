package software.engineering.contraptioncrack;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import software.engineering.contraptioncrack.game.GameController;
import software.engineering.contraptioncrack.game.Player;
import software.engineering.contraptioncrack.gameobjects.GameObject;
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

    @Override
    public void start(Stage stage) throws IOException {
        GameController gameController = new GameController(stage);
        gameController.startGame();
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