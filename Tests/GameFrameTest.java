package Tests;

import Frames.GameFrame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*; // Potrebné pre GraphicsEnvironment a HeadlessException

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit testy pre triedu GameFrame BEZ POUŽITIA MOCKITO a bez priameho prístupu k gameLoop.
 */
class GameFrameTest {

    private GameFrame gameFrameInstance;

    @BeforeEach
    void setUp() {
        // Vynulovanie inštancie pred každým testom
        gameFrameInstance = null;
        System.out.println("Nastavenie testu: Headless prostredie = " + GraphicsEnvironment.isHeadless());
    }

    @AfterEach
    void tearDown() {
        // Upratovanie po teste
        // POZOR: Bez prístupu k 'gameLoop' vláknu ho nemôžeme explicitne zastaviť.
        // Ak konštruktor GameFrame úspešne spustil vlákno, môže ostať bežať aj po skončení testu!
        // Toto môže spôsobovať problémy v testovacom prostredí.
        if (gameFrameInstance != null) {
            System.out.println("Teardown: Uvoľnenie referencie na GameFrame...");
            // Volanie dispose() je stále problematické.
            gameFrameInstance = null;
        }
        System.out.println("Test ukončený.");
    }

    @Test
    void testKonstruktorGameFrame() {
        System.out.println("--- testKonstruktorGameFrame ŠTART ---");
        try {
            // Pokus o vytvorenie inštancie GameFrame
            // Vyžaduje funkčné závislosti A grafické prostredie.
            gameFrameInstance = new GameFrame();

            // Základné overenia, ak konštruktor prebehol úspešne
            assertNotNull(gameFrameInstance, "Inštancia GameFrame by mala byť vytvorená.");
            // Nemôžeme overiť stav herného vlákna bez getteru.
            // Môžeme overiť existenciu iných komponentov, ak by mali gettery (napr. getGame())
            // assertNotNull(gameFrameInstance.getGame(), "Game inštancia by mala byť vytvorená."); // Ak existuje getter

            System.out.println("Konštrukcia GameFrame bola úspešná (v tomto prostredí).");

        } catch (HeadlessException e) {
            // Očakávaná chyba v headless prostredí
            System.err.println("Test Ignorovaný/Zlyhal: Nie je možné vytvoriť GameFrame v headless prostredí. " + e.getMessage());
            fail("Test zlyhal kvôli HeadlessException počas vytvárania GameFrame.", e);
        } catch (Exception e) {
            // Iné neočakávané chyby
            System.err.println("Test Zlyhal: Neočakávaná chyba počas vytvárania GameFrame. " + e.getMessage());
            e.printStackTrace();
            fail("Test zlyhal kvôli neočakávanej výnimke počas vytvárania GameFrame.", e);
        } finally {
            System.out.println("--- testKonstruktorGameFrame KONIEC ---");
        }
    }

    @Test
    void testMetodaOnTimeUp() {
        System.out.println("--- testMetodaOnTimeUp ŠTART ---");
        try {
            // 1. Vytvorenie inštancie (rovnaké riziká ako v predchádzajúcom teste)
            gameFrameInstance = new GameFrame();
            assertNotNull(gameFrameInstance, "Predpoklad zlyhal: Nepodarilo sa vytvoriť GameFrame.");

            // 2. Priame zavolanie metódy onTimeUp (upravenej pre testy - bez JOptionPane a dispose)
            System.out.println("Volanie metódy onTimeUp priamo...");
            gameFrameInstance.onTimeUp();

            // 3. Overenie (veľmi obmedzené)
            // Nemôžeme overiť stav herného vlákna (či bolo zastavené).
            // Môžeme len predpokladať, že metóda prebehla bez chyby, ak sa sem dostaneme.
            System.out.println("Metóda onTimeUp bola zavolaná bez výnimky.");
            // Ak by sme mali getter napr. na skóre, mohli by sme ho tu overiť.


        } catch (HeadlessException e) {
            System.err.println("Test Ignorovaný/Zlyhal: Nie je možné spustiť test v headless prostredí. " + e.getMessage());
            fail("Test zlyhal kvôli HeadlessException.", e);
        } catch (Exception e) {
            System.err.println("Test Zlyhal: Neočakávaná chyba počas testu onTimeUp. " + e.getMessage());
            e.printStackTrace();
            fail("Test zlyhal kvôli neočakávanej výnimke počas testu onTimeUp.", e);
        } finally {
            System.out.println("--- testMetodaOnTimeUp KONIEC ---");
        }
    }
}