package Score;

import javax.swing.*;
import java.awt.*;

/**
 * View pre zobrazenie skore v hre.
 * Tato trieda sluzi na vizualizaciu aktualneho skore a jeho aktualizaciu na obrazovke.
 */
public class ScoreView {
    // Popis pre zobrazenie skore
    private JLabel scoreLabel;

    /**
     * Vytvori novu instanciu ScoreView s pociatocnym skore.
     */
    public ScoreView() {
        scoreLabel = new JLabel("Score: 1");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
    }

    /**
     * Ziska JLabel, ktory zobrazuje skore.
     *
     * @return JLabel pre skore.
     */
    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    /**
     * Aktualizuje zobrazenie skore.
     *
     * @param score Nove skore, ktore sa ma zobraziť.
     */
    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
