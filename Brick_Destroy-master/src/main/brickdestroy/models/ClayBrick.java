package main.brickdestroy.models;


import java.awt.*;
import java.awt.Point;


/**
 * This class contains all the properties of a clay brick.
 * It also has all the properties of a brick.
 */
public class ClayBrick extends Brick {

    private static final String NAME = "Clay Brick";
    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;



    public ClayBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CLAY_STRENGTH);
    }

    /**
     * This method creates a clay brick in a specified position on the sceen.
     * @param pos The position where the clay brick will be created
     * @param size The size of the clay brick
     * @return The clay brick in a position on the screen.
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method returns a clay brick.
     * @return
     */
    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
