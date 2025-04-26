package Game.Fruit;

import java.util.List;
import java.util.Random;

/**
 * Továreň na vytváranie náhodných strategických typov ovocia.
 * Táto trieda poskytuje spôsob generovania náhodného ovocia, ktoré bude mať rôzne vlastnosti
 * a správanie, ako je pridávanie bodov alebo zobrazenie rôznych obrázkov ovocia.
 */
public class StrategyFactory {
    // Zoznam všetkých dostupných strategických typov ovocia
    private static final List<ScoreStrategy> strategies = List.of(
            new AppleStrategy(), // Apple ovocie
            new BananaStrategy(), // Banana ovocie
            new BombStrategy() // Bombová strategia (pravdepodobne poškodí skóre alebo hru)
    );

    // Generátor náhodných čísel na výber náhodnej stratégie
    private static final Random random = new Random();

    /**
     * Vráti náhodnú strategiu ovocia. Je to staticke, lebo mi prislo zbytocne vytrvarat konstuktor pre toto.
     * Metóda vyberie jednu náhodnú strategiu z preddefinovaného zoznamu a vráti ju.
     *
     * @return Náhodná strategia typu {@link ScoreStrategy}.
     */
    public static ScoreStrategy getRandomStrategy() {
        return strategies.stream()
                .skip(random.nextInt(strategies.size())) // Skipne náhodný počet prvkov
                .findFirst() // Zoberie prvý element po preskaku
                .orElseThrow(); // Nikdy by sa to nemalo stať, ale ak áno, vyvolá výnimku
    }
}
