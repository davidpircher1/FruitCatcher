package Game.Player;

import Game.GameObject;
import Loger.ErrorLog;
import Loger.InfoLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Trieda PlayerModel reprezentuje model hraca v hre. Obsahuje informacie o pozicii, rychlosti a ovladaci hraca.
 * Umoznuje pohyb hraca a nacitanie obrazka.
 */
public class PlayerModel extends GameObject {
    private int speed; // Rychlost pohybu hraca
    private PlayerController controller; // Ovladac pre pohyb hraca

    /**
     * Konstruktor vytvori model hraca s urcenou poziciou a nastavi rychlost pohybu.
     * @param x suradnica x pozicie hraca
     * @param y suradnica y pozicie hraca
     */
    public PlayerModel(int x, int y) {
        super(x, y, 100, 100); // Zavolanie konstruktoru nadtriedy GameObject
        this.speed = 3; // Nastavenie rychlosti pohybu
        this.controller = new PlayerController(this); // Inicializacia ovladaca
        loadSprite(); // Nacitanie obrazka hraca
    }

    /**
     * Nacita obrazok pre hraca a nastavi ho ako sprite.
     * Ak sa obrazok nepodarilo nacitat, zapise chybu do logu.
     */
    private void loadSprite() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("Images/player.png");
            if (is != null) {
                Image image = ImageIO.read(is); // Nacitanie obrazka
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH); // Zmensenie obrazka
                sprite = new JLabel(new ImageIcon(scaledImage)); // Nastavenie sprite
                sprite.setBounds(x, y, width, height); // Nastavenie pozicie sprite
                new InfoLog("Obrazok hraca sa uspesne nacital");
            } else {
                new ErrorLog("Obrazok hraca sa nepodarilo nacitat");
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ErrorLog("Obrazok hraca sa nepodarilo nacitat");
        }
    }

    /**
     * Posunie hraca vsetkym smerom do lava.
     */
    public void moveLeft() {
        setPosition(getX() - speed, getY()); // Posunie hraca do lava
    }

    /**
     * Posunie hraca vsetkym smerom do prava.
     */
    public void moveRight() {
        setPosition(getX() + speed, getY()); // Posunie hraca do prava
    }

    /**
     * @return controller pre ovladanie pohybu hraca
     */
    public PlayerController getController() {
        return controller;
    }
}
