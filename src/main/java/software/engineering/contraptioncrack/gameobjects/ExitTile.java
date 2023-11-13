package software.engineering.contraptioncrack.gameobjects;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ExitTile extends GameObject{
    private Group eTile;
    private ImageView imageView;
    private Boolean active = false;
    private Image exitTile;
    private int rotateValue;
    private final int size = 80;

    public ExitTile(int x, int y, int rotateValue, int level) throws FileNotFoundException {
        super(x, y);
        type = "EXT";
        this.leadsToLevel = readLevel(level);
        FileInputStream stream = new FileInputStream("src/main/java/software/engineering/contraptioncrack/resources/arrow_square.jpg");
        this.rotateValue = rotateValue;
        exitTile = new Image(stream);
        imageView = new ImageView(exitTile);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(size);
        imageView.setFitWidth(size);
        imageView.setRotate(rotateValue);
        imageView.preserveRatioProperty();
        eTile = new Group(imageView);
    }

    public Group getExitTile(){
        return eTile;
    }
    @Override
    public void tick() {

    }
    public String getLevel(){
        return leadsToLevel;
    }

    public String readLevel(int level){
        switch (level){
            case 1:
                return "Level1";
            case 2:
                return "Level2";
            case 3:
                return "Level3";
            case 4:
                return "Level4";
            default:
                return "Level0";
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
    }

    @Override
    public Rectangle getBounds() {
        if (!active) {
            if(rotateValue == 0){
                return new Rectangle(x, y, 80, 20);
            }else if(rotateValue == 90){
                return new Rectangle(x + 60, y, 20, 80);
            }else if (rotateValue == 180) {
                return new Rectangle(x, y + 60, 80, 20);
            }else if (rotateValue == 270){
                return new Rectangle(x, y, 20, 80);
            }
        }
        return new Rectangle();
    }

    @Override
    public void activate() {
        active = true;
    }
}
