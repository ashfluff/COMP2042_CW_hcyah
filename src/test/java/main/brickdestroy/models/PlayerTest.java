package main.brickdestroy.models;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void moveLeft() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.moveLeft();
        assertEquals(-5, player.getMoveAmount());
    }

    @Test
    void moveRight() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.moveRight();
        assertEquals(5, player.getMoveAmount());
    }

    @Test
    void stop() {
        Player player = new Player(new Point(1,1),2,3, new Rectangle(10, 10));
        player.stop();
        assertEquals(0, player.getMoveAmount());
    }
}