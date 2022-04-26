package BattleshipBoard

import Coordinates.Coordinates
import spock.lang.Specification

import java.util.stream.Collectors

class BoardTest extends Specification {

    def "NPE should be thrown when setting coordinates"() {
        given:
        def board = new Board()
        when:
        board.setCoordinate(coordinates, type)
        then:
        thrown(NullPointerException)
        where:
        coordinates           || type
        null                  || BoardStatus.HIT
        null                  || null
        new Coordinates(1, 2) || null
    }

    def "setting coordinates on board when target is hit"() {
        given:
        def board = new Board()
        def coordinates = new Coordinates(rowCoordinate, columnCoordinate)
        when:
        board.setCoordinate(coordinates, type)
        then:
        board.getBoard()[rowCoordinate][columnCoordinate] == expectedValue
        where:
        rowCoordinate || columnCoordinate || type             || expectedValue
        1             || 1                || BoardStatus.HIT  || 3
        9             || 8                || BoardStatus.MISS || 2
        10            || 10               || BoardStatus.SHIP || 1
    }

    def "board should be printed without exceptions"() {
        given:
        def board = new Board()
        def coordinates = new Coordinates(rowCoordinate, columnCoordinate)
        when:
        board.setCoordinate(coordinates, type)
        board.printBoard()
        then:
        noExceptionThrown()
        where:
        rowCoordinate || columnCoordinate || type
        1             || 1                || BoardStatus.HIT
        9             || 8                || BoardStatus.MISS
        10            || 10               || BoardStatus.SHIP

    }

    def "adding coordinates to shipCoordinatesLis should throw NPE"() {
        given:
        def board = new Board()
        when:
        board.addToShipCoordinatesMap(coordinates, type)
        then:
        thrown(NullPointerException)
        where:
        coordinates                                           || type
        List.of(new Coordinates(1, 2), new Coordinates(2, 4)) || null
        null                                                  || ShipType.DESTROYER1
        null                                                  || null

    }

    def "coordinates should be added to addToShipCoordinatesMap"() {
        given:
        def board = new Board()
        when:
        board.addToShipCoordinatesMap(coordinates, type)
        then:
        board.getShipCoordinatesMap().get(coordinates) == expectedValue
        where:
        coordinates                                                                  || type                || expectedValue
        List.of(new Coordinates(1, 2), new Coordinates(2, 2), new Coordinates(3, 2)) || ShipType.DESTROYER1  || ShipType.DESTROYER1
        List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || ShipType.BATTLESHIP || ShipType.BATTLESHIP
        List.of(new Coordinates(1, 4), new Coordinates(1, 3), new Coordinates(1, 2)) || ShipType.DESTROYER2  || ShipType.DESTROYER2
    }

