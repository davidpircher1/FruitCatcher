package Frames;

import Loger.ErrorLog;
import Loger.InfoLog;
import Score.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * trieda EndFrame predstavuje koncove okno hry, ktore zobrazuje skore
 * a ponuka moznosti spustit novu hru alebo sa vratit do menu.
 * dedi z triedy {@link Frame} a implementuje {@link ActionListener}.
 */
public class EndFrame extends Frame implements ActionListener {

    /**
     * objekt triedy TopScore pre pracu so skore
     */
    private TopScore topScore;

    /**
     * konstruktor vytvori koncove okno s aktualnym skore hraca,
     * zaznamena ho do suboru a zobrazi tlacidla na dalsiu akciu.
     *
     * @param finalScore vysledne skore hraca
     */
    public EndFrame(int finalScore) {
        super();
        try {
            topScore = new TopScore();
            topScore.updateScore(finalScore, topScore.getPath());
        } catch (Exception e) {
            new ErrorLog("Nepodarilo sa nacitat txt subor");
        } finally {
            new InfoLog("Zapisujem nove skore: " + finalScore);
        }

        // nastavi pozadie
        setBackgroundImage("src/Images/gameBackground.png");

        // vytvori panel pre zobrazenie skore a tlacidiel
        JPanel scorePanel = new JPanel(new GridLayout(3, 1, 0, 15));
        scorePanel.setBounds(100, 175, 200, 150);
        scorePanel.setOpaque(false);

        // zobrazi skore
        JLabel scoreHeading = new JLabel("Your score: " + finalScore, SwingConstants.CENTER);
        scoreHeading.setFont(new Font("Arial", Font.BOLD, 26));
        scorePanel.add(scoreHeading);

        // tlacidlo na spustenie novej hry
        Button newGameButton = new Button("New game", 200, 50, Color.RED, Color.WHITE, e -> {
            new InfoLog("User clicked: New game");
            this.dispose();
            new GameFrame();
        });

        // tlacidlo na navrat do menu
        Button backButton = new Button("Back to menu", 200, 50, Color.RED, Color.WHITE, e -> {
            new InfoLog("User clicked: Back to menu");
            this.dispose();
            new MenuFrame();
        });

        scorePanel.add(newGameButton);
        scorePanel.add(backButton);

        // prida panel do layered pane
        getLayeredPane().add(scorePanel, Integer.valueOf(1));
    }

    /**
     * spracovanie akcii tlacidiel, ak sa pouzivaju klasicke ActionCommandy
     *
     * @param e udalost tlacidla
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        new InfoLog("User clicked: " + action);

        if ("Back to menu".equals(action)) {
            this.dispose();
            new MenuFrame();
        } else if ("New game".equals(action)) {
            this.dispose();
            new GameFrame();
        }
    }
}

