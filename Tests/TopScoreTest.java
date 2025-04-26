package Tests;

import Score.TopScore;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testovacia trieda pre triedu TopScore.
 * Testuje inicializaciu a aktualizaciu skore v subore pre top skore.
 */
class TopScoreTest {

    private File tempFile;

    /**
     * Nastavuje docasny subor pred kazdym testom, ktory obsahuje 3 pociatocne skore.
     * @throws IOException Ak nastane chyba pri vytvarani alebo pisani do suboru.
     */
    @BeforeEach
    void setup() throws IOException {
        // Vytvor docasny subor s 3 skore
        tempFile = File.createTempFile("topScoreTest", ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            writer.write("100\n90\n80\n");
        }
    }

    /**
     * Odstraňuje docasny subor po kazdom teste.
     */
    @AfterEach
    void cleanup() {
        // Vymaz docasny subor
        if (tempFile.exists()) {
            tempFile.delete();
        }
    }

    /**
     * Testuje inicializaciu top skore zo suboru.
     * Ocakava sa, ze top skore bude [100, 90, 80].
     * @throws IOException Ak nastane chyba pri citani zo suboru.
     */
    @Test
    void testTopScoreInitialization() throws IOException {
        TopScore topScore = new TopScore() {
            @Override
            public String getPath() {
                return tempFile.getPath();
            }
        };

        int[] scores = topScore.getTopScores();
        assertArrayEquals(new int[]{100, 90, 80}, scores);
    }

    /**
     * Testuje, ze skore mensie ako existujuce skore neaktualizuje subor.
     * Ocakava sa, ze skore 50 nebude pridane do suboru.
     * @throws IOException Ak nastane chyba pri citani alebo pisani do suboru.
     */
    @Test
    void testUpdateScoreLowerThanExisting() throws IOException {
        // Skore mensie ako tretie najlepsie
        TopScore topScore = new TopScore() {
            @Override
            public String getPath() {
                return tempFile.getPath();
            }
        };

        topScore.updateScore(50, tempFile.getPath());

        // Subor by mal zostat nezmeneny
        String content = Files.readString(tempFile.toPath());
        assertTrue(content.contains("100"));
        assertTrue(content.contains("90"));
        assertFalse(content.contains("50"));
    }

    /**
     * Testuje, ze skore vacsie ako tretie najlepsie bude pridane a zoradene v subore.
     * Ocakava sa, ze subor bude obsahovat skore v poradí [100, 95, 90].
     * @throws IOException Ak nastane chyba pri citani alebo pisani do suboru.
     */
    @Test
    void testUpdateScoreHigherThanLowest() throws IOException {
        // Skore vacsie ako tretie najlepsie, malo by zmenit pole a zapisat do suboru
        TopScore topScore = new TopScore() {
            @Override
            public String getPath() {
                return tempFile.getPath();
            }
        };

        topScore.updateScore(95, tempFile.getPath());

        // Skore by malo byt zaktualizovane a zoradene v subore
        String[] updated = Files.readAllLines(tempFile.toPath()).toArray(new String[0]);
        assertEquals("100", updated[0]);
        assertEquals("95", updated[1]);
        assertEquals("90", updated[2]);
    }

    /**
     * Testuje, ze skore rovnake ako tretie najlepsie sa neaktualizuje v subore.
     * @throws IOException Ak nastane chyba pri citani alebo pisani do suboru.
     */
    @Test
    void testUpdateScoreEqualsExisting() throws IOException {
        // Skore rovnake ako tretie najlepsie, neaktualizuje subor
        TopScore topScore = new TopScore() {
            @Override
            public String getPath() {
                return tempFile.getPath();
            }
        };

        topScore.updateScore(80, tempFile.getPath());

        String[] updated = Files.readAllLines(tempFile.toPath()).toArray(new String[0]);
        assertEquals("100", updated[0]);
        assertEquals("90", updated[1]);
        assertEquals("80", updated[2]); // Skore sa nezmeni
    }

    /**
     * Testuje, ze skore nove, najvacsie, bude najprv pridane.
     * @throws IOException Ak nastane chyba pri citani alebo pisani do suboru.
     */
    @Test
    void testUpdateScoreNewHighest() throws IOException {
        // Skore nove, najvacsie, bude najprv pridane
        TopScore topScore = new TopScore() {
            @Override
            public String getPath() {
                return tempFile.getPath();
            }
        };

        topScore.updateScore(120, tempFile.getPath());

        // Skore by malo byt zaktualizovane, zoradene a pridane do suboru
        String[] updated = Files.readAllLines(tempFile.toPath()).toArray(new String[0]);
        assertEquals("120", updated[0]); // Najvacsie
        assertEquals("100", updated[1]);
        assertEquals("90", updated[2]);
    }
}
