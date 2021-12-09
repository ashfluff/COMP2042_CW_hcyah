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

/**
 * This class contains all the properties of a steel brick.
 * It also has all the properties of a brick.
 */
public class SteelBrick extends Brick {

    private static final String NAME = "Steel Brick";
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    public SteelBrick(Point point, Dimension size){
        super(NAME,point,size,DEF_BORDER,DEF_INNER,STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;
    }

    /**
     * This method creates a brick in a specific position.
     * @param pos The position of the brick
     * @param size The size of the brick
     * @return A brick in a position on the screen
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos,size);
    }

    /**
     * This method is called to return a steel brick.
     * @return A steel brick
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    /**
     * This method sets the impact of the brick when it is hit by the ball.
     * @param point Point at where it is impacted by the ball
     * @param dir The direction of the ball when it impacts the brick
     * @return A boolean value if the brick has been destroyed; true if brick has been destroyed, false if it has not been destroyed
     */
    public  boolean setImpact(Point2D point , int dir){
        if(super.isBroken())
            return false;
        impact();
        return  super.isBroken();
    }

    /**
     * This method is called to determine if the brick has been impacted by the ball.
     */
    public void impact(){
        if(rnd.nextDouble() < STEEL_PROBABILITY){
            super.impact();
        }
    }

}
