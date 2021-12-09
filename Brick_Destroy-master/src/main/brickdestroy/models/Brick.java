package main.brickdestroy.models;

import java.awt.*;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * This class contains the properties of the bricks in the game, such as it's appearance and attitudes.
 */
abstract public class Brick  {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    /**
     * ImpactDirection used to be assigned to integers. Changed to enum for simplicity and convenience.
     */
    enum ImpactDirection {
        UP_IMPACT,
        DOWN_IMPACT,
        LEFT_IMPACT,
        RIGHT_IMPACT,
        NO_IMPACT
    }


    /**
     * This class contains the attributes of the crack that can appear in bricks.
     */
    public class Crack{

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10;
        public static final int RIGHT = 20;
        public static final int UP = 30;
        public static final int DOWN = 40;
        public static final int VERTICAL = 100;
        public static final int HORIZONTAL = 200;



        private GeneralPath crack;

        private int crackDepth;
        private int steps;


        public Crack(int crackDepth, int steps){

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }



        public GeneralPath draw(){

            return crack;
        }

        public void reset(){
            crack.reset();
        }

        /**
         * This determines where to draw cracks in certain bricks when it is impacted by the ball.
         * @param point Point of the brick which is hit by the ball
         * @param direction Direction in which the ball is traveling when it hit the brick
         */
        public void makeCrack(Point2D point, int direction){
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int)point.getX(),(int)point.getY());
            Point start = new Point();
            Point end = new Point();


            switch(direction){
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,VERTICAL);
                    makeCrack(impact,tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start,end,HORIZONTAL);
                    makeCrack(impact,tmp);

                    break;

            }
        }

        /**
         * This method draws the cracks of the brick when it is impacted by the ball.
         * @param start The starting path of the crack in the brick
         * @param end The ending path of the crack in the brick
         */
        protected void makeCrack(Point start, Point end){

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x,start.y);

            double w = (end.x - start.x) / (double)steps;
            double h = (end.y - start.y) / (double)steps;

            int bound = crackDepth;
            int jump  = bound * 5;

            double x,y;

            for(int i = 1; i < steps;i++){

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if(inMiddle(i,CRACK_SECTIONS,steps))
                    y += jumps(jump,JUMP_PROBABILITY);

                path.lineTo(x,y);

            }

            path.lineTo(end.x,end.y);
            crack.append(path,true);
        }

        private int randomInBounds(int bound){
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i,int steps,int divisions){
            int low = (steps / divisions);
            int up = low * (divisions - 1);

            return  (i > low) && (i < up);
        }

        private int jumps(int bound,double probability){

            if(rnd.nextDouble() > probability)
                return randomInBounds(bound);
            return  0;

        }

        private Point makeRandomPoint(Point from,Point to, int direction){

            Point out = new Point();
            int pos;

            switch(direction){
                case HORIZONTAL:
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos,to.y);
                    break;
                case VERTICAL:
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x,pos);
                    break;
            }
            return out;
        }

    }

    private static Random rnd;

    private String name;
    public Shape brickFace;

    private Color border;
    private Color inner;

    private int fullStrength;
    private int strength;

    private boolean broken;

    /**
     * This method contains all attributes of the brick in the game including its appearance.
     * @param name Type of brick
     * @param pos Position of the brick
     * @param size Size of the brick
     * @param border Color of the brick's border
     * @param inner Color of the brick's inner part
     * @param strength Determines how many impact the brick can take from the ball before being destroyed
     */
    public Brick(String name, Point pos,Dimension size,Color border,Color inner,int strength){
        rnd = new Random();
        broken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    public  boolean setImpact(Point2D point , int dir){
        if(broken)
            return false;
        impact();
        return  broken;
    }

    public abstract Shape getBrick();



    public Color getBorderColor(){
        return  border;
    }

    public Color getInnerColor(){
        return inner;
    }

    /**
     * This method determines the direction of the ball after impacting the brick.
     * Initially returns an int value, now returns enum ImpactDirection
     * if(broken) initially returns 0. Now it returns NO_IMPACT meaning the ball has not yet hit anything.
     * @param b Ball
     * @return the direction of the ball after impact
     */
    public final ImpactDirection findImpact(Ball b){
        if(broken)
            return ImpactDirection.NO_IMPACT;
        ImpactDirection out = ImpactDirection.NO_IMPACT;
        if(brickFace.contains(b.right))
            out = ImpactDirection.LEFT_IMPACT;
        else if(brickFace.contains(b.left))
            out = ImpactDirection.RIGHT_IMPACT;
        else if(brickFace.contains(b.up))
            out = ImpactDirection.DOWN_IMPACT;
        else if(brickFace.contains(b.down))
            out = ImpactDirection.UP_IMPACT;
        return out;
    }

    /**
     * This method is called when the brick has been broken.
     * @return
     */
    public final boolean isBroken(){
        return broken;
    }

    /**
     * This method is called to repair the brick to its original state.
     */
    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    /**
     * This method is called when the brick has been impacted by the ball.
     */
    public void impact(){
        strength--;
        boolean isStrengthZero = strength == 0;
        this.broken = isStrengthZero;
    }



}





