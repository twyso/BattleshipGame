package Coordinates;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Coordinates {

    private int rowCoordinate;
    private int columnCoordinate;

    public Coordinates() {
    }

    public Coordinates(int rowCoordinate, int columnCoordinate) {
        this.rowCoordinate = rowCoordinate;
        this.columnCoordinate = columnCoordinate;
    }

    public int getColumnCoordinate() {
        return columnCoordinate;
    }


    public int getRowCoordinate() {
        return rowCoordinate;
    }


    public static Coordinates randomCoordinates() {
        Random random = new Random();
        return new Coordinates(random.nextInt(10) + 1, random.nextInt(10) + 1);
    }

    public Coordinates coordinatesScanner() throws InputMismatchException {
        Scanner scanner = new Scanner(System.in);
        String coordinates = scanner.next("(^[a-jA-J][0-9]$)|(^[a-jA-J][1][0]$)");
        return translateCoordinate(coordinates);
    }

    public Coordinates translateCoordinate(String coordinates) {
        if (coordinates == null) throw new NullPointerException();
        String rowCoordinatesString = coordinates.substring(0, 1).toUpperCase();
        String columnCoordinatesString = coordinates.substring(1);
        int rowCoordinate;
        int columnCoordinate = Integer.parseInt(columnCoordinatesString);
        if (columnCoordinate > 10 || columnCoordinate < 1) throw new InputMismatchException();
        switch (rowCoordinatesString) {
            case "A" -> rowCoordinate = 1;
            case "B" -> rowCoordinate = 2;
            case "C" -> rowCoordinate = 3;
            case "D" -> rowCoordinate = 4;
            case "E" -> rowCoordinate = 5;
            case "F" -> rowCoordinate = 6;
            case "G" -> rowCoordinate = 7;
            case "H" -> rowCoordinate = 8;
            case "I" -> rowCoordinate = 9;
            case "J" -> rowCoordinate = 10;
            default -> throw new InputMismatchException();
        }
        return new Coordinates(rowCoordinate, columnCoordinate);
    }

    public static String decodeCoordinate(int i) {
        String rowCoordinate = "";
        switch (i) {
            case 1 -> rowCoordinate = "A";
            case 2 -> rowCoordinate = "B";
            case 3 -> rowCoordinate = "C";
            case 4 -> rowCoordinate = "D";
            case 5 -> rowCoordinate = "E";
            case 6 -> rowCoordinate = "F";
            case 7 -> rowCoordinate = "G";
            case 8 -> rowCoordinate = "H";
            case 9 -> rowCoordinate = "I";
            case 10 -> rowCoordinate = "J";
        }
        return rowCoordinate;
    }

    @Override
    public String toString() {
        return "[" + rowCoordinate + ", " + columnCoordinate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return rowCoordinate == that.rowCoordinate && columnCoordinate == that.columnCoordinate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowCoordinate, columnCoordinate);
    }
}
