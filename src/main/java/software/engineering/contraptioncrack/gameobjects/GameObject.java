package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class GameObject extends Canvas {
    protected GraphicsContext gc;
    protected int x, y;
    protected int verticalBounds, horizontalBounds;
    protected Color color;
    protected String leadsToLevel;
    /**
     * types: Button = BTN, Spike = SPI, WALL = WALL, Spring = SPG, Jukebox = JUK, Screwdriver = SCW, ExitTile = EXT;
     */
    protected String type;
    public GameObject(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
        setWidth(900);
        setHeight(900);
        gc = getGraphicsContext2D();
    }
    public GameObject(int x, int y){
        this.x = x;
        this.y = y;
        gc = getGraphicsContext2D();
    }


    public abstract void tick();
    public abstract void draw(GraphicsContext gc);
    public abstract Rectangle getBounds();

    public void setX(int x){
        this.x = x;
    }
    public int getX(){
        return x;
    }
    public void setY(int y){
        this.y = y;
    }
    public int getY(int y){
        return y;
    }
    public void setColor(Color color){
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public abstract void activate();
}
