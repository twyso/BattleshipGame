package Coordinates

import spock.lang.Specification

class CoordinatesTest extends Specification {

    def "random coordinates generator form 1-10"() {
        given:
        def coordinates = new Coordinates()
        when:
        def coords = coordinates.randomCoordinates()
        then:
        coords.columnCoordinate <= 10 && coords.columnCoordinate > 0
        coords.rowCoordinate <= 10 && coords.rowCoordinate > 0

    }

    def "coordinates translator should throw null pointer exception"() {
        given:
        def coordinates = new Coordinates()
        when:
        coordinates.translateCoordinate(null)
        then:
        thrown(NullPointerException)

    }

    def "coordinates translator should throw InputMismatchException"() {
        given:
        def coordinates = new Coordinates()
        when:
        coordinates.translateCoordinate(example)
        then:
        thrown(InputMismatchException)
        where:
        example << ["n11", "M8", "C22", "a17"]
    }

    def "coordinates are correctly translated"() {
        given:
        def coordinates = new Coordinates()
        when:
        def coords = coordinates.translateCoordinate(example)
        then:
        coords.rowCoordinate == rowCoordinate
        coords.columnCoordinate == columnCoordinate
        where:
        rowCoordinate || columnCoordinate || example
        1             || 10               || "A10"
        2             || 1                || "B1"
        3             || 4                || "C4"
        10            || 1                || "j1"
        7             || 9                || "g9"
        4             || 2                || "d2"
        5             || 5                || "e5"
        6             || 10               || "f10"
        8             || 9                || "h9"
        9             || 10               || "I10"

    }

    def "coordinates scanner should throw Exception"() {
        given:
        def coordinates = new Coordinates()
        def userInput = exampleInput
        def byteInputStream = new ByteArrayInputStream(userInput.getBytes())
        System.setIn(byteInputStream)
        when:
        coordinates.coordinatesScanner()
        then:
        thrown(InputMismatchException)
        where:
        exampleInput << ["asda", "a 1 ", "13"]
    }

    def "coordinates scanner should return translated coords"() {
        given:
        def coordinates = new Coordinates()
        def userInput = exampleInput
        def byteInputStream = new ByteArrayInputStream(userInput.getBytes())
        System.setIn(byteInputStream)
        when:
        def value = coordinates.coordinatesScanner()
        then:
        value == coords
        where:
        exampleInput || coords
        "A1"         || new Coordinates(1, 1)
        "J9"         || new Coordinates(10, 9)
        "B8"         || new Coordinates(2, 8)
    }

    def "decode coordinate should return string coordinate"() {
        when:
        def value = Coordinates.decodeCoordinate(exampleInput)
        then:
        value == expect
        where:
        exampleInput || expect
        1            || "A"
        2            || "B"
        3            || "C"
        4            || "D"
        5            || "E"
        6            || "F"
        7            || "G"
        8            || "H"
        9            || "I"
        10           || "J"
    }
}
