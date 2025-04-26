package Game;

import Game.Fruit.FruitModel;
import Game.Fruit.FruitView;
import Game.Player.*;
import Score.ScoreModel;
import Score.ScoreView;
import Score.ScoreController;

/**
 * trieda Game predstavuje hlavnu logiku hry, spravuje hraca, ovocie a skore.
 * Zabezpecuje pravidelnu aktualizaciu pozicie objektov a skore.
 */
public class Game {
    // Model a view pre hraca
    private PlayerModel playerModel;
    private PlayerView playerView;

    // Model, view a controller pre skore
    private ScoreModel scoreModel;
    private ScoreView scoreView;
    private ScoreController scoreController;

    // Model a view pre ovocie
    private FruitModel fruit = new FruitModel(100, 20, 50, 50);
    private FruitView fruitView = new FruitView(fruit);

    /**
     * Konstruktor vytvori novu instanciu hry, inicializuje hraca, skore a ovocie.
     */
    public Game() {
        playerModel = new PlayerModel(150, 340);
        playerView = new PlayerView(playerModel);

        scoreModel = new ScoreModel();
        scoreView = new ScoreView();
        scoreController = new ScoreController(scoreModel, scoreView);
    }

    /**
     * Aktualizuje hru: pohyb hraca, aktualizacia skore a pohyb ovocia.
     * Ak hrac koliduje s ovocim, skore sa zvysuje.
     */
    public void update() {
        playerModel.getController().updateMovement();
        playerView.updatePosition();
        scoreController.setScore(scoreController.getScore()); // aktualizuje zobrazovanie skore
        fruit.getFruitController().move();
        fruitView.updatePosition();
        if (playerModel.checkCollision(fruit)) {
            scoreModel.setScore(scoreModel.getScore() + fruit.getChangeScore());
        }
    }

    /** @return model hraca */
    public PlayerModel getPlayerModel() {
        return playerModel;
    }

    /** @return view skore */
    public ScoreView getScoreView() {
        return scoreView;
    }

    /** @return controller skore */
    public ScoreController getScoreController() {
        return scoreController;
    }

    /** @return model ovocia */
    public FruitModel getFruit() {
        return fruit;
    }

    /**
     * Nastavi nove ovocie v hre.
     * @param fruit nove ovocie
     */
    public void setFruit(FruitModel fruit) {
        this.fruit = fruit;
        fruitView = new FruitView(fruit);
    }
}

