package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Spike extends GameObject{
    private static int size = 20;
    private Boolean active;
    private Boolean orientation;
    private Color colorHolder;
    private GraphicsContext gc;
    private int group;

    public Spike(int x, int y, Color color, Boolean orientation, Boolean active, int group){
        super(x, y, color);
        type = "SPI";
        colorHolder = color;
        this.gc = getGraphicsContext2D();
        this.orientation = orientation;
        this.group = group;
        this.active = active;
        draw(this.gc);
    }

    public void activate(){
        if(active){
            active = false;
            color = Color.BLACK;
            draw(this.gc);
        }else{
            active = true;
            color = colorHolder;
            draw(this.gc);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        if(active)
            gc.setFill(color);
        else
            gc.setFill(Color.BLACK);
        /**
         * Orientation: true = horizontal ; false = vertical
         */
        if(orientation){
            gc.fillOval(x + 5, y, size, size);
            gc.fillOval(x + size + 10, y, size, size);
            gc.fillOval(x + (size * 2) + 15, y, size, size);
        }else {
            gc.fillOval(x, y + 5, size, size);
            gc.fillOval(x, y + size + 10, size, size);
            gc.fillOval(x, y + (size * 2) + 15, size, size);
        }
    }

    @Override
    public Rectangle getBounds() {
        if (active) {
            if (orientation) {
                return new Rectangle(x, y, size * 4, size);
            }
            return new Rectangle(x , y, size, size * 4);
        }
        return new Rectangle();
    }

    public int getGroup() {
        return group;
    }
}
