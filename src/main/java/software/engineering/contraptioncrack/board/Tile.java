package software.engineering.contraptioncrack.board;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Tile extends Canvas {
    private int width;
    private int height;
    private int posX;
    private int posY;
    private Paint color;

    public Tile(int height, int width, int posX, int posY, Color color){
        this.setHeight(height);
        this.setWidth(width);
        this.setPosX(posX);
        this.setPosY(posY);
        this.setColor(color);
    }
    public Tile(int height, int width, int posX, int posY){
        this.setHeight(height);
        this.setWidth(width);
        this.setPosX(posX);
        this.setPosY(posY);
    }

    public void draw(GraphicsContext gc){
        gc.setFill(Color.GRAY);
        gc.fillRect(posX, posY, width, height);
        gc.setFill(this.color);
        gc.fillRect(posX + 2, posY + 2, width - 4, height - 4);

    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public Paint getColor() {
        return color;
    }

    public void setColor(Paint color) {
        this.color = color;
    }
}