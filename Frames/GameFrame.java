package Frames;

import Game.Fruit.FruitModel;
import Game.Game;
import Pause.PauseController;
import Loger.InfoLog;
import Timer.*;
import javax.swing.*;
import java.awt.*;

/**
 * trieda GameFrame predstavuje hlavne herne okno, kde prebieha cela hra.
 * obsahuje hernu logiku, ovladanie pauzy, casovac, zobrazovanie skore
 * a spracovanie kolizii pocas hernej slucky.
 */
public class GameFrame extends Frame {

    /** instancia hry */
    private Game game;

    /** model casovaca */
    private TimerModel timerModel;

    /** pohlad casovaca (zobrazuje zostavajuci cas) */
    private TimerView timerView;

    /** kontroler casovaca */
    private TimerController timerController;

    /** panel zobrazujuci skore, cas a tlacidlo pauzy */
    private JPanel gameInfoPanel;

    /** hlavna herna slucka beziaca v osobitnom vlakne */
    private Thread gameLoop;

    /** kontroler pre pauzu, uchovava stav pauzy a ovlada tlacidlo */
    private PauseController pauseController;

    /**
     * konstruktor vytvori herne okno, inicializuje vsetky komponenty hry,
     * casovac, skore a pauzu, a spusta hernu slucku.
     */
    public GameFrame() {
        super();
        game = new Game();

        setBackgroundImage("src/images/gameBackground.png");

        timerModel = new TimerModel();
        timerView = new TimerView();
        timerController = new TimerController(timerModel, timerView);

        gameInfoPanel = new JPanel();
        gameInfoPanel.setBounds(5, 5, 375, 40);
        gameInfoPanel.setLayout(new GridLayout(1, 3, 15, 0));
        gameInfoPanel.setOpaque(false);

        gameInfoPanel.add(game.getScoreView().getScoreLabel());
        gameInfoPanel.add(timerView.getTimeLabel());

        pauseController = new PauseController(this, timerController);
        gameInfoPanel.add(pauseController.getView().getPauseButton());

        getLayeredPane().add(gameInfoPanel, Integer.valueOf(1));

        timerController.start();

        getLayeredPane().add(game.getPlayerModel().getObjectSprite(), Integer.valueOf(2));
        getLayeredPane().add(game.getFruit().getObjectSprite(), Integer.valueOf(3));

        this.setFocusable(true);
        game.getPlayerModel().getObjectSprite().setFocusable(true);
        game.getPlayerModel().getObjectSprite().addKeyListener(game.getPlayerModel().getController());

        // herna slucka
        gameLoop = new Thread(() -> {
            while (true) {
                if (timerModel.getTimeLeft() == 0) {
                    onTimeUp();
                    break;
                }

                if (!pauseController.getModel().isPaused()) {
                    if (game.getFruit().getFruitController().OutOfBounds()) {
                        new InfoLog("Ovocie mimo hranic");
                        getLayeredPane().remove(game.getFruit().getObjectSprite());
                        repaint();
                        revalidate();

                        FruitModel newFruitModel = game.getFruit().getFruitController().newFruit();
                        game.setFruit(newFruitModel);
                        getLayeredPane().add(newFruitModel.getObjectSprite(), Integer.valueOf(3));
                    } else if (game.getPlayerModel().checkCollision(game.getFruit())) {
                        new InfoLog("Kolizia hraca s ovocim");
                        getLayeredPane().remove(game.getFruit().getObjectSprite());
                        repaint();
                        revalidate();

                        FruitModel newFruitModel = game.getFruit().getFruitController().newFruit();
                        game.setFruit(newFruitModel);
                        getLayeredPane().add(newFruitModel.getObjectSprite(), Integer.valueOf(3));
                    }

                    game.update();
                }

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        gameLoop.start();
    }

    /**
     * metoda sa zavola, ked vyprsi cas. Ukonci hru, zobrazi spravu
     * a vytvori {@link EndFrame} s vyslednym skore.
     */
    public void onTimeUp() {
        new InfoLog("Koniec!");
        JOptionPane.showMessageDialog(this, "Time's up!");
        this.dispose();
        new EndFrame(game.getScoreController().getScore());
    }
}

