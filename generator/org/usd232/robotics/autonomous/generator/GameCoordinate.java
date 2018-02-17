package org.usd232.robotics.autonomous.generator;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class GameCoordinate {
    private FieldView field;
    private boolean   delta;
    private Point2D   point2d;
    private Point     point;

    public Point getPixels() {
        if (point2d != null) {
            try {
                Point2D px;
                if (delta) {
                    px = field.getTransformationRO().deltaTransform(point2d, null);
                } else {
                    px = field.getTransformationRO().transform(point2d, null);
                }
                Dimension image = field.getImageSize();
                Point2D imagePx = field.getTransformationRO()
                                .transform(new Point2D.Double(image.getWidth(), image.getHeight()), null);
                Point offset = delta ? new Point(0, 0) : field.getOffsetTranslation();
                Point pt = new Point((int) (px.getX() * imagePx.getX() + offset.getX()),
                                (int) (px.getY() * imagePx.getY() + offset.getY()));
                System.out.printf("Transforming normal point %c%f, %f%c to pixel point %c%f, %f%c\n", delta ? '<' : '(',
                                point2d.getX(), point2d.getY(), delta ? '>' : ')', delta ? '<' : '(', pt.getX(),
                                pt.getY(), delta ? '>' : ')');
                return pt;
            } catch (Exception ex) {
                throw new UnsupportedOperationException(ex);
            }
        } else if (point != null) {
            System.out.printf("Passing through pixel point %c%f, %f%c\n", delta ? '<' : '(', point.getX(), point.getY(),
                            delta ? '>' : ')');
            return new Point(point.x, point.y);
        } else {
            throw new IllegalStateException("Coordinate is invalid");
        }
    }

    public Point2D getNormalizedPoint() {
        if (point2d != null) {
            System.out.printf("Passing through normal point %c%f, %f%c\n", delta ? '<' : '(', point2d.getX(),
                            point2d.getY(), delta ? '>' : ')');
            return new Point2D.Double(point2d.getX(), point2d.getY());
        } else if (point != null) {
            try {
                Dimension image = field.getImageSize();
                Point offset = delta ? new Point(0, 0) : field.getOffsetTranslation();
                Point2D pt = new Point2D.Double((point.getX() - offset.getX()) / image.width,
                                (point.getY() - offset.getY()) / image.height);
                AffineTransform matrix = field.getTransformationRO().createInverse();
                if (delta) {
                    pt = matrix.deltaTransform(pt, null);
                } else {
                    pt = matrix.transform(pt, null);
                }
                System.out.printf("Transforming pixel point %c%f, %f%c to normal point %c%f, %f%c\n", delta ? '<' : '(',
                                point.getX(), point.getY(), delta ? '>' : ')', delta ? '<' : '(', pt.getX(), pt.getY(),
                                delta ? '>' : ')');
                return pt;
            } catch (Exception ex) {
                throw new UnsupportedOperationException(ex);
            }
        } else {
            throw new IllegalStateException("Coordinate is invalid");
        }
    }

    public static GameCoordinate fromPixels(FieldView field, boolean delta, int x, int y) {
        return new GameCoordinate(field, delta, null, new Point(x, y));
    }

    public static GameCoordinate fromNormalizedPoint(FieldView field, boolean delta, double x, double y) {
        return new GameCoordinate(field, delta, new Point2D.Double(x, y), null);
    }

    private GameCoordinate(FieldView field, boolean delta, Point2D point2d, Point point) {
        this.field = field;
        this.delta = delta;
        this.point2d = point2d;
        this.point = point;
    }
}
