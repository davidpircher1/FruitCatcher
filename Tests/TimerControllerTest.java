package Tests; // Test bude v tomto balicku

// Importy potrebnych tried z balicka Timer
import Timer.TimerController;
import Timer.TimerModel;
import Timer.TimerView;
import Timer.TimerModel.TimerListener; // Import vnutorneho rozhrania

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Jednotkove testy pre triedu TimerController bez pouzitia Mockito.
 * Tieto testy overuju spravne delegovanie volani na falesne implementacie
 * zavislych tried (TimerModel a TimerView).
 */
class TimerControllerTest {

    /** Falesna implementacia modelu pre testovanie. */
    private FakeTimerModel fakeModel;
    /** Falesna implementacia view pre testovanie. */
    private FakeTimerView fakeView;
    /** Instancia TimerController, ktora bude testovana. */
    private TimerController controller;

    // --- Falesna implementacia TimerModel ---

    /**
     * Falesna implementacia TimerModel (Fake/Stub) na zaznamenavanie interakcii.
     * Rozsiruje TimerModel, aby splnila typovu zavislost, ale prepisuje metody
     * tak, aby len zaznamenavali volania bez spustania realnej logiky casovaca.
     * POZNAMKA: Tento pristup moze byt krehky, ak konstruktor TimerModel
     * alebo jeho metody maju zavazne vedlajsie ucinky (napr. praca s vlaknami).
     * Idealnejsie by bolo mat rozhranie, ktore by TimerModel implementoval.
     */
    private static class FakeTimerModel extends TimerModel {

        // Premenne na zaznamenanie volani metod
        boolean startCalled = false;
        boolean stopCalled = false;
        boolean pauseTimerCalled = false;
        boolean resumeTimerCalled = false;
        TimerListener listenerSet = null;
        int setListenerCallCount = 0;

        /**
         * Konstruktor falesneho modelu.
         * Volanie super() moze byt potrebne, ale moze spustit nezaducu logiku.
         * Ak super() startuje javax.swing.Timer, moze to sposobovat problemy.
         * V tomto priklade predpokladame, ze to vieme zvladnut alebo ignorovat.
         */
        public FakeTimerModel() {
            super(); // Nutne volat, ak nema TimerModel bezparametricky konstruktor
            // alebo ak je to vyzadovane. Moze niest rizika.
        }

        /** Zaznamena volanie start(). */
        @Override
        public void start() {
            this.startCalled = true;
        }

        /** Zaznamena volanie stop(). */
        @Override
        public void stop() {
            this.stopCalled = true;
        }

        /** Zaznamena volanie pauseTimer(). */
        @Override
        public void pauseTimer() {
            this.pauseTimerCalled = true;
        }

        /** Zaznamena volanie resumeTimer(). */
        @Override
        public void resumeTimer() {
            this.resumeTimerCalled = true;
        }

        /** Zaznamena, ktory listener bol nastaveny a kolkokrat. */
        @Override
        public void setListener(TimerListener listener) {
            this.listenerSet = listener;
            this.setListenerCallCount++;
            // Nevolame super.setListener(), aby sme nepouzili realnu logiku
        }

        // Metody na overenie stavu v testoch
        public boolean wasStartCalled() { return startCalled; }
        public boolean wasStopCalled() { return stopCalled; }
        public boolean wasPauseTimerCalled() { return pauseTimerCalled; }
        public boolean wasResumeTimerCalled() { return resumeTimerCalled; }
        public TimerListener getListenerSet() { return listenerSet; }
        public int getSetListenerCallCount() { return setListenerCallCount; }

        /** Resetuje zaznamenany stav (pouzitelne, ak sa nevytvara nova instancia v setUp). */
        public void reset() {
            startCalled = false;
            stopCalled = false;
            pauseTimerCalled = false;
            resumeTimerCalled = false;
            listenerSet = null;
            setListenerCallCount = 0;
        }
    }

    // --- Falesna implementacia TimerView ---

    /**
     * Falesna implementacia TimerView (Fake/Stub) na zaznamenavanie interakcii.
     * Rozsiruje TimerView a prepisuje metody na zaznamenanie volani.
     */
    private static class FakeTimerView extends TimerView { // Predpokladame, ze TimerView je trieda

        boolean updateTimeLabelCalled = false;
        int lastTimeLabelValue = -1; // Pociatocna neplatna hodnota

        /**
         * Konstruktor falesneho view.
         */
        public FakeTimerView() {
            super(); // Ak vyzaduje rodicovsky konstruktor
        }

        /** Zaznamena volanie updateTimeLabel a prijatu hodnotu. */
        @Override
        public void updateTimeLabel(int time) {
            this.updateTimeLabelCalled = true;
            this.lastTimeLabelValue = time;
            // Nevolame super.updateTimeLabel(), aby sme nepouzili realnu logiku
        }

