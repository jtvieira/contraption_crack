package software.engineering.contraptioncrack.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import software.engineering.contraptioncrack.gameobjects.GameObject;

import java.util.ArrayList;


public class Player extends Rectangle{
    private final static int SIZE = 30;
    private ArrayList<GameObject> gameObjects;
    private int[] movementBounds = {900,900};

    public Player(int x, int y){
        setHeight(SIZE);
        setWidth(SIZE);
        setFill(Color.CYAN);
        setLayoutX(x);
        setLayoutY(y);
        gameObjects = new ArrayList<>();
    }
    public Player() {

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
        setLayoutX(xPos);
        setLayoutY(yPos);
    }
}
