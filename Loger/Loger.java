package Loger;

/**
 * Abstraktna trieda pre logovanie sprav. Umoznuje definovat logovanie spravy do konzoly.
 * Tieto logy su predvolene formatovane s prefixom "[LOG]".
 */
public abstract class Loger {

    /**
     * Konstruktor pre inicializaciu logu so spravou.
     * Tento konstruktor zavola metodu {@link #logMessage(String)}, ktora vypise spravu do konzoly.
     *
     * @param message Sprava, ktoru je potrebne zaznamenat.
     */
    public Loger(String message) {
        logMessage(message);
    }

    /**
     * Metoda pre logovanie spravy do konzoly. Tento format moze byt specificky pre jednotlivé typy logov.
     * @param message Sprava, ktoru je potrebne zaznamenat.
     */
    public void logMessage(String message) {
        System.out.println("[LOG] " + message);
    }
}
