package brickdestroy.models;

import main.brickdestroy.models.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class PlayerTest {

    @Test
    void moveLeft() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.moveLeft();
        Assertions.assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.moveRight();
        Assertions.assertEquals(5, player.getMoveAmount());
    }

    @Test
    void stop() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.stop();
        Assertions.assertEquals(0, player.getMoveAmount());
    }
}