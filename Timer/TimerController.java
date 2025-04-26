package Timer;

/**
 * Kontroler pre riadenie casovaca.
 * Tato trieda poskytuje rozhranie na spustenie, pozastavenie, obnovu a zastavenie casovaca.
 * Okrem toho spracovava aktualizacie casu a zobrazuje ich na obrazovke.
 */
public class TimerController implements TimerModel.TimerListener {
    private TimerModel model;
    private TimerView view;

    /**
     * Kontruktor pre inicializaciu kontrolera casovaca.
     * Nastavi model a zobrazenie, a tiez priradi listener pre model.
     *
     * @param model Model casovaca, ktory spravuje logiku casovania.
     * @param view  Zobrazenie casu, ktore ukazuje zostavajuci cas na obrazovke.
     */
    public TimerController(TimerModel model, TimerView view) {
        this.model = model;
        this.view = view;
        this.model.setListener(this);
    }

    /**
     * Spusti casovac.
     * Tento metod spusti casovac, ktory zacne odmeriavanie casu.
     */
    public void start() {
        model.start();
    }

    /**
     * Pozastavi casovac.
     * Tento metod pozastavi odmeriavanie casu.
     */
    public void pauseTimer() {
        model.pauseTimer();
    }

    /**
     * Obnovi casovac.
     * Tento metod pokracuje v odmeriavani casu, ak bol casovac pozastaveny.
     */
    public void resumeTimer() {
        model.resumeTimer();
    }

    /**
     * Zastavi casovac.
     * Tento metod zastavi odmeriavanie casu a resetuje casovac.
     */
    public void stop() {
        model.stop();
    }

    /**
     * Metoda, ktora je volana pri kazdej zmene casu.
     * Aktualizuje zobrazenie casu v pohliade.
     *
     * @param timeLeft Zostavajuci cas, ktory bude zobrazeny.
     */
    @Override
    public void onTimeUpdate(int timeLeft) {
        view.updateTimeLabel(timeLeft);
    }
}
