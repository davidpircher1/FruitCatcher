package Tests;

import Game.Player.PlayerModel;
import Game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovacia trieda pre triedu Game.
 * Testuje inicializáciu hry, nastavenie počiatočnej pozície hráča a správnu funkčnosť metódy update.
 */
class GameTest {
    private Game game;

    /**
     * Inicializácia pred každým testom.
     * Vytvorí novú inštanciu hry pred každým testom.
     */
    @BeforeEach
    void setup() {
        game = new Game(); // vytvorenie instancie triedy Game
    }

    /**
     * Testuje, či je model hráča inicializovaný správne.
     * Očakáva sa, že model hráča nebude null po inicializácii hry.
     */
    @Test
    void testgameinitialization() {
        assertNotNull(game.getPlayerModel(), "playermodel by nemal byt null po inicializacii");
    }

    /**
     * Testuje počiatočnú pozíciu hráča.
     * Očakáva sa, že počiatočná pozícia hráča bude (150, 340).
     */
    @Test
    void testplayermodelinitialposition() {
        PlayerModel playermodel = game.getPlayerModel();
        assertEquals(150, playermodel.getX(), "zaciatocna x pozicia hraca by mala byt 150");
        assertEquals(340, playermodel.getY(), "zaciatocna y pozicia hraca by mala byt 340");
    }

    /**
     * Testuje, že metóda update() nevyhodí žiadnu výnimku.
     * Očakáva sa, že volanie metódy update() nevyhodí výnimku.
     */
    @Test
    void testupdatedoesnotcrash() {
        assertDoesNotThrow(() -> game.update(), "volanie update() by nemalo vyhodit vynimku");
    }
}
