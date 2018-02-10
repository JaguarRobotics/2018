package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.FieldView;

public class MoveTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "open_with";

    @Override
    public void onDrag(int x, int y, int dx, int dy) {
        FieldView view = getToolbar().getFieldView();
        view.setImageX(view.getImageX() + ((double) dx) / (double) view.getWidth());
        view.setImageY(view.getImageY() + ((double) dy) / (double) view.getHeight());
        System.out.printf("(%d, %d) x (%d, %d) -> (%f, %f)\n", dx, dy, view.getWidth(), view.getHeight(), view.getImageX(), view.getImageY());
    }

    public MoveTool() {
        super(ICON);
    }
}
