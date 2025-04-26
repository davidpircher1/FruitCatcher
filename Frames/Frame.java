package Frames;

import Loger.ErrorLog;

import javax.swing.*;
import java.util.Objects;

/**
 * abstraktna trieda frame predstavuje zakladne okno aplikacie.
 * dedi od {@link JFrame} a poskytuje zakladnu funkcionalitu
 * ako nastavenie ikonky a pozadia.
 */
public abstract class Frame extends JFrame {
    /**
     * obrazkova ikona aplikacie
     */
    private final ImageIcon icon;

    /**
     * konstruktor nastavuje zakladne vlastnosti okna ako je nazov,
     * ikonka, rozmery a viditelnost.
     */
    public Frame() {
        super("Fruit Catcher");
        icon = loadIcon();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setIconImage(icon.getImage());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * nacita ikonku aplikacie zo zlozky resources.
     *
     * @return {@link ImageIcon} objekt s nacitanou ikonou,
     *         alebo prazdny {@link ImageIcon}, ak sa ikonku nepodarilo nacitat.
     */
    public ImageIcon loadIcon() {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("Images/ico.png")));
        } catch (Exception e) {
            new ErrorLog("Chyba pri nacitani ikony: " + e.getMessage());
            return new ImageIcon();
        }
    }

    /**
     * nastavi pozadie okna pomocou obrazka zo zadanej cesty.
     *
     * @param imagePath cesta k obrazku, ktory sa pouzije ako pozadie
     */
    public void setBackgroundImage(String imagePath) {
        try {
            ImageIcon backgroundImage = new ImageIcon(imagePath);
            if (backgroundImage.getIconWidth() == -1) {
                throw new Exception("Obrazok sa nepodarilo nacitat");
            }

            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(getSize());
            this.setContentPane(layeredPane);

            JLabel background = new JLabel(backgroundImage);
            background.setBounds(0, 0, getWidth(), getHeight());
            layeredPane.add(background, Integer.valueOf(0));

            this.revalidate();
            this.repaint();
        } catch (Exception e) {
            new ErrorLog("Chyba pri nacitani pozadia: " + e.getMessage());
        }
    }

    /**
     * vrati ikonku aplikacie.
     *
     * @return {@link ImageIcon} s ikonou aplikacie
     */
    public ImageIcon getIcon() {
        return icon;
    }
}

