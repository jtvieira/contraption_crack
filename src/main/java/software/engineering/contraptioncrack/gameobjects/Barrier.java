package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Barrier extends GameObject{
    private int length, width;
    public Barrier(int x, int y, int length, int width) {
        super(x, y);
        type = "BAR";
        this.length = length;
        this.width = width;
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(GraphicsContext gc) {

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x , y, length, width);
    }

    @Override
    public void activate() {

    }
}
