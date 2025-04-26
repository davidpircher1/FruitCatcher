package Tests;

import Timer.TimerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Jednotkove testy pre triedu TimerModel bez pouzitia Mockito.
 * Tieto testy overuju funkcnost TimerModel, najma spravu casu
 * a interakciu s listenerom, pomocou vlastnej implementacie listenera.
 */
class TimerModelTest {

    /** Model casovaca, ktory je predmetom testovania. */
    private TimerModel timerModel;
    /** Testovacia implementacia listenera na sledovanie jeho volani. */
    private TestTimerListener testListener;

    /**
     * Jednoducha vnorena staticka trieda, ktora implementuje TimerListener
     * pre uceli zachytavania a overovania volani metody onTimeUpdate pocas testov.
     */
    private static class TestTimerListener implements TimerModel.TimerListener {
        /** Uklada poslednu hodnotu casu prijatu metodou onTimeUpdate. Inicializovana na -1. */
        int lastTimeLeft = -1;
        /** Pocita, kolkokrat bola metoda onTimeUpdate zavolana. */
        int callCount = 0;

        /**
         * Implementacia metody rozhrania. Pri volani aktualizuje
         * posledny prijaty cas a inkrementuje pocitadlo volani.
         * @param timeLeft Zostavajuci cas prijaty z TimerModel.
         */
        @Override
        public void onTimeUpdate(int timeLeft) {
            this.lastTimeLeft = timeLeft;
            this.callCount++;
        }

        /**
         * Pomocna metoda na resetovanie stavu listenera (posledny cas a pocet volani).
         * V aktualnom nastaveni s @BeforeEach nie je nevyhnutne potrebna,
         * kedze vzdy vznikne nova instancia.
         */
        public void reset() {
            this.lastTimeLeft = -1;
            this.callCount = 0;
        }
    }

    /**
     * Pripravna metoda spustena pred kazdym testom (@Test).
     * Inicializuje novu instanciu TimerModel a TestTimerListener,
     * cim zabezpecuje izolaciu medzi testami.
     */
    @BeforeEach
    void setUp() {
        timerModel = new TimerModel();
        testListener = new TestTimerListener();
        // Listener nie je automaticky nastaveny v modeli; testy ho musia nastavit podla potreby.
    }

    /**
     * Testuje, ci je pociatocna hodnota zostavajuceho casu spravne nastavena
     * podla konstruktora TimerModel (ocakava sa 100).
     */
    @Test
    void testInitialTimeLeft() {
        assertEquals(30, timerModel.getTimeLeft(), "Pociatocny cas by mal byt 100");
    }

    /**
     * Testuje funkcnost metody setTimeLeft a nasledne getTimeLeft.
     * Overuje, ci je mozne nastavit novu hodnotu casu a ci ju getter spravne vrati.
     */
    @Test
    void testSetTimeLeft() {
        timerModel.setTimeLeft(50);
        assertEquals(50, timerModel.getTimeLeft(), "Cas by mal byt nastaveny na 50");
    }

    /**
     * Testuje metodu setListener.
     * Overuje, ci volanie metody s instanciou listenera alebo s hodnotou null
     * nevyvola vynimku. Nepriamo overuje priradenie.
     */
    @Test
    void testSetListener() {
        assertDoesNotThrow(() -> timerModel.setListener(testListener), "Nastavenie platneho listenera by nemalo sposobit chybu.");
        assertDoesNotThrow(() -> timerModel.setListener(null), "Nastavenie null listenera by nemalo sposobit chybu.");
    }

    /**
     * Testuje, ci jedno volanie metody updateTime spravne znizi
     * hodnotu zostavajuceho casu o 1.
     */
    @Test
    void testUpdateTimeDecrementsTime() {
        int initialTime = timerModel.getTimeLeft();
        timerModel.updateTime();
        assertEquals(initialTime - 1, timerModel.getTimeLeft(), "Cas by sa mal znizit o 1");
    }

    /**
     * Testuje, ci volanie metody updateTime spravne upozorni nastaveneho listenera
     * volanim jeho metody onTimeUpdate s aktualizovanou hodnotou casu.
     */
    @Test
    void testUpdateTimeCallsListener() {
        timerModel.setListener(testListener);
        int initialTime = timerModel.getTimeLeft();

        timerModel.updateTime();
        int expectedTime = initialTime - 1;

        assertEquals(1, testListener.callCount, "Metoda onTimeUpdate mala byt volana raz.");
        assertEquals(expectedTime, testListener.lastTimeLeft, "Listener mal prijat spravny zostavajuci cas.");
    }

