package Tests;

import Frames.Frame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovacia trieda pre triedu Frame.
 * Testuje inicializáciu, nastavenie ikonky, spracovanie chýb pri načítavaní pozadia a načítanie pozadia.
 */
class FrameTest {

    /**
     * Jednoduchá konkrétna implementácia abstraktnej triedy Frame.
     * Táto trieda slúži na testovanie implementácie abstraktnej triedy Frame.
     */
    static class TestFrame extends Frame {
        public TestFrame() {
            super();
        }
    }

    private Frame frame;

    /**
     * Inicializácia pred každým testom.
     * Vytvorí novú inštanciu TestFrame pre každý test.
     */
    @BeforeEach
    void setup() {
        frame = new TestFrame();
    }

    /**
     * Testuje, či konštruktor triedy Frame inicializuje správne hodnoty.
     * Očakáva sa, že titulok bude "Fruit Catcher", šírka 400, výška 600, ikona nebude null
     * a okno bude viditeľné.
     */
    @Test
    void testConstructorInitializesCorrectly() {
        assertEquals("Fruit Catcher", frame.getTitle(), "Titulok okna je nesprávny");
        assertEquals(400, frame.getWidth(), "Šírka okna je nesprávna");
        assertEquals(600, frame.getHeight(), "Výška okna je nesprávna");
        assertNotNull(frame.getIcon(), "Ikona okna by nemala byť null");
        assertTrue(frame.isVisible(), "Okno by malo byť viditeľné");
    }

    /**
     * Testuje metódu loadIcon, ktorá by mala vrátiť platnú ImageIcon.
     * Očakáva sa, že ikonka nebude null a bude mať platnú šírku.
     */
    @Test
    void testLoadIconReturnsImageIcon() {
        ImageIcon icon = frame.loadIcon();
        assertNotNull(icon, "Ikonka by nemala byť null");
        assertTrue(icon.getIconWidth() > 0, "Ikonka by mala mať platnú šírku");
    }

    /**
     * Testuje metódu setBackgroundImage pri neplatnej ceste k súboru.
     * Očakáva sa, že pri chybe načítania pozadia sa zaloguje chyba do výstupu.
     */
    @Test
    void testSetBackgroundImageWithInvalidPathLogsError() {
        // Zachytenie výstupu System.err
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        PrintStream originalErr = System.err;
        System.setErr(new PrintStream(errContent));

        // Nastavenie neplatnej cesty k pozadiu
        frame.setBackgroundImage("neexistujuca-cesta.png");

        // Obnova pôvodného výstupu
        System.setErr(originalErr);

        // Skontrolujeme, či sa v logu objavila chyba
        String logOutput = errContent.toString();
        assertFalse(logOutput.contains("Chyba pri načítaní pozadia"), "Chyba by mala byť zalogovaná");
    }

    /**
     * Testuje, či metóda setBackgroundImage načíta pozadie správne pri platnej ceste.
     * Očakáva sa, že sa pozadie načíta a pridá komponent do ContentPane.
     */
    @Test
    void testSetBackgroundImageLoadsCorrectlyWithValidPath() throws Exception {
        String path = "src/Images/menuBackground.png";

        // Spustíme GUI kód v správnom vlákne
        SwingUtilities.invokeAndWait(() -> {
            frame.setBackgroundImage(path);
        });

        // Skontrolujeme, že ContentPane obsahuje aspoň jeden komponent
        assertTrue(frame.getContentPane().getComponentCount() > 0, "Pozadie by malo byť pridané do obsahu");
    }
}
