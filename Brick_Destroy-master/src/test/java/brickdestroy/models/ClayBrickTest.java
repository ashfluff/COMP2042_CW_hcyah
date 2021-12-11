package brickdestroy.models;

import main.brickdestroy.models.CementBrick;
import main.brickdestroy.models.ClayBrick;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ClayBrickTest {

    @Test
    void repair() {
        ClayBrick clayBrick = new ClayBrick(new Point(1,1), new Dimension(2,4));
        clayBrick.repair();
        assertEquals(1, clayBrick.getStrength()); //full strength is 2 because it takes two hits to break the brick
        assertEquals(!clayBrick.getIsBroken(), true);
    }

    @Test
    void impact() {
        ClayBrick clayBrick = new ClayBrick(new Point(1,1), new Dimension(2,4));
        clayBrick.impact();
        assertEquals(0, clayBrick.getStrength());
        assertEquals(clayBrick.getIsBroken(), true);
    }

}