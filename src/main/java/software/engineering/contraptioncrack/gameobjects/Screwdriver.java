package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Screwdriver extends GameObject{
    private Boolean pickedUp = false;
    private ImageView imageView;
    private Group screwdriver;
    public Screwdriver(int x, int y) throws FileNotFoundException {
        super(x, y);
        type = "SCW";
        FileInputStream stream = new FileInputStream("src/main/java/software/engineering/contraptioncrack/resources/screwdriver.png");
        Image screwdriverImg = new Image(stream);
        imageView = new ImageView(screwdriverImg);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(80);
        imageView.setFitWidth(160);
        imageView.preserveRatioProperty();
        screwdriver = new Group(imageView);
    }
    public Group getScrewdriver(){
        return screwdriver;
    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(GraphicsContext gc) {

    }

    @Override
    public Rectangle getBounds() {
        if(!pickedUp)
            return new Rectangle(x, y, 160, 60);
        else
            return new Rectangle();
    }

    @Override
    public void activate() {
        imageView.setVisible(false);
        pickedUp = true;
    }
}
