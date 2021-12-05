package main.brickdestroy.models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * This class has the properties of the ball in the game such as its speed and movement direction.
 */
 public abstract class Ball {

    private Shape ballFace;

    public Point2D center;

    //up, down, left, right changed to public
    public Point2D up;
    public Point2D down;
    public Point2D left;
    public Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    public Ball(Point2D center,int radiusA,int radiusB,Color inner,Color border){
        this.center = center;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(center.getX(),center.getY()-(radiusB / 2));
        down.setLocation(center.getX(),center.getY()+(radiusB / 2));

        left.setLocation(center.getX()-(radiusA /2),center.getY());
        right.setLocation(center.getX()+(radiusA /2),center.getY());


        ballFace = makeBall(center,radiusA,radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;
    }

    protected abstract Shape makeBall(Point2D center,int radiusA,int radiusB);

    /**
     * This method sets the location in which the ball is allowed to move, as well as its starting location.
     */
    public void move(){
        RectangularShape tmp = (RectangularShape) ballFace;
        center.setLocation((center.getX() + speedX),(center.getY() + speedY));
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)),width,height);
        setPoints(width,height);


        ballFace = tmp;
    }

    /**
     * This method sets the speed of the ball in the x- and y-coordinate.
     * @param x Speed of the ball in the x-coordinate
     * @param y Speed of the ball in the y-coordinate
     */
    public void setSpeed(int x,int y){
        speedX = x;
        speedY = y;
    }

    /**
     * This method sets the speed of the ball in the x-coordinate.
     * @param s Speed of the ball in the x-coordinate
     */
    public void setXSpeed(int s){
        speedX = s;
    }

    /**
     * This method sets the speed of the ball in the y-coordinate.
     * @param s Speed of the ball in the y-coordinate
     */
    public void setYSpeed(int s){
        speedY = s;
    }

    /**
     * This method allows the ball to go in the opposite direction in the x plane.
     */
    public void reverseX(){
        speedX *= -1;
    }

    /**
     * This method allows the ball to go in the opposite direction in the x plane.
     */
    public void reverseY(){
        speedY *= -1;
    }

    public Color getBorderColor(){
        return border;
    }

    public Color getInnerColor(){
        return inner;
    }

    public Point2D getPosition(){
        return center;
    }

    public Shape getBallFace(){
        return ballFace;
    }

    /**
     * This method moves the ball to a specified location within the game window.
     * @param p The x-coordinate of the location you want the ball to move.
     */
    public void moveTo(Point p){
        center.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((center.getX() -(width / 2)),(center.getY() - (height / 2)),width,height);
        ballFace = tmp;
    }

    /**
     * This method sets the location of the ball within the game window.
     * @param width Set the x-coordinate location you want the ball to travel to
     * @param height Set the y-coordinate location you want the ball to travel to
     */
    private void setPoints(double width,double height){
        up.setLocation(center.getX(),center.getY()-(height / 2));
        down.setLocation(center.getX(),center.getY()+(height / 2));

        left.setLocation(center.getX()-(width / 2),center.getY());
        right.setLocation(center.getX()+(width / 2),center.getY());
    }

    public int getSpeedX(){
        return speedX;
    }

    public int getSpeedY(){
        return speedY;
    }


}
