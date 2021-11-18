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
package main.brickdestroy.items;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

import static main.brickdestroy.items.LevelFactory.makeChessboardLevel;
import static main.brickdestroy.items.LevelFactory.makeSingleTypeLevel;
import static main.brickdestroy.items.WallType.*;


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



    private void makeBall(Point2D ballPos){
        ball = new RubberBall(ballPos);
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickCount,int lineCount,double brickDimensionRatio){
        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickCount,lineCount,brickDimensionRatio,STEEL,CEMENT);
        return tmp;
    }

    public void move(){
        player.move();
        ball.move();
    }

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

    private boolean impactWall(){
        for(Brick b : bricks){
            int impact = b.findImpact(ball);
            switch(impact) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    return reactToUpImpact(b);
                case Brick.DOWN_IMPACT:
                    return reactToDownImpact(b);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    return reactToLeftImpact(b);
                case Brick.RIGHT_IMPACT:
                    return reactToRightImpact(b);
            }
        }
        return false;
    }



    private boolean reactToUpImpact(Brick b) {
        ball.reverseY();
        return b.setImpact(ball.down, Brick.Crack.UP);
    }

    private boolean reactToDownImpact(Brick b) {
        ball.reverseY();
        return b.setImpact(ball.up,Brick.Crack.DOWN);
    }

    private boolean reactToLeftImpact(Brick b) {
        ball.reverseX();
        return b.setImpact(ball.right,Brick.Crack.RIGHT);
    }

    private boolean reactToRightImpact(Brick b) {
        ball.reverseX();
        return b.setImpact(ball.left,Brick.Crack.LEFT);
    }

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

    public boolean isBallLost(){
        return ballLost;
    }

    public void ballReset(){
        player.moveTo(startPoint);
        ball.moveTo(startPoint);
        initialiseSpeed();
        ballLost = false;
    }

    public void wallReset(){
        for(Brick b : bricks)
            b.repair();
        brickCount = bricks.length;
        ballCount = 3;
    }

    public boolean ballEnd(){
        return ballCount == 0;
    }

    public boolean isDone(){
        return brickCount == 0;
    }

    public void nextLevel(){
        bricks = levels[level++];
        this.brickCount = bricks.length;
    }

    public boolean hasLevel(){
        return level < levels.length;
    }

    public void setBallXSpeed(int s){
        ball.setXSpeed(s);
    }

    public void setBallYSpeed(int s){
        ball.setYSpeed(s);
    }

    public void resetBallCount(){
        ballCount = 3;
    }


}
