package main.brickdestroy.models;


import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This class contains all the properties of a cement brick.
 * It also has all the properties of a brick.
 */
public class CementBrick extends Brick {


    private static final String NAME = "Cement Brick";
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;


    public CementBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH,DEF_STEPS);
        brickFace = super.brickFace;
    }

    /**
     * This method returns a cement brick in a specified location on the screen of the game.
     * @param pos The position of where the brick will be created
     * @param size The size of the brick created
     * @return A cement brick on the specified location
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method sets the impact of the brick when it is hit by the ball.
     * @param point Point at where it is impacted by the ball
     * @param dir The direction of the ball when it impacts the brick
     * @return A boolean value if the brick has been destroyed; true if brick has been destroyed, false if it has not been destroyed
     */
    @Override
    public boolean setImpact(Point2D point, int dir) {
        if(super.isBroken())
            return false;
        super.impact();
        if(!super.isBroken()){
            crack.makeCrack(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }

    /**
     * This method returns a steel brick.
     * @return
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * This method is called to update the crack on the brick when it is impacted by the ball.
     */
    private void updateBrick(){
        if(!super.isBroken()){
            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;
        }
    }

    /**
     * This method is called to repair the brick and remove all cracks.
     */
    public void repair(){
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }
}
