package Game.Fruit;

import Game.GameObject;
import Loger.ErrorLog;
import Loger.InfoLog;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Model reprezentujúci ovocie v hre.
 * Ovocie je objekt, ktorý sa pohybuje a mení skóre na základe svojej stratégie.
 *
 * @see ScoreStrategy
 */
public class FruitModel extends GameObject {
    private int changeScore;
    private int speed;
    private ScoreStrategy strategy;
    private FruitController fruitController;

    /**
     * Vytvorí nové ovocie s náhodne zvolenou stratégiou.
     * Nastaví pozíciu a rýchlosť ovocia.
     *
     * @param x Počiatočná X pozícia ovocia.
     * @param y Počiatočná Y pozícia ovocia.
     * @param width Šírka ovocia.
     * @param height Výška ovocia.
     */
    public FruitModel(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.strategy = StrategyFactory.getRandomStrategy(); // náhodná strategia
        this.changeScore = strategy.addScore();
        this.speed = 3 + new Random().nextInt(5);  // Rýchlosť 3–6
        this.setPosition(x, y); // nastaví pozíciu
        this.fruitController = new FruitController(this);
        loadSprite();
    }

    /**
     * Získava skóre, ktoré sa má pridať, keď sa ovocie získa.
     *
     * @return Hodnota skóre priradená tomuto ovociu.
     */
    public int getChangeScore() {
        return changeScore;
    }

    /**
     * Získava rýchlosť, ktorou sa ovocie pohybuje.
     *
     * @return Rýchlosť pohybu ovocia.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Nastaví novú pozíciu ovocia na základe jeho aktuálnej pozície a pridaného posunu.
     *
     * @param speed Posun v smere Y pre pohyb ovocia.
     */
    public void setPosition(int speed) {
        setPosition(getX(), getY() + speed);
    }

    /**
     * Získava ovládací objekt ovocia, ktorý riadi jeho pohyb.
     *
     * @return Ovládací objekt pre ovocie.
     */
    public FruitController getFruitController() {
        return fruitController;
    }

    /**
     * Načíta obrázok ovocia zo súboru a priradí ho ako zobrazenie ovocia.
     * Ak sa obrázok nepodarí načítať, zobrazí sa chyba v logu.
     */
    private void loadSprite() {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(strategy.getImage());
            if (is != null) {
                Image image = ImageIO.read(is);
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                sprite = new JLabel(new ImageIcon(scaledImage));
                sprite.setBounds(x, y, width, height);
                new InfoLog("Obrázok " + strategy.getName() + " sa úspešne načítal");
            } else {
                new ErrorLog("Obrázok " + strategy.getName() + " sa nepodarilo načítať");
            }
        } catch (IOException e) {
            e.printStackTrace();
            new ErrorLog("Obrázok " + strategy.getName() + " sa nepodarilo načítať");
        }
    }
}
