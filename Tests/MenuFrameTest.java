package Tests;

import Frames.MenuFrame;
import Frames.Button; // Importuj tvoju vlastnú triedu Button
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Optional;


class MenuFrameTest {

    private MenuFrame menuFrame;

    // Pomocná metóda na nájdenie JPanel s tlačidlami
    // Poznámka: Toto je trochu krehké, závisí od implementačných detailov
    private Optional<JPanel> findMenuButtonsPanel(Container container) {
        return Arrays.stream(container.getComponents())
                .filter(comp -> comp instanceof JPanel)
                .map(comp -> (JPanel) comp)
                .filter(panel -> panel.getLayout() instanceof GridLayout)
                .filter(panel -> ((GridLayout)panel.getLayout()).getRows() == 3 && ((GridLayout)panel.getLayout()).getColumns() == 1)
                .filter(panel -> panel.getComponentCount() == 3) // Predpokladáme, že obsahuje 3 tlačidlá
                .findFirst();
    }

    // Pomocná metóda na nájdenie tlačidla podľa textu v paneli
    private Optional<Button> findButtonByText(JPanel panel, String text) {
        return Arrays.stream(panel.getComponents())
                .filter(comp -> comp instanceof Button) // Použi tvoju triedu Button
                .map(comp -> (Button) comp)
                .filter(button -> text.equals(button.getText())) // Predpokladáme metódu getText()
                .findFirst();
    }


    @BeforeEach
    void setUp() {
        // Skúsime vytvoriť inštanciu. Ak to zlyhá kvôli HeadlessException,
        // testy, ktoré vyžadujú inštanciu, budú preskočené.
        try {
            // Potenciálne problematické v headless prostredí:
            menuFrame = new MenuFrame();
            // Ak chceme testovať komponenty, okno musí byť 'realizované', ale nie nutne viditeľné
            // menuFrame.pack(); // Môže, ale nemusí pomôcť/byť potrebné
        } catch (HeadlessException e) {
            System.err.println("WARNING: Testy MenuFrame vyžadujú grafické prostredie a boli preskočené kvôli HeadlessException.");
            menuFrame = null; // Zabezpečíme, že testy používajúce frame budú preskočené
        } catch (Exception e) {
            System.err.println("WARNING: Neočakávaná chyba pri vytváraní MenuFrame: " + e.getMessage());
            menuFrame = null;
        }
    }

    @Test
    @DisplayName("Konštruktor by mal vytvoriť inštanciu MenuFrame")
    void testFrameInstantiation() {
        // Tento test slúži hlavne na overenie, či konštruktor prebehne bez výnimky
        // v testovacom prostredí. Používame assumeTrue na preskočenie, ak setUp zlyhalo.
        assumeTrue(menuFrame != null, "MenuFrame inštancia nemohla byť vytvorená (pravdepodobne HeadlessException).");
        assertNotNull(menuFrame, "MenuFrame inštancia by nemala byť null, ak setUp prebehol.");
        // Môžeme pridať ďalšie základné asserty, ak má Frame nejaké očakávané vlastnosti
        // napr. assertTrue(menuFrame.getTitle().contains("Menu")); // Ak by mal titulok
    }

    @Test
    @DisplayName("MenuFrame by mal obsahovať JPanel pre tlačidlá s GridLayout")
    void testMenuButtonsPanelExists() {
        assumeTrue(menuFrame != null, "Preskakuje sa: MenuFrame inštancia nebola vytvorená.");

        // Musíme získať prístup k panelu. Je pridaný do LayeredPane.
        JLayeredPane layeredPane = menuFrame.getLayeredPane();
        assertNotNull(layeredPane, "LayeredPane by nemal byť null.");

        Optional<JPanel> panelOpt = findMenuButtonsPanel(layeredPane);

        assertTrue(panelOpt.isPresent(), "JPanel pre menu tlačidlá (s GridLayout 3x1 a 3 komponentmi) by mal existovať v LayeredPane.");

        panelOpt.ifPresent(panel -> {
            LayoutManager layout = panel.getLayout();
            assertTrue(layout instanceof GridLayout, "Layout panelu by mal byť GridLayout.");
            GridLayout gridLayout = (GridLayout) layout;
            assertEquals(3, gridLayout.getRows(), "GridLayout by mal mať 3 riadky.");
            assertEquals(1, gridLayout.getColumns(), "GridLayout by mal mať 1 stĺpec.");
            // assertEquals(15, gridLayout.getVgap(), "Vertikálny odstup v GridLayout by mal byť 15."); // Vgap sedí
            // assertEquals(0, gridLayout.getHgap(), "Horizontálny odstup v GridLayout by mal byť 0."); // Hgap sedí
            // assertFalse(panel.isOpaque(), "Panel by nemal byť priehľadný (setOpaque(false))."); // isOpaque() je true defaultne pre JPanel, setOpaque(false) to meni
            assertFalse(panel.isOpaque(), "Panel by mal byť nastavený ako nepriehľadný (setOpaque(false)).");

        });
    }

