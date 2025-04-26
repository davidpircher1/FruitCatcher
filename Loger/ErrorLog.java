package Loger;

/**
 * Trieda pre logovanie chybovych sprav.
 * Tento log zapise spravy s predponou "[ERROR]" do chyboveho vystupu (stderr).
 */
public class ErrorLog extends Loger {

    /**
     * Konstruktor pre inicializaciu chyboveho logu.
     * Zavola konstruktor predka {@link Loger} a nasledne zapise spravu s predponou "[ERROR]" do chyboveho vystupu.
     *
     * @param message Sprava, ktoru je potrebne zaznamenat ako chybovy log.
     */
    public ErrorLog(String message) {
        super(message);
    }

    /**
     * Metoda pre logovanie chybovej spravy.
     * Tento log je formatovany s predponou "[ERROR]" a vypisuje sa do chyboveho vystupu (stderr).
     *
     * @param message Sprava, ktoru je potrebne zaznamenat.
     */
    @Override
    public void logMessage(String message) {
        System.err.println("[ERROR] " + message);
    }
}
