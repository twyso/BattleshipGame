package BattleshipBoard;

import Coordinates.Coordinates;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final int ROW_SIZE = 11;
    public static final int COLUMN_SIZE = 11;

    private final int[][] board = new int[ROW_SIZE][COLUMN_SIZE];
    private final HashMap<List<Coordinates>, ShipType> shipCoordinatesMap = new HashMap<>();


    public Board() {
    }

    public int[][] getBoard() {
        return board;
    }

    public HashMap<List<Coordinates>, ShipType> getShipCoordinatesMap() {
        return shipCoordinatesMap;
    }

    public void addToShipCoordinatesMap(List<Coordinates> coordinates, ShipType type) {
        if (coordinates == null || type == null) throw new NullPointerException();
        this.shipCoordinatesMap.put(coordinates, type);
    }


    public void printBoard() {

        for (int i = 0; i < ROW_SIZE; i++) {
            for (int j = 0; j < COLUMN_SIZE; j++) {
                if (i == 0) {
                    System.out.print(ANSI_BLACK_BACKGROUND + " " + j + " " + ANSI_RESET);
                } else if (j == 0) {
                    System.out.print(ANSI_BLACK_BACKGROUND + " " + Coordinates.decodeCoordinate(i) + "\t" + ANSI_RESET);
                } else {
                    if (board[i][j] == 0) {
                        System.out.print(ANSI_CYAN_BACKGROUND + " O " + ANSI_RESET);
                    } else if (board[i][j] == 1) {
                        System.out.print(ANSI_GREEN_BACKGROUND + " X " + ANSI_RESET);
                    } else if (board[i][j] == 2) {
                        System.out.print(ANSI_WHITE_BACKGROUND + " E " + ANSI_RESET);
                    } else if (board[i][j] == 3) {
                        System.out.print(ANSI_RED_BACKGROUND + " H " + ANSI_RESET);
                    }
                }
            }
            System.out.println();
        }
    }

    public void generateComputerBoard() {
        List<Coordinates> ship1 = generateShipCoordinates(ShipType.DESTROYER1);
        addToShipCoordinatesMap(ship1, ShipType.DESTROYER1);
        List<Coordinates> ship2 = generateShipCoordinates(ShipType.DESTROYER2);
        addToShipCoordinatesMap(ship2, ShipType.DESTROYER2);
        List<Coordinates> ship3 = generateShipCoordinates(ShipType.BATTLESHIP);
        addToShipCoordinatesMap(ship3, ShipType.BATTLESHIP);
        addShipsToBoard();
    }

    private void addShipsToBoard() {
        List<Coordinates> addToShipCoordinatesMap = getShipCoordinatesMap().keySet().stream().flatMap(Collection::stream).collect(Collectors.toList());
        for (Coordinates coordinates : addToShipCoordinatesMap) {
            setCoordinate(coordinates, BoardStatus.SHIP);
        }
    }

    public void setCoordinate(Coordinates coordinates, BoardStatus boardStatus) {
        if (coordinates == null || boardStatus == null) throw new NullPointerException();
        switch (boardStatus) {
            case HIT -> board[coordinates.getRowCoordinate()][coordinates.getColumnCoordinate()] = 3;
            case SHIP -> board[coordinates.getRowCoordinate()][coordinates.getColumnCoordinate()] = 1;
            case MISS -> board[coordinates.getRowCoordinate()][coordinates.getColumnCoordinate()] = 2;
        }
    }

    public boolean coordinateValidator(Coordinates coordinates) {
        List<Coordinates> addToShipCoordinatesMap = getShipCoordinatesMap().keySet().stream().flatMap(Collection::stream).collect(Collectors.toList());
        for (Coordinates coords : addToShipCoordinatesMap) {
            if (coords.equals(coordinates)) return false;
        }
        return true;
    }

    public boolean shipCoordinatesValidator(List<Coordinates> coordinates) {
        if (coordinates == null) throw new NullPointerException();
        boolean valid = true, irregularRows = false, irregularColumns = false, rowSequenceCheck = true, columnSequenceCheck = true;
        List<Integer> rowCoordinatesList = new ArrayList<>();
        List<Integer> columnCoordinateList = new ArrayList<>();
        for (Coordinates coordinate : coordinates) {
            if (!coordinateValidator(coordinate)) return false;
            rowCoordinatesList.add(coordinate.getRowCoordinate());
            columnCoordinateList.add(coordinate.getColumnCoordinate());
        }
        for (int i = rowCoordinatesList.size() - 1; i > 0; i--) {
            if (!Objects.equals(rowCoordinatesList.get(i), rowCoordinatesList.get(i - 1))) irregularRows = true;
            if (!Objects.equals(columnCoordinateList.get(i), columnCoordinateList.get(i - 1))) irregularColumns = true;
            if (Math.abs(rowCoordinatesList.get(i) - rowCoordinatesList.get(i - 1)) != 1) rowSequenceCheck = false;
            if (Math.abs(columnCoordinateList.get(i) - columnCoordinateList.get(i - 1)) != 1)
                columnSequenceCheck = false;
        }
        if ((irregularColumns && irregularRows) || (!irregularColumns && !irregularRows) || (rowSequenceCheck && columnSequenceCheck) || (!rowSequenceCheck && !columnSequenceCheck))
            valid = false;
        return valid;
    }


    public List<Coordinates> generateShipCoordinates(ShipType type) {
        if (type == null) throw new NullPointerException();
        int shipSize = 0, direction = new Random().nextInt(4);
        List<Coordinates> coordinatesList = new ArrayList<>();
        Coordinates randomCoordinates = Coordinates.randomCoordinates();
        switch (type) {
            case DESTROYER1, DESTROYER2 -> shipSize = ShipType.getDestroyerSize();
            case BATTLESHIP -> shipSize = ShipType.getBattleshipSize();
        }


        boolean generated = false, reset = false;
        while (!generated) {
            if (direction == 0) {
                if (randomCoordinates.getRowCoordinate() - shipSize < 1) reset = true;
                else {
                    for (int i = 0; i < shipSize; i++) {
                        if (!coordinateValidator(new Coordinates(randomCoordinates.getRowCoordinate() - i, randomCoordinates.getColumnCoordinate()))) {
                            reset = true;
                            break;
                        } else {
                            coordinatesList.add(new Coordinates(randomCoordinates.getRowCoordinate() - i, randomCoordinates.getColumnCoordinate()));
                            generated = true;
                        }
                    }
                }
            } else if (direction == 1) {
                if (randomCoordinates.getRowCoordinate() + shipSize > ROW_SIZE) reset = true;
                else {
                    for (int i = 0; i < shipSize; i++) {
                        if (!coordinateValidator(new Coordinates(randomCoordinates.getRowCoordinate() + i, randomCoordinates.getColumnCoordinate()))) {
                            reset = true;
                            break;
                        } else {
                            coordinatesList.add(new Coordinates(randomCoordinates.getRowCoordinate() + i, randomCoordinates.getColumnCoordinate()));
                            generated = true;
                        }
                    }
                }
            }
            //RIGHT
            else if (direction == 2) {
                if (randomCoordinates.getColumnCoordinate() + shipSize > COLUMN_SIZE - 1) reset = true;
                else {
                    for (int i = 0; i < shipSize; i++) {
                        if (!coordinateValidator(new Coordinates(randomCoordinates.getRowCoordinate(), randomCoordinates.getColumnCoordinate() + i))) {
                            reset = true;
                            break;
                        } else {
                            coordinatesList.add(new Coordinates(randomCoordinates.getRowCoordinate(), randomCoordinates.getColumnCoordinate() + i));
                            generated = true;
                        }
                    }
                }
            }
            //LEFT
            else {
                if (randomCoordinates.getColumnCoordinate() - shipSize < 1) reset = true;
                else {
                    for (int i = 0; i < shipSize; i++) {
                        if (!coordinateValidator(new Coordinates(randomCoordinates.getRowCoordinate(), randomCoordinates.getColumnCoordinate() - i))) {
                            reset = true;
                            break;
                        } else {
                            coordinatesList.add(new Coordinates(randomCoordinates.getRowCoordinate(), randomCoordinates.getColumnCoordinate() - i));
                            generated = true;
                        }
                    }
                }
            }
            if (reset) {
                reset = false;
                generated = false;
                coordinatesList.clear();
                randomCoordinates = Coordinates.randomCoordinates();
                direction = new Random().nextInt(4);
            }

            if (!shipCoordinatesValidator(coordinatesList)) generated = false;
        }
        return coordinatesList;
    }

    public ShipType attackBoard(Coordinates coordinates) {
        if(coordinates == null) throw new NullPointerException();
        ShipType type = null;
        for(List<Coordinates> coordinatesList : getShipCoordinatesMap().keySet()){
            for(Coordinates coords : coordinatesList){
                if(coordinates.equals(coords)){
                  type = shipCoordinatesMap.get(coordinatesList);
                }
            }
        }
        return type;
    }
}