    /**
     * Testuje spravanie metody updateTime, ked nie je nastaveny ziadny listener (je null).
     * Overuje, ze cas sa sice znizi, ale nedojde k chybe (NullPointerException)
     * a testovaci listener (ktory nie je v modeli nastaveny) nie je volany.
     */
    @Test
    void testUpdateTimeDoesNotCallListenerWhenNull() {
        timerModel.setListener(null);
        int initialTime = timerModel.getTimeLeft();
        int listenerInitialCallCount = testListener.callCount; // Malo by byt 0

        assertDoesNotThrow(() -> timerModel.updateTime(), "Volanie updateTime s null listenerom by nemalo sposobit chybu.");

        assertEquals(initialTime - 1, timerModel.getTimeLeft(), "Cas by sa mal znizit aj bez listenera.");
        assertEquals(listenerInitialCallCount, testListener.callCount, "Listener nemal byt volany, ked je nastaveny na null.");
    }

    /**
     * Testuje spravanie casovaca, ked cas dosiahne nulu.
     * Overuje, ze po dosiahnuti nuly sa cas dalej neznizuje
     * a listener uz nie je dalej volany pri naslednych volaniach updateTime.
     */
    @Test
    void testUpdateTimeStopsAtZero() {
        timerModel.setListener(testListener);
        timerModel.setTimeLeft(1); // Priprava na dosiahnutie nuly

        // Prve volanie updateTime
        timerModel.updateTime();
        assertEquals(0, timerModel.getTimeLeft(), "Cas by mal klesnut na 0");
        assertEquals(1, testListener.callCount, "Listener mal byt volany prvykrat.");
        assertEquals(0, testListener.lastTimeLeft, "Listener mal prijat cas 0.");

        // Druhe volanie updateTime (ked uz je cas 0)
        timerModel.updateTime();
        assertEquals(0, timerModel.getTimeLeft(), "Cas by mal zostat 0");
        // Pocet volani listenera by mal zostat 1
        assertEquals(1, testListener.callCount, "Listener nemal byt volany druhykrat.");
    }

    /**
     * Testuje, ze ak je casovac uz na nule, volanie updateTime
     * nezmeni cas na negativnu hodnotu a ani nezavola listenera.
     */
    @Test
    void testUpdateTimeDoesNotGoBelowZero() {
        timerModel.setListener(testListener);
        timerModel.setTimeLeft(0);

        timerModel.updateTime();

        assertEquals(0, timerModel.getTimeLeft(), "Cas by nemal klesnut pod 0");
        assertEquals(0, testListener.callCount, "Listener nemal byt volany, ked cas uz bol 0.");
    }

    /**
     * Zakladny test pre metodu start().
     * Overuje len to, ci jej volanie nevyvola vynimku.
     * Priamy test funkcnosti Swigoveho casovaca je mimo rozsahu tohto jednotkoveho testu.
     */
    @Test
    void testStartDoesNotThrowException() {
        assertDoesNotThrow(() -> timerModel.start());
        timerModel.stop(); // Zastavenie pre istotu
    }

    /**
     * Zakladny test pre metodu stop().
     * Overuje, ci jej volanie nevyvola vynimku po predchadzajucom spusteni.
     */
    @Test
    void testStopDoesNotThrowException() {
        timerModel.start();
        assertDoesNotThrow(() -> timerModel.stop());
    }

    /**
     * Zakladny test pre metodu pauseTimer().
     * Overuje, ci jej volanie nevyvola vynimku. V implementacii je ekvivalentna stop().
     */
    @Test
    void testPauseTimerDoesNotThrowException() {
        timerModel.start();
        assertDoesNotThrow(() -> timerModel.pauseTimer());
    }

    /**
     * Zakladny test pre metodu resumeTimer().
     * Overuje, ci jej volanie nevyvola vynimku po predchadzajucom pozastaveni. V implementacii je ekvivalentna start().
     */
    @Test
    void testResumeTimerDoesNotThrowException() {
        timerModel.start();
        timerModel.pauseTimer();
        assertDoesNotThrow(() -> timerModel.resumeTimer());
        timerModel.stop(); // Zastavenie pre istotu
    }
}