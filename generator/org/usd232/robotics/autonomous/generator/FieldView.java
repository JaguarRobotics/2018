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

public class FieldView extends Container {
    private static final long serialVersionUID = -6766487415290825938L;
    private Image             image;
    private AffineTransform   imageTransformation;

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

    @Override
    public void paint(Graphics g) {
        paintBackground(g);
        super.paint(g);
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

    public FieldView() {
        setBackground(Color.BLACK);
        image = new ImageIcon(
                        FieldView.class.getResource("/org/usd232/robotics/autonomous/generator/resources/field.png"))
                                        .getImage();
        imageTransformation = new AffineTransform();
        resetTransformation();
    }
}
