package Game.Fruit;

import java.util.Random;

/**
 * Kontrolér pre ovocie v hre.
 * Tento kontrolér je zodpovedný za pohyb ovocia, kontrolu, či ovocie opustilo hranice obrazovky,
 * a generovanie nového ovocia s náhodnými vlastnosťami.
 */
public class FruitController {
    private FruitModel model;

    /**
     * Vytvorí nový kontrolér pre ovocie s daným modelom ovocia.
     *
     * @param model Model ovocia, ktorý tento kontrolér bude riadiť.
     */
    public FruitController(FruitModel model) {
        this.model = model;
    }

    /**
     * Skontroluje, či ovocie opustilo hranice obrazovky (ak jeho Y pozícia presiahne dolnú hranicu).
     *
     * @return true, ak ovocie je mimo hraníc (y > 340 + výška ovocia), inak false.
     */
    public boolean OutOfBounds() {
        if(model.getY() > 340 + model.getHeight()) {
            return true;
        }

        return false;
    }

    /**
     * Presúva ovocie nadol po obrazovke, pokiaľ nevyšlo mimo hraníc.
     *
     * @see #OutOfBounds()
     */
    public void move() {
        if(!OutOfBounds()) {
            model.setPosition(model.getSpeed());
        }
    }

    /**
     * Vytvorí nové ovocie na obrazovke s náhodnými vlastnosťami,
     * ako sú pozícia a rýchlosť.
     *
     * @return nový model ovocia s náhodnou pozíciou a rýchlosťou.
     */
    public FruitModel newFruit() {
        Random random = new Random();

        // Vytvárame nový náhodný model ovocia s náhodnou pozíciou a rýchlosťou
        int newX = random.nextInt(350); // Predpokladajme, že obrazovka má šírku 400px
        int newY = 0; // Nastavíme Y na vrchol obrazovky (0)

        // Vytvoríme nový model ovocia s náhodnými hodnotami
        return new FruitModel(newX, newY, 50, 50); // Predpokladám, že FruitModel má konstruktor (x, y, width, height)
    }
}
