package brickdestroy.models;

import main.brickdestroy.models.RubberBall;
import main.brickdestroy.models.Wall;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class WallTest {

    @Test
    void findImpacts() {
        Wall wall = new Wall(new Rectangle(0, 0), 5, 1, 2, new Point(0,0));
        wall.findImpacts();
        assertEquals(0, wall.getBrickCount());
    }

    @Test
    void ballReset() {
        Wall wall = new Wall(new Rectangle(0, 0), 5, 1, 2, new Point(0,0));
        wall.ballReset();
        assertEquals(wall.getBallLost() == false, true);
    }


    @Test
    void resetBallCount() {
        Wall wall = new Wall(new Rectangle(0, 0), 5, 1, 2, new Point(0,0));
        wall.resetBallCount();
        assertEquals(3, wall.getBallCount());
    }
}