package Pause;

/**
 * Model pre spravu stavu pozastavenia hry.
 * Udrziava informacie o tom, ci je hra pozastavena alebo nie.
 */
public class PauseModel {
    // Premenna uchovavajuca stav pozastavenia hry
    private boolean isPaused;

    /**
     * Konstruktor pre inicializaciu modelu pozastavenia.
     * Predvoleny stav je "nie je pozastavene" (false).
     */
    public PauseModel() {
        this.isPaused = false;
    }

    /**
     * Metoda na ziskanie aktualneho stavu pozastavenia.
     *
     * @return true ak je hra pozastavena, inak false.
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Metoda na prepnutie stavu pozastavenia.
     * Ak je hra pozastavena, po zavolani sa odstaví (nepozastavene), a naopak.
     */
    public void togglePause() {
        this.isPaused = !this.isPaused;
    }


}
