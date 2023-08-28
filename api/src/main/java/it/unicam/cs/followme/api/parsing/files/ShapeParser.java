package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.ShapeCircle;
import it.unicam.cs.followme.api.model.Coordinates;
import it.unicam.cs.followme.api.model.ShapeRectangle;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.utilities.ShapeData;

import java.io.IOException;
import java.util.*;

/**
 * This class is responsible for returning the map of shapes and their coordinates.
 * @author Mohit Vijay Saini
 * @see ShapeParserInterface
 */
public class ShapeParser implements ShapeParserInterface<ShapeInterface> {

    /**
     * Shapes and their coordinates.
     */
    private final Map<ShapeInterface, Coordinates> shapes = new HashMap<>();

    @Override
    public Map<ShapeInterface, Coordinates> parseShapes(List<ShapeData> listShapeData) {
        for (ShapeData s : listShapeData){
            switch (s.shape()){
                case "CIRCLE" -> addCircle(s);
                case "RECTANGLE" -> addRectangle(s);
                default -> throw new IllegalArgumentException("Error during parsing: unrecognized shape");
            }
        }
        return shapes;
    }

    /**
     * Helper method to add rectangle.
     * @param s rectangle values.
     */
    private void addRectangle(ShapeData s) {
        shapes.put(new ShapeRectangle(s.args()[2], s.args()[3], s.label()),
                new Coordinates(s.args()[0], s.args()[1]));
    }

    /**
     * Helper method to add circle.
     * @param s circle values.
     */
    private void addCircle(ShapeData s) {
        shapes.put(new ShapeCircle(s.args()[2], s.label()),
                new Coordinates(s.args()[0], s.args()[1]));
    }
}
