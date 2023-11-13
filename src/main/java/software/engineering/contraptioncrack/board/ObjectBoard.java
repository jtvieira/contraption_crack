package software.engineering.contraptioncrack.board;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import software.engineering.contraptioncrack.gameobjects.GameObject;

import java.util.ArrayList;

public class ObjectBoard extends Canvas {
    private GraphicsContext gc;
    private ArrayList<GameObject> gameObjects;
    private Boolean[] spikeIsUp;
    private int[] spike;

    public ObjectBoard(){
        gc = getGraphicsContext2D();
        gameObjects = new ArrayList<>();
    }




    public void addGameObject(GameObject objectIn){
        gameObjects.add(objectIn);
    }
    public void removeGameObject(GameObject objectIn){
        gameObjects.remove(objectIn);
    }
    public void clearGameObjects(){
        gameObjects.clear();
    }

}
