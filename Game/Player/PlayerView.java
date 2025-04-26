package Game.Player;

import javax.swing.*;

/**
 * Trieda PlayerView je zodpovedna za zobrazenie hraca na obrazovke.
 * Tato trieda spravuje vizualizaciu hraca, jeho umiestnenie na obrazovke.
 */
public class PlayerView extends JLabel {
    private PlayerModel model; // Model hraca, ktory reprezentuje jeho vlastnosti a spravanie

    /**
     * Konstruktor triedy PlayerView.
     * Inicializuje PlayerView s modelom hraca, ktory bude zobrazeny.
     * @param model model hraca, ktory bude zobrazeny
     */
    public PlayerView(PlayerModel model) {
        this.model = model;
    }

    /**
     * Metoda na aktualizaciu pozicie hraca.
     * Nastavi poziciu hraca na zaklade aktualnych suradnic modelu.
     */
    public void updatePosition() {
        model.getObjectSprite().setBounds(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
}
