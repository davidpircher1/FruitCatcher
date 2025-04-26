package Tests;

import Game.Collidable;
import Game.GameObject;
import Game.Player.PlayerController;
import Game.Player.PlayerModel;
import Loger.ErrorLog;
import Loger.InfoLog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Jednotkove testy pre triedu PlayerModel.
 */
class PlayerModelTest {

    private PlayerModel player;
    private final int startX = 50;
    private final int startY = 60;
    private final int expectedWidth = 100;
    private final int expectedHeight = 100;
    private final int expectedSpeed = 3;

    private boolean imageResourceExists;

    /**
     * Pripravi testovacie prostredie pred kazdym testom.
     * Inicializuje PlayerModel a zisti dostupnost obrazka.
     */
    @BeforeEach
    void setUp() {
        imageResourceExists = false;
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("Images/player.png")) {
            if (is != null) {
                imageResourceExists = true;
            }
        } catch (Exception e) {
            imageResourceExists = false;
        }
        player = new PlayerModel(startX, startY);
    }

    /**
     * Testuje inicializaciu vlastnosti objektu v konstruktore.
     */
    @Test
    void constructorShouldInitializeProperties() {
        assertEquals(startX, player.getX());
        assertEquals(startY, player.getY());
        assertEquals(expectedWidth, player.getWidth());
        assertEquals(expectedHeight, player.getHeight());

        assertNotNull(player.getController());
        assertTrue(player.getController() != null);

        if (imageResourceExists) {
            JLabel sprite = player.getObjectSprite();
            assertNotNull(sprite);
            assertNotNull(sprite.getIcon());
            assertTrue(sprite.getIcon() instanceof ImageIcon);
            assertEquals(startX, sprite.getX());
            assertEquals(startY, sprite.getY());
            assertEquals(expectedWidth, sprite.getWidth());
            assertEquals(expectedHeight, sprite.getHeight());
        } else {
            assertNull(player.getObjectSprite());
        }
    }

    /**
     * Testuje pohyb hraca dolava.
     */
    @Test
    void moveLeftShouldDecreaseXAndKeepY() {
        int initialX = player.getX();
        int initialY = player.getY();

        player.moveLeft();

        assertEquals(initialX - expectedSpeed, player.getX());
        assertEquals(initialY, player.getY());

        JLabel sprite = player.getObjectSprite();
        if (sprite != null) {
            assertEquals(initialX - expectedSpeed, sprite.getX());
            assertEquals(initialY, sprite.getY());
            assertEquals(expectedWidth, sprite.getWidth());
            assertEquals(expectedHeight, sprite.getHeight());
        }
    }

    /**
     * Testuje pohyb hraca doprava.
     */
    @Test
    void moveRightShouldIncreaseXAndKeepY() {
        int initialX = player.getX();
        int initialY = player.getY();

        player.moveRight();

        assertEquals(initialX + expectedSpeed, player.getX());
        assertEquals(initialY, player.getY());

        JLabel sprite = player.getObjectSprite();
        if (sprite != null) {
            assertEquals(initialX + expectedSpeed, sprite.getX());
            assertEquals(initialY, sprite.getY());
            assertEquals(expectedWidth, sprite.getWidth());
            assertEquals(expectedHeight, sprite.getHeight());
        }
    }

    /**
     * Testuje ziskanie instancie PlayerController.
     */
    @Test
    void getControllerShouldReturnInitializedController() {
        PlayerController controller = player.getController();
        assertNotNull(controller);
    }
}