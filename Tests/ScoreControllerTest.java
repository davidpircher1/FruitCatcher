package Tests;

import Score.ScoreController;
import Score.ScoreModel;
import Score.ScoreView; // Must be importable (interface or class)

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovacia trieda pre ScoreController.
 * Testuje funkcie ScoreController ako inicializacia modelu a view,
 * nastavovanie a ziskavanie skore, a spravne volanie metody updateScore na view.
 */
class ScoreControllerTest {

    private ScoreModel realModel;
    private ScoreView anonymousView; // Instance of the anonymous class
    private ScoreController scoreController;

    // Variables to track state within the anonymous ScoreView
    private boolean viewUpdateScoreCalled;
    private int viewLastDisplayedScore;

    /**
     * Inicializuje testove prostredie pred kazdym testom.
     * Vytvori instanciu ScoreModel, anonymnu implementaciu ScoreView
     * a instanciu ScoreController.
     */
    @BeforeEach
    void setUp() {
        // Pouzijeme realnu instanciu ScoreModel
        realModel = new ScoreModel(); // ScoreModel ma defaultnu hodnotu score = 1

        // Vytvorime anonymnu triedu implementujucu ScoreView
        viewUpdateScoreCalled = false; // Reset flagov pred kazdym testom
        viewLastDisplayedScore = -1;  // Reset hodnoty

        anonymousView = new ScoreView() { // Alebo new ScoreView() { ... } ak je ScoreView trieda
            @Override
            public void updateScore(int score) {
                // Toto je telo nasej falošnej implementacie
                ScoreControllerTest.this.viewUpdateScoreCalled = true; // Nastavime flag testovacej triedy
                ScoreControllerTest.this.viewLastDisplayedScore = score; // Ulozime skore
                // Nerobime ziadne realne GUI operacie
            }
        };

        // Vytvorime controller s realnym modelom a nasou anonymnou implementaciou view
        scoreController = new ScoreController(realModel, anonymousView);
    }

    /**
     * Testuje, ci konstruktor ScoreController inicializuje model a view
     * a ci zavola metodu updateScore s pociatocnym skore.
     */
    @Test
    @DisplayName("Konstruktor by mal inicializovat model a view a aktualizovat view")
    void constructorShouldInitializeAndCallUpdateView() {
        // Arrange: Nastavenie prebehlo v @BeforeEach. Model ma score = 1 defaultne.
        // Controller bol vytvoreny, cim sa zavolal konstruktor a updateView().

        // Assert:
        // 1. Overime, ze anonymny view zaznamenal volanie updateScore
        assertTrue(viewUpdateScoreCalled, "Konstruktor mal zavolat updateScore na view.");
        // 2. Overime, ze view dostal spravne pociatocne skore z realneho modelu (ktore je defaultne 1)
        assertEquals(1, viewLastDisplayedScore, "View by mal zobrazit pociatocne skore (1) z modelu.");
    }

    /**
     * Testuje, ci metoda getScore vrati aktualnu hodnotu skore z modelu.
     */
    @Test
    @DisplayName("getScore by mal vratit skore z modelu")
    void getScoreShouldReturnScoreFromModel() {
        // Arrange: Nastavime znamke skore priamo v realnom modeli
        int expectedScore = 42;
        realModel.setScore(expectedScore); // Zmenime defaultne skore

        // Act: Zavolame getScore na controlleri
        int actualScore = scoreController.getScore();

        // Assert:
        // 1. Skore vratene controllerom sa musi zhodovat s tym v modeli
        assertEquals(expectedScore, actualScore, "getScore by malo vratit aktualnu hodnotu z modelu.");
    }

    /**
     * Testuje, ci metoda setScore nastavi skore v modeli a zavola updateScore na view.
     */
    @Test
    @DisplayName("setScore by mal nastavit skore v modeli a aktualizovat view")
    void setScoreShouldUpdateModelAndUpdateView() {
        // Arrange: Pripravime nove skore a resetujeme prizmaky view
        int newScore = 100;
        viewUpdateScoreCalled = false; // Reset flagu pred volanim setScore
        viewLastDisplayedScore = -1;

        // Act: Zavolame setScore na controlleri
        scoreController.setScore(newScore);

        // Assert:
        // 1. Overime, ze model bol aktualizovany (cez jeho getter)
        assertEquals(newScore, realModel.getScore(), "Model by mal mat ulozene nove skore po volani controller.setScore().");

        // 2. Overime, ze view bol aktualizovany (cez flag a hodnotu v anonymnej triede)
        assertTrue(viewUpdateScoreCalled, "controller.setScore() mal zavolat updateView(), ktory vola view.updateScore().");
        assertEquals(newScore, viewLastDisplayedScore, "View by mal zaznamenat nove skore poslane cez updateScore.");
    }
}
