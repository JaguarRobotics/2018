package org.usd232.robotics.autonomous.generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class FieldView extends Container {
    private static final long serialVersionUID = -6766487415290825938L;
    private Image             image;
    private AffineTransform   imageTransformation;

    public void paintBackground(Graphics g) {
        // Handle resizing windows
        Graphics2D g2d = (Graphics2D) g;
        double maxWidth = ((double) getWidth()) / (double) image.getWidth(this);
        double maxHeight = ((double) getHeight()) / (double) image.getHeight(this);
        double scale = Math.min(maxWidth, maxHeight);
        int width = (int) (image.getWidth(this) * scale);
        int height = (int) (image.getHeight(this) * scale);
        int x = (getWidth() - width) / 2;
        int y = (getHeight() - height) / 2;
        // Draw background
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        // Apply transformations
        AffineTransform transform = g2d.getTransform();
        AffineTransform realTransform = new AffineTransform(transform);
        realTransform.concatenate(imageTransformation);
        g2d.setTransform(realTransform);
        // Draw
        g2d.drawImage(image, x, y, width, height, this);
        // Restore the old matrix so nothing bad happens
        g2d.setTransform(transform);
    }

    @Override
    public void paint(Graphics g) {
        paintBackground(g);
        super.paint(g);
    }

    public AffineTransform getTransformation() {
        repaint();
        return imageTransformation;
    }

    public FieldView() {
        setBackground(Color.BLACK);
        image = new ImageIcon(
                        FieldView.class.getResource("/org/usd232/robotics/autonomous/generator/resources/field.png"))
                                        .getImage();
        imageTransformation = new AffineTransform();
        imageTransformation.setToIdentity();
    }
}
