package org.usd232.robotics.autonomous.generator.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.DriveParameter;
import org.usd232.robotics.autonomous.TurnParameter;
import org.usd232.robotics.autonomous.generator.GameCoordinate;
import org.usd232.robotics.autonomous.generator.PropertiesBar;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;

public class DriveTool extends DrawingTool {
    private static final long   serialVersionUID = -7087329524983618906L;
    private static final String ICON             = "linear_scale";
    private static final double ANGLE_SNAP       = Math.PI / 12;
    private PropertiesBar       props;
    private GameCoordinate      mouse;

    @Override
    public void onHover(GameCoordinate position, GameCoordinate change) {
        mouse = position;
        repaintOverlay();
    }

    public float getTargetDistance() {
        GameCoordinate[] lastCoord = new GameCoordinate[1];
        float[] angle = new float[1];
        resolvePath(lastCoord, angle);
        Point2D ptFinal = mouse.getNormalizedPoint();
        Point2D ptInitial = lastCoord[0].getNormalizedPoint();
        double dy = ptFinal.getY() - ptInitial.getY();
        double dx = ptFinal.getX() - ptInitial.getX();
        if (Math.abs(Math.atan2(dy, dx) - angle[0]) < ANGLE_SNAP) {
            return (float) (dx * Math.cos(angle[0]) + dy * Math.sin(angle[0]));
        } else {
            return Float.NaN;
        }
    }

    @Override
    public void onClick(GameCoordinate position) {
        AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
        GameCoordinate[] coord = new GameCoordinate[1];
        float[] angle = new float[1];
        repaintOverlay();
        switch (resolvePath(coord, angle)) {
            case 0: {
                Point2D pt = position.getNormalizedPoint();
                route.setStartX((float) pt.getX());
                route.setStartY((float) pt.getY());
                props.updateStartingPos();
                model.localDatabase.onUpdate();
                return;
            }
            case 1: {
                Point2D mouse = position.getNormalizedPoint();
                Point2D pt = coord[0].getNormalizedPoint();
                angle[0] = (float) Math.atan2(mouse.getY() - pt.getY(), mouse.getX() - pt.getX()) - angle[0];
                route.setStartAngle(angle[0]);
                break;
            }
            default:
                break;
        }
        float distance = getTargetDistance();
        if (Float.isNaN(distance)) {
            Point2D ptFinal = mouse.getNormalizedPoint();
            Point2D ptInitial = coord[0].getNormalizedPoint();
            TurnParameter param = new TurnParameter();
            double dx = ptFinal.getX() - ptInitial.getX();
            double dy = ptFinal.getY() - ptInitial.getY();
            float angleDiff = (float) (Math.atan2(dy, dx) - angle[0]);
            param.setAngle(angleDiff);
            angle[0] += angleDiff;
            model.stepList.add(null).setParameter(param);
            distance = (float) Math.sqrt(dx * dx + dy * dy);
        }
        {
            DriveParameter param = new DriveParameter();
            param.setDistance(distance);
            model.stepList.add(null).setParameter(param);
        }
    }

    private int resolvePath(GameCoordinate[] coord, float[] angle) {
        AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
        if (route.getStartX() == 0 && route.getStartY() == 0) {
            return 0;
        } else {
            int i = 1;
            coord[0] = GameCoordinate.fromNormalizedPoint(getToolbar().getFieldView(), false, route.getStartX(),
                            route.getStartY());
            angle[0] = route.getStartAngle();
            for (AutonomousStep step : route.getSteps()) {
                switch (step.getType()) {
                    case Drive:
                        Point2D pt = coord[0].getNormalizedPoint();
                        float distance = ((DriveParameter) step.getGenericParameter()).getDistance();
                        coord[0] = GameCoordinate.fromNormalizedPoint(getToolbar().getFieldView(), false,
                                        pt.getX() + Math.cos(angle[0]) * distance,
                                        pt.getY() + Math.sin(angle[0]) * distance);
                        ++i;
                        break;
                    case Turn:
                        angle[0] += ((TurnParameter) step.getGenericParameter()).getAngle();
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
            case 1: {
                Point start = coord[0].getPixels();
                Point end = mouse.getPixels();
                g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
                break;
            }
            default: {
                float distance = getTargetDistance();
                Point start = coord[0].getPixels();
                if (Float.isNaN(distance)) {
                    Point end = mouse.getPixels();
                    g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
                } else {
                    Point2D start2d = coord[0].getNormalizedPoint();
                    Point end = GameCoordinate.fromNormalizedPoint(getToolbar().getFieldView(), false,
                                    start2d.getX() + Math.cos(angle[0]) * distance,
                                    start2d.getY() + Math.sin(angle[0]) * distance).getPixels();
                    g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
                }
                break;
            }
        }
    }

    @Override
    public void onDisable() {
        repaintOverlay();
    }

    public DriveTool(GeneratorModel model, PropertiesBar props) {
        super(ICON, model);
        this.props = props;
        setForeground(Color.BLUE);
    }
}
