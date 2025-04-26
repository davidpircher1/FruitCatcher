package Tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Loger.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit testy pre abstraktnu triedu Loger.
 * Pouziva konkretnu podtriedu pre instanciaciu a zachytava System.out
 * na overenie zaznamenanych sprav.
 */
class LogerTest {

    // Stream na zachytenie System.out
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Konkretna podtrieda pre testovanie abstraktneho Loger-a
    private static class ConcreteLoger extends Loger {
        /**
         * Konstruktor pre konkretny testovaci logger.
         * Odovzdava spravu konstruktoru nadtriedy.
         * @param message Sprava, ktora sa ma zaznamenat.
         */
        public ConcreteLoger(String message) {
            super(message);
        }
    }

    /**
     * Presmeruje System.out na zachytenie konzoloveho vystupu pred kazdym testom.
     */
    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Obnovuje povodny System.out stream po kazdom teste.
     */
    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

    /**
     * Testuje, ci konstruktor spravne zaznamena standardnu spravu
     * pomocou metody logMessage.
     */
    @Test
    void testConstructorLogsStandardMessage() {
        String testMessage = "Toto je testovacia sprava";
        new ConcreteLoger(testMessage);
        // Overenie, ze vystup obsahuje prefix a spravu, nasledovanu novym riadkom
        String expectedOutput = "[LOG] " + testMessage + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Testuje, ci konstruktor spravne zaznamena prazdnu spravu.
     */
    @Test
    void testConstructorLogsEmptyMessage() {
        String testMessage = "";
        new ConcreteLoger(testMessage);
        String expectedOutput = "[LOG] " + testMessage + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Testuje, ako konstruktor spracuje null spravu.
     * Mal by zaznamenat retazec "null".
     */
    @Test
    void testConstructorLogsNullMessage() {
        String testMessage = null;
        new ConcreteLoger(testMessage);
        String expectedOutput = "[LOG] " + testMessage + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    /**
     * Testuje chranenu metodu logMessage priamo cez podtriedu,
     * hoci je implicitne testovana testami konstruktora.
     * Sluzi ako priklad, ak je potrebne priame testovanie chranenych metod.
     */
    @Test
    void testLogMessageDirectly() {
        // Vytvorenie instancie bez spustenia logu konstruktora cez trik polymorfizmu (ak treba)
        // Alebo len normalne vytvorit a otestovat logMessage znova.
        ConcreteLoger logger = new ConcreteLoger(""); // Pociatocny log sa ignoruje resetovanim streamu
        outContent.reset(); // Vycistenie vystupu z konstruktora

        String directMessage = "Priame volanie logu";
        logger.logMessage(directMessage); // Priame volanie chranenej metody
        String expectedOutput = "[LOG] " + directMessage + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }
}