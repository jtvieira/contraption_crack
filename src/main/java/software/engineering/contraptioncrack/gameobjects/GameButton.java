package software.engineering.contraptioncrack.gameobjects;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class GameButton extends GameObject{
    private static int size = 80;
    boolean pressed = false;
    private GraphicsContext gc;
    private int group;
    private ArrayList<Spike> spikeList;
    String shape;
    Boolean timed;

    public GameButton(int x, int y, Color color, int group, String shape, Boolean timed, ArrayList<Spike> spikeList){
        super(x, y, color);
        type = "BTN";
        this.timed = timed;
        this.gc = getGraphicsContext2D();
        this.shape = shape;
        this.group = group;
        this.spikeList = spikeList;
        draw(this.gc);

    }
    @Override
    public void tick() {

    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GREY);
        gc.fillRect(x, y, size, size);
        gc.setFill(color);
        if(shape.equals("SQUARE")){
            gc.fillRect(x + 5, y + 5, size - 10, size - 10);
        }else if(shape.equals("CIRCLE")){
            gc.fillOval(x + 5, y + 5, size - 10, size - 10);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x + 5, y + 5, size - 10, size - 10);
    }
    public void pressButton(){
        if(!pressed)
            pressed = true;
        else if(pressed)
            pressed = false;
    }
    public void activate(){
        if(!pressed){
            this.pressButton();
            color = color.darker();
            draw(this.gc);
            if(timed) {
                timer.start();
            }
            for(Spike s : spikeList) {
                if(s.getGroup() == this.group) {
                    s.activate();
                }
            }
        }
    }
    public void deactivate(){
        if(pressed){
            this.pressButton();
            color = color.brighter();
            draw(this.gc);
            if(timed){
                timer.stop();
            }
            for (Spike s: spikeList){
                if (s.getGroup() == this.group){
                    s.activate();
                }
            }
        }
    }

    private AnimationTimer timer = new AnimationTimer() {
        long prevTime = System.nanoTime();
        long tracker = 0L;
        int time = 0;
        @Override
        public void handle(long l) {
            tracker += l - prevTime;
            if(tracker > 1000000000L){
                time++;
                tracker -= 1000000000L;
            }
            if(time == 1200){
                deactivate();
                time = 0;
            }
        }
    };

}
