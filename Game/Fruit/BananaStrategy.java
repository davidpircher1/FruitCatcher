package Game.Fruit;

/**
 * Stratégia pre banán, implementuje rozhranie {@link ScoreStrategy}.
 * Tento typ stratégie určuje, že pri zachytení banánu hráč získa 5 bodov.
 */
public class BananaStrategy implements ScoreStrategy {

    /**
     * Pridá 5 bodov pri zachytení banánu.
     *
     * @return Počet bodov, ktoré hráč získa za zachytenie banánu.
     */
    @Override
    public int addScore() {
        return 5; // Banán pridáva 5 bodov
    }

    /**
     * Vráti názov stratégie (typ ovocia).
     *
     * @return Názov stratégie, v tomto prípade "Banan".
     */
    @Override
    public String getName() {
        return "Banan";
    }

    /**
     * Vráti cestu k obrázku ovocia.
     *
     * @return Cesta k obrázku, ktorý sa má zobraziť pre banán.
     */
    @Override
    public String getImage() {
        return "Images/banana.png"; // Cesta k obrázku banánu
    }
}
