package software.engineering.contraptioncrack.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import software.engineering.contraptioncrack.gameobjects.GameObject;

import java.util.ArrayList;


public class Player extends Rectangle{
    private final static int SIZE = 30;
    private ArrayList<GameObject> gameObjects;
    private int[] movementBounds = {900,900};
    private static Player player;

    private Player(int x, int y){
        setHeight(SIZE);
        setWidth(SIZE);
        setFill(Color.CYAN);
        relocate(x, y);
        gameObjects = new ArrayList<>();
    }
    private Player() {
        gameObjects = new ArrayList<>();
    }
    public static synchronized Player getInstance() {
        if(player == null) {
            player =  new Player();
        }
        return player;
    }
    public void initialize(int x, int y) {
        setHeight(SIZE);
        setWidth(SIZE);
        setFill(Color.CYAN);
        this.reposition(x, y);

    }
    public void reposition(int x, int y) {
        relocate(x, y);
    }
    public Rectangle getBounds(int dx, int dy){
        return new Rectangle(dx, dy, SIZE, SIZE);
    }
    public void addGameObject(GameObject gameObjectIn){
        gameObjects.add(gameObjectIn);
    }
    public void clearGameObjects(){
        gameObjects.clear();
    }

    public void movePlayer(int dx, int dy) {
        this.move(dx, dy);
    }

    private void move(int dx, int dy) {
        double xPos = getLayoutX() + dx;
        double yPos = getLayoutY() + dy;

        int leftBound = 0;
        int topBound = 0;
        int rightBound = movementBounds[0] - 30;
        int bottomBound = movementBounds[1] - 30;

        if(xPos < leftBound || xPos > rightBound) {
            return;
        }
        if(yPos < topBound || yPos > bottomBound) {
            return;
        }
//        setLayoutX(xPos);
//        setLayoutY(yPos);
        relocate(xPos, yPos);
    }
}
