package Frames;

import Loger.*;
import Score.TopScore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * trieda ScoreFrame zobrazuje okno s najlepsimi vysledkami (top skore),
 * ktore su nacitane zo suboru. Obsahuje aj tlacidlo na navrat do menu.
 */
public class ScoreFrame extends Frame implements ActionListener {

    /**
     * konstruktor vytvori okno, nacita top skore zo suboru
     * a zobrazi ich spolu s nadpisom a tlacidlom na navrat.
     */
    public ScoreFrame() {
        super();

        // nastavenie pozadia
        setBackgroundImage("src/Images/gameBackground.png");

        // panel pre zobrazenie skore a tlacidla
        JPanel scorePanel = new JPanel(new GridLayout(5, 1, 0, 15));
        scorePanel.setBounds(100, 125, 200, 300);
        scorePanel.setOpaque(false);

        // nadpis pre sekciu so skore
        JLabel scoreHeading = new JLabel("Top score:", SwingConstants.CENTER);
        scoreHeading.setFont(new Font("Arial", Font.BOLD, 30));
        scorePanel.add(scoreHeading);

        // nacitanie skore a ich zobrazenie
        int[] scoreArray = loadScores();
        for (int score : scoreArray) {
            JLabel scoreLabel = new JLabel(String.valueOf(score), SwingConstants.CENTER);
            scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
            scorePanel.add(scoreLabel);
        }

        // tlacidlo na navrat do menu
        Button backButton = new Button("Back to menu", 200, 50, Color.RED, Color.WHITE, this);
        scorePanel.add(backButton);

        getLayeredPane().add(scorePanel, Integer.valueOf(1));
    }

    /**
     * metoda nacita top skore zo suboru pomocou triedy {@link TopScore}.
     *
     * @return pole s top tromi vysledkami alebo predvolene hodnoty v pripade chyby
     */
    private int[] loadScores() {
        try {
            return new TopScore().getTopScores();
        } catch (IOException e) {
            new ErrorLog("Chyba pri nacitani textoveho suboru topScore.txt");
            return new int[]{0, 0, 0};
        } finally {
            new InfoLog("Uspesne nacitanie textoveho suboru topScore.txt");
        }
    }

    /**
     * spracovanie kliknuti na tlacidla v ramci tohto okna.
     * konkretne reaguje na tlacidlo "Back to menu".
     *
     * @param e event ktory obsahuje informacie o kliknuti
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        new InfoLog("User clicked: " + action);

        if ("Back to menu".equals(action)) {
            this.dispose();
            new MenuFrame();
        }
    }
}

