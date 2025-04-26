package Tests;
import Score.ScoreModel; // Importujeme testovanu triedu

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovacia trieda pre ScoreModel.
 * Testuje zakladne funkcie triedy ScoreModel, ako nastavenie skore, jeho ziskavanie
 * a kontrolu, ci sa skore spravne aktualizuje a je konzistentne pri viackratnom volani.
 */
class ScoreModelTest {

    private ScoreModel scoreModel;

    /**
     * Inicializuje novú instanciu ScoreModel pred každým testom.
     * Vytvorí čistú inštanciu ScoreModel s počiatočným skóre 1.
     */
    @BeforeEach
    void setUp() {
        // Vytvorime novu, cistu instanciu ScoreModel pred kazdym testom
        scoreModel = new ScoreModel();
    }

    /**
     * Testuje, ci nový ScoreModel ma počiatočné skóre 1.
     * Očakáva sa, že skóre bude 1, keď sa model vytvorí.
     */
    @Test
    @DisplayName("Nový ScoreModel by mal mat pociatocne skore 1")
    void initialScoreShouldBeOne() {
        // Arrange: Objekt scoreModel je vytvoreny v setUp() s defaultnym skore 1

        // Act: Ziskame skore hned po inicializacii
        int actualScore = scoreModel.getScore();

        // Assert: Overime, ci je skutocne 1
        assertEquals(1, actualScore, "Pociatocne skore modelu by malo byt 1.");
    }

    /**
     * Testuje, ci metoda setScore správne nastaví hodnotu skóre.
     * Očakáva sa, že pri volaní setScore s hodnotou 42, getScore vráti hodnotu 42.
     */
    @Test
    @DisplayName("setScore by malo spravne nastavit hodnotu skore")
    void setScoreShouldUpdateTheScoreValue() {
        // Arrange: Pripravíme si novu hodnotu pre skore
        int expectedScore = 42;

        // Act: Nastavíme nové skore pomocou setScore
        scoreModel.setScore(expectedScore);

        // Assert: Overime, ci getScore teraz vrati tuto novu hodnotu
        assertEquals(expectedScore, scoreModel.getScore(), "getScore by malo vratit hodnotu nastavenú cez setScore.");
    }

    /**
     * Testuje, ci getScore vracia aktualnu hodnotu po viacerých nastaveniach.
     * Očakáva sa, že každé nové nastavenie skóre bude správne odrážané vo vrátenej hodnote.
     */
    @Test
    @DisplayName("getScore by malo vracat aktualnu hodnotu po viacerych nastaveniach")
    void getScoreShouldReturnCurrentValueAfterMultipleSets() {
        // Arrange: Nastavime skore niekolkokrat
        int firstScore = 10;
        int secondScore = -5;
        int finalScore = 0;

        // Act & Assert sekvencia
        scoreModel.setScore(firstScore);
        assertEquals(firstScore, scoreModel.getScore(), "Skore by malo byt " + firstScore + " po prvom nastaveni.");

        scoreModel.setScore(secondScore);
        assertEquals(secondScore, scoreModel.getScore(), "Skore by malo byt " + secondScore + " po druhom nastaveni.");

        scoreModel.setScore(finalScore);
        assertEquals(finalScore, scoreModel.getScore(), "Skore by malo byt " + finalScore + " po finalnom nastaveni.");
    }

    /**
     * Testuje, ci getScore vracia konzistentne hodnoty pri opakovanom volani.
     * Očakáva sa, že opakované volanie getScore vráti rovnakú hodnotu, keď sa nezmení stav modelu.
     */
    @Test
    @DisplayName("getScore by malo byt konzistentne pri opakovanom volani")
    void getScoreShouldBeConsistentOnRepeatedCalls() {
        // Arrange: Nastavime nejaku hodnotu
        int testScore = 88;
        scoreModel.setScore(testScore);

        // Act & Assert: Volame getScore viackrat za sebou
        assertEquals(testScore, scoreModel.getScore(), "Prve volanie getScore.");
        assertEquals(testScore, scoreModel.getScore(), "Druhe volanie getScore by malo vratit rovnaku hodnotu.");
        // Tento test overuje, ze getScore nemeni stav modelu (co by ani nemalo)
    }
}
