package org.usd232.robotics.autonomous.generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import org.usd232.robotics.autonomous.AutonomousRoute;
import org.usd232.robotics.autonomous.AutonomousStep;
import org.usd232.robotics.autonomous.DriveParameter;
import org.usd232.robotics.autonomous.TurnParameter;
import org.usd232.robotics.autonomous.generator.model.GeneratorModel;
import org.usd232.robotics.autonomous.generator.tools.Tool;
import org.usd232.robotics.autonomous.generator.tools.Toolbar;

public class FieldView extends Container {
    private static final long serialVersionUID = -6766487415290825938L;
    private Image             image;
    private AffineTransform   imageTransformation;
    private Toolbar           toolbar;
    private GeneratorModel    model;

    public Dimension getImageSize() {
        double maxWidth = ((double) getWidth()) / (double) image.getWidth(this);
        double maxHeight = ((double) getHeight()) / (double) image.getHeight(this);
        double scale = Math.min(maxWidth, maxHeight);
        return new Dimension((int) (image.getWidth(this) * scale), (int) (image.getHeight(this) * scale));
    }

    public Point getOffsetTranslation() {
        Point imageSize = GameCoordinate.fromNormalizedPoint(this, true, 1, 1).getPixels();
        System.out.printf("Starting to paint the field with a viewport size of (%d, %d) and image size of (%f, %f)\n",
                        getWidth(), getHeight(), imageSize.getX(), imageSize.getY());
        int x = (getWidth() - imageSize.x) / 2;
        int y = (getHeight() - imageSize.y) / 2;
        return new Point(x, y);
    }

    public void paintBackground(Graphics g) {
        // Handle resizing windows
        Point imageSize = GameCoordinate.fromNormalizedPoint(this, true, 1, 1).getPixels();
        System.out.printf("Starting to paint the field with a viewport size of (%d, %d) and image size of (%f, %f)\n",
                        getWidth(), getHeight(), imageSize.getX(), imageSize.getY());
        Point offset = getOffsetTranslation();
        // Draw background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        // Apply transformations
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform transform = g2d.getTransform();
        AffineTransform realTransform = new AffineTransform(transform);
        realTransform.translate(offset.x, offset.y);
        realTransform.scale(imageSize.x, imageSize.y);
        realTransform.concatenate(imageTransformation);
        g2d.setTransform(realTransform);
        // Draw
        g.drawImage(image, 0, 0, 1, 1, this);
        // Restore the old matrix so nothing bad happens
        g2d.setTransform(transform);
    }

    public void paintToolOverlay(Graphics g) {
        if (toolbar != null) {
            Tool tool = toolbar.getSelectedTool();
            if (tool != null) {
                tool.paintOverlay(g);
            }
        }
    }

    public void paintOverlay(Graphics g) {
        g.setColor(getForeground());
        if (!model.versionListView.isSelectionEmpty()) {
            AutonomousRoute route = model.versionList.getRawElementAt(model.versionListView.getSelectedIndex());
            double x = route.getStartX();
            double y = route.getStartY();
            double angle = route.getStartAngle();
            for (AutonomousStep step : route.getSteps()) {
                Point pt = GameCoordinate.fromNormalizedPoint(this, false, x, y).getPixels();
                g.fillArc(pt.x, pt.y, 10, 10, 0, (int) Math.PI * 2);
                switch (step.getType()) {
                    case Drive:
                        double distance = ((DriveParameter) step.getGenericParameter()).getDistance();
                        Point start = GameCoordinate.fromNormalizedPoint(this, false, (float) x, (float) y).getPixels();
                        x += Math.cos(angle) * distance;
                        y += Math.sin(angle) * distance;
                        Point end = GameCoordinate.fromNormalizedPoint(this, false, (float) x, (float) y).getPixels();
                        g.drawLine(start.x, start.y, end.x, end.y);
                        break;
                    case Turn:
                        angle -= ((TurnParameter) step.getGenericParameter()).getAngle();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        paintBackground(g);
        super.paint(g);
        paintToolOverlay(g);
        paintOverlay(g);
    }

    AffineTransform getTransformationRO() {
        return imageTransformation;
    }

    public AffineTransform getTransformation() {
        repaint();
        return getTransformationRO();
    }

    public void resetTransformation() {
        AffineTransform matrix = getTransformation();
        matrix.setToIdentity();
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public FieldView(GeneratorModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        setForeground(Color.MAGENTA);
        image = new ImageIcon(
                        FieldView.class.getResource("/org/usd232/robotics/autonomous/generator/resources/field.png"))
                                        .getImage();
        imageTransformation = new AffineTransform();
        resetTransformation();
    }
}
