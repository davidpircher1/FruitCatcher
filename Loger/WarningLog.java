package Loger;

/**
 * Trieda pre logovanie varovnych sprav.
 * Tento log zapise spravy s predponou "[WARNING]" do konzoly.
 */
class WarningLog extends Loger {

    /**
     * Konstruktor pre inicializaciu varovneho logu.
     * Zavola konstruktor predka {@link Loger} a nasledne zapise spravu s predponou "[WARNING]".
     *
     * @param message Sprava, ktoru je potrebne zaznamenat ako varovny log.
     */
    public WarningLog(String message) {
        super(message);
    }

    /**
     * Metoda pre logovanie varovnej spravy.
     * Tento log je formatovany s predponou "[WARNING]".
     *
     * @param message Sprava, ktoru je potrebne zaznamenat.
     */
    @Override
    public void logMessage(String message) {
        System.out.println("[WARNING] " + message);
    }
}
