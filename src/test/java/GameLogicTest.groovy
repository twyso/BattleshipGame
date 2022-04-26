import BattleshipBoard.Board
import BattleshipBoard.ShipType
import Coordinates.Coordinates
import spock.lang.Specification
import spock.util.EmbeddedSpecCompiler


class GameLogicTest extends Specification {

    def "target hit function should throw npe"() {
        given:
        def game = new GameLogic()
        when:
        game.targetHit(new Coordinates(1, 2), null, ShipType.DESTROYER1)
        then:
        thrown(NullPointerException)
    }

    def "target hit method should return boolean value"() {
        given:
        def game = new GameLogic()
        game.getDestroyer1().addAll(coordinatesSet)
        when:
        def value = game.targetHit(new Coordinates(1, 5), game.destroyer1, ShipType.DESTROYER1)
        then:
        value == expectedValue
        where:
        coordinatesSet                                                              || expectedValue
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4)) || true
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3))                        || false

    }

    def "ship sunk check should return boolean value"() {
        given:
        def game = new GameLogic()
        when:
        def value = game.sunkShipCheck(coords, type)
        then:
        value == expectedValue
        where:
        coords                                                                                                                    || type                || expectedValue
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4), new Coordinates(1, 5))                        || ShipType.DESTROYER1 || true
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4), new Coordinates(1, 5))                        || ShipType.DESTROYER2 || true
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4), new Coordinates(1, 5), new Coordinates(1, 6)) || ShipType.BATTLESHIP || true
        Set.of(new Coordinates(1, 2), new Coordinates(1, 3), new Coordinates(1, 4))                                               || ShipType.DESTROYER1 || false

    }

    def "game loop should break - all ships are sunk"() {
        given:
        def game = new GameLogic()
        game.setBattleshipSunk(true)
        game.setDestroyer1Sunk(true)
        game.setDestroyer2sunk(true)
        when:
        game.play()
        then:
        noExceptionThrown()
    }

    def "game loop - battle ship to sunk"() {
        given:
        def game = new GameLogic()
        def userInput = "A7"
        def byteInputStream = new ByteArrayInputStream(userInput.getBytes())
        game.setDestroyer2sunk(true)
        game.setDestroyer1Sunk(true)
        game.getComputer().addToShipCoordinatesMap(List.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5), new Coordinates(1, 6), new Coordinates(1,7)), ShipType.BATTLESHIP)
        game.getBattleship().addAll(Set.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5), new Coordinates(1, 6)))
        System.setIn(byteInputStream)
        when:
        game.play()
        then:
        noExceptionThrown()
    }

    def "game loop - destroyer1 ship to sunk"() {
        given:
        def game = new GameLogic()
        def userInput = "A6"
        def byteInputStream = new ByteArrayInputStream(userInput.getBytes())
        game.setDestroyer2sunk(true)
        game.setBattleshipSunk(true)
        game.getComputer().addToShipCoordinatesMap(List.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5), new Coordinates(1, 6)), ShipType.DESTROYER1)
        game.getDestroyer1().addAll(Set.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5)))
        System.setIn(byteInputStream)
        when:
        game.play()
        then:
        noExceptionThrown()
    }

    def "game loop - destroyer2 ship to sunk"() {
        given:
        def game = new GameLogic()
        def userInput = "A6"
        def byteInputStream = new ByteArrayInputStream(userInput.getBytes())
        game.setDestroyer1Sunk(true)
        game.setBattleshipSunk(true)
        game.getComputer().addToShipCoordinatesMap(List.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5), new Coordinates(1, 6)), ShipType.DESTROYER2)
        game.getDestroyer2().addAll(Set.of(new Coordinates(1, 3), new Coordinates(1, 4),
                new Coordinates(1, 5)))
        System.setIn(byteInputStream)
        when:
        game.play()
        then:
        noExceptionThrown()
    }

    def "game loop - player missed, player input wrong coordinates"() {
        given:
        def game = new GameLogic()
        when:
        game.attackHandler(null)
        then:
        noExceptionThrown()
    }

}
