package it.unicam.cs.followme.api.parsing.files;

import it.unicam.cs.followme.api.model.Coordinates;
import it.unicam.cs.followme.api.model.ShapeInterface;
import it.unicam.cs.followme.utilities.FollowMeParserException;
import it.unicam.cs.followme.utilities.ShapeData;
import it.unicam.cs.followme.utilities.FollowMeShapeChecker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Any class that implements this interface can add the parsed shapes.
 * @author Mohit Vijay Saini
 */
public interface ShapeParserInterface<S extends ShapeInterface> {

    /**
     * This method adds the parsed shapes from the {@link FollowMeShapeChecker}.
     * @param listShapeData the list of shapes parsed by {@link FollowMeShapeChecker}.
     * @return the shapes and their coordinates in the environment.
     */
    Map<S, Coordinates> parseShapes(List<ShapeData> listShapeData);
}
