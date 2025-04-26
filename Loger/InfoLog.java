package Loger;

/**
 * Trieda pre logovanie informacnych sprav.
 * Tento log zapise spravy s predponou "[INFO]" do konzoly.
 */
public class InfoLog extends Loger {

    /**
     * Konstruktor pre inicializaciu informacneho logu.
     * Zavola konstruktor predka {@link Loger} a nasledne zapise spravu s predponou "[INFO]".
     *
     * @param message Sprava, ktoru je potrebne zaznamenat ako informacny log.
     */
    public InfoLog(String message) {
        super(message);
    }

    /**
     * Metoda pre logovanie informacnej spravy.
     * Tento log je formatovany s predponou "[INFO]".
     *
     * @param message Sprava, ktoru je potrebne zaznamenat.
     */
    @Override
    public void logMessage(String message) {
        System.out.println("[INFO] " + message);
    }
}
