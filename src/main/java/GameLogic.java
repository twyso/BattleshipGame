import BattleshipBoard.Board;
import BattleshipBoard.BoardStatus;
import BattleshipBoard.ShipType;
import Coordinates.Coordinates;

import java.io.InputStream;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;


public class GameLogic {

    private final Board player = new Board();
    private final Board computer = new Board();
    private Coordinates coordinates = new Coordinates();

    private final Set<Coordinates> destroyer1 = new HashSet<>();
    private boolean destroyer1Sunk = false;

    private final Set<Coordinates> destroyer2 = new HashSet<>();
    private boolean destroyer2sunk = false;

    private final Set<Coordinates> battleship = new HashSet<>();
    private boolean battleshipSunk = false;

    public GameLogic() {
    }

    public Board getComputer() {
        return computer;
    }


    public void setBattleshipSunk(boolean battleshipSunk) {
        this.battleshipSunk = battleshipSunk;
    }

    public void setDestroyer1Sunk(boolean destroyer1Sunk) {
        this.destroyer1Sunk = destroyer1Sunk;
    }

    public void setDestroyer2sunk(boolean destroyer2sunk) {
        this.destroyer2sunk = destroyer2sunk;
    }

    public Set<Coordinates> getBattleship() {
        return battleship;
    }

    public Set<Coordinates> getDestroyer1() {
        return destroyer1;
    }

    public Set<Coordinates> getDestroyer2() {
        return destroyer2;
    }


    public void play() {
        System.out.println("Battleship game. Play against computer try to destroy 3 ships");
        computer.generateComputerBoard();
        player.printBoard();
        gameLoop();
        System.out.println("Game won");
    }

    void gameLoop() {
        while (!destroyer1Sunk || !destroyer2sunk || !battleshipSunk) {
            try {
                System.out.println("Enter coordinates for example \"a7, B5, h7\"");
                coordinates = coordinates.coordinatesScanner();
                ShipType attackedShipType = computer.attackBoard(coordinates);
                attackHandler(attackedShipType);
                player.printBoard();
            } catch (InputMismatchException e) {
                System.out.println("Wrong coordinates format");
            }
        }
    }

    void attackHandler(ShipType attackedShipType){
        if (attackedShipType == null) {
            player.setCoordinate(coordinates, BoardStatus.MISS);
            System.out.println("Miss");
        } else if (attackedShipType.equals(ShipType.DESTROYER1)) {
            destroyer1Sunk = targetHit(coordinates, destroyer1, ShipType.DESTROYER1);
        } else if (attackedShipType.equals(ShipType.DESTROYER2)) {
            destroyer2sunk = targetHit(coordinates, destroyer2, ShipType.DESTROYER2);
        } else if (attackedShipType.equals(ShipType.BATTLESHIP)) {
            battleshipSunk = targetHit(coordinates, battleship, ShipType.BATTLESHIP);
        }
    }


    boolean targetHit(Coordinates coordinates, Set<Coordinates> coordinatesSet, ShipType type) {
        if (coordinates == null) throw new NullPointerException();
        coordinatesSet.add(coordinates);
        if (sunkShipCheck(coordinatesSet, type)) {
            System.out.println(type + " sunk");
            player.setCoordinate(coordinates, BoardStatus.HIT);
            return true;
        } else {
            System.out.println(type + " hit");
            player.setCoordinate(coordinates, BoardStatus.HIT);
        }
        return false;
    }

    boolean sunkShipCheck(Set<Coordinates> shipCoordinates, ShipType shipType) {
        boolean returnValue;
        if (shipType == ShipType.DESTROYER1 || shipType == ShipType.DESTROYER2)
            returnValue = shipCoordinates.size() == ShipType.getDestroyerSize();
        else returnValue = shipCoordinates.size() == ShipType.getBattleshipSize();
        return returnValue;
    }


}
