package Score;

/**
 * Model pre spravu skore v hre.
 * Tato trieda uchovava aktualne skore a poskytuje metody na jeho ziskanie a nastavenie.
 */
public class ScoreModel {
    // Aktualne skore
    private int score = 1;

    /**
     * Ziska aktualne skore.
     *
     * @return Aktualne skore.
     */
    public int getScore() {
        return score;
    }

    /**
     * Nastavi nove skore.
     *
     * @param score Nove skore, ktore sa ma nastavit.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
