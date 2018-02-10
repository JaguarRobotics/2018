package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.FieldView;

public class ZoomOutTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "zoom_out";

    public ZoomOutTool() {
        super(ICON);
    }
    
    @Override
    public void onClick(int x, int y) {
        FieldView view = getToolbar().getFieldView();
        view.setImageScale(view.getImageScale()*.75);
    }
}