/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package main.brickdestroy.models;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static main.brickdestroy.models.LevelFactory.makeChessboardLevel;
import static main.brickdestroy.models.LevelFactory.makeSingleTypeLevel;
import static main.brickdestroy.models.WallType.*;

/**
 * This class contains methods to create the wall of bricks in each level of the game.
 */
public class Wall {

    private static final int LEVELS_COUNT = 4;


    private Random rnd;
    private Rectangle area;

    public Brick[] bricks;
    public Ball ball;
    public Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos){

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea,brickCount,lineCount,brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        initialiseSpeed();

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;


    }

    /**
     * This method initialises the speed of the ball.
     * It initialises the speed at random.
     */
    private void initialiseSpeed() {
        int speedX, speedY;
        do {
            speedX = rnd.nextInt(5) - 2;
        } while (speedX == 0);
        do {
            speedY = -rnd.nextInt(3);
        } while (speedY == 0);

        ball.setSpeed(speedX, speedY);
    }


    /**
     * This method creates the ball.
     * @param ballPos The position of the ball
     */
    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    /**
     * This method creates the wall for each level of the game. Each level can have multiple type of bricks.
     * @param drawArea The area which the wall will take up
     * @param brickCount The number of bricks in a wall
     * @param lineCount The number of lines of bricks in a wall
     * @param brickDimensionRatio The area of the brick
     * @return The wall for each level of the game
     */
    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    /**
     * This method calls methods to move the player and ball.
     */
    public void move(){
        player.move();
        ball.move();
    }

    /**
     * This method finds when the ball has made impact with the wall.
     */
    public void findImpacts(){
        if(player.impact(ball)){
            ball.reverseY();
        }
        else if(impactWall()){
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickCount--;
        }
        else if(impactBorder()) {
            ball.reverseX();
        }
        else if(ball.getPosition().getY() < area.getY()){
            ball.reverseY();
        }
        else if(ball.getPosition().getY() > area.getY() + area.getHeight()){
            ballCount--;
            ballLost = true;
        }
    }

    /**
     * This method will be called when the ball has made impact with the wall.
     * @return A boolean value; true if the ball has made impact, false if the ball did not make impact
     */
    private boolean impactWall(){
        for(Brick b : bricks){
             Brick.ImpactDirection impact = b.findImpact(ball);
            switch(impact) {
                //Vertical Impact
                case UP_IMPACT:
                    return reactToUpImpact(b);
                case DOWN_IMPACT:
                    return reactToDownImpact(b);

                //Horizontal Impact
                case LEFT_IMPACT:
                    return reactToLeftImpact(b);
                case RIGHT_IMPACT:
                    return reactToRightImpact(b);

                    //else
                case NO_IMPACT: continue;
            }
        }
        return false;
    }


    /**
     * This method is called when the ball is moving upwards and impacts the wall.
     * @param b The brick the ball has impacted
     * @return Direction the ball will go after impact
     */
    private boolean reactToUpImpact(Brick b) {
        ball.reverseY();
        return b.setImpact(ball.down, Brick.Crack.UP);
    }

    /**
     * This method is called when the ball is moving downwards and impacts the wall.
     * @param b The brick the ball has impacted
     * @return Direction the ball will go after impact
     */
    private boolean reactToDownImpact(Brick b) {
        ball.reverseY();
        return b.setImpact(ball.up,Brick.Crack.DOWN);
    }

    /**
     * This method is called when the ball is moving left and impacts the wall.
     * @param b The brick the ball has impacted
     * @return Direction the ball will go after impact
     */
    private boolean reactToLeftImpact(Brick b) {
        ball.reverseX();
        return b.setImpact(ball.right,Brick.Crack.RIGHT);
    }

    /**
     * This method is called when the ball is moving right and impacts the wall.
     * @param b The brick the ball has impacted
     * @return Direction the ball will go after impact
     */
    private boolean reactToRightImpact(Brick b) {
        ball.reverseX();
        return b.setImpact(ball.left,Brick.Crack.LEFT);
    }

    /**
     * This methopd is called when the ball has made impact with the border of the screen, not the wall.
     * @return
     */
    private boolean impactBorder(){
        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));
    }

    public int getBrickCount(){
        return brickCount;
    }

    public int getBallCount(){
        return ballCount;
    }

    /**
     * This method determines whether or not the ball has been lost.
     * @return
     */
    public boolean isBallLost(){
        return ballLost;
    }

    /**
     * This method resets the ball and player to their starting location
     */
    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        initialiseSpeed();
        ballLost = false;
    }

    /**
     * This method resets the wall.
     */
    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    /**
     * This method is called when the there are no balls left.
     * @return
     */
    public boolean ballEnd(){
        return ballCount == 0;
    }

    /**
     * This method is called when all bricks in the level has been destroyed.
     * @return
     */
    public boolean isDone(){
        return brickCount == 0;
    }

    /**
     * This method is called to go to the next level.
     */
    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    /**
     * This method is called to check that there are still levels after the current one it is called from.
     * @return A boolean value; true if the current level is not the last level, false if the current level is the last level
     */
    public boolean hasLevel(){
        return level < levels.length;
    }

    /**
     * This method sets the x-coordinate speed of the ball.
     * @param s The speed of the ball in the x-coordinate
     */
    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    /**
     * This method sets the y-coordinate speed of the ball.
     * @param s The speed of the ball in the y-coordinate
     */
    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    /**
     * This method resets the number of balls in the game, i.e. 3.
     */
    public void resetBallCount(){
        ballCount = 3;
    }

    public boolean getBallLost() {
        return ballLost;
    }

}
