package Game.Fruit;

/**
 * Stratégia pre bombu, implementuje rozhranie {@link ScoreStrategy}.
 * Tento typ stratégie určuje, že pri zachytení bomby hráč stratí 5 bodov.
 */
public class BombStrategy implements ScoreStrategy {

    /**
     * Odpočíta 5 bodov pri zachytení bomby.
     *
     * @return Počet bodov, ktoré hráč stratí za zachytenie bomby (v tomto prípade -5).
     */
    @Override
    public int addScore() {
        return -5; // Bomba odpočíta 5 bodov
    }

    /**
     * Vráti názov stratégie (typ ovocia).
     *
     * @return Názov stratégie, v tomto prípade "Bomb".
     */
    @Override
    public String getName() {
        return "Bomb";
    }

    /**
     * Vráti cestu k obrázku bomby.
     *
     * @return Cesta k obrázku, ktorý sa má zobraziť pre bombu.
     */
    @Override
    public String getImage() {
        return "Images/bomb.png"; // Cesta k obrázku bomby
    }
}
