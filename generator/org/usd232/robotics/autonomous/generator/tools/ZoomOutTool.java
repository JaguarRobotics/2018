package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.GameCoordinate;

public class ZoomOutTool extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "zoom_out";

    @Override
    public void onClick(GameCoordinate position) {
        ZoomInTool.zoom(this, position, false);
    }

    public ZoomOutTool() {
        super(ICON);
    }
}
