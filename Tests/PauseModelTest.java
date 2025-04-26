package Tests; // Test bude v tomto balicku

import Pause.PauseModel; // Import testovanej triedy

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Jednotkove testy pre triedu PauseModel.
 * Overuju spravnu inicializaciu a funkcnost prepinania stavu pauzy.
 */
class PauseModelTest {

    /** Instancia PauseModel, ktora bude testovana v kazdom teste. */
    private PauseModel pauseModel;

    /**
     * Pripravna metoda spustena pred kazdym testom (@Test).
     * Vytvori novu, cistu instanciu PauseModel, ktora je standardne
     * v stave "nie je pozastavene" (isPaused = false).
     */
    @BeforeEach
    void setUp() {
        pauseModel = new PauseModel();
    }

    /**
     * Testuje pociatocny stav modelu po vytvoreni konstruktorom.
     * Ockava sa, ze hra nie je na zaciatku pozastavena.
     */
    @Test
    @DisplayName("Pociatocny stav by mal byt 'nepozastavene' (false)")
    void testInitialStateIsNotPaused() {
        assertFalse(pauseModel.isPaused(), "Standardne by model nemal byt v stave 'pozastavene'.");
    }

    /**
     * Testuje prve volanie metody togglePause().
     * Ockava sa, ze stav sa prepne z false na true.
     */
    @Test
    @DisplayName("Prve volanie togglePause by malo zmenit stav na 'pozastavene' (true)")
    void testTogglePauseChangesStateToPaused() {
        // Pociatocny stav je false (overene v setUp a testInitialStateIsNotPaused)
        pauseModel.togglePause();
        assertTrue(pauseModel.isPaused(), "Po prvom volani togglePause by mal byt model v stave 'pozastavene'.");
    }

    /**
     * Testuje druhe volanie metody togglePause().
     * Ockava sa, ze stav sa prepne spat z true na false.
     */
    @Test
    @DisplayName("Druhe volanie togglePause by malo zmenit stav spat na 'nepozastavene' (false)")
    void testTogglePauseTwiceReturnsToInitialState() {
        // Pociatocny stav je false
        pauseModel.togglePause(); // Stane sa true
        pauseModel.togglePause(); // Malo by sa stat opat false
        assertFalse(pauseModel.isPaused(), "Po druhom volani togglePause by sa mal model vratit do stavu 'nepozastavene'.");
    }

    /**
     * Testuje metodu isPaused() priamo po nastaveni stavu.
     * Hoci je to pokryte inymi testami, explicitne overuje getter.
     */
    @Test
    @DisplayName("Metoda isPaused() by mala spravne vracat aktualny stav")
    void testIsPausedGetter() {
        // Pociatocny stav
        assertFalse(pauseModel.isPaused(), "isPaused() by malo vratit false na zaciatku.");

        // Po prvom prepnuti
        pauseModel.togglePause();
        assertTrue(pauseModel.isPaused(), "isPaused() by malo vratit true po prvom togglePause.");

        // Po druhom prepnuti
        pauseModel.togglePause();
        assertFalse(pauseModel.isPaused(), "isPaused() by malo vratit false po druhom togglePause.");
    }
}