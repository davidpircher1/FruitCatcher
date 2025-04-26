package Pause;

import Frames.MenuFrame;
import Loger.InfoLog;
import Timer.TimerController;
import javax.swing.*;

/**
 * Kontroler pre spravu pauzy v hre.
 * Tento kontroler spravuje zastavenie a pokracovanie casovaca a zobrazenie menu pauzy.
 */
public class PauseController {
    private PauseModel model;
    private PauseView view;
    private TimerController timerController;
    private JFrame parent;

    /**
     * Konstruktor pre inicializaciu kontrolera pauzy.
     * Inicializuje model, pohlad a casovac. Nastavi akciu pre tlacidlo pauzy.
     *
     * @param parent JFrame, ktory sluzi ako hlavny ramec hry.
     * @param timerController Controller pre casovac, ktory je zastaveny/pokroceny pri pauze.
     */
    public PauseController(JFrame parent, TimerController timerController) {
        this.parent = parent;
        this.timerController = timerController;
        this.model = new PauseModel();
        this.view = new PauseView(e -> togglePauseMenu());
    }

    /**
     * Getter pre pohlad pauzy.
     *
     * @return Pohlad pauzy.
     */
    public PauseView getView() {
        return view;
    }

    /**
     * Prepne medzi zobrazenim menu pauzy a pokracovanim hry.
     * Ak je hra pauzovana, ponukne moznost pokracovat alebo skoncit hru.
     */
    private void togglePauseMenu() {
        if (!model.isPaused()) {
            new InfoLog("Pauza hry!");
            timerController.pauseTimer();
            model.togglePause();
        }

        int choice = view.showPauseMenu(parent);
        if (choice == 1) { // Quit
            parent.dispose();
            new MenuFrame();
            new InfoLog("Hrac odisiel z hry");
        } else { // Resume
            model.togglePause();
            timerController.resumeTimer();
            new InfoLog("Hrac pustil hru");
        }
    }

    /**
     * Getter pre model pauzy.
     *
     * @return Model pauzy.
     */
    public PauseModel getModel() {
        return model;
    }
}
