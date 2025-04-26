package Game.Fruit;

/**
 * Stratégia pre jablko, implementuje rozhranie {@link ScoreStrategy}.
 * Tento typ stratégie určuje, že pri zachytení jablka hráč získa 1 bod.
 */
public class AppleStrategy implements ScoreStrategy {

    /**
     * Pridá 1 bod pri zachytení jablka.
     *
     * @return Počet bodov, ktoré hráč získa za zachytenie jablka.
     */
    @Override
    public int addScore() {
        return 1; // Jablko pridáva 1 bod
    }

    /**
     * Vráti názov stratégie (typ ovocia).
     *
     * @return Názov stratégie, v tomto prípade "Jablko".
     */
    @Override
    public String getName() {
        return "Jablko";
    }

    /**
     * Vráti cestu k obrázku ovocia.
     *
     * @return Cesta k obrázku, ktorý sa má zobraziť pre jablko.
     */
    @Override
    public String getImage() {
        return "Images/jablko.png"; // Cesta k obrázku jablka
    }
}