    def "validate single coordinate"() {
        given:
        def board = new Board()
        when:
        def shipCoords = List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4))
        board.addToShipCoordinatesMap(shipCoords, ShipType.BATTLESHIP)
        def value = board.coordinateValidator(coords)
        then:
        value == expectedValue
        where:
        coords                || expectedValue
        new Coordinates(1, 2) || false
        new Coordinates(3, 3) || true
        new Coordinates(1, 3) || false
        new Coordinates(6, 7) || true

    }

    def "coordinates validator should throw NPE"() {
        given:
        def board = new Board()
        when:
        board.shipCoordinatesValidator(null)
        then:
        thrown(NullPointerException)
    }

    def "coordinates are being correctly validated"() {
        given:
        def board = new Board()
        when:
        board.addToShipCoordinatesMap(coordinates2, ShipType.DESTROYER1)
        def valid = board.shipCoordinatesValidator(coordinates1)
        board.addToShipCoordinatesMap(coordinates1, ShipType.BATTLESHIP)
        then:
        valid == expectedValid
        where:
        coordinates1                                                                 || coordinates2                                                                 || expectedValid
        List.of(new Coordinates(1, 2), new Coordinates(2, 2), new Coordinates(3, 2)) || List.of(new Coordinates(2, 1), new Coordinates(2, 2), new Coordinates(2, 3)) || false
        List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || List.of(new Coordinates(5, 2), new Coordinates(5, 3), new Coordinates(5, 4)) || true
        List.of(new Coordinates(1, 4), new Coordinates(1, 3), new Coordinates(1, 2)) || List.of(new Coordinates(1, 4), new Coordinates(1, 3), new Coordinates(1, 2)) || false
        List.of(new Coordinates(4, 2), new Coordinates(3, 2), new Coordinates(2, 2)) || List.of(new Coordinates(4, 2), new Coordinates(5, 2), new Coordinates(6, 2)) || false
        List.of(new Coordinates(1, 1), new Coordinates(1, 2), new Coordinates(1, 4)) || List.of(new Coordinates(9, 0), new Coordinates(8, 0), new Coordinates(7, 0)) || false
        List.of(new Coordinates(1, 1), new Coordinates(2, 2), new Coordinates(1, 3)) || List.of(new Coordinates(1, 1), new Coordinates(2, 2), new Coordinates(1, 3)) || false
        List.of(new Coordinates(3, 1), new Coordinates(3, 2), new Coordinates(3, 3)) || List.of(new Coordinates(4, 1), new Coordinates(5, 1), new Coordinates(6, 1)) || true
        List.of(new Coordinates(3, 3), new Coordinates(3, 3), new Coordinates(3, 3)) || List.of(new Coordinates(4, 4), new Coordinates(4, 3), new Coordinates(4, 2)) || false
        List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || false
    }

    def "ship coordinates generator should throw NPE"() {
        given:
        def board = new Board()
        when:
        board.generateShipCoordinates(null)
        then:
        thrown(NullPointerException)
    }

    def "ship coordinates generator should generate valid coordinates"() {
        given:
        def board = new Board()
        when:
        def coords1 = board.generateShipCoordinates(type)
        def check1 = board.shipCoordinatesValidator(coords1)
        board.addToShipCoordinatesMap(coords1, type)
        def coords2 = board.generateShipCoordinates(type)
        def check2 = board.shipCoordinatesValidator(coords2)
        board.addToShipCoordinatesMap(coords2, type)
        def coords3 = board.generateShipCoordinates(type)
        def check3 = board.shipCoordinatesValidator(coords3)
        board.addToShipCoordinatesMap(coords3, type)
        then:
        check1
        check2
        check3
        noExceptionThrown()
        where:
        type << [ShipType.DESTROYER1, ShipType.BATTLESHIP, ShipType.DESTROYER2]
    }

    def "ships should be added to board"() {
        given:
        def board = new Board()
        def count = 0
        when:
        board.addToShipCoordinatesMap(example as List<Coordinates>, ShipType.DESTROYER1)
        board.addShipsToBoard()
        for (int i = 0; i < board.ROW_SIZE; i++) {
            for (int j = 0; j < board.COLUMN_SIZE; j++) {
                if (board.getBoard()[i][j] == 1)
                    count++
            }
        }
        then:
        count == expectedCount
        where:
        example                                                                      || expectedCount
        List.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || 3
        Collections.emptyList()                                                      || 0
    }

    def "computer board generator generate board with 3 ships"() {
        given:
        def board = new Board()
        when:
        board.generateComputerBoard()
        List<Coordinates> addToShipCoordinatesMap = board.getShipCoordinatesMap().keySet().stream().flatMap(Collection::stream).collect(Collectors.toList())
        then:
        addToShipCoordinatesMap.size() == ShipType.battleshipSize + ShipType.destroyerSize + ShipType.destroyerSize
    }

    def "attack ship method throws NPE"() {
        given:
        def board = new Board()
        when:
        board.attackBoard(null)
        then:
        thrown(NullPointerException)
    }

    def "attack ship method should return map with shipType and coords"() {
        given:
        def board = new Board()
        board.addToShipCoordinatesMap(List.of(new Coordinates(1,2), new Coordinates(1,3)), expectedType)
        when:
        def value = board.attackBoard(coords)
        then:
        value == expectedType
        where:
        coords                  || expectedType
        new Coordinates(1, 2)   || ShipType.DESTROYER1
        new Coordinates(1, 2)   || ShipType.DESTROYER2
        new Coordinates(1, 2)   || ShipType.BATTLESHIP
    }
}