        // Metody na overenie stavu v testoch
        public boolean wasUpdateTimeLabelCalled() { return updateTimeLabelCalled; }
        public int getLastTimeLabelValue() { return lastTimeLabelValue; }

        /** Resetuje zaznamenany stav. */
        public void reset() {
            updateTimeLabelCalled = false;
            lastTimeLabelValue = -1;
        }
    }

    // --- Testovacie Metody ---

    /**
     * Pripravna metoda spustena pred kazdym testom (@Test).
     * Inicializuje falesne implementacie (FakeTimerModel, FakeTimerView)
     * a vytvori instanciu TimerController s tymito falesnymi zavislostami.
     */
    @BeforeEach
    void setUp() {
        fakeModel = new FakeTimerModel();
        fakeView = new FakeTimerView();
        controller = new TimerController(fakeModel, fakeView);
    }

    /**
     * Testuje, ci konstruktor TimerController spravne nastavi
     * sam seba ako listenera na poskytnutom (falesnom) TimerModel.
     */
    @Test
    void testConstructorSetsListenerOnModel() {
        // Overime, ze listener nastaveny vo falesnom modeli je prave ten controller,
        // ktory bol vytvoreny v setUp metode.
        assertEquals(controller, fakeModel.getListenerSet(), "Konstruktor mal nastavit controller ako listenera modelu.");
        // Overime aj pocet volani, hoci pri jednom vytvoreni by mal byt 1.
        assertEquals(1, fakeModel.getSetListenerCallCount(), "Metoda setListener mala byt volana prave raz.");
    }

    /**
     * Testuje, ci volanie metody start() na controlleri
     * spravne deleguje volanie na metodu start() falesneho modelu.
     */
    @Test
    void testStartCallsModelStart() {
        assertFalse(fakeModel.wasStartCalled(), "Pred volanim start() by startCalled malo byt false.");
        controller.start();
        assertTrue(fakeModel.wasStartCalled(), "Po volani start() by startCalled malo byt true.");
    }

    /**
     * Testuje, ci volanie metody pauseTimer() na controlleri
     * spravne deleguje volanie na metodu pauseTimer() falesneho modelu.
     */
    @Test
    void testPauseTimerCallsModelPauseTimer() {
        assertFalse(fakeModel.wasPauseTimerCalled(), "Pred volanim pauseTimer() by pauseTimerCalled malo byt false.");
        controller.pauseTimer();
        assertTrue(fakeModel.wasPauseTimerCalled(), "Po volani pauseTimer() by pauseTimerCalled malo byt true.");
    }

    /**
     * Testuje, ci volanie metody resumeTimer() na controlleri
     * spravne deleguje volanie na metodu resumeTimer() falesneho modelu.
     */
    @Test
    void testResumeTimerCallsModelResumeTimer() {
        assertFalse(fakeModel.wasResumeTimerCalled(), "Pred volanim resumeTimer() by resumeTimerCalled malo byt false.");
        controller.resumeTimer();
        assertTrue(fakeModel.wasResumeTimerCalled(), "Po volani resumeTimer() by resumeTimerCalled malo byt true.");
    }

    /**
     * Testuje, ci volanie metody stop() na controlleri
     * spravne deleguje volanie na metodu stop() falesneho modelu.
     */
    @Test
    void testStopCallsModelStop() {
        assertFalse(fakeModel.wasStopCalled(), "Pred volanim stop() by stopCalled malo byt false.");
        controller.stop();
        assertTrue(fakeModel.wasStopCalled(), "Po volani stop() by stopCalled malo byt true.");
    }

    /**
     * Testuje spravanie metody onTimeUpdate controllera.
     * Overuje, ci prijatie aktualizacie casu (zavolanim onTimeUpdate priamo na controlleri)
     * spravne sposobi volanie metody updateTimeLabel na falesnom view
     * s tou istou hodnotou casu.
     */
    @Test
    void testOnTimeUpdateCallsViewUpdateTimeLabel() {
        int testTimeLeft = 55; // Arbitrarna testovacia hodnota casu

        assertFalse(fakeView.wasUpdateTimeLabelCalled(), "Pred onTimeUpdate by updateTimeLabelCalled malo byt false.");
        assertEquals(-1, fakeView.getLastTimeLabelValue(), "Pred onTimeUpdate by lastTimeLabelValue malo byt -1.");

        // Priamo zavolame metodu onTimeUpdate na controlleri (simulujeme udalost z modelu)
        controller.onTimeUpdate(testTimeLeft);

        assertTrue(fakeView.wasUpdateTimeLabelCalled(), "Po onTimeUpdate by updateTimeLabelCalled malo byt true.");
        assertEquals(testTimeLeft, fakeView.getLastTimeLabelValue(), "View malo prijat spravnu hodnotu casu.");
    }
}