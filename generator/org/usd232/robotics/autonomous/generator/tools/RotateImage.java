package org.usd232.robotics.autonomous.generator.tools;

import java.awt.geom.AffineTransform;
import org.usd232.robotics.autonomous.generator.FieldView;

public class RotateImage extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "rotate_90_degrees_ccw";

    public RotateImage() {
        super(ICON);
    }

    @Override
    public void onEnable() {
        FieldView view = getToolbar().getFieldView();
        AffineTransform matrix = view.getTransformation();
        int x = view.getWidth() / 2;
        int y = view.getHeight() / 2;
        matrix.translate(x, y);
        matrix.rotate(Math.PI / 2);
        matrix.translate(-x, -y);
        setSelected(false);
    }
}
