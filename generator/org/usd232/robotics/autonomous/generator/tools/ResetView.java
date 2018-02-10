package org.usd232.robotics.autonomous.generator.tools;

import org.usd232.robotics.autonomous.generator.FieldView;

public class ResetView extends Tool {
    private static final long   serialVersionUID = -2152990834180811625L;
    private static final String ICON             = "settings_overscan";

    public ResetView() {
        super(ICON);
    }
    
    @Override
    public void onEnable() {
        FieldView view = getToolbar().getFieldView();
        view.setImageScale(1);
        view.setImageX(0);
        view.setImageY(0);
        super.onEnable();
        setSelected(false);
    }

}