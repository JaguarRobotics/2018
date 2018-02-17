package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class ZoomInTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "zoom_in";
    private static final double ZOOM_AMOUNT      = 1.1;

    static void zoom(Tool tool, GameCoordinate position, boolean in) {
        AffineTransform matrix = tool.getToolbar().getFieldView().getTransformation();
        /*
         * Point2D pt = position.getNormalizedPoint(); matrix.translate(-pt.getX(), -pt.getY()); double scale = in ?
         * ZOOM_AMOUNT : 1. / ZOOM_AMOUNT; matrix.scale(scale, scale); matrix.translate(pt.getX() / scale, pt.getY() /
         * scale);
         */
        double scale = in ? ZOOM_AMOUNT : 1. / ZOOM_AMOUNT;
        Point then = position.getPixels();
        matrix.scale(scale, scale);
        Point now = position.getPixels();
        Point2D diff = GameCoordinate.fromPixels(tool.getToolbar().getFieldView(), true,
                        (int) (now.getX() - then.getX()), (int) (now.getY() - then.getY())).getNormalizedPoint();
        matrix.translate(diff.getX(), diff.getY());
    }

    @Override
    public void onClick(GameCoordinate position) {
        zoom(this, position, true);
    }

    public ZoomInTool(GeneratorModel model) {
        super(ICON, model);
    }
}
