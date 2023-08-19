package it.unicam.cs.followme.utilities;

import java.util.stream.IntStream;

/**
 * This record was provided by the Advanced Programming professor.
 * @author Michele Loreti.
 */
public record ShapeData(String label, String shape, double[] args) {
    public static ShapeData fromString(String[] elements) {
        return new ShapeData(elements[0],
            elements[1],
            IntStream.range(2,elements.length).mapToDouble(i -> Double.parseDouble(elements[i])).toArray()
        );
    }

}
