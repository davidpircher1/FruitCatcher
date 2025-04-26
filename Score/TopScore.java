package Score;

import java.io.*;
import java.util.Arrays;

/**
 * Trieda na spravu a nacitanie top skore zo suboru.
 * Tato trieda nacitava 3 najlepsie skore zo suboru, poskytuje pristup k tymto hodnotam
 * a umoznuje aktualizaciu skore, ak je nove skore dostatocne vysoke.
 */
public class TopScore {
    // Pole pre ulozenie 3 najlepsich skore
    private final int[] topScore;

    /**
     * Konstruktor, ktory nacita top skore zo suboru.
     * Tento konstruktor nacita skore zo suboru a ulozi ich do poľa.
     * Ak subor obsahuje neplatny format, alebo nie je dostatok skore, vyhodi vynimku.
     *
     * @throws IOException Ak dojde k chybe pri citani zo suboru.
     * @throws InvalidScoreFileException Ak subor obsahuje neplatny format skore alebo neobsahuje dostatok skore.
     */
    public TopScore() throws IOException, InvalidScoreFileException {
        topScore = new int[3];  // Ocakavame 3 top skore
        BufferedReader reader = new BufferedReader(new FileReader(getPath()));

        String line;
        int index = 0;

        // Citanie jednotlivych skore zo suboru
        while ((line = reader.readLine()) != null && index < topScore.length) {
            try {
                topScore[index] = Integer.parseInt(line.trim());
                index++;
            } catch (NumberFormatException e) {
                reader.close();
                throw new InvalidScoreFileException("Neplatny format skore v subore: \"" + line + "\"");
            }
        }
        reader.close();

        // Ak nie su 3 skore, vyhodi vynimku
        if (index < topScore.length) {
            throw new InvalidScoreFileException("Subor neobsahuje dostatok skore. Ocakvane: 3, nacitane: " + index);
        }
    }

    /**
     * Ziska 3 top skore.
     *
     * @return Pole obsahujuci 3 najlepsie skore.
     */
    public int[] getTopScores() {
        return topScore;
    }

    /**
     * Ziska cestu k suboru obsahujucemu top skore.
     *
     * @return Cesta k suboru top skore.
     */
    public String getPath() {
        return "src/Score/topScore.txt";  // Predpokladana cesta k suboru
    }

    /**
     * Aktualizuje skore v subore, ak je nove skore vyssie nez posledne skore v zozname.
     * Po pridani noveho skore, zoznam sa zoradi a ulozi spat do suboru.
     *
     * @param newScore Nove skore, ktore sa ma pridat.
     * @param fileName Nazov suboru, kde sa maju ulozit nove skore.
     * @throws IOException Ak dojde k chybe pri zapisovani do suboru.
     */
    public void updateScore(int newScore, String fileName) throws IOException {
        int[] scores = getTopScores();

        // Ak je nove skore vacsie nez najmensie skore, aktualizuj skore
        if (newScore > scores[2]) {
            scores[2] = newScore;  // Nahrad posledne skore
            Arrays.sort(scores);  // Zoradime skore vzostupne

            // Zapismen zoradene skore do suboru
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                for (int i = scores.length - 1; i >= 0; i--) {
                    writer.write(scores[i] + "\n");
                }
            }
        }
    }
}
