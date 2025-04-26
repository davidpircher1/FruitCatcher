package Game.Fruit;

/**
 * Rozhranie ScoreStrategy urcuje strategiu pre pridavanie skore a poskytovanie informacií o ovoci.
 * Toto rozhranie implementuju konkretne typy ovocia, ktore urcuju, ako sa prida skore a ake obrazky sa pouziju.
 */
public interface ScoreStrategy {

    /**
     * Metoda na pridanie skore na zaklade specifickej implementacie ovocia.
     * @return hodnota, ktora bude pridana do skore
     */
    int addScore();

    /**
     * Metoda na ziskanie nazvu ovocia.
     * @return nazov ovocia
     */
    String getName();

    /**
     * Metoda na ziskanie obrazka reprezentujuceho ovocie.
     * @return cesta k obrazku ovocia
     */
    String getImage();
}
