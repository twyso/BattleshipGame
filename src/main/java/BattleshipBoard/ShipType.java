package BattleshipBoard;

public enum ShipType {
    BATTLESHIP, DESTROYER1, DESTROYER2;

    private static final int BATTLESHIP_SIZE = 5;
    private static final int DESTROYER_SIZE = 4;

    public static int getBattleshipSize() {
        return BATTLESHIP_SIZE;
    }

    public static int getDestroyerSize() {
        return DESTROYER_SIZE;
    }
}
