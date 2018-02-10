package org.usd232.robotics.autonomous.generator.tools;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import org.usd232.robotics.autonomous.generator.FieldView;

public class MoveTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "open_with";

    @Override
    public void onDrag(int x, int y, int dx, int dy) {
        try {
            FieldView view = getToolbar().getFieldView();
            AffineTransform matrix = view.getTransformation();
            Point2D pt = new Point2D.Double(dx, dy);
            pt = matrix.createInverse().deltaTransform(pt, null);
            matrix.translate(pt.getX(), pt.getY());
        } catch (NoninvertibleTransformException ex) {
            ex.printStackTrace();
        }
    }

    public MoveTool() {
        super(ICON);
    }
}
