package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Spring extends GameObject{
    private final int size = 80;
    private GraphicsContext gc;
    private ImageView imageView;
    private ImageView imageView2;
    private Group spring;
    private Group spring2;
    private int direction;

    private Boolean active = false;
    private Boolean hold;


    public Spring(int x, int y, int direction, Boolean hold) throws FileNotFoundException {
        super(x, y);
        this.hold = hold;
        type = "SPG";
        this.direction = direction;
        FileInputStream stream = new FileInputStream("src/main/java/software/engineering/contraptioncrack/gameobjects/spring.png");
        Image springImg = new Image(stream);
        imageView = new ImageView(springImg);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.preserveRatioProperty();
        imageView.setRotate(direction);

        FileInputStream stream2 = new FileInputStream("src/main/java/software/engineering/contraptioncrack/gameobjects/spring2.png");
        Image springUpImg = new Image(stream2);
        imageView2 = new ImageView(springUpImg);
        imageView2.setX(x);
        imageView2.setY(y);
        imageView2.setFitHeight(size);
        imageView2.setFitWidth(size);
        imageView2.preserveRatioProperty();
        imageView2.setRotate(direction);

        spring = new Group(imageView);
        spring2 = new Group(imageView2);
    }

    public Group getSpring(){
        if(!active)
            return spring;
        else
            return spring2;
    }

    @Override
    public void tick() {

    }
    public Boolean getActive(){
        return active;
    }
    public Boolean getHold(){
        return hold;
    }

    public int getDirection(){
        return direction;
    }

    @Override
    public void draw(GraphicsContext gc) {
    }

    @Override
    public Rectangle getBounds() {
        if(active) {
            return new Rectangle(x , y , 80, 80);
        }else{
            return new Rectangle(x + 30, y + 30, 20, 20);
        }
    }

    @Override
    public void activate() {
        active = true;
        spring = spring2;
    }
}
