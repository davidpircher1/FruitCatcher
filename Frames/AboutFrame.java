package Frames;

import Loger.InfoLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * trieda AboutFrame zobrazuje informacne okno o autorovi hry.
 * dedi z {@link Frame} a implementuje {@link ActionListener} pre spracovanie interakcie s tlacidlom.
 */
public class AboutFrame extends Frame implements ActionListener {

    /**
     * konstruktor vytvori okno s informaciami o autorovi
     * a tlacidlom na navrat do hlavneho menu.
     */
    public AboutFrame() {
        super();

        // nastavenie pozadia
        setBackgroundImage("src/Images/gameBackground.png");

        // vytvorenie panelu
        JPanel aboutPanel = new JPanel(new GridLayout(4, 1, 0, 15));
        aboutPanel.setBounds(75, 200, 250, 200);
        aboutPanel.setOpaque(false);

        // vytvorenie popisu o autorovi
        JLabel aboutText = new JLabel("Created by David Pircher", SwingConstants.CENTER);
        aboutText.setFont(new Font("Arial", Font.BOLD, 20));

        // tlacidlo na navrat do menu
        Button backButton = new Button("Back to menu", 200, 50, Color.RED, Color.WHITE, this);

        // pridanie komponentov do panelu
        aboutPanel.add(aboutText);
        aboutPanel.add(backButton);

        // pridanie panelu do layered pane
        getLayeredPane().add(aboutPanel, Integer.valueOf(1));
    }

    /**
     * spracovanie akcie pri kliknuti na tlacidlo
     *
     * @param e udalost tlacidla
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        new InfoLog("User clicked: " + action);

        if (e.getActionCommand().equals("Back to menu")) {
            this.dispose();
            new MenuFrame();
        }
    }
}

