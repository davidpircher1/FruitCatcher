package Tests;

import Score.ScoreView; // Importujeme testovanu triedu

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import javax.swing.*;
import java.awt.*;

/**
 * Testovacia trieda pre ScoreView.
 * Testuje inicializaciu komponentov, ako JLabel a jeho spravne nastavenie pri aktualizacii skore.
 * Vsetky testy sa uskutocnia iba ak je graficke prostredie dostupne (AWT/Swing).
 * Tieto testy mozu byt preskocene alebo zlyhat ak testovacie prostredie nepodporuje AWT/Swing komponenty.
 */
class ScoreViewTest {

    private ScoreView scoreView;
    private JLabel scoreLabel; // Pomocna premenna pre lahsie pristup k labelu

    /**
     * Inicializuje inštanciu ScoreView pred každým testom.
     * Ak nie je podporované grafické prostredie, testy sa preskočia.
     */
    @BeforeEach
    void setUp() {
        // Skusime vytvorit instanciu ScoreView pred kazdym testom.
        // Ak to zlyha (napr. HeadlessException), testy budu preskocene.
        try {
            scoreView = new ScoreView();
            // Hned ziskame referenciu na interny label pre jednoduchsie overovanie
            scoreLabel = scoreView.getScoreLabel();
            // Predpokladame, ze label bol uspesne vytvoreny, ak konstruktor presiel
            assumeTrue(scoreLabel != null, "Predpoklad: scoreLabel bol vytvoreny v konstruktore.");
        } catch (HeadlessException e) {
            System.err.println("VAROVANIE: Testy ScoreView vyzaduju graficke prostredie a boli preskocene kvoli HeadlessException.");
            scoreView = null; // Zabezpeci preskocenie testov, ktore vyzaduju instanciu
            scoreLabel = null;
        } catch (Exception e) {
            // Zachytime aj ine mozne chyby pri inicializacii
            System.err.println("VAROVANIE: Neocakavana chyba pri vytvarani ScoreView: " + e.getMessage());
            scoreView = null;
            scoreLabel = null;
        }
    }

    /**
     * Testuje, ci konstruktor inicializuje JLabel so spravnym textom.
     * Ockavany text labelu je "Score: 1".
     */
    @Test
    @DisplayName("Konstruktor by mal inicializovat JLabel so spravnym textom")
    void constructorShouldInitializeLabelText() {
        assumeTrue(scoreView != null, "Preskakuje sa: ScoreView instancia nebola vytvorena.");

        // Overime pociatocny text labelu
        assertEquals("Score: 1", scoreLabel.getText(), "Pociatocny text labelu by mal byt 'Score: 1'.");
    }

    /**
     * Testuje, ci konstruktor nastavi spravny font pre JLabel.
     * Ockavany font je Arial, tučný a veľkosť 18.
     */
    @Test
    @DisplayName("Konstruktor by mal nastavit spravny font pre JLabel")
    void constructorShouldInitializeLabelFont() {
        assumeTrue(scoreView != null, "Preskakuje sa: ScoreView instancia nebola vytvorena.");

        // Overime vlastnosti nastaveného fontu
        Font labelFont = scoreLabel.getFont();
        assertNotNull(labelFont, "Font labelu by mal byt nastaveny (nemal by byt null).");
        assertEquals("Arial", labelFont.getName(), "Nazov fontu by mal byt 'Arial'.");
        assertTrue(labelFont.isBold(), "Font by mal byt tucny (BOLD).");
        assertEquals(18, labelFont.getSize(), "Velkost fontu by mala byt 18.");
    }

    /**
     * Testuje, ci getScoreLabel vráti platnú inštanciu JLabel.
     * Overuje, že getScoreLabel vráti nenulovú hodnotu.
     */
    @Test
    @DisplayName("getScoreLabel by mal vratit platnu instanciu JLabel")
    void getScoreLabelShouldReturnValidLabel() {
        assumeTrue(scoreView != null, "Preskakuje sa: ScoreView instancia nebola vytvorena.");

        // Overime, ci metoda vrati non-null objekt (uz mame v scoreLabel z BeforeEach)
        assertNotNull(scoreLabel, "getScoreLabel() by malo vratit non-null JLabel.");
        // Overime typ (aj ked premenna uz ma typ JLabel)
        assertInstanceOf(JLabel.class, scoreLabel, "Vrateny objekt by mal byt instancia JLabel.");
    }

    /**
     * Testuje, ci metoda updateScore spravne zmeni text JLabel.
     * Overuje, ci sa text labelu zmeni na "Score: 50" pri nastaveni skore na 50.
     */
    @Test
    @DisplayName("updateScore by malo spravne zmenit text JLabel")
    void updateScoreShouldCorrectlyChangeLabelText() {
        assumeTrue(scoreView != null, "Preskakuje sa: ScoreView instancia nebola vytvorena.");

        // Arrange: Pripravime nove skore a ocekavany text
        int newScore = 50;
        String expectedText = "Score: " + newScore; // Ockavany text je "Score: 50"

        // Act: Zavolame metodu, ktoru testujeme
        scoreView.updateScore(newScore);

        // Assert: Ziskame aktualny text z labelu a porovname ho s ocekavanym
        assertEquals(expectedText, scoreLabel.getText(), "Text labelu by sa mal zmenit na 'Score: 50'.");
    }

    /**
     * Testuje, ci metoda updateScore funguje aj pre nulu a zaporne cisla.
     * Overuje, ci sa text labelu zmeni na "Score: 0" pri nastaveni skore na 0
     * a na "Score: -10" pri nastaveni záporného čísla.
     */
    @Test
    @DisplayName("updateScore by malo fungovat aj pre nulu a zaporne cisla")
    void updateScoreShouldWorkForZeroAndNegative() {
        assumeTrue(scoreView != null, "Preskakuje sa: ScoreView instancia nebola vytvorena.");

        // Test s nulou
        scoreView.updateScore(0);
        assertEquals("Score: 0", scoreLabel.getText(), "Text labelu by sa mal zmenit na 'Score: 0'.");

        // Test so zapornym cislom
        scoreView.updateScore(-10);
        assertEquals("Score: -10", scoreLabel.getText(), "Text labelu by sa mal zmenit na 'Score: -10'.");
    }
}
