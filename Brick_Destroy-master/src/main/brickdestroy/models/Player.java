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

/**
 * This class contains methods with the attributes of the rectangle block that the player is able to control and play as.
 */
public class Player {


    public static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    public static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point ballPoint;
    private int moveAmount;
    private int min;
    private int max;


    public Player(Point ballPoint,int width,int height,Rectangle container) {
        this.ballPoint = ballPoint;
        moveAmount = 0;
        playerFace = makePlayerFace(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * Creates the rectangle and allocates its location that the player will be able to play as.
     * Renamed from makeRectangle
     * @param width The width of the rectangle block
     * @param height The height of the rectangle block
     * @return The shape of the player's rectangle as well as its location on the screen of the game.
     */
    private Rectangle makePlayerFace(int width, int height){
        Point p = new Point((int)(ballPoint.getX() - (width / 2)),(int)ballPoint.getY());
        return  new Rectangle(p,new Dimension(width,height));
    }

    public boolean impact(Ball b){

        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * This method moves the player in a specified location on the screen of the game.
     */
    public void move(){
        double x = ballPoint.getX() + moveAmount;
        if(x < min || x > max)
            return;
        ballPoint.setLocation(x,ballPoint.getY());
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }


    /**
     * This method moves the player to the left of the screen.
     */
    public void moveLeft(){
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * This method moves the player to the right of the screen.
     */
    public void moveRight(){
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * This method stops the player from moving.
     */
    public void stop(){
        moveAmount = 0;
    }

    public Shape getPlayerFace(){
        return  playerFace;
    }

    /**
     * This method moves the player to a specified location on the screen in relation to the ball.
     * @param p x- and y-coordinates on the screen
     */
    public void moveTo(Point p){
        ballPoint.setLocation(p);
        playerFace.setLocation(ballPoint.x - (int)playerFace.getWidth()/2,ballPoint.y);
    }

    public int getMoveAmount() {
        return moveAmount;
    }
}
