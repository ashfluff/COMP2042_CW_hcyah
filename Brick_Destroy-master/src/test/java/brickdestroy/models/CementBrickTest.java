package brickdestroy.models;

import main.brickdestroy.models.CementBrick;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class CementBrickTest {

    @Test
    void repair() {
        CementBrick cementBrick = new CementBrick(new Point(1,1), new Dimension(2,4));
        cementBrick.repair();
        assertEquals(2, cementBrick.getStrength()); //full strength is 2 because it takes two hits to break the brick
        assertEquals(!cementBrick.getIsBroken(), true);
    }

    @Test
    void impact() {
    }

}