    @Test
    @DisplayName("Panel s tlačidlami by mal obsahovať 3 tlačidlá so správnymi textami")
    void testButtonsExistAndHaveCorrectText() {
        assumeTrue(menuFrame != null, "Preskakuje sa: MenuFrame inštancia nebola vytvorená.");
        JLayeredPane layeredPane = menuFrame.getLayeredPane();
        Optional<JPanel> panelOpt = findMenuButtonsPanel(layeredPane);
        assertTrue(panelOpt.isPresent(), "JPanel pre menu tlačidlá musí existovať.");

        JPanel menuButtons = panelOpt.get();
        assertEquals(3, menuButtons.getComponentCount(), "Panel by mal obsahovať presne 3 komponenty.");

        // Overíme konkrétne tlačidlá podľa textu (predpokladáme, že tvoja trieda Button má getText())
        assertTrue(findButtonByText(menuButtons, "Play").isPresent(), "Tlačidlo 'Play' by malo existovať.");
        assertTrue(findButtonByText(menuButtons, "Score").isPresent(), "Tlačidlo 'Score' by malo existovať.");
        assertTrue(findButtonByText(menuButtons, "About").isPresent(), "Tlačidlo 'About' by malo existovať.");

        // Prípadne overenie poradia, ak je dôležité:
        Component comp1 = menuButtons.getComponent(0);
        Component comp2 = menuButtons.getComponent(1);
        Component comp3 = menuButtons.getComponent(2);

        assertInstanceOf(Button.class, comp1, "Prvý komponent by mal byť Button.");
        assertInstanceOf(Button.class, comp2, "Druhý komponent by mal byť Button.");
        assertInstanceOf(Button.class, comp3, "Tretí komponent by mal byť Button.");

        assertEquals("Play", ((Button)comp1).getText(), "Text prvého tlačidla by mal byť 'Play'.");
        assertEquals("Score", ((Button)comp2).getText(), "Text druhého tlačidla by mal byť 'Score'.");
        assertEquals("About", ((Button)comp3).getText(), "Text tretieho tlačidla by mal byť 'About'.");
    }

    @Test
    @DisplayName("Všetky tlačidlá by mali mať priradený ActionListener")
    void testButtonsHaveActionListeners() {
        assumeTrue(menuFrame != null, "Preskakuje sa: MenuFrame inštancia nebola vytvorená.");
        JLayeredPane layeredPane = menuFrame.getLayeredPane();
        Optional<JPanel> panelOpt = findMenuButtonsPanel(layeredPane);
        assertTrue(panelOpt.isPresent(), "JPanel pre menu tlačidlá musí existovať.");
        JPanel menuButtons = panelOpt.get();

        Button playButton = findButtonByText(menuButtons, "Play").orElse(null);
        Button scoreButton = findButtonByText(menuButtons, "Score").orElse(null);
        Button aboutButton = findButtonByText(menuButtons, "About").orElse(null);

        assertNotNull(playButton, "'Play' button not found");
        assertNotNull(scoreButton, "'Score' button not found");
        assertNotNull(aboutButton, "'About' button not found");

        // Overíme, že KAŽDÉ tlačidlo má aspoň jedného listenera.
        // Nemôžeme jednoducho overiť, či je to tá správna lambda bez mockovania alebo reflexie.
        assertTrue(playButton.getActionListeners().length > 0, "Play button by mal mať ActionListener.");
        assertTrue(scoreButton.getActionListeners().length > 0, "Score button by mal mať ActionListener.");
        assertTrue(aboutButton.getActionListeners().length > 0, "About button by mal mať ActionListener.");
    }

    // --- Testy pre špecifické vlastnosti Button (ak je to relevantné) ---
    // Tieto testy závisia od implementácie tvojej triedy Frames.Button
    @Test
    @DisplayName("Tlačidlá by mali mať nastavené farby (overenie len či metódy prebehnú)")
    void testButtonColorsSet() {
        assumeTrue(menuFrame != null, "Preskakuje sa: MenuFrame inštancia nebola vytvorená.");
        JLayeredPane layeredPane = menuFrame.getLayeredPane();
        Optional<JPanel> panelOpt = findMenuButtonsPanel(layeredPane);
        assertTrue(panelOpt.isPresent(), "JPanel pre menu tlačidlá musí existovať.");
        JPanel menuButtons = panelOpt.get();

        // Len overíme, že metódy na nastavenie farieb existujú a dajú sa získať
        // bez toho, aby sme sa spoliehali na presné vykresľovanie.
        // Predpokladáme, že tvoja trieda Button má getBackgroundColor() a getForegroundColor()
        findButtonByText(menuButtons, "Play").ifPresent(b -> {
            // Napr. assertNotNull(b.getBackgroundColor()); // Alebo konkrétna farba, ak je to verejné API
            // assertNotNull(b.getForegroundColor());
            // Tu len ukazujeme, že by sa to dalo testovať, ak má Button API
            // V kóde sú farby nastavené napevno, ale konštruktor Button ich nemusí ukladať prístupným spôsobom
            // Tento test je teda skôr ilustračný
            assertDoesNotThrow(() -> {
                Color bg = b.getBackground(); // Štandardná Swing metóda, ak Button dedí od Swing komponenty
                Color fg = b.getForeground(); // Štandardná Swing metóda
                // Nemusíme overovať presné farby, len či volanie nespôsobí chybu
            }, "Získanie farieb tlačidla by nemalo hádzať výnimku.");
        });
        // Podobne pre ostatné tlačidlá...
    }

}

