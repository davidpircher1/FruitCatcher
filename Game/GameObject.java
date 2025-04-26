package Game;

import javax.swing.*;

/**
 * abstraktna trieda GameObject predstavuje zakladny objekt v hre,
 * ktory ma poziciu, rozmery a graficky prvok (sprite).
 * vsetky objekty, ktore chcu byt kolidovatelne, ju musia rozsirit.
 */
public abstract class GameObject implements Collidable {

    // suradnice objektu
    protected int x, y;

    // rozmery objektu
    protected int width, height;

    // vizualne zobrazenie objektu
    protected JLabel sprite;

    /**
     * konstruktor pre inicializaciu pozicie a rozmerov objektu.
     *
     * @param x suradnica x
     * @param y suradnica y
     * @param width sirka objektu
     * @param height vyska objektu
     */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * nastavi novu poziciu objektu a aktualizuje umiestnenie jeho sprite-u.
     *
     * @param x nova suradnica x
     * @param y nova suradnica y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
        if (sprite != null) {
            sprite.setBounds(x, y, width, height);
        }
    }

    /** @return aktualna suradnica x objektu */
    public int getX() { return x; }

    /** @return aktualna suradnica y objektu */
    public int getY() { return y; }

    /** @return sirka objektu */
    public int getWidth() { return width; }

    /** @return vyska objektu */
    public int getHeight() { return height; }

    /** @return graficke zobrazenie objektu (JLabel) */
    public JLabel getObjectSprite() { return sprite; }
}

