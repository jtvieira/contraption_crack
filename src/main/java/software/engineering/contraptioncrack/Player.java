package software.engineering.contraptioncrack;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import software.engineering.contraptioncrack.gameobjects.GameObject;

import java.util.ArrayList;


public class Player extends Rectangle{
    private static int size = 30;
    ArrayList<GameObject> gameObjects;
    private int[] movementBounds;
    public Player(int x, int y, int[] movementBounds){
        setHeight(size);
        setWidth(size);
        setFill(Color.CYAN);
        this.movementBounds = movementBounds;
        setLayoutX(x);
        setLayoutY(y);
        gameObjects = new ArrayList<>();
    }
    public Rectangle getBounds(int dx, int dy){
        return new Rectangle(dx, dy, size, size);
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
