package Game;

/**
 * Rozhranie Collidable definuje spravanie objektov, ktore mozu kolidovat.
 * Obsahuje metody na ziskanie pozicie a rozmerov objektov, ako aj metodu na kontrolu kolizie medzi dvoma objektami.
 */
public interface Collidable {

    /** @return suradnica x objektu */
    int getX();

    /** @return suradnica y objektu */
    int getY();

    /** @return sirka objektu */
    int getWidth();

    /** @return vyska objektu */
    int getHeight();

    /**
     * Kontroluje, ci tento objekt koliduje s inym objektom.
     *
     * @param other druhy objekt, s ktorym kontrolujeme koliziu
     * @return true, ak objekty koliduju, inak false
     */
    default boolean checkCollision(Collidable other) {
        return this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY();
    }
}

