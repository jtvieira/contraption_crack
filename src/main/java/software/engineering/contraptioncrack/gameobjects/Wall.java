package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;

public class Wall extends GameObject{
    private int length, width;
    private Boolean isTimedWall;
    private GraphicsContext gc;
    private boolean isActive = true;
    private int delay;
    Timer timer = new Timer();

    public Wall(int x, int y, Color color, int length, int width, Boolean isTimedWall, int delay) {
        super(x, y, color);
        type = "WALL";
        this.length = length;
        this.width = width;
        this.isTimedWall = isTimedWall;
        this.gc = getGraphicsContext2D();
        this.delay = delay;
        draw(this.gc);
        class tt extends TimerTask {

            @Override
            public void run() {
                if(isActive) {
                    deactivate();
                } else {
                    activate();
                }
            }
        }
        if(isTimedWall)
            timer.schedule(new tt(), delay, 5000);
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x, y, length, width);
    }

    public void clear(GraphicsContext gc) {
        gc.clearRect(x, y, length, width);
    }

    @Override
    public Rectangle getBounds() {
        if(isActive) {
            return new Rectangle(x, y, length, width);
        }
        else {
            return new Rectangle();
        }
    }

    @Override
    public void activate() {
        draw(this.gc);
        isActive = true;
    }

    public void deactivate() {
        clear(this.gc);
        isActive = false;
    }
}
