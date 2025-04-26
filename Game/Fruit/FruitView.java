package Game.Fruit;

/**
 * Zobrazenie ovocia v hre.
 * Táto trieda je zodpovedná za zobrazenie ovocia na obrazovke a aktualizáciu jeho pozície
 * podľa modelu ovocia.
 */
public class FruitView {
    private FruitModel model;

    /**
     * Vytvorí nové zobrazenie ovocia, ktoré bude spojené s daným modelom ovocia.
     *
     * @param model Model ovocia, ktorý táto trieda bude zobrazovať.
     */
    public FruitView(FruitModel model) {
        this.model = model;
    }

    /**
     * Aktualizuje pozíciu zobrazenia ovocia na obrazovke podľa aktuálnych
     * hodnôt pozície a rozmerov modelu ovocia.
     */
    public void updatePosition() {
        model.getObjectSprite().setBounds(model.getX(), model.getY(), model.getWidth(), model.getHeight());
    }
}
