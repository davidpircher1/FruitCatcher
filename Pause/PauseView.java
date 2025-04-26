package Pause;

import Frames.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Zobrazuje tlacidlo pozastavenia a ponuku pozastavenia hry.
 * Umoznuje pouzivatelskemu vybrat moznost buď na obnovu hry alebo na jej ukoncenie.
 */
public class PauseView {
    // Tlacidlo pre pozastavenie hry
    private Button pauseButton;

    /**
     * Konstruktor pre inicializaciu pohľadu pozastavenia.
     * Vytvara tlacidlo pre pozastavenie s poskytnutym posluchacom akcii.
     *
     * @param pauseAction posluchac akcie pre tlacidlo pozastavenia.
     */
    public PauseView(ActionListener pauseAction) {
        // Tlacidlo pre pozastavenie s akciou poskytnutou cez parameter
        pauseButton = new Button("Pause", 200, 50, Color.RED, Color.WHITE, pauseAction);
    }

    /**
     * Getter pre tlacidlo pozastavenia.
     *
     * @return tlacidlo pozastavenia.
     */
    public Button getPauseButton() {
        return pauseButton;
    }

    /**
     * Zobrazuje ponuku pri pozastaveni hry, kde pouzivatel moze buď pokracovat, alebo ukoncit hru.
     * Zobrazuje dialogove okno s moznostami "Resume" a "Quit".
     *
     * @param parent hlavne okno aplikacie, ktore bude sluzit ako rodic pre dialogove okno.
     * @return index zvoleneho tlacidla (0 pre "Resume", 1 pre "Quit").
     */
    public int showPauseMenu(JFrame parent) {
        // Moznosti v ponuke pozastavenia
        String[] options = {"Resume", "Quit"};
        // Zobrazenie dialogoveho okna s moznostami
        return JOptionPane.showOptionDialog(
                parent, "Game Paused", "Pause Menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]
        );
    }
}
