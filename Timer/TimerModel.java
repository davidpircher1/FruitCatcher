package Timer;

import Loger.InfoLog;
import javax.swing.Timer;

/**
 * Model pre riadenie casovaca.
 * Tato trieda spravuje logiku casovaca, vrátane zaciatku, pozastavenia, obnovenia a zastavenia casovania.
 * Taktiez poskytuje rozhranie pre aktualizacie casu.
 */
public class TimerModel {
    private int timeLeft = 30;
    private TimerListener listener;
    private Timer timer;

    /**
     * Rozhranie pre listenera, ktory spracuvava aktualizacie casu.
     * Metoda {@link #onTimeUpdate(int)} je volana pri kazdej zmene casu.
     */
    public interface TimerListener {
        /**
         * Metoda volana pri kazdej zmene casu.
         *
         * @param timeLeft Zostavajuci cas, ktory bude zobrazeny.
         */
        void onTimeUpdate(int timeLeft);
    }

    /**
     * Kontruktor pre inicializaciu modelu casovaca.
     * Vytvara casovac, ktory sa spusta kazdu sekundu a zavola {@link #updateTime()}.
     */
    public TimerModel() {
        timer = new Timer(1000, e -> updateTime());
    }

    /**
     * Nastavi listenera pre aktualizacie casu.
     * Listener je volany pri kazdej zmene casu.
     *
     * @param listener Listener, ktory spracovava aktualizacie casu.
     */
    public void setListener(TimerListener listener) {
        this.listener = listener;
    }

    /**
     * Spusti casovac.
     * Casovac zacne odmeriavanie casu.
     */
    public void start() {
        timer.start();
    }

    /**
     * Zastavi casovac.
     * Casovac je zastaveny a odmeriavanie sa zastavi.
     */
    public void stop() {
        timer.stop();
    }

    /**
     * Pozastavi casovac.
     * Casovac je pozastaveny a dalsie odmeriavanie sa zastavi.
     */
    public void pauseTimer() {
        timer.stop();
    }

    /**
     * Obnovi casovac.
     * Casovac sa spusti z miesta, kde bol pozastaveny.
     */
    public void resumeTimer() {
        timer.start();
    }

    /**
     * Metoda pre aktualizaciu casu.
     * Zmensi zostavajuci cas o 1 sekundu a zavola {@link TimerListener#onTimeUpdate(int)}.
     * Ak cas dosiahne 0, casovac sa zastavi.
     */
    public void updateTime() {
        if (timeLeft > 0) {
            timeLeft--;
            new InfoLog("Ostavajuci cas: " + timeLeft);
            if (listener != null) listener.onTimeUpdate(timeLeft);
        } else {
            stop();
        }
    }

    /**
     * Získa zostávajúci čas.
     *
     * @return Zostávajúci čas v sekundách.
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * Nastaví zostávajúci čas.
     *
     * @param timeLeft Nový zostávajúci čas v sekundách.
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
