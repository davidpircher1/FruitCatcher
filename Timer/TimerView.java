package Timer;

import javax.swing.*;
import java.awt.*;

/**
 * View pre zobrazenie casu v hre.
 * Tato trieda sluzi na vizualizaciu aktualneho casu a jeho aktualizaciu na obrazovke.
 */
public class TimerView {
    private JLabel timeLabel;

    /**
     * Kontruktor pre vytvorenie pohľadu casovaca.
     * Inicializuje JLabel pre zobrazenie casu a nastavi jeho pismo.
     */
    public TimerView() {
        timeLabel = new JLabel("Time: 10");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
    }

    /**
     * Získa JLabel, ktorý zobrazuje čas.
     *
     * @return JLabel pre cas.
     */
    public JLabel getTimeLabel() {
        return timeLabel;
    }

    /**
     * Aktualizuje zobrazenie casu na obrazovke.
     *
     * @param timeLeft Novy cas, ktory sa ma zobrazit.
     */
    public void updateTimeLabel(int timeLeft) {
        timeLabel.setText("Time: " + timeLeft);
    }
}
