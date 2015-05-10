package game;


import com.ClassicRockFan.ShockWave.engine.core.CoreEngine;

public class Main {

    public static void main(String[] args) {
        CoreEngine engine = new CoreEngine(800, 600, 60, new BubbleGumSimulator(), "CandleLight TestGame");
        engine.createConsole();
        engine.start();
    }
}
