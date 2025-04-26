package Score;

/**
 * Kontroler pre spravu a aktualizaciu skore v hre.
 * Tato trieda je zodpovedna za interakciu medzi modelom a zobrazenim skore.
 * Umoznuje nastavit a ziskat skore, ako aj aktualizovat zobrazenie skore na obrazovke.
 */
public class ScoreController {
    private ScoreModel model;
    private ScoreView view;

    /**
     * Konstruktor pre vytvorenie noveho kontrolera, ktory prepoji model a zobrazenie.
     *
     * @param model Model, ktory uchovava aktualne skore.
     * @param view  Zobrazenie, ktore aktualizuje skore na obrazovke.
     */
    public ScoreController(ScoreModel model, ScoreView view) {
        this.model = model;
        this.view = view;
        updateView();
    }

    /**
     * Nastavi nove skore a aktualizuje zobrazenie skore.
     *
     * @param score Nove skore, ktore sa ma nastavit.
     */
    public void setScore(int score) {
        model.setScore(score);
        updateView();
    }

    /**
     * Ziska aktualne skore z modelu.
     *
     * @return Aktualne skore.
     */
    public int getScore() {
        return model.getScore();
    }

    /**
     * Aktualizuje zobrazenie skore na zaklade aktualneho skore v modeli.
     */
    private void updateView() {
        view.updateScore(model.getScore());
    }
}
