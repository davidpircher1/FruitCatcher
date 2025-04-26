package Frames;

import javax.swing.*;
import java.awt.*;
import Loger.*;

/**
 * trieda MenuFrame predstavuje uvodne menu hry.
 * obsahuje tri hlavne tlacidla: Play, Score a About,
 * ktore spustaju prislusne okna.
 */
public class MenuFrame extends Frame {

    /**
     * konstruktor vytvori hlavne menu hry s tromi moznostami:
     * spustenie hry, zobrazenie skore a zobrazenie informacii o hre.
     */
    public MenuFrame() {
        super();

        // nastavenie pozadia
        setBackgroundImage("src/Images/menuBackground.png");

        // vytvorenie panelu pre tlacidla
        JPanel menuButtons = new JPanel(new GridLayout(3, 1, 0, 15));
        menuButtons.setBounds(100, 265, 200, 150);
        menuButtons.setOpaque(false);

        // tlacidlo na spustenie hry
        Button playButton = new Button("Play", 200, 50, Color.RED, Color.WHITE, null);
        playButton.addActionListener(e -> {
            new InfoLog("User clicked: Play");
            this.dispose();
            new GameFrame();
        });

        // tlacidlo na zobrazenie skore
        Button scoreButton = new Button("Score", 200, 50, Color.RED, Color.WHITE, null);
        scoreButton.addActionListener(e -> {
            new InfoLog("User clicked: Score");
            this.dispose();
            new ScoreFrame();
        });

        // tlacidlo pre informacie o hre
        Button aboutButton = new Button("About", 200, 50, Color.RED, Color.WHITE, null);
        aboutButton.addActionListener(e -> {
            new InfoLog("User clicked: About");
            this.dispose();
            new AboutFrame();
        });

        // pridanie tlacidiel do panelu
        menuButtons.add(playButton);
        menuButtons.add(scoreButton);
        menuButtons.add(aboutButton);

        // pridanie panelu do layered pane
        getLayeredPane().add(menuButtons, Integer.valueOf(1));
    }
}

