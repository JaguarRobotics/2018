package org.usd232.robotics.autonomous.generator.tools;

import java.awt.geom.Point2D;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class MoveTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "open_with";

    @Override
    public void onDrag(GameCoordinate position, GameCoordinate change) {
        Point2D pt = change.getNormalizedPoint();
        getToolbar().getFieldView().getTransformation().translate(pt.getX(), pt.getY());
    }

    public MoveTool(GeneratorModel model) {
        super(ICON, model);
    }
}
