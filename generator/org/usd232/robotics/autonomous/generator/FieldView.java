package org.usd232.robotics.autonomous.generator;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class FieldView extends Container {
    private static final long serialVersionUID = -6766487415290825938L;
    private Image             image;
    private double            x;
    private double            y;
    private double            scale;
    private double            rotation;

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
        // Apply transformations
        width *= this.scale;
        height *= this.scale;
        x += this.x * getWidth();
        y += this.y * getHeight();
        // Draw
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.rotate(rotation * Math.PI, getWidth()/2, getHeight()/2);
        g2d.drawImage(image, x, y, width, height, this);
    }

    @Override
    public void paint(Graphics g) {
        paintBackground(g);
        super.paint(g);
    }

    public void resetScale() {
        x = 0;
        y = 0;
        scale = 1;
    }

    public double getImageX() {
        return x;
    }

    public void setImageX(double x) {
        this.x = x;
        repaint();
    }

    public double getImageY() {
        return y;
    }

    public void setImageY(double y) {
        this.y = y;
        repaint();
    }

    public double getImageScale() {
        return scale;
    }

    public void setImageScale(double scale) {
        this.scale = scale;
        repaint();
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation += rotation;
        repaint();
    }

    public FieldView() {
        setBackground(Color.BLACK);
        image = new ImageIcon(
                        FieldView.class.getResource("/org/usd232/robotics/autonomous/generator/resources/field.png"))
                                        .getImage();
        resetScale();
    }
}
