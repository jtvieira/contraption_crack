package software.engineering.contraptioncrack.gameobjects;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Jukebox extends GameObject{

    private final int size = 80;
    private GraphicsContext gc;

    private AnimationTimer at = new AnimationTimer() {
        Boolean ticker = false;
        @Override
        public void handle(long now) {
            Color c = new Color(.3, .6, .6, 1);
            int xPos = x;
            int tempWidth = size;
            long n = now % 100000000000l;
            if((n % 2000) == 0) {

                if(ticker) {
                    ticker = false;
                } else {
                    ticker = true;
                }

            }
            if(ticker) {
                color = c;

            } else {
                color = Color.CYAN;

            }
            gc.clearRect(xPos, y, tempWidth, size);
            draw(xPos, tempWidth);

        }
    };

    public Jukebox(int x, int y, Color c) {
        super(x, y, c);
        type = "JUK";
        this.gc = getGraphicsContext2D();
        this.draw(gc);
    }
    @Override
    public void tick() {
        at.start();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.color);
        gc.fillRect(x, y, size, size);
    }

    public void draw(int xPos, int tempWidth) {

        gc.setFill(this.color);
        gc.fillRect(xPos, y, tempWidth, size);
    }

    @Override
    public Rectangle getBounds() {

        Rectangle r = new Rectangle(x, y, size, size);
        return r;
    }

    public GraphicsContext getGc() {
        return this.gc;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public void activate() {

    }
}
