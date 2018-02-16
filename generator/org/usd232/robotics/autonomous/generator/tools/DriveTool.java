package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.usd232.robotics.autonomous.AutonomousModel;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.TurnParameter;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.PropertiesBar;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class DriveTool extends DrawingTool {
    private static final long   serialVersionUID = -7087329524983618906L;
    private static final String ICON             = "linear_scale";
    private PropertiesBar       props;
    private GameCoordinate      mouse;

    @Override
    public void onHover(GameCoordinate position, GameCoordinate change) {
        mouse = position;
        repaintOverlay();
    }

    @Override
    public void onClick(GameCoordinate position) {
        AutonomousModel auto = model.localDatabase.getRawElementAt(model.localDatabaseView.getSelectedIndex()).obj2;
        AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
        Point2D pt = position.getNormalizedPoint();
        if (route.getStartX() == 0 && route.getStartY() == 0) {
            route.setStartX((float) pt.getX());
            route.setStartY((float) pt.getY());
            props.updateStartingPos();
            model.localDatabase.onUpdate();
        } else {
            // TODO
        }
        repaintOverlay();
    }

    private int resolvePath(GameCoordinate[] coord, float[] angle) {
        AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
        if (route.getStartX() == 0 && route.getStartY() == 0) {
            return 0;
        } else {
            int i = 1;
            coord[0] = GameCoordinate.fromNormalizedPoint(getToolbar().getFieldView(), false, route.getStartX(),
                            route.getStartY());
            angle[0] = Float.NaN;
            for (AutonomousStep step : route.getSteps()) {
                switch (step.getType()) {
                    case Drive:
                        // TODO
                        ++i;
                        break;
                    case Turn:
                        // TODO
                        break;
                    default:
                        break;
                }
            }
            return i;
        }
    }

    @Override
    public void paintOverlay(Graphics g) {
        g.setColor(getForeground());
        GameCoordinate[] coord = new GameCoordinate[1];
        float[] angle = new float[1];
        switch (resolvePath(coord, angle)) {
            case 0:
                break;
            case 1:
                Point start = coord[0].getPixels();
                Point mouse = this.mouse.getPixels();
                g.drawLine((int) start.getX(), (int) start.getY(), (int) mouse.getX(), (int) mouse.getY());
                break;
            default:
                // TODO
                break;
        }
    }

    public DriveTool(GeneratorModel model, PropertiesBar props) {
        super(ICON, model);
        this.props = props;
        setForeground(Color.BLUE);
    }
}
