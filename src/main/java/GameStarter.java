import java.io.InputStream;

import static java.lang.System.in;

public class GameStarter {

    public static void main(String[] args) {
        GameLogic gameLogic = new GameLogic();
        gameLogic.play();
    }
}
