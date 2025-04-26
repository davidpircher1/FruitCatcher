package Game.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Trieda PlayerController spravuje vstupy z klavesnice a ovlada pohyb hraca.
 * Obsahuje logiku pre spracovanie stlacenia a pustenia klaves, aby sa hrac pohyboval do lava a doprava.
 */
public class PlayerController implements KeyListener {
    private PlayerModel model; // Model hraca
    private boolean moveLeft = false; // Prikaz na pohyb do lava
    private boolean moveRight = false; // Prikaz na pohyb do prava

    /**
     * Konstruktor inicializuje PlayerController s modelom hraca.
     * @param model model hraca, ktory bude ovladany
     */
    public PlayerController(PlayerModel model) {
        this.model = model;
    }

    /**
     * Metoda, ktoru volame pri stlaceni klavesy.
     * Urci, ci sa ma hrac pohybovat do lava alebo doprava.
     * @param e objekt reprezentujuci udalost stlacenia klavesy
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = true; // Nastavi pohyb do lava
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = true; // Nastavi pohyb do prava
        }
    }

    /**
     * Metoda, ktoru volame pri pusteni klavesy.
     * Zastavi pohyb hraca v danom smere.
     * @param e objekt reprezentujuci udalost pustenia klavesy
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = false; // Zastavi pohyb do lava
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = false; // Zastavi pohyb do prava
        }
    }

    /**
     * Tento metod nevyuzivame, pretoze sa nezaujimame o typ klavesy, ale je potrebny pre implementaciu rozhrania KeyListener.
     * @param e objekt reprezentujuci udalost stlacenia klavesy
     */
    @Override
    public void keyTyped(KeyEvent e) {}

    /**
     * Metoda aktualizuje pohyb hraca na zaklade stlaceni klaves.
     * Zabezpecuje, aby hrac nemohol prejst mimo hranice obrazovky.
     */
    public void updateMovement() {
        if (!model.getObjectSprite().hasFocus()) {
            model.getObjectSprite().requestFocusInWindow(); // Zabezpeci, aby mal hrac zameranie pri pohybe
        }

        if (moveLeft && model.getX() > 0) { // Kontrola, ci sa hrac moze pohybovat do lava
            model.moveLeft();
        }
        if (moveRight && model.getX() < 300) { // Kontrola, ci sa hrac moze pohybovat do prava
            model.moveRight();
        }
    }
}
