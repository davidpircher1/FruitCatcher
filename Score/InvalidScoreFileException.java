package Score;

/**
 * Vynimka, ktora signalizuje neplatny alebo nespravny format suboru so skore.
 * Tato vynimka sa pouziva na indikaciu, ze subor, ktory sa pokusame nacitat alebo spracovat,
 * ma neplatny format alebo je poskodeny.
 */
public class InvalidScoreFileException extends RuntimeException {

    /**
     * Konstruktor pre vytvorenie vynimky s poskytnutym spravou.
     *
     * @param message sprava popisujuca pricinu vynimky.
     */
    public InvalidScoreFileException(String message) {
        super(message);
    }
}
