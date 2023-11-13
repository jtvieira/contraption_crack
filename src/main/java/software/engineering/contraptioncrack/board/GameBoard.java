package software.engineering.contraptioncrack.board;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import software.engineering.contraptioncrack.board.Tile;

public class GameBoard extends Canvas {

    /** width dimension of the game board */
    private int width;
    /**height dimension of the game board */
    private int height;
    private int [][] tileData;
    private GraphicsContext gc;
    private int tileEdgeLength;
    /**
     * The public constructor for GameBoard
     * @param width the width in pixels of the game board
     * @param height the height of the game board in pixels
     * */
    public GameBoard(int width, int height, int tileEdgeLength, int [][] tileData){
        this.width = width;
        this.height = height;
        this.tileData = tileData;
        this.tileEdgeLength = tileEdgeLength;
        this.setWidth(900);
        this.setHeight(900);
        gc = getGraphicsContext2D();
        int firstTileX = (900 - width) / 2;
        int firstTileY = (900 - height) / 2;
        this.drawTiles(gc, firstTileX, firstTileY);
    }

    private void drawTiles(GraphicsContext gc, int firstTileX, int firstTileY) {
        int xPosTopRightCorner = firstTileX;
        int yPosTopRightCorner = firstTileY;
        for(int[] row: tileData) {
            for(int tile: row) {
                Tile t = new Tile(tileEdgeLength, tileEdgeLength, xPosTopRightCorner, yPosTopRightCorner);
                switch(tile) {
                    case 0:
                        t.setColor(Color.BLACK);
                        break;
                    case 1:
                        t.setColor(Color.WHITE);
                        break;
                    case 2:
                        Color c = new Color(.15, .15, .15, 1);
                        t.setColor(c);
                        break;
                    case 3:
                        t.setColor(Color.LIGHTGRAY);
                        break;
                    case 4:
                        t.setColor(Color.LIGHTSKYBLUE);
                        break;
                    default:
                        Color tempColor = Color.GRAY;
                        t.setColor(tempColor.darker());
                        break;
                }
                t.draw(gc);
                xPosTopRightCorner += tileEdgeLength;
            }
            xPosTopRightCorner = firstTileX;
            yPosTopRightCorner += tileEdgeLength;
        }

    }

}
