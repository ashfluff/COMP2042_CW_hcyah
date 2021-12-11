package brickdestroy.models;

import main.brickdestroy.models.CementBrick;
import main.brickdestroy.models.SteelBrick;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SteelBrickTest {

    @Test
    void repair() {
        SteelBrick steelBrick = new SteelBrick(new Point(1,1), new Dimension(2,4));
        steelBrick.repair();
        assertEquals(1, steelBrick.getStrength()); //full strength is 1 because it takes one hit to break the brick
        assertEquals(!steelBrick.getIsBroken(), true);
    }

    @Test
    void impact() {
        SteelBrick steelBrick = new SteelBrick(new Point(1, 1), new Dimension(2, 4));
        steelBrick.impact();
        assertEquals(0, steelBrick.getStrength());
        assertEquals(steelBrick.getIsBroken(), true);
    }
